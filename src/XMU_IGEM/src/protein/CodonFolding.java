package protein;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class CodonFolding {

	public static void main(String[] args) {
		try {
			BufferedReader txt = new BufferedReader(new FileReader(
					"蛋白质折叠序列.txt"));
			String name = "";
			String seq = txt.readLine();
			String codon = new CodonFolding("").getCodon();
			for (int i = 0; i < codon.length(); i++)
				System.out.print(codon.charAt(i) + "\t");
			System.out.println();
			while (seq != null && !seq.isEmpty()) {
				name = seq.substring(0, seq.indexOf("\t"));
				seq = seq.substring(seq.indexOf("\t") + 1);
				System.out.print(name + "\t");
				if (!seq.isEmpty()) {
					CodonFolding c = new CodonFolding(seq);
					double[] Theta = c.getLowerCaseTheta();
//					for (int i = 0; i < Theta.length; i++) {
//						System.out.print(Theta[i] + "\t");
//					}
//					System.out.print("分割线"+"\t");
//					for (int i = 0; i < codon.length(); i++) {
//						int count = 0;
//						for (int j = 0; j < seq.length(); j++) {
//							if (codon.charAt(i) == seq.charAt(j))
//								count++;
//						}
//						System.out.print((double) count / seq.length() + "\t");
//					}
					double[] rates = c.getRate();
					double result = -0.845*Math.log(seq.length())+0.4*Theta[1]-0.188*Theta[3]-0.149*Theta[7]-0.090*Theta[8]
							+0.061*Theta[9]+0.11*rates[codon.indexOf("N")]+0.284*rates[codon.indexOf("Q")]-0.021*rates[codon.indexOf("Y")]
									-0.215*rates[codon.indexOf("W")]-0.3*rates[codon.indexOf("T")]+0.038*rates[codon.indexOf("A")]-0.209*rates[codon.indexOf("L")]
											-0.337*rates[codon.indexOf("V")]-0.214*rates[codon.indexOf("I")]+13.74734589;
//					System.out.print(seq+"\t"+result);
				}
//				System.out.println();
				seq = txt.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public CodonFolding(String seq) {
		this.seq = seq;
		double[] H = { -4.5, -3.9, -3.5, -3.5, -3.5, -3.5, -3.2, -1.6, -1.3,
				-0.9, -0.8, -0.7, -0.4, 1.8, 1.9, 2.5, 2.8, 3.8, 4.2, 4.5 };
		this.H = H;
		codon = "RKNDQEHPYWSTGAMCFLVI";
		String[] codonName = { "Arg", "Lys", "Asn", "Asp", "Gln", "Glu", "His",
				"Pro", "Tyr", "Trp", "Ser", "Thr", "Gly", "Ala", "Met", "Cys",
				"Phe", "Leu", "Val", "Ile" };
		this.codonName = codonName;
		// System.out.println(this.codonName.length+"\t"+this.codon.length()+"\t"+this.H.length);
		double[] Theta = getLowerCaseTheta();
		double[] rates = getRate();
		result = -0.845*Math.log(seq.length())+0.4*Theta[1]-0.188*Theta[3]-0.149*Theta[7]-0.090*Theta[8]
				+0.061*Theta[9]+0.11*rates[codon.indexOf("N")]+0.284*rates[codon.indexOf("Q")]-0.021*rates[codon.indexOf("Y")]
						-0.215*rates[codon.indexOf("W")]-0.3*rates[codon.indexOf("T")]+0.038*rates[codon.indexOf("A")]-0.209*rates[codon.indexOf("L")]
								-0.337*rates[codon.indexOf("V")]-0.214*rates[codon.indexOf("I")]+13.74734589;
		result = Math.exp(result);
	}

	public double getResult() {
		return result;
	}

	//获取每个氨基酸的百分比
	public double[] getRate()
	{
		String seq = this.seq;
		double[] rates = new double[this.codon.length()];
		for(int i=0; i<rates.length; i++)
		{
			double count = 0.0;
			for(int j=0; j<seq.length(); j++)
				if(codon.charAt(i) == seq.charAt(j))
					count++;
			rates[i] = count;
		}
		return rates;
	}
	
	private double getCapitalTheta(char Rj, char Ri) {
		int i = this.codon.indexOf(Ri);
		int j = this.codon.indexOf(Rj);
		if (i < 0 || j < 0) {
			System.out.println("输入序列有误！");
			return 0.0;
		}

		return Math.abs(H[i] - H[j]);
	}

	public double[] getLowerCaseTheta() {
		String seq = this.seq;
		double[] Theta = new double[20];
		for (int i = 0; i < 20; i++) {
			double sum = 0.0;
			for (int j = 0; j < seq.length() - i - 1; j++) {
				sum += getCapitalTheta(seq.charAt(j), seq.charAt(j + i + 1));
			}
			Theta[i] = sum / (seq.length() - i - 1);
		}
		return Theta;
	}

	public double[] getLowerCaseTheta(String[] seq) {
		String s = "";
		for (int i = 0; i < seq.length; i++) {
			int j = 0;
			for (; j < this.codonName.length; j++)
				if (seq[i].equals(this.codonName[j]))
					break;
			s += this.codon.charAt(j);

		}
		this.seq = s;
		return getLowerCaseTheta();
	}

	public String getCodon() {
		return codon;
	}

	public double[] getH() {
		return H;
	}

	private double[] H;
	private String codon;
	private String[] codonName;
	private String seq;
	private double result;
}
