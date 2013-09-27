package protein;

//为尽量加快运算速度，将数据库中的数据一次取出并保存在内存中，计算时都按被转化成的int型计算
//注意程序很多关键地方用到Codondb数据库的parseInt方法和parseStr方法，注意随时保持一致

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Algorithm {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("输入字符串");
		String str = in.nextLine();
		in.close();
		System.out.println("序列长度：" + str.length());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println("开始时间：" + df.format(new Date()));
		Algorithm al = new Algorithm(str, Cell.ANIGER,200,200,0.1,0.1);
		String[] codon = al.getFinalCodon();
		String codons[] = al.getSerialCodons();
		int countOfPrimaryCodons[] = al.getCountOfPrimaryCodons();
		int countOfFinalCodons[] = al.getCountOfFinalCodons();
		double proportionOfPrimaryCodons[] = al.getProportionOfPrimaryCodons();
		double proportionOfFinalCodons[] = al.getProportionOfFinalCodons();
		System.out.println("\n结束时间：" + df.format(new Date()));
		try {
			PrintWriter p=new PrintWriter("蛋白质结果.txt");
			p.println("输入序列如下：\n"+str);
			p.println("输出序列如下：\n");
			for (int i = 0; i < codon.length; i++)
				p.print(codon[i]);
			p.println();
			p.println("密码子\t初始数量\t初始比例\t最终数量\t最终比例");
			for(int i=0; i<codons.length; i++)
				p.println(codons[i]+"\t"+countOfPrimaryCodons[i]+"\t"+proportionOfPrimaryCodons[i]+"\t"+countOfFinalCodons[i]+"\t"+proportionOfFinalCodons[i]);
			p.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 第二个构造函数，将序列分开
		public Algorithm(String seq, String cell,int population, int daishu, double crossRate, double varRate) {
			/*
			 * if(seq.length()%3!=0) { System.out.println("输入序列长度不是3的倍数！"); return;
			 * }
			 */
			Seq = new String[seq.length() / 3];
			IntOfSeq = new int[seq.length() / 3];
			for (int i = 0; i < seq.length() / 3; i++) {
				Seq[i] = seq.substring(3 * i, 3 * i + 3);
				IntOfSeq[i] = Codondb.parseInt(Seq[i]);
			} // 对Seq和IntOfSeq初始化；
			synCodonArray = Codondb.selectTable();
			this.cell = Cell.valueOf(cell);
			if(!this.cell.equals(Cell.ANIGER))
				this.nObsHigh = NHighdb.selectTable(Cell.valueOf(cell));
			else
				this.w = Wdb.selectTable();
			this.rScAll = AllAndHighdb.selectAllFromTable();
			this.rScHigh = AllAndHighdb.selectHighFromTable();
			int codon[] = this.makeGenerations(this.getPopulation(this.Seq, population), daishu, crossRate, varRate);			//遗传
			finalCodon = new String[codon.length];
			for(int i=0; i<finalCodon.length; i++)
				finalCodon[i] = Codondb.parseStr(codon[i]);		//转化
			
			//以下用在分析程序上
			this.countOfPrimaryCodons = new int[64];
			this.proportionOfPrimaryCodons = new double[64];
			this.countOfFinalCodons = new int[64];
			this.proportionOfFinalCodons = new double[64];
			

				for(int j=0; j<this.IntOfSeq.length; j++)
						this.countOfPrimaryCodons[IntOfSeq[j]]++;
			
			for(int i=0; i<64; i++)
				this.proportionOfPrimaryCodons[i] = (double)this.countOfPrimaryCodons[i]/IntOfSeq.length;
			

				for(int j=0; j<codon.length; j++)
						this.countOfFinalCodons[codon[j]]++;
			
			for(int i=0; i<64; i++)
				this.proportionOfFinalCodons[i] = (double)this.countOfFinalCodons[i]/codon.length;
			//保存密码子序列顺序数组的初始化
			this.serialCodons = new String[64];
			for(int i=0; i<64; i++)
				this.serialCodons[i] = Codondb.parseStr(i);
		}
	
	// 构造函数，将序列分开
	public Algorithm(String seq, Cell cell,int population, int daishu, double crossRate, double varRate) {
		/*
		 * if(seq.length()%3!=0) { System.out.println("输入序列长度不是3的倍数！"); return;
		 * }
		 */
		Seq = new String[seq.length() / 3];
		IntOfSeq = new int[seq.length() / 3];
		for (int i = 0; i < seq.length() / 3; i++) {
			Seq[i] = seq.substring(3 * i, 3 * i + 3);
			IntOfSeq[i] = Codondb.parseInt(Seq[i]);
		} // 对Seq和IntOfSeq初始化；
		this.cell = cell;
		synCodonArray = Codondb.selectTable();
		if(!this.cell.equals(Cell.ANIGER))
			this.nObsHigh = NHighdb.selectTable(cell);
		else
			this.w = Wdb.selectTable();
		this.rScAll = AllAndHighdb.selectAllFromTable();
		this.rScHigh = AllAndHighdb.selectHighFromTable();
		int codon[] = this.makeGenerations(this.getPopulation(this.Seq, population), daishu, crossRate, varRate);			//遗传
		finalCodon = new String[codon.length];
		for(int i=0; i<finalCodon.length; i++)
			finalCodon[i] = Codondb.parseStr(codon[i]);		//转化
		// 下面这些构造好了可以删掉，这是第一种方法，没有用到遗传算法
		/*
		 * int c[] = new int[Seq.length]; c=this.getFit(IntOfSeq); for(int i=0;
		 * i<c.length; i++) System.out.print(Codondb.parseStr(c[i]));
		 */

		//以下用在分析程序上
		this.countOfPrimaryCodons = new int[64];
		this.proportionOfPrimaryCodons = new double[64];
		this.countOfFinalCodons = new int[64];
		this.proportionOfFinalCodons = new double[64];
		

			for(int j=0; j<this.IntOfSeq.length; j++)
					this.countOfPrimaryCodons[IntOfSeq[j]]++;
		
		for(int i=0; i<64; i++)
			this.proportionOfPrimaryCodons[i] = (double)this.countOfPrimaryCodons[i]/IntOfSeq.length;
		

			for(int j=0; j<codon.length; j++)
					this.countOfFinalCodons[codon[j]]++;
		
		for(int i=0; i<64; i++)
			this.proportionOfFinalCodons[i] = (double)this.countOfFinalCodons[i]/codon.length;
		//保存密码子序列顺序数组的初始化
		this.serialCodons = new String[64];
		for(int i=0; i<64; i++)
			this.serialCodons[i] = Codondb.parseStr(i);
	}

	public String[] getFinalCodon() {
		return finalCodon;
	}

	public int[] getCountOfPrimaryCodons() {
		return countOfPrimaryCodons;
	}

	public double[] getProportionOfPrimaryCodons() {
		return proportionOfPrimaryCodons;
	}

	public int[] getCountOfFinalCodons() {
		return countOfFinalCodons;
	}

	public double[] getProportionOfFinalCodons() {
		return proportionOfFinalCodons;
	}

	public String[] getSerialCodons() {
		return serialCodons;
	}

	/* 以下均被改成是用int行计算 */
	// 获取n_obs_high
	private int getNObsHigh(int ci, int cj) {
		if (ci > 63 || cj > 63) {
			System.out.println("wrong at Algorithm.getNObsHigh about length!!");
			return 0;
		}
		return this.nObsHigh[ci][cj];
	}

	// 获取某个密码子在序列中比例,即r_sc_all
	private double getRScAll(int str) // 这个函数是对AllAndHighdb数据库进行操作，已经将调用到的数据库的函数改了
	{
		return this.rScAll[str] / 100.0;
	}

	// 获取某个密码子在序列中的比例，是r_sc_high
	private double getRScHigh(int str) // 这个函数要改，用NHighdb里的数据，将调用到的函数直接改为int，已改
	{
		return this.rScHigh[str] / 100.0;
	}

	// 获取同义密码子，在构造函数中即取出数据库中的所有数据，保存在synCodonArray数组中，以不小于64的数字为分隔符
	private int[] getSynCodon(int seq) {
		int point = 0; // point用于保存此密码子在同义密码子数组synCodonArray中的位点
		for (int i = 0; i < this.synCodonArray.length; i++)
			if (seq == synCodonArray[i]) {
				point = i;
				break;
			}
		int startPoint = point; // startPoint用于保存此密码子的同义密码子的启示位点
		while (this.synCodonArray[startPoint - 1] < 64)
			startPoint--;
		int endPoint = point; // endPoint用于保存此密码子的同义密码子的结束位点
		while (endPoint < synCodonArray.length
				&& this.synCodonArray[endPoint] < 64)
			endPoint++;
		int[] synCodon = new int[endPoint - startPoint]; // 这个数组即为要返回的同义密码子数组
		for (int i = startPoint; i < endPoint; i++)
			synCodon[i - startPoint] = this.synCodonArray[i];
		return synCodon;
	}

	// 获取n_exp_combi
	private double getNExpCombi(int ci, int cj) {
		int synCiCodon[];
		synCiCodon = getSynCodon(ci);
		int synCjCodon[];
		synCjCodon = getSynCodon(cj);
		int nObsHighSum = 0;
		for (int i = 0; i < synCiCodon.length; i++)
			for (int j = 0; j < synCjCodon.length; j++)
				nObsHighSum += getNObsHigh(synCiCodon[i], synCjCodon[j]);
		return getRScAll(ci) * getRScAll(cj) * nObsHighSum;
	}

	// 获取w
	private double getW(int ci, int cj) {
		if(this.cell == Cell.ANIGER)
			return this.w[ci][cj];
		double m = getNExpCombi(ci, cj);
		double n = getNObsHigh(ci, cj);
		if (m > n)
			return (m - n) / m;
		else
			return (m - n) / n;
	}

	// 获取r_sc_target
	private double getRScTarget(int ck) {
		return 2 * getRScHigh(ck) - getRScAll(ck);
	}

	// 获取r_sc_g，此处用到的并非是完整序列，而是序列分成的每三个一组的string数组
	private double getRScG(int ck, int g[]) {
		int count = 0;
		for (int i = 0; i < g.length; i++)
			if (g[i] == ck)
				count += 1;
		return count / 100.0;
	}

	// 获取fit_sc
	private double getFitSc(int c[]) {
		if (c.length != Seq.length) {
			System.out.println("wrong at Algorithm.getFitSc. c.length="
					+ c.length);
			return 0.0;
		}
		double halfResult = 0.0;
		for (int k = 0; k < c.length; k++)
			halfResult += getRScTarget(c[k]) - getRScG(c[k], c);
		return halfResult / c.length;
	}

	// 获取fit_cp
	private double getFitCp(int c[]) {
		double halfResult = 0.0;
		for (int k = 0; k < c.length - 1; k++) {
			// System.out.println("c[k]="+c[k]+"c[k+1]"+c[k+1]+"clength="+c.length+"k="+k);
			halfResult += getW(c[k], c[k + 1]);
		}
		return halfResult / (c.length - 1);
	}

	// 获取fit_combi
	private double getFitCombi(int c[]) {
		return getFitCp(c) / (this.Cpi + getFitSc(c));
	}

	/* 以下用遗传算法实现 */
	// 根据氨基酸序列得到一个种群，暂定长度200
	private int[][] getPopulation(String[] aordCodon, int population) {
		int[] ordCodon = new int[aordCodon.length];
		for (int i = 0; i < ordCodon.length; i++)
			ordCodon[i] = Codondb.parseInt(aordCodon[i]); // 将原来的序列转化成int型数据
		int[][] pop = new int[population][ordCodon.length]; // 用于保存生成的种群，序列全部用int型表示
		for (int i = 0; i < population; i++) {
			for (int j = 0; j < ordCodon.length; j++) {
				Random r = new Random();
				int[] str = getSynCodon(ordCodon[j]); // 从数据库中读取同义密码子
				pop[i][j] = str[r.nextInt(str.length)];
			}
		}
		return pop;
	}

	// 将两序列以一定交叉率交叉,两个间只交叉一次
	private int[][] makeCross(int[] pa, int[] ma) {
		if (pa.length != ma.length) {
			System.out.println("wrong at Algorithm.makeCross!");
			return null;
		}
		int[][] result = new int[2][pa.length];
		result[0] = (int[]) pa.clone();
		result[1] = (int[]) ma.clone();
		Random r = new Random();
		int point = r.nextInt(result.length);
		int tmp = 0;
		for (int i = point; i < result[0].length; i++) {
			tmp = result[0][i];
			result[0][i] = result[1][i];
			result[1][i] = tmp;
		}
		return result;
	}

	// 变异，只变异其中的一个密码子，变异为同义密码子，产生下一代的时候没用，而是直接实现，似乎是忘了。。。
	private int[] makeVariation(int[] ordCodon) {
		int newCodon[] = (int[]) ordCodon.clone();
		Random r = new Random();
		int point = r.nextInt(newCodon.length);
		Random r1 = new Random();
		newCodon[point] = getSynCodon(newCodon[point])[r1
				.nextInt(getSynCodon(newCodon[point]).length)]; // 这句是把point位变成同义密码子
		return newCodon;
	}

	// 变异一代
	private int[][] makeOneGeneration(int[][] ordCodon, double crossRate,
			double varRate) {
		double P_k[] = new double[ordCodon.length]; // P_k用于保存每个个体产生的随机数
		for (int i = 0; i < ordCodon.length; i++) {

			Random r = new Random();
			P_k[i] = r.nextDouble();
		}

		int count = 0; // 此处count用于保存小于crossRate的个数
		for (int i = 0; i < ordCodon.length; i++)
			if (P_k[i] < crossRate)
				count += 1;

		int[] crossPoint = new int[count]; // corssPoint用于保存小于crossRate的位点
		int k = 0; // 保存前一次crossPoint已经赋值到哪里了
		for (int i = 0; i < P_k.length; i++) // 注意此处可能溢出！！！
		{
			if (P_k[i] < crossRate) {
				crossPoint[k] = i;
				k++;
			}
		}

		// 打印，调试用
		/*
		 * for(int i=0; i<ordCodon.length; i++) for(int j=0;
		 * j<ordCodon[0].length; j++) System.out.print(ordCodon[i][j]+"\t");
		 */

		// 保存交叉之后新生成的序列，交叉类似这样：第一个跟第二个交叉，第二个再跟第三个交叉，直到最后两个，最后一个没有跟第一个交叉
		int[][] newSeq = new int[2 * (count - 1)][ordCodon[0].length];
		for (int i = 0; i < newSeq.length / 2; i++) {
			int seq[][] = new int[2][ordCodon[0].length];
			seq = makeCross(ordCodon[crossPoint[i]],
					ordCodon[crossPoint[i + 1]]);
			newSeq[2 * i] = seq[0];
			newSeq[2 * i + 1] = seq[1];
		}

		// 打印，调试用
//		System.out.println("newSeq如下：");
//		for (int i = 0; i < newSeq.length; i++) {
//			for (int j = 0; j < newSeq[0].length; j++)
//				System.out.print(newSeq[i][j] + "\t");
//			System.out.println();
//		}
//		System.out.println("newSeq.length=" + newSeq.length);

		// 貌似

		// 下面先从那200个跟交叉新生成的序列中取fit最大的200个，所用方法是将每个newSeq中的序列跟每个ordCodon中的序列比较
		// 先算出newSeq和ordCodon的FitCombi，结果分别是一个长为newSeq.length和ordCodon.length的数组
//		System.out.println("开始排列newSeq："
//				+ new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS")
//						.format(new Date()));
		double[] newSeq_FitCombi = new double[newSeq.length];
		double[] ordCodon_FitCombi = new double[ordCodon.length];
		for (int i = 0; i < newSeq.length; i++)
			newSeq_FitCombi[i] = getFitCombi(newSeq[i]);
		for (int i = 0; i < ordCodon.length; i++)
			ordCodon_FitCombi[i] = getFitCombi(ordCodon[i]);
		// 以上初始化完毕
		// 首先对newSeq进行从小到大的排列
//		System.out.println("开始排列newSeq："
//				+ new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS")
//						.format(new Date()));
		int[] tmp = new int[newSeq.length];
		double tmp_FitCombi = 0.0;
		for (int i = 0; i < newSeq.length; i++)
			for (int j = i; j < newSeq.length; j++)
				if (newSeq_FitCombi[i] > newSeq_FitCombi[j]) {
					// 先对newSeq_FitCombi进行交换
					tmp_FitCombi = newSeq_FitCombi[i];
					newSeq_FitCombi[i] = newSeq_FitCombi[j];
					newSeq_FitCombi[j] = tmp_FitCombi;

					// 再对newSeq进行交换
					tmp = (int[]) newSeq[i].clone();
					newSeq[i] = newSeq[j];
					newSeq[j] = (int[]) tmp.clone();
				}
//		System.out.println("newSeq排列完毕："
//				+ new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS")
//						.format(new Date()));
		// 下面即将newSeq中的每个FitCombi与ordCodon中的比较，替换
		for (int i = 0; i < newSeq.length; i++) {
			for (int j = 0; j < ordCodon.length; j++) {
				/*
				 * for(int m=0; m<newSeq[i].length; m++)
				 * System.out.print(newSeq[i][m]); System.out.println(); for(int
				 * n=0; n<ordCodon[j].length; n++)
				 * System.out.print(ordCodon[j][n]); System.out.println();
				 */
				if (newSeq_FitCombi[i] > ordCodon_FitCombi[j]) {
					tmp = (int[]) newSeq[i].clone();
					newSeq[i] = ordCodon[j];
					ordCodon[j] = tmp;
				}
			}
		}

		// 打印，调试用
//		System.out.println("交叉之后：");
//		for (int i = 0; i < ordCodon.length; i++)
//			for (int j = 0; j < ordCodon[0].length; j++)
//				System.out.print(ordCodon[i][j] + "\t");

		// 下面进行变异，变异过程中直接找出变异之前跟变异之后的最大
		for (int i = 0; i < ordCodon.length; i++) {
			Random r = new Random();
			if (r.nextDouble() < varRate) // 如果得到的随机数小于varRate，则进行变异
			{
				int[] seq = (int[]) ordCodon[i].clone();
				Random s = new Random();
				int sResult = s.nextInt(seq.length); // s用于产生变异位点的随机数，产生的结果保存在sResult
				Random t = new Random(); // 表示准备用哪个同义密码子
				seq[sResult] = getSynCodon(seq[sResult])[t
						.nextInt(getSynCodon(seq[sResult]).length)]; // 此处具体说就是得到sResult位点同义密码子，有多个同义密码子，同样也是随机产生一个
				if (getFitCombi(seq) > getFitCombi(ordCodon[i]))
					ordCodon[i] = seq;

				// System.out.println("变异过程："+new
				// SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS") .format(new
				// Date() ));
			}
		}

		return ordCodon;
	}

	// 接下来进行多代遗传
	private int[] makeGenerations(int[][] ordCodon, int daishu,
			double crossRate, double varRate) {
		for (int i = 0; i < daishu; i++)
			ordCodon = makeOneGeneration(ordCodon, crossRate, varRate);
		return getBest(ordCodon);
	}

	// 这个函数是找出ordCodon中最大数组
	private int[] getBest(int[][] ordCodon) {
		double max = 0.0;
		int maxPoint = 0;
		for (int i = 0; i < ordCodon.length; i++)
			if (max < getFitCombi(ordCodon[i])) {
				max = getFitCombi(ordCodon[i]);
				maxPoint = i; // 保存最大位点
			}
		return ordCodon[maxPoint];
	}

	/**
	 * @deprecated 此函数不建议使用
	 * @param k
	 * @param c
	 * @return
	 */
	// 将第k位替换成同义密码子找出最大
	private int[] getSingleFit(int k, int c[]) {
		int cl[] = c; // 此处cl数组用于保存上一次的c值
		int synCodon[] = getSynCodon(c[k - 1]);
		for (int i = 0; i < synCodon.length; i++) {
			cl[k - 1] = synCodon[i];
			if (getFitCombi(c) < getFitCombi(cl)) {
				c[k - 1] = cl[k - 1];
			}
		}
		return c;
	}

	/**
	 * @deprecated 此函数遍历所有可能情况，效率不高
	 * @param c
	 * @return
	 */
	// 进行从头到尾的遍历
	public int[] getFit(int c[]) {
		for (int i = 1; i <= c.length; i++)
			c = getSingleFit(i, c);
		return c;
	}

	// 域
	private Cell cell;
	private String Seq[];
	private int IntOfSeq[];
	private double Cpi;
	private int synCodonArray[];
	private int nObsHigh[][];
	private int rScAll[];
	private int rScHigh[];
	private String finalCodon[];
	//以下四个数组分别保存输入序列各密码子数量、比例和输出序列各密码子数量、比例，用作分析
	private int countOfPrimaryCodons[];
	private double proportionOfPrimaryCodons[];
	private int countOfFinalCodons[];
	private double proportionOfFinalCodons[];
	//还有一个也是用作分析，用于保存密码子序列
	private String serialCodons[];
	//下面这个名为w的数组，是为了保存A.niger的w矩阵用的，实际应用中可能为null，小心！
	private double w[][];
}
