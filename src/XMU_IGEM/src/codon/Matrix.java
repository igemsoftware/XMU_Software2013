package codon;

public class Matrix {
	
	public Matrix(int m, int n)
	{
		this.matrix = new double[m][n];
		this.rows = m;
		this.columns = n;
	}
	
	
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}
	
	public boolean setElement(int m, int n, double value)
	{
		if(m>this.rows || n>this.columns)
		{
			System.out.println("OUT OF MATRIX!(FROM SETTERS)");
			return false;
		}else{
			this.matrix[m-1][n-1] = value;
			return true;
		}
	}
	
	public double getElement(int m, int n)
	{
		if(m>this.rows || n>this.columns)
		{
			System.out.println("OUT OF MATRIX!(FROM GETTERS)");
			return 0.0;
		}else{
			return this.matrix[m-1][n-1];
		}
	}
	
	
	//求每列最大值
	public double getMaxInCol(int i)
	{
		double max = 0.0;
		for(int j=1; j<=rows; j++)
		{
			if(max<matrix[j-1][i-1])
				max = matrix[j-1][i-1];
		}
		return max;
	}
	
	//求每列最小值
	public double getMinInCol(int i)
	{
		double min = 0.0;
		for(int j=1; j<=rows; j++)
		{
			if(min>matrix[j-1][i-1])
				min = matrix[j-1][i-1];
		}
		return min;
	}
	
	//将频数转换为频率
	public boolean parseFre()
	{
		for(int i=1; i<=this.rows; i++)
		{
			double sum = 0.0;
			for(int j=1; j<=this.columns; j++)
			{
				sum += this.getElement(i, j);
			}
			for(int j=1; j<=this.columns; j++)
			{
				this.setElement(i, j, this.getElement(i, j)/sum);
			}
		}
		return true;
	}
	
	//两个矩阵相加
	public void add(Matrix matrix)
	{
		for(int i=1; i<=this.getRows(); i++)
			for(int j=1; j<=this.getColumns(); j++)
				this.setElement(i, j, this.getElement(i, j)+matrix.getElement(i, j));
	}
	
	
	//打印矩阵
	public void print(){
		for(int m = 0; m < matrix.length; m++)
		{
			for(int n = 0; n < matrix[0].length; n++)
				System.out.print(matrix[m][n]+"\t");
			System.out.println();
		}
	}
	


	//域
	private int rows;
	private int columns;
	private double matrix[][];
}
