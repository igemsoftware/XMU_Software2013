package codon;

public class Rbs {

	public static void main(String[] args) {
		// 第一个输入是输入序列，第二个输入只能是如下四种："ATG","TTG","GTG"，null
		Rbs r = new Rbs("AGATAGACGATAGACGATAGACGATAGAATGACGATAATG", "UNKNOWN");
		System.out.println(r.getBestStartPoint() + "\t"
				+ r.getBestSpaceLength() + "\t" + r.getSeq() + "\t"
				+ r.getSimilarity());
	}

	public Rbs(GeneFreMatrix matrix, String seq, String str) {
		this.getMaxmSS(matrix, seq, str);
	}

	public Rbs(String seq, String str) {
		double[][] pinlv = { { 0.681, 0.105, 0.015, 0.861, 0.164 },
				{ 0.077, 0.037, 0.012, 0.025, 0.046 },
				{ 0.077, 0.808, 0.960, 0.043, 0.659 },
				{ 0.161, 0.050, 0.012, 0.071, 0.115 } };
		GeneFreMatrix matrix = new GeneFreMatrix(pinlv[0].length);
		for (int i = 0; i < pinlv.length; i++)
			for (int j = 0; j < pinlv[i].length; j++)
				matrix.setElement(i + 1, j + 1, pinlv[i][j]);
		if(str.equals("UNKNOWN"))
		{
			this.getMaxmSS(matrix, seq, "ATG");
			double atgSimilarity = this.getSimilarity();
			int atgStartPoint = this.bestStartPoint;
			int atgSpaceLength = this.bestSpaceLength;
			
			this.getMaxmSS(matrix, seq, "TTG");
			double ttgSimilarity = this.getSimilarity();
			int ttgStartPoint = this.bestStartPoint;
			int ttgSpaceLength = this.bestSpaceLength;
			
			this.getMaxmSS(matrix, seq, "GTG");
			double gtgSimilarity = this.getSimilarity();
			int gtgStartPoint = this.bestStartPoint;
			int gtgSpaceLength = this.bestSpaceLength;
			
			double max = atgSimilarity;
			int maxStartPoint = atgStartPoint;
			int maxSpaceLength = atgSpaceLength;
			this.str = "ATG";
			
			if(max < ttgSimilarity)
			{
				max = ttgSimilarity;
				maxStartPoint = ttgStartPoint;
				maxSpaceLength = ttgSpaceLength;
				this.str = "TTG";
			}
			if(max < gtgSimilarity)
			{
				max = gtgSimilarity;
				maxStartPoint = gtgStartPoint;
				maxSpaceLength = gtgSpaceLength;
				this.str = "GTG";
			}
			
			this.bestStartPoint = maxStartPoint;
			this.bestSpaceLength = maxSpaceLength;
			this.similarity = max*(0.21*1.0+0.35*getGS(7, 7, 0.0));
			this.seq = seq.substring(this.bestStartPoint-1, this.bestStartPoint-1+this.bestSpaceLength);
		}else{
		this.getMaxmSS(matrix, seq, str);
		this.seq = seq.substring(this.bestStartPoint - 1, this.bestStartPoint
				- 1 + this.bestSpaceLength);
		this.str = str;
		}
	}

	public String getSeq() {
		return seq;
	}

	public String getStr() {
		return str;
	}

	// 求出最大mss值
	private void getMaxmSS(GeneFreMatrix matrix, String seq, String str) {
		if (str != "NONE" && !str.equals("ATG") && !str.equals("TTG")
				&& !str.equals("GTG")) {
			System.out.println("出现错误，在Rbs.getMaxmSS函数中");
			return;
		}
		if (str.equals("NONE")) {
			seq = seq + "ATG";
			str = "ATG";
			this.str = "ATG";
		}
		// String[] strs = {"ATG","TTG","GTG"};
		int bestStartPoint = 0;
		int bestSpaceLength = 0;
		double max = 0.0;
		double cur = 0.0;
		int index = 0; // 准备保存找到的位点
		index = seq.indexOf(str, index + 1);
		while (index != -1) {
			for (int j = index - 16 - matrix.getColumns(); j >= 0
					&& index - j - matrix.getColumns() >= 3; j++) {
				cur = 0.29*getmSS(matrix, seq.substring(j, j + matrix.getColumns()))+0.355*getGS(index-j-matrix.getColumns(), 7, 0.0);
				if (max < cur) {
					bestStartPoint = j;
					bestSpaceLength = index - j - matrix.getColumns();
					max = cur;
				}
			}
			index = seq.indexOf(str, index + 1);
		}
		this.bestStartPoint = bestStartPoint + 1;
		this.bestSpaceLength = bestSpaceLength;
		this.similarity = max;
	}

	// getters
	public int getBestStartPoint() {
		return bestStartPoint;
	}

	public int getBestSpaceLength() {
		return bestSpaceLength;
	}

	public double getSimilarity() {
		return similarity/(0.21*1.0+0.35*getGS(7, 7, 0.0));
	}

	// 域
	private int bestStartPoint;
	private int bestSpaceLength;
	private double similarity;
	private String seq;
	private String str;

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

	// 求空隙得分，先求accessibility
	private double getAccessibility(int d,
			int center) {
		return 1.0 + Math.cos(2 * Math.PI / 10.6 * (d - center));
	}

	// 求空隙得分，再求GS
	private double getGS(int d, int center,
			double adjuste) {
		return getAccessibility(d, center);
	}
}
