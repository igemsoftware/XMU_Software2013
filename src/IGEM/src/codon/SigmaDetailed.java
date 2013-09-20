package codon;

//此类用于保存sigma因子的详细信息

public class SigmaDetailed {
	
	public SigmaDetailed()
	{
		
	}
	
	
	public SigmaDetailed(String name, GeneFreMatrix matrix1, GeneFreMatrix matrix2, double motifWeight, double spaceWeight, int spaceMin, int spaceMax, double adjustE)
	{
		this.matrix1=matrix1;
		this.matrix2=matrix2;
		this.motifWeight=motifWeight;
		this.spaceWeight=spaceWeight;
		this.spaceMin=spaceMin;
		this.spaceMax=spaceMax;
		this.adjustE=adjustE;
		this.name=name;
	}
	
	
	public String getName(){
		return name;
	}
	public GeneFreMatrix getMatrix1() {
		return matrix1;
	}
	public GeneFreMatrix getMatrix2() {
		return matrix2;
	}
	public double getMotifWeight() {
		return motifWeight;
	}
	public double getSpaceWeight() {
		return spaceWeight;
	}
	public int getSpaceMin() {
		return spaceMin;
	}
	public int getSpaceMax() {
		return spaceMax;
	}
	public double getAdjustE() {
		return adjustE;
	}

	


	public void setName(String name) {
		this.name = name;
	}


	public void setMatrix1(GeneFreMatrix matrix1) {
		this.matrix1 = matrix1;
	}


	public void setMatrix2(GeneFreMatrix matrix2) {
		this.matrix2 = matrix2;
	}


	public void setMotifWeight(double motifWeight) {
		this.motifWeight = motifWeight;
	}


	public void setSpaceWeight(double spaceWeight) {
		this.spaceWeight = spaceWeight;
	}


	public void setSpaceMin(int spaceMin) {
		this.spaceMin = spaceMin;
	}


	public void setSpaceMax(int spaceMax) {
		this.spaceMax = spaceMax;
	}


	public void setAdjustE(double adjustE) {
		this.adjustE = adjustE;
	}




	private String name;
	private GeneFreMatrix matrix1;
	private GeneFreMatrix matrix2;
	private double motifWeight;
	private double spaceWeight;
	private int spaceMin;
	private int spaceMax;
	private double adjustE;
}
