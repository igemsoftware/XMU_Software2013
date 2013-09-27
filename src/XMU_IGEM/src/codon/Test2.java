package codon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test2 {
	public static void main(String[] args)
	{
		try {
			BufferedReader txt = new BufferedReader(new FileReader("启动子数据验证-不同sigma因子.txt"));
			String str = txt.readLine();
			SigmaDetailed[] sigma = Promoterdb.selectTable();
			int count = 0;			//做计数用
			while(str != null)
			{
				if(str.isEmpty())
				{
					str = txt.readLine();
					continue;
				}
				if(str.indexOf("sigma")!=-1)
				{
					str = txt.readLine();
					continue;
				}
				String seq = str.substring(str.indexOf("\t")+1);
				double max = 0.0;
				double cur = 0.0;
				String name = "";
				for(int i=0; i<sigma.length; i++)
				{
					cur = new SeqSimilarity(sigma[i],seq).getSimilarity();
					if(max < cur)
					{
						max = cur;
						name = sigma[i].getName();
					}
				}
				System.out.println(++count+"\t"+seq+"\t"+name+"\t"+max);
				str = txt.readLine();
			}
			txt.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
