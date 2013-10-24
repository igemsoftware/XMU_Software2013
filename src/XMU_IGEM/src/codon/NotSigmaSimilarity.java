package codon;

import java.util.ArrayList;
import java.util.Scanner;

public class NotSigmaSimilarity {

	public static void main(String[] args)
	{
		String maxId;
		String maxName;
		double maxmSS=0;
		double curmSS=0;
		System.out.println("输入序列：");
		Scanner in = new Scanner(System.in);
		String seq = in.nextLine();
		in.close();
		NotSigmaDetailed[] sigma = NotSigmadb.selectTable();
		for(int i=0; i<sigma.length; i++)
		{
			NotSigmaSimilarity ns = new NotSigmaSimilarity(sigma[i],seq);
			System.out.println("id:"+ns.getId()+",\tbestPoint:"+ns.getBestPoint()+",\tname:"+ns.getName()+",\tmSS值："+ns.getmSS());
			curmSS = ns.getmSS();
			if(maxmSS<curmSS)
			{
				maxId = ns.getId();
				maxName = ns.getName();
				maxmSS = curmSS;
			}
		}
	}
	
	public NotSigmaSimilarity(NotSigmaDetailed notsigma, String seq)
	{
		this.id = notsigma.getId();
		this.name = notsigma.getName();
		this.getMaxmSS(notsigma.getMatrix(), seq);
	}
	
	//这是一个不同的构造函数，没有notsigma输入，目的是取出所有大于某一mSS的值
	public NotSigmaSimilarity(String seq, double limit)
	{
		NotSigmaDetailed[] notsigma=NotSigmadb.selectTable();
		
		int[] alBestPoint=new int[notsigma.length];
		double[] almSS = new double[notsigma.length];
		String[] alName = new String[notsigma.length];
		String[] alMotif = new String[notsigma.length];
		int count=0;			//count做计数用，找出大于limit的个数
		for(int i=0; i<notsigma.length; i++)
		{
			this.getMaxmSS(notsigma[i].getMatrix(), seq);
			if(limit<=this.mSS)
			{
				alBestPoint[count]=this.bestPoint;
				alName[count]=notsigma[i].getName();
				almSS[count]=this.mSS;
				alMotif[count]=seq.substring(alBestPoint[count]-1,alBestPoint[count]-1+notsigma[i].getPssm_size());
				count++;
			}
		}
		this.bestPointArray = new int[count];
		this.nameArray = new String[count];
		this.mSSArray = new double[count];
		this.motifArray = new String[count];
		for(int i=0; i<count; i++)
		{
			this.bestPointArray[i] = alBestPoint[i];
			this.nameArray[i] = alName[i];
			this.mSSArray[i] = almSS[i];
			this.motifArray[i] = alMotif[i];
		}
	}
	
	//以下是第二个构造函数用到的值
	public double[] getmSSArray() {
		return mSSArray;
	}

	public int[] getBestPointArray() {
		return bestPointArray;
	}

	public String[] getNameArray() {
		return nameArray;
	}
	
	
	public String[] getMotifArray()
	{
		return motifArray;
	}

	//以下是第一个构造函数用到的值
	public String getId() {
		return id;
	}

	public double getmSS() {
		return mSS;
	}
	public String getName() {
		return name;
	}

	//域
	private String id;
	private String name;
	private int bestPoint;
	private double mSS;
	private double[] mSSArray;			//这个跟下面的nameArray是用来保存大于某一数值的mSS值数组
	private int[] bestPointArray;
	private String[] nameArray;
	private String[] motifArray;
	
	//这是最后要求的mSS值
	private void getMaxmSS(GeneFreMatrix matrix, String seq)
	{
		double max = 0.0;
		double cur = 0.0;
		for(int i=0; i+matrix.getColumns()<seq.length(); i++)
		{
			cur = getmSS(matrix, seq.substring(i, i+matrix.getColumns()));
			if(max<cur)
			{
				this.bestPoint=i+1;
				max = cur;
			}
		}
		this.mSS = max;
	}
	public int getBestPoint() {
		return bestPoint;
	}

	// 算法，一步步来
	// 先求出I
	private double getI(GeneFreMatrix matrix, int i) {
		double I = 0.0;
		for (int j = 1; j <= matrix.getRows(); j++) {
			if (matrix.getElement(j, i) == 0.0)
				continue;
			I += matrix.getElement(j, i)
					* Math.log(4 * matrix.getElement(j, i));
		}
		return I;
	}

	// 求最大值
	private double getMax(GeneFreMatrix matrix) {
		double max = 0.0;
		for (int i = 1; i <= matrix.getLength(); i++) {
			max += getI(matrix, i) * matrix.getMaxInCol(i);
		}
		return max;
	}

	// 求最小值
	private double getMin(GeneFreMatrix matrix) {
		double min = 0.0;
		for (int i = 1; i <= matrix.getLength(); i++) {
			min += getI(matrix, i) * matrix.getMinInCol(i);
		}
		return min;
	}

	// 求当前值之前把序列转成数字，默认序列为ACGT
	private int parseInt(String s) {
		int a = 1;
		switch (s.substring(0, 1)) {
		case "A":
			a = 1;
			break;
		case "a":
			a = 1;
			break;
		case "C":
			a = 2;
			break;
		case "c":
			a = 2;
			break;
		case "G":
			a = 3;
			break;
		case "g":
			a = 3;
			break;
		case "T":
			a = 4;
			break;
		case "t":
			a = 4;
			break;
		default:
			System.out.println("NO ELSE EXCEPT \"ATGC\"");
		}
		return a;
	}

	// 求当前值，默认排序ATGC
	private double getCur(GeneFreMatrix matrix, String s) {
		double cur = 0.0;
		if (s.length() != matrix.getLength()) {
			System.out
					.println("Wrong at function getCur! Has the string the same length with frequency matrix??");
			return cur;
		}
		for (int i = 1; i <= s.length(); i++) {
			cur += getI(matrix, i)
					* matrix.getElement(parseInt(s.substring(i - 1, i)), i);
		}
		return cur;
	}

	// 求mSS
	private double getmSS(GeneFreMatrix matrix, String s) {
		return (getCur(matrix, s) - getMin(matrix))
				/ (getMax(matrix) - getMin(matrix));
	}

}
