package codon;

import java.util.Scanner;

public class Primer {
	
	public static void main(String[] args)
	{
		double K[]={0.5,0.5,1,1,1,1,0.1,0.1,0.2,0.2,0.1,0.2};
		Scanner s=new Scanner(System.in);
		System.out.println("输入序列:");
		String str=s.nextLine();
		s.close();
		Primer p=new Primer(str);
		double [] min5=p.getMin5();
		String[] minp=p.getMinP();
		String[] minq=p.getMinQ();
		int[] minppos=p.getMinPPos();
		int[] minqpos=p.getMinQPos();
		for(int i=0; i<min5.length; i++)
		{
			System.out.println("min["+i+"]="+min5[i]+"\t"+"\t"+minp[i]+"\t"+minq[i]+"\t"+minppos[i]+"\t"+minqpos[i]+"\t"+minp[i].length()+"\t"+minq[i].length());
		}
	}
	
	//构造函数
	public Primer(String seq)
	{
		double K[]={0.5,0.5,1,1,1,1,0.1,0.1,0.2,0.2,0.1,0.2};
		this.ordSeq=seq;
		StringBuffer str=new StringBuffer();
		for(int i=0; i<seq.length(); i++)
			switch(seq.charAt(i))
			{
			case 'A':
				str.append("T");
				break;
			case 'G':
				str.append("C");
				break;
			case 'C':
				str.append("G");
				break;
			case 'T':
				str.append("A");
				break;
			}
		this.cpSeq=str.toString();
		this.K=K;
		this.minP = new String[5];
		this.minQ = new String[5];
		this.minPPos = new int[5];
		this.minQPos = new int[5];
		this.getMin5(K);
	}
	
	public double[] getMin5() {
		return min5;
	}

	public String[] getMinP() {
		return minP;
	}

	public String[] getMinQ() {
		return minQ;
	}

	public int[] getMinPPos() {
		return minPPos;
	}

	public int[] getMinQPos() {
		return minQPos;
	}

	//求最小的5个数
	public boolean getMin5(double K[])
	{
		
		for(int i=0; i<cpSeq.length()-1; i++)
			for(int j=i+18; j<cpSeq.length()&&j<i+28; j++)
				for(int m=i; m<ordSeq.length(); m++)
					for(int n=m+18; n<ordSeq.length()&&m<n+28; n++)
						getMin5(i, j, m, n);
		return true;
		
	}
	
	//获取sc_ideal
	public double getScideal(String p, String q, double K[])
	{
		int m=0;
		int n=0;
		if(p.length()<18)
			m = 18-p.length();
		if(q.length()<18)
			n = 18-q.length();
		if(p.length()>28)
			m = p.length()-28;
		if(q.length()>28)
			n = q.length()-28;
		return K[0]*m+K[1]*n+K[2]*0.55+K[3]*0.55+K[4]*55+K[5]*55;
	}
	
	//获取sc
	public double getSc(String p, String q, double K[])
	{
		if(K.length!=12)
		{
			System.out.println("wrong at primer.getSc. k.length="+K.length);
			return 0.0;
		}
		return K[0]*p.length()+K[1]*q.length()+K[2]*getGc(p)+K[3]*getGc(q)+K[4]*getTm(p)+K[5]*getTm(q)+
				K[6]*getSa(p)+K[7]*getSa(q)+K[8]*getSea(p)+K[9]*getSea(q)+K[10]*getPa(p,q)+K[11]*getPea(p,q);
	}	
	
	//将一个double数组的从大到小的五个数同时与一个数进行比较，小于哪个就代替哪个，其他前移
	private boolean getMin5(int k, int l, int m, int n)
	{
		double[] a=this.min5;
		String[] minp=this.minP;
		String[] minq=this.minQ;
		int[] minppos=this.minPPos;
		int[] minqpos=this.minQPos;
		double b=getSc(cpSeq.substring(k, l),ordSeq.substring(m, n), K)-getScideal(cpSeq.substring(k, l),ordSeq.substring(m, n),K);
		boolean result=false;
		if(b>a[0])
			return result;
		else{
			int i=0;
			while(i<a.length)
			{
				if(b>a[i])
					break;
				i++;
			}
			for(int j=0; j+1<i; j++)
			{
				a[j]=a[j+1];
				minp[j]=minp[j+1];
				minq[j]=minq[j+1];
				minppos[j]=minppos[j+1];
				minqpos[j]=minqpos[j+1];
			}
			a[i-1]=b;
			minp[i-1]=cpSeq.substring(k, l);
			minq[i-1]=ordSeq.substring(m, n);
			minppos[i-1]=k+1;
			minqpos[i-1]=m+1;
			result=true;
			return result;
		}
	}
	
	//获取GC
	private double getGc(String p)
	{
		double count = 0.0;
		for(int i=0; i<p.length(); i++)
			if(p.charAt(i)=='G' || p.charAt(i)=='C')
				count += 1;
		return count/p.length();
	}
	
