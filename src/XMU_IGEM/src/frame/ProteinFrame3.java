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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import protein.CodonFolding;

public class ProteinFrame3 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4142561319114177776L;

	public static void main(String[] args) {
		String[] codons = new String[64];
		int[] numBefOpt = new int[64];
		double[] proBefOpt = new double[64];
		int[] numAftOpt = new int[64];
		double[] proAftOpt = new double[64];
		new ProteinFrame3("ATG",codons,numBefOpt,proBefOpt,numAftOpt,proAftOpt);
	}

	public ProteinFrame3(String seq, String[] codons, int[] numBefOpt, double[] proBefOpt, int[] numAftOpt, double[] proAftOpt) {
		super("Analysis and Prediction");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setBounds((int) width / 4, 0, 795, 768);
		this.setResizable(false);
		
		
		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);
		
		JLayeredPane layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 795, 768);
		
		JPanel panel = new MyPanel();
		panel.setBounds(0, 0, 795, 768);
		layeredPanel.add(panel, new Integer(0));
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 795, 768);
		panel1.setLayout(null);
		panel1.setOpaque(false);
		
		//赋值
		this.codons = codons;
		this.numBefOpt = numBefOpt;
		this.proBefOpt = proBefOpt;
		this.numAftOpt = numAftOpt;
		this.proAftOpt = proAftOpt;
		result = new Object[this.codons.length][5];
		for (int i = 0; i < this.codons.length; i++) {
			this.result[i][0] = this.codons[i];
			this.result[i][1] = this.numBefOpt[i];
			this.result[i][2] = this.proBefOpt[i];
			this.result[i][3] = this.numAftOpt[i];
			this.result[i][4] = this.proAftOpt[i];
		}

		JTextField foldingrate = new JTextField();
		foldingrate.setBounds(33, 60, 715, 33);
		foldingrate.setBorder(BorderFactory.createEmptyBorder());
		panel1.add(foldingrate);
		foldingrate.setHorizontalAlignment(JTextField.CENTER);
		foldingrate.setText(String.format("%.2f", new CodonFolding(seq).getResult()));
		
		JTable table = new JTable(result,new String[]{"","","","",""});
		table.getTableHeader().setVisible(false);
		table.setBounds(0, 194, 795-20, 768-194);
		table.getTableHeader().setReorderingAllowed(false);
		panel1.add(table);
		table.setRowHeight(30);
		JScrollPane jsp = new JScrollPane(table);
		jsp.setBounds(0, 194, 795-20, 768-194);
		panel1.add(jsp);
		
		layeredPanel.add(panel1, new Integer(1));
		getContentPane().add(layeredPanel);
		setVisible(true);
	}

	JPanel panel;
	JLayeredPane layeredPanel;
	JTable table;
	String[] codons;
	int[] numBefOpt;
	double[] proBefOpt;
	int[] numAftOpt;
	double[] proAftOpt;
	Object[][] result;

//	class MyTableModel extends AbstractTableModel {
//		final String[] columnNames = { "codons", "numbers before optimizing",
//				"proportion before optimizing", "numbers after optimizing",
//				"proportion after optimizing" };
//		final Object[][] data = result;
//
//		public int getColumnCount() {
//			return columnNames.length;
//		}
//
//		public int getRowCount() {
//			return data.length;
//		}
//
//		public Object getValueAt(int row, int col) {
//			return data[row][col];
//		}
//	}

	class MyPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6691994734082150758L;

		public MyPanel() {
			setLayout(new FlowLayout());
		}

		public void paint(Graphics g) {
			super.paint(g);
			ImageIcon img = new ImageIcon("codon_analysis.jpg");
			img.getIconWidth();
			img.getIconHeight();
			// System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 795, 768, this);
		}
	}
}
