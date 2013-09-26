package codon;

public class NotSigmaDetailed {
	public NotSigmaDetailed()
	{
		
	}
	
	private String id;
	private String name;
	private int tbs;
	private int pssm_size;
	private GeneFreMatrix matrix;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTbs() {
		return tbs;
	}
	public void setTbs(int tbs) {
		this.tbs = tbs;
	}
	public int getPssm_size() {
		return pssm_size;
	}
	public void setPssm_size(int pssm_size) {
		this.pssm_size = pssm_size;
	}
	public GeneFreMatrix getMatrix() {
		return matrix;
	}
	public void setMatrix(GeneFreMatrix matrix) {
		this.matrix = matrix;
	}
}
