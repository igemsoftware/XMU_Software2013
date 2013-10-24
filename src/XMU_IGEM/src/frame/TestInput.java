package frame;

import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TestInput {
	
	public static void main(String[] args)
	{
		TestInput t = new TestInput("AGTAGDGATA");
		System.out.println(t.getSeq());
	}
	
	private String seq;
	private boolean changeSuccess=true;
	
	public String getSeq() {
		return seq;
	}
	public boolean isChangeSuccess() {
		return changeSuccess;
	}
	public TestInput(String seq)
	{
		this.seq = seq;
		this.seq = changeUToT(this.seq);
		if(test(this.seq) == false )
		{
			Locale.setDefault(Locale.US);
			ImageIcon logo = new ImageIcon(
				"Alert.png");
			if(JOptionPane.showConfirmDialog(null, "Input wrong��change all which isn\'t \"A、G、T、C、U\" to \"A\"?", "warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, logo) == JOptionPane.YES_OPTION)
				this.seq = change(this.seq);
			else
				this.changeSuccess = false;
		}
	}
	public static boolean test(String seq)
	{
		for(int i=0; i<seq.length(); i++)
		{
			char ch = seq.charAt(i);
			if(ch!='A' && ch!='T' && ch!='C' && ch!='G')
				return false;
		}
		return true;
	}
	
	public static String change(String seq)
	{
		StringBuffer seqBuf = new StringBuffer(seq.length());
		for(int i=0; i<seq.length(); i++)
		{
			char ch = seq.charAt(i);
			if(ch!='A' && ch!='T' && ch!='C' && ch!='G')
				seqBuf.append('A');
			else
				seqBuf.append(ch);
		}
		return seqBuf.toString();
	}
	
	public static String changeUToT(String seq)
	{
		StringBuffer seqBuf = new StringBuffer(seq.length());
		for(int i=0; i<seq.length(); i++)
		{
			char ch = seq.charAt(i);
			if(ch == 'U')
				seqBuf.append('T');
			else
				seqBuf.append(ch);
		}
		return seqBuf.toString();
	}
}