	//算解链温度
	//保存deltaH(i,i+1)和deltaS(i,i+1)
	private double getdeltaHt(String str)
	{
		if(str.length()!=2)
		{
			System.out.println("wrong at primer.getdeltaHt");
			return 0.0;
		}
		if(str.equals("AT"))
			return 8.6;
		if(str.equals("TA"))
			return 6.0;
		if(str.equals("CG"))
			return 11.9;
		if(str.equals("GC"))
			return 11.1;
		if(str.equals("AA") || str.equals("TT"))
			return 9.1;
		if(str.equals("CA") || str.equals("TG"))
			return 5.8;
		if(str.equals("GT") || str.equals("AC"))
			return 6.5;
		if(str.equals("CT") || str.equals("AG"))
			return 7.8;
		if(str.equals("GA") || str.equals("TC"))
			return 5.6;
		if(str.equals("GG") || str.equals("CC"))
			return 11.0;
		System.out.println("wrong at primer.getdeltaHt, input is "+str);
		return 0.0;
	}
	private double getdeltaSt(String str)
	{
		if(str.length()!=2)
		{
			System.out.println("wrong at primer.getdeltaSt");
			return 0.0;
		}
		if(str.equals("AT"))
			return 23.9;
		if(str.equals("TA"))
			return 16.9;
		if(str.equals("CG"))
			return 27.8;
		if(str.equals("GC"))
			return 26.7;
		if(str.equals("AA") || str.equals("TT"))
			return 24.0;
		if(str.equals("CA") || str.equals("TG"))
			return 12.9;
		if(str.equals("GT") || str.equals("AC"))
			return 17.3;
		if(str.equals("CT") || str.equals("AG"))
			return 20.8;
		if(str.equals("GA") || str.equals("TC"))
			return 13.5;
		if(str.equals("GG") || str.equals("CC"))
			return 26.6;
		System.out.println("wrong at primer.getdeltaSt. input is "+ str);
		return 0.0;
	}
	
	//得deltaH和deltaS
	private double getdeltaH(String str)
	{
		double sum = 0.0;
		for(int i=0; i<str.length()-1; i++)
			sum += getdeltaHt(str.substring(i,i+2));
		return sum;
	}
	private double getdeltaS(String str)
	{
		double sum = 0.0;
		for(int i=0; i<str.length()-1; i++)
			sum += getdeltaSt(str.substring(i, i+2));
		return sum;
	}
	
	//获取解链温度
	private double getTm(String p)
	{
		return 1000*getdeltaH(p)/(getdeltaS(p)+1.987*Math.log( 50*Math.pow(10, -9)/4 ))-273.15-21.6;
	}
	
	//获取sa
	//获取小s
	private int getSxiyi(char x, char y)
	{
		if((x=='A'&&y=='T') || (x=='T'&&y=='A'))
			return 2;
		if((x=='C'&&y=='G') || (x=='G'&&y=='C'))
			return 4;
		return 0;
	}
	
	//获取大S
	private double getS(String x, String y)
	{
//		System.out.println("进入primer.getS");
		double cur=0.0;
		double max=0.0;
		int n = x.length();
		int m = y.length();
		for(int k=-(n-1); k<=m-1; k++)
		{
			for(int i=1; i<=n && i+k<=m && i+k>0; i++)
				cur += getSxiyi(x.charAt(i-1),y.charAt(i+k-1));
			if(max<cur)
				max=cur;
		}
//		for(int k=-(m-1); k<=0; k++)
//		{
//			for(int i=-k+1; i<=m; i++)
//				cur += getSxiyi(y.charAt(i-1),x.charAt(i+k));
//			if(max<cur)
//				max=cur;
//		}
//		System.out.println("离开getS");
		return max;
	}
	
	//获取pa
	private double getPa(String p, String q)
	{
		StringBuilder pbb = new StringBuilder(p);
		String pb = pbb.reverse().toString();
		return getS(pb,q);
	}
	
	//获取sa
	private double getSa(String p)
	{
		StringBuilder qb = new StringBuilder(p);
		String q = qb.reverse().toString();
		return getS(q,p);
	}
	
	//获取S撇撇
	private double getSpp(String x, String y)
	{
//		System.out.println("进入primer.getS");
		double cur=0.0;
		double max=0.0;
		int n = x.length();
		int m = y.length();
		for(int k=-(n-1); k<=0; k++)
		{
			for(int i=1; i<=n && i+k<=m && i+k>0; i++)
			{
				if(getSxiyi(x.charAt(i-1),y.charAt(i+k-1))!=0)
					cur += getSxiyi(x.charAt(i-1),y.charAt(i+k-1));
			}
			if(max<cur)
				max=cur;
		}
//		for(int k=-(m-1); k<=0; k++)
//		{
//			for(int i=-k+1; i<=n; i++)
//			{
//				if(getSxiyi(x.charAt(i-1),y.charAt(i+k))!=0)
//					cur += getSxiyi(x.charAt(i-1),y.charAt(i+k));
//			}
//			if(max<cur)
//				max=cur;
//		}
//		System.out.println("离开getS");
		return max;
	}
	
	//获取sea
	private double getSea(String p)
	{
		StringBuilder qb = new StringBuilder(p);
		String q = qb.reverse().toString();
		return getSpp(q,p);
	}
	
	//获取pea
	private double getPea(String p, String q)
	{
		StringBuilder pbb = new StringBuilder(p);
		String pb = pbb.reverse().toString();
		return getSpp(pb,q);
	}
	
	//域
	private String p="";
	private String q="";
	private String ordSeq;
	private String cpSeq;
	private final double[] K;
	private double[] min5 = {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
	private String[] minP;
	private String[] minQ;
	private int[] minPPos;
	private int[] minQPos;
}
