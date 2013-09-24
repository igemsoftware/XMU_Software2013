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
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			System.out.println("开始时间：" + df.format(new Date()));
			GeneFreMatrix matrix1 = new GeneFreMatrix(6);
			GeneFreMatrix matrix2 = new GeneFreMatrix(6);
			BufferedReader txt = new BufferedReader(new FileReader("数据.txt"));
			for(int i=1; i<=4; i++)
			{
				String str = txt.readLine();
				String strs[] = str.split("\t");
				String str1[] = strs[0].split(" ");
				String str2[] = strs[1].split(" ");
				for(int j=1; j<=6; j++)
				{
					matrix1.setElement(i, j, Double.valueOf(str1[j-1]));
					matrix2.setElement(i, j, Double.valueOf(str2[j-1]));
				}
			}
			
			//转频数到频率
			matrix1.parseFre();
			matrix2.parseFre();
			
			String gene = txt.readLine();
			txt.close();
			System.out.println("Result: "+ new SeqSimilarity(matrix1,matrix2,gene,1.0,0.0,15,20,0.0));
			System.out.println("结束时间：" + df.format(new Date()));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
