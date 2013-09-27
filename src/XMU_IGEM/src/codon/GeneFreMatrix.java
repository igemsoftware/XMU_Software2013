package codon;

public class GeneFreMatrix extends Matrix {
	
	public GeneFreMatrix(int n)
	{
		super(4,n);
	}
	
	public int getLength()
	{
		return getColumns();
	}
	
}
