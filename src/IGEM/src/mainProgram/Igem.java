package mainProgram;

import codon.GeneFreMatrix;
import codon.NotSigmaSimilarity;
import codon.Primer;
import codon.Rbs;
import codon.SeqSimilarity;
import protein.Algorithm;
import protein.Algorithm2;

public class Igem {
	public static void main(String[] args)
	{
		//启动子，带有两个模体的部分，算法实现在codon.SeqSimilarity类中
		String s1="AGATAGCGATGACGATGACGATAGCAG";		//这是输入序列
		String sigma1="";		//用于保存输出sigma序列
		int bestStartPoint=0;		//用于保存输出最佳起始位点
		int bestSpaceLength=0;			//用于保存输出最佳间隙长度
		double similarity=0.0;			//用于保存与上面几个对应的相似度
		SeqSimilarity sigmaSimilarity=new SeqSimilarity(s1);			//这是算法实现，不用理解
		sigma1=sigmaSimilarity.getName();	
		bestStartPoint = sigmaSimilarity.getBestStartPoint();
		bestSpaceLength = sigmaSimilarity.getBestSpaceLength();
		similarity = sigmaSimilarity.getSimilarity();
		
		//黄新做的単模体启动子序列，大于一定数值的输出
		double limit=0.0;				//限制大于一定mSS值的那个数
		String s2="AGATAGCGATGACGATGACGATAGCAG";		//这是输入序列
		NotSigmaSimilarity ns = new NotSigmaSimilarity(s2, limit);
		String[] name = ns.getNameArray();			//这三个是输出，分别是名称、开始位点、相似度
		int[] bestPoint = ns.getBestPointArray();
		double[] mSS = ns.getmSSArray();
		
		
		//蛋白质序列优化
		String s3="AGATAGCGATGACGATGACGATAGCAG";		//这是输入序列
		String[] cell={"ECOLI","PPASTORIS","LLACTIS","SCEREVISIZE"};
		int population=200;			//种群大小
		int  daishu = 200;				//遗传代数
		double crossRate=0.1;			//交叉概率
		double varRate = 0.1;				//变异概率
		Algorithm al = new Algorithm(s3,cell[1],population,daishu,crossRate,varRate);
		String[] finalCodon = al.getFinalCodon();				//这是最后的结果
		
		
		//蛋白质序列优化2，跟上个一样，所以直接用上个的数据
		Algorithm2 al2 = new Algorithm2(s3,cell[1],population,daishu,crossRate,varRate);
		String[] finalCodon2 = al2.getFinalCodon();			//这是最后的结果
		
		//Sc-Scideal这个程序
		String s4 = "AGATAGCGATGACGATGACGATAGCAG";		//这是输入序列
		Primer p = new Primer(s4);
		double[] min5 = p.getMin5();				//得到最小的5个值
		
		//rbs这个程序，其中str在以下四个选项中选一："ATG""TTG""GTG"null,matrix暂时没有，所以先注释掉
//		GeneFreMatrix matrix=null;
//		String s5 = "AGATAGCGATGACGATGACGATAGCAG";		//这是输入序列
//		String[] strs = {"ATG","TTG","GTG",null};
//		Rbs r = new Rbs(matrix, s5, strs[0]);
//		int bestStartPointOfRbs = r.getBestStartPoint();
//		int bestSpaceLengthOfRbs = r.getBestSpaceLength();
//		double bestmSS = r.getSimilarity();
	}
}
