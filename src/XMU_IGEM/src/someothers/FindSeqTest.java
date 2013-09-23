package someothers;

//要求“序列.txt”文件必须是如下格式：首先是两个矩阵以制表符为分隔符，接下来是一系列的从网站中下载下来的数据，不要刚开始的一些说明文字
//另外，数据要求以制表符为分隔，第一个是ID，第六个是序列，从官网下载即可
//同时文件中不能出现空行情况

import java.io.*;

import codon.Matrix;

public class FindSeqTest {
	
	public static void main(String[] args)
	{
		try {
			BufferedReader txt = new BufferedReader(new FileReader("序列.txt"));
			String str="";
			
			//先读入sigma因子名，模体间隙最小长度，模体间隙最大长度，最小起始位点，最大起始位点（从大写字母开始数，向左是负值）
			str=txt.readLine();
			String baseInformation[]=str.split("\t");				//用baseInformation保存基本信息，如上
			if(baseInformation.length!=5)
			{
				System.out.println("基本信息录入有误，请检查\"序列.txt\"文件！");
				System.exit(0);
			}
			
			//先读入两个矩阵
			str=txt.readLine();
			String[] strs=str.split("\t");
			int i=1;
			Matrix matrix1=new Matrix(4,strs.length);
			while(i<=4)
			{
				for(int j=1; j<=matrix1.getColumns(); j++)
					matrix1.setElement(i, j, Double.parseDouble(strs[j-1]));
				i++;
				str=txt.readLine();
				strs=str.split("\t");
			}						//以上是对第一个矩阵的读入
			i=1;
			Matrix matrix2=new Matrix(4,strs.length);
			while(i<=4)
			{
				for(int j=1; j<=matrix2.getColumns(); j++)
					matrix2.setElement(i, j, Double.parseDouble(strs[j-1]));
				i++;
				str=txt.readLine();
				strs=str.split("\t");
			}					//以上是对第二个矩阵的读入，注意此处已经读到了序列的第一行
			Matrix resultMatrix1 = new Matrix(4,matrix1.getColumns());
			Matrix resultMatrix2 = new Matrix(4,matrix2.getColumns());
			//int idlength=strs[0].length();		//保存ID长度					以下这三个被注释是因为之后没用到
			//int genelength=strs[5].length();  //用于保存序列长度
			String ID=strs[0];  //用于保存ID
			String gene=strs[5];		//用于保存启动子序列
			//int genePosition=0;		//序列开始位置
			while(str!=null&&!str.isEmpty())
			{
				strs=str.split("\t");
				ID = strs[0];
				gene = strs[5];
				if(gene.isEmpty())
				{
					str=txt.readLine();
					continue;
				}
				FindSth findSimilarity = new FindSth(gene,matrix1,matrix2,Integer.parseInt(baseInformation[1]),Integer.parseInt(baseInformation[2]),Integer.parseInt(baseInformation[3]),Integer.parseInt(baseInformation[4]));
				System.out.println("Result:\n"+"ID: "+ID+";  序列："+gene+";\n最佳起始位点："+findSimilarity.getBestStart()+";  最佳间隙："+findSimilarity.getBestSpace()+";  相似度："+findSimilarity.getSimilarity()+";\n\n");
				resultMatrix1.add(findSimilarity.getMatrix1());
				resultMatrix2.add(findSimilarity.getMatrix2());
				str = txt.readLine();
			}
			txt.close();
			System.out.println("模体基本信息：");
			System.out.println("模体名称："+baseInformation[0]+",最小间隙："+baseInformation[1]+",最大间隙："+baseInformation[2]+",最小起始位点："+baseInformation[3]+",最大起始位点："+baseInformation[4]);
			System.out.println("第一个模体：");
			resultMatrix1.print();
			System.out.println("\n\n第二个模体：");
			resultMatrix2.print();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
