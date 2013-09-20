package codon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Test {
	
	public static void main(String[] args)
	{
		try
		{
			GeneFreMatrix matrix1 = new GeneFreMatrix(6);
			GeneFreMatrix matrix2 = new GeneFreMatrix(6);
			BufferedReader txt = new BufferedReader(new FileReader("sigma模体位置验证.txt"));
			
			SigmaDetailed sigma=Promoterdb.selectTable("sigma70");
			matrix1 = sigma.getMatrix1();
			matrix2 = sigma.getMatrix2();
			
			//转频数到频率
			matrix1.parseFre();
			matrix2.parseFre();
			
			//其他基本信息
			double motifWeight = sigma.getMotifWeight();
			double spaceWeight = sigma.getSpaceWeight();
			int spaceMin = sigma.getSpaceMin();
			int spaceMax = sigma.getSpaceMax();
			double adjustE = sigma.getAdjustE();
			
			String gene = txt.readLine();
			SeqSimilarity s;
			int i=0;
			while(gene!=null)
			{
				gene = gene.substring(gene.indexOf("\t")+1, gene.length()-4);
				i++;
				s = new SeqSimilarity(matrix1, matrix2, gene, motifWeight, spaceWeight, spaceMin, spaceMax, adjustE);
				System.out.println(i+"\t"+s.getBestStartPoint()+"\t"+s.getBestSpaceLength()+"\t"+s.getSimilarity());
				gene = txt.readLine();
			}
			txt.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
