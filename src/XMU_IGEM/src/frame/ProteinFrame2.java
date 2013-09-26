package frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ProteinFrame2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6650753642006810398L;

	public static void main(String[] args)
	{
		new ProteinFrame2("AGATAGACGAG",null,null,null,null,null);
	}
	JButton yes;
	JButton no;
	JTextArea text;
	MyPanel panel1;
	String result;
	String[] codons;
	int[] numBefOpt;
	double[] proBefOpt;
	int[] numAftOpt;
	double[] proAftOpt;
	
	public ProteinFrame2(String result, String[] codons, int[] numBefOpt, double[] proBefOpt, int[] numAftOpt, double[] proAftOpt) {
		super();

		this.setUndecorated(true);

		this.result = result;
		this.codons = codons;
		this.numBefOpt = numBefOpt;
		this.proBefOpt = proBefOpt;
		this.numAftOpt = numAftOpt;
		this.proAftOpt = proAftOpt;
		
		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(480, 419);
		setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);

		getContentPane().setLayout(null);
		panel1 = new MyPanel(this.result);
		panel1.setBounds(0, 0, 480, 419);
		this.getContentPane().add(panel1, java.awt.BorderLayout.CENTER);
		
//		ImageIcon yesImage = new ImageIcon("protein2_yes.jpg");
//		yes = new JButton(yesImage);
//		yes.addActionListener(new MyAction());
//		yes.setBounds(40, 344, 192, 53);
//		panel1.add(yes);
//		
//		ImageIcon noImage = new ImageIcon("protein2_yes.jpg");
//		no = new JButton(noImage);
//		no.addActionListener(new MyAction());
//		no.setBounds(258, 340, 192, 51);
//		panel1.add(no);
//		
//		text= new JTextArea();
//		text.setBounds(43, 93, 400, 175);
//		Font font=new Font("宋体",Font.PLAIN,18);
//		text.setFont(font);
//		text.setLineWrap(true);
//		text.setEditable(false);
//		panel1.add(text);
//		text.setText(result);
//		
//		JScrollPane jsp = new JScrollPane(text);
//		jsp.setBounds(43 , 93, 400, 175);
//		panel1.add(jsp);

		setVisible(true);
	}

	
	class MyPanel extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4229105713128494661L;
		private String output;
		
		public MyPanel(String output) {
			setLayout(null);
			this.output = output;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			ImageIcon yesImage = new ImageIcon("protein2_yes.jpg");
			yes = new JButton(yesImage);
			yes.addActionListener(new MyAction());
			yes.setBounds(40, 344, 192, 53);
			add(yes);
			
			ImageIcon noImage = new ImageIcon("protein2_no.jpg");
			no = new JButton(noImage);
			no.addActionListener(new MyAction());
			no.setBounds(258, 340, 192, 51);
			add(no);
			
			text= new JTextArea();
			text.setBounds(43, 93, 400, 175);
			Font font=new Font("宋体",Font.PLAIN,18);
			text.setFont(font);
			text.setLineWrap(true);
			text.setEditable(false);
			add(text);
			text.setText(this.output);
			
			JScrollPane jsp = new JScrollPane(text);
			jsp.setBounds(43 , 93, 400, 175);
			add(jsp);
			
			ImageIcon img = new ImageIcon("protein2.jpg");
			int width = img.getIconWidth();
			int height = img.getIconHeight();
			// System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, width, height, this);
		}
		
		private class MyAction implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == yes)
				{
					new ProteinFrame3(result, codons,numBefOpt,proBefOpt,numAftOpt,proAftOpt);
					ProteinFrame2.this.dispose();
				}else if(arg0.getSource() == no)
				{
					ProteinFrame2.this.dispose();
				}
			}
			
		}

	}
}
