package frame;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BestPrimer extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		String p = "AGTAGACGATAGACGATA";
		String q = "AGTTGACCGATAGACGATAG";
		String notSigma = "GAGACGATAGACGATAGA";
		new BestPrimer(p, q, notSigma, 1.033,1.033,1.033,1.033);
	}
	public JLayeredPane layeredPanel;

	public JTable table;

	// Object[][] p = {{"1121", "Arac", "ATGCATGC"}, {"1122", "Arbc",
	// "ATGCTAGC"}, {"1123", "Arer", "ATGCTTTC"}};
	
	private String forwardString;
	private String reverseString;
	private double minpgc;
	private double minptm;
	private double minqgc;
	private double minqtm;
	
	public BestPrimer(String p, String q, String notsigma, double minpgc, double minptm, double minqgc, double minqtm) {

		super("The Best Primer");
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.forwardString = p+notsigma;
		String reverseNotsigma = "";
		for(int i = 0 ; i<notsigma.length(); i++)
		{
			switch(notsigma.charAt(i))
			{
			case 'A':
				reverseNotsigma += "T";
				break;
			case 'C':
				reverseNotsigma += "G";
				break;
			case 'G':
				reverseNotsigma += "C";
				break;
			case 'T':
				reverseNotsigma +="A";
				break;
				default:
					reverseNotsigma += "N";
			}
		}
		this.reverseString = q+reverseNotsigma;
		this.minpgc = minpgc;
		this.minqgc = minqgc;
		this.minptm = minptm;
		this.minqtm = minqtm;
		
		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(900, 613);
		setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);

		layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 900, 613);

		JPanel panel = new BestPrimerImage();
		panel.setBounds(0, 0, 900, 613);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 900, 613);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		JTextField forward = new JTextField();
		forward.setBounds(230+3, 183, 500-3, 40);
		forward.setBorder(BorderFactory.createEmptyBorder());
		panel1.add(forward);
		forward.setText(this.forwardString);
		
		JTextField reverse = new JTextField();
		reverse.setBounds(230+3, 283+55, 500-3, 40);
		reverse.setBorder(BorderFactory.createEmptyBorder());
		panel1.add(reverse);
		reverse.setText(this.reverseString);
		
		JTextField Gcp = new JTextField();
		Gcp.setBounds(320, 243, 160, 40);
		Gcp.setBorder(BorderFactory.createEmptyBorder());
		panel1.add(Gcp);
		Gcp.setText(String.format("%.2f", this.minpgc));
		
		JTextField Tmp = new JTextField();
		Tmp.setBounds(550, 243, 160, 40);
		Tmp.setBorder(BorderFactory.createEmptyBorder());
		panel1.add(Tmp);
		Tmp.setText(String.format("%.2f", this.minptm));
		
		
		JTextField Gcq = new JTextField();
		Gcq.setBounds(320, 406, 160, 40);
		Gcq.setBorder(BorderFactory.createEmptyBorder());
		panel1.add(Gcq);
		Gcq.setText(String.format("%.2f", this.minqgc));
		
		JTextField Tmq = new JTextField();
		Tmq.setBounds(550, 406, 160, 40);
		Tmq.setBorder(BorderFactory.createEmptyBorder());
		panel1.add(Tmq);
		Tmq.setText(String.format("%.2f", this.minqtm));
		
		// Mytable mt = new Mytable();
//		table = new JTable(10, 6);
//		table.setBounds(173 - 9, 201 - 29, 590, 320);
//		table.setRowHeight(32);
//		table.getColumnModel().getColumn(0).setPreferredWidth(60);
//		table.getColumnModel().getColumn(1).setPreferredWidth(70);
//		table.getColumnModel().getColumn(2).setPreferredWidth(70);
//		table.getColumnModel().getColumn(3).setPreferredWidth(100);
//		table.getColumnModel().getColumn(4).setPreferredWidth(40);
//		table.getColumnModel().getColumn(5).setPreferredWidth(40);
//		panel1.add(table);
//		table.getModel().setValueAt("Forward", 0, 0);
//		table.getModel().setValueAt("Reverse", 1, 0);
//		table.getModel().setValueAt("Forward", 2, 0);
//		table.getModel().setValueAt("Reverse", 3, 0);
//		table.getModel().setValueAt("Forward", 4, 0);
//		table.getModel().setValueAt("Reverse", 5, 0);
//		table.getModel().setValueAt("Forward", 6, 0);
//		table.getModel().setValueAt("Reverse", 7, 0);
//		table.getModel().setValueAt("Forward", 8, 0);
//		table.getModel().setValueAt("Reverse", 9, 0);

		layeredPanel.add(panel1, new Integer(1));
		getContentPane().add(layeredPanel);
		setVisible(true);

//		this.addMouseMotionListener(new MouseAdapter() {
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				System.out.println("X:" + e.getXOnScreen() + "  Y:"
//						+ e.getYOnScreen());
//				super.mouseMoved(e);
//			}
//		});
	}


	class BestPrimerImage extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BestPrimerImage() {
			setLayout(new FlowLayout());
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			ImageIcon img = new ImageIcon("codon/bestprimer.jpg");
			img.getIconWidth();
			img.getIconHeight();
//			System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 900, 613, this);
		}
	}
}
