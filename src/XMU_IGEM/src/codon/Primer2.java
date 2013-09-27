package codon;

public class Primer2 {
	
	public static void main(String[] args)
	{
		Primer2 p = new Primer2();
		String[] id = p.getId();
		String[] name = p.getName();
		String[] seq = p.getSeq();
		for(int i=0; i<id.length; i++)
			System.out.println(id[i]+"\t"+name[i]+"\t"+seq[i]);
	}
	
	public Primer2()
	{
		NotSigmaDetailed[] ns = NotSigmadb.selectTable();
		this.id = new String[ns.length];
		this.name = new String[ns.length];
		this.seq = new String[ns.length];
		for(int i=0; i<ns.length; i++)
		{
			this.id[i] = ns[i].getId();
			this.name[i] = ns[i].getName();
			GeneFreMatrix matrix = ns[i].getMatrix();
			StringBuffer s = new StringBuffer();
			for(int k=1; k<=matrix.getColumns(); k++)
			{
				double max=0.0;
				int maxPoint=0;
				for(int m=1; m<=4; m++)
					if(max<matrix.getElement(m, k))
					{
						max = matrix.getElement(m, k);
						maxPoint = m;
					}
				switch(maxPoint)
				{
				case 1:
					s.append("A");
					break;
				case 2:
					s.append("C");
					break;
				case 3:
					s.append("G");
					break;
				case 4:
					s.append("T");
					default:
						s.append("A");
				}
			}
			this.seq[i] = s.toString();
		}
	}
	
	public String[] getName() {
		return name;
	}

	public String[] getId() {
		return id;
	}

	public String[] getSeq() {
		return seq;
	}

	private String[] name;
	private String[] id;
	private String[] seq;
}
