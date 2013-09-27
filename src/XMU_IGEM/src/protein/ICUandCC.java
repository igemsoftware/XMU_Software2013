package protein;

import java.util.Random;

public class ICUandCC {
	
	public ICUandCC(String seq)
	{
		this.seq=seq;
		this.seqs=parseArray(this.seq);
	}
	
	private String[] parseArray(String seq)
	{
		if(seq.length()%3!=0)
		{
			System.out.println("wrong at parseArray");
			return null;
		}
		String aSeqs[]=new String[seq.length()/3];
		for(int i=0; i<aSeqs.length; i++)
			aSeqs[i]=seq.substring(3*i, 3*i+3);
		return aSeqs;
	}
	
	private String[] getSa1(String[] seqs)
	{
		String aminoAcid[]=new String[seqs.length];
		for(int i=0; i<aminoAcid.length; i++)
			aminoAcid[i]=Codondb.selectTable(seqs[i]);
		return aminoAcid;
	}
	
	private String[] getSc1(String[] aminoAcid)
	{
		String[] tymmzxl=new String[aminoAcid.length];
		for(int i=0; i<tymmzxl.length; i++)
		{
			String[] tymmz=Codondb.selectTable(aminoAcid[i], 0);
			Random r=new Random();
			tymmzxl[i]=tymmz[r.nextInt(tymmz.length)];
		}
		return tymmzxl;
	}
	
	
	
	private String seq;
	private String seqs[];
}
