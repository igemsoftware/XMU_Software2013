package protein;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Algorithm2 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("输入序列：");
		String seq = in.nextLine();
		System.out.println("序列长度：" + seq.length());
		in.close();
		Algorithm2 al = new Algorithm2(seq, "ECOLI", 200, 200, 0.1, 0.1);
		String[] result = al.getFinalCodon();
		String codons[] = al.getSerialCodons();
		int countOfPrimaryCodons[] = al.getCountOfPrimaryCodons();
		int countOfFinalCodons[] = al.getCountOfFinalCodons();
		double proportionOfPrimaryCodons[] = al.getProportionOfPrimaryCodons();
		double proportionOfFinalCodons[] = al.getProportionOfFinalCodons();
		try {
			PrintWriter p=new PrintWriter("蛋白质结果2.txt");
			p.println("输入序列如下：\n"+seq);
			p.println("输出序列如下：\n");
			for (int i = 0; i < result.length; i++)
				p.print(result[i]);
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

	public Algorithm2(String seq, String cell, int population, int daishu,
			double crossRate, double varRate) {
		Seq = new String[seq.length() / 3];
		IntOfSeq = new int[seq.length() / 3];
		for (int i = 0; i < seq.length() / 3; i++) {
			Seq[i] = seq.substring(3 * i, 3 * i + 3);
			IntOfSeq[i] = Codondb.parseInt(Seq[i]);
		} // 对Seq和IntOfSeq初始化；
		synCodonArray = Codondb.selectTable();
		if(!cell.equals(Cell.ANIGER))
			this.nObsHigh = NHighdb.selectTable(Cell.valueOf(cell));
		else
			this.nObsHigh = NHighdb.selectTable(Cell.ECOLI);
		this.single_nObsHigh = NHighdb.selectTableOfSingleCodon(Cell
				.valueOf(cell));
		int[] result = this
				.makeGenerations(this.getPopulation(this.IntOfSeq, population),
						daishu, crossRate, varRate);
		finalCodon = new String[result.length];
		for (int i = 0; i < finalCodon.length; i++)
			finalCodon[i] = NHighdb.parseStr(result[i]);
		
		//以下用在分析程序上
		this.countOfPrimaryCodons = new int[64];
		this.proportionOfPrimaryCodons = new double[64];
		this.countOfFinalCodons = new int[64];
		this.proportionOfFinalCodons = new double[64];
		

			for(int j=0; j<this.IntOfSeq.length; j++)
					this.countOfPrimaryCodons[IntOfSeq[j]]++;
		
		for(int i=0; i<64; i++)
			this.proportionOfPrimaryCodons[i] = (double)this.countOfPrimaryCodons[i]/IntOfSeq.length;
		

			for(int j=0; j<result.length; j++)
					this.countOfFinalCodons[result[j]]++;
		
		for(int i=0; i<64; i++)
			this.proportionOfFinalCodons[i] = (double)this.countOfFinalCodons[i]/result.length;
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

	/* 以下用遗传算法实现 */
	// 根据氨基酸序列得到一个种群
	private int[][] getPopulation(String[] aordCodon, int length) {
		int[] ordCodon = new int[aordCodon.length];
		for (int i = 0; i < ordCodon.length; i++)
			ordCodon[i] = Codondb.parseInt(aordCodon[i]); // 将原来的序列转化成int型数据
		int[][] pop = new int[length][ordCodon.length]; // 用于保存生成的种群，序列全部用int型表示
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < ordCodon.length; j++) {
				Random r = new Random();
				int[] str = getSynCodon(ordCodon[j]); // 从数据库中读取同义密码子
				pop[i][j] = str[r.nextInt(str.length)];
			}
		}
		return pop;
	}

	// 下面产生种群时直接输入int数组
	private int[][] getPopulation(int[] aordCodon, int length) {
		int[] ordCodon = new int[aordCodon.length];
		for (int i = 0; i < ordCodon.length; i++)
			ordCodon[i] = aordCodon[i]; // 将原来的序列转化成int型数据
		int[][] pop = new int[length][ordCodon.length]; // 用于保存生成的种群，序列全部用int型表示
		for (int i = 0; i < length; i++) {
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
			newSeq_FitCombi[i] = getMOCO(newSeq[i]);
		for (int i = 0; i < ordCodon.length; i++)
			ordCodon_FitCombi[i] = getMOCO(ordCodon[i]);
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
				if (getMOCO(seq) > getMOCO(ordCodon[i]))
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
			if (max < getMOCO(ordCodon[i])) {
				max = getMOCO(ordCodon[i]);
				maxPoint = i; // 保存最大位点
			}
		return ordCodon[maxPoint];
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

	// 此函数返回某个密码子对应的氨基酸在序列中的个数
	private int getTheta_A_1_j(int[] seq, int codon) {
		int count = 0;
		int[] synCodon = getSynCodon(codon);
		for (int element : synCodon)
			// 下面这个循环找出分别找出不同的同义密码子在序列中的个数，然后相加
			for (int i = 0; i < seq.length; i++)
				if (element == seq[i])
					count++;
		return count;
	}

	// 此函数返回某个密码子在序列中出现次数
	private int getTheta_C_1_k(int[] seq, int codon) {
		int count = 0;
		for (int i = 0; i < seq.length; i++)
			if (codon == seq[i])
				count++;
		return count;
	}

	// 此函数返回宿主细胞中某密码子对应氨基酸出现次数
	private int getTheta_A_0_j(int codon) {
		int count = 0;
		int[] synCodon = getSynCodon(codon);
		for (int i = 0; i < synCodon.length; i++)
			count += this.single_nObsHigh[synCodon[i]];
		return count;
	}

	// 此函数返回宿主细胞中某密码子出现次数
	private int getTheta_C_0_k(int codon) {
		return this.single_nObsHigh[codon];
	}

	// 此函数返回用户输入序列中p_1_k
	private double getP_1_k(int[] seq, int codon) {
		int fenmu = 0; // 分母
		fenmu = getTheta_A_1_j(seq, codon); // TODO此处分母如果是0？
		if (fenmu == 0)
			return 0;
		return getTheta_C_1_k(seq, codon) / (double) fenmu;
	}

	// 此函数返回宿主细胞p_0_k
	private double getP_0_k(int codon) {
		int fenmu = 0;
		fenmu = getTheta_A_0_j(codon);
		if (fenmu == 0)
			return 0;
		return getTheta_C_0_k(codon) / (double) fenmu;
	}

	// 获得ICU
	public double getICU(int[] seq) {
		double PhiICU = 0.0;
		for (int i = 0; i < 64; i++)
			PhiICU += Math.abs(getP_0_k(i) - getP_1_k(seq, i));
		PhiICU /= 64;
		return PhiICU;
	}

	// 接下来是CCO
	// 首先得到用户序列中某密码子对应氨基酸出现次数
	private int getTheta_AA_1_j(int[] seq, int codon1, int codon2) {
		int count = 0;
		int[] synCodon1 = getSynCodon(codon1);
		int[] synCodon2 = getSynCodon(codon2);
		for (int i = 0; i < synCodon1.length; i++)
			for (int j = 0; j < synCodon2.length; j++)
				for (int k = 0; k < seq.length - 1; k++)
					if (synCodon1[i] == seq[k] && synCodon2[j] == seq[k + 1])
						count++;
		return count;
	}

	// 然后得到用户序列中某密码子对出现次数
	private int getTheta_CC_1_k(int[] seq, int codon1, int codon2) {
		int count = 0;
		for (int i = 0; i < seq.length - 1; i++)
			if (codon1 == seq[i] && codon2 == seq[i + 1])
				count++;
		return count;
	}

	// 得到宿主细胞某密码子对对应的氨基酸出现的次数
	private int getTheta_AA_0_j(int codon1, int codon2) {
		int count = 0;
		int[] synCodon1 = getSynCodon(codon1);
		int[] synCodon2 = getSynCodon(codon2);
		for (int i = 0; i < synCodon1.length; i++)
			for (int j = 0; j < synCodon2.length; j++)
				count += this.nObsHigh[synCodon1[i]][synCodon2[j]];
		return count;
	}

	// 得到宿主细胞中某密码子对出现次数
	private int getTheta_CC_0_k(int codon1, int codon2) {
		return this.nObsHigh[codon1][codon2];
	}

	// 得到q_1_k，用户输入的序列
	private double getQ_1_k(int[] seq, int codon1, int codon2) {
		if (getTheta_AA_1_j(seq, codon1, codon2) == 0)
			return 0;
		return getTheta_CC_1_k(seq, codon1, codon2)
				/ (double) getTheta_AA_1_j(seq, codon1, codon2);
	}

	// 得到q_0_k，宿主细胞的序列
	private double getQ_0_k(int codon1, int codon2) {
		if (getTheta_AA_0_j(codon1, codon2) == 0)
			return 0;
		return getTheta_CC_0_k(codon1, codon2)
				/ (double) getTheta_AA_0_j(codon1, codon2);
	}

	// 得到CCO
	public double getCCO(int[] seq) {
		double PhiCC = 0.0;
		for (int i = 0; i < 64; i++)
			for (int j = 0; j < 64; j++)
				PhiCC += getQ_0_k(i, j) / getQ_1_k(seq, i, j);
		PhiCC /= 3904;
		return PhiCC;
	}

	// 获得MOCO，ICU和CCO的权重分别取0.5
	public double getMOCO(int[] seq) {
		return 0.5 * getICU(seq) + 0.5 * getCCO(seq);
	}

	// 域
	private String Seq[];
	private int IntOfSeq[]; // 用于保存Seq每个密码子转换成数字之后的值
	private int synCodonArray[];
	private int nObsHigh[][];
	private int single_nObsHigh[];
	private String finalCodon[];
	// 以下四个数组分别保存输入序列各密码子数量、比例和输出序列各密码子数量、比例，用作分析
	private int countOfPrimaryCodons[];
	private double proportionOfPrimaryCodons[];
	private int countOfFinalCodons[];
	private double proportionOfFinalCodons[];
	// 还有一个也是用作分析，用于保存密码子序列
	private String serialCodons[];
}
