package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class ProteinFrame3 extends JFrame {
	public static void main(String[] args) {
		String[] codons = new String[64];
		int[] numBefOpt = new int[64];
		double[] proBefOpt = new double[64];
		int[] numAftOpt = new int[64];
		double[] proAftOpt = new double[64];
		new ProteinFrame3(codons,numBefOpt,proBefOpt,numAftOpt,proAftOpt);
	}

	public ProteinFrame3(String[] codons, int[] numBefOpt, double[] proBefOpt, int[] numAftOpt, double[] proAftOpt) {
		super("Analysis and Prediction");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setBounds((int) width / 4, 0, (int) width / 2, (int) height);
		
		
		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);
		
		panel = new JPanel();
		panel.setBounds(this.getBounds());
		this.add(panel);
		
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

		table = new JTable(result, new String[]{ "codons", "numbers before optimizing",
			"proportion before optimizing", "numbers after optimizing",
			"proportion after optimizing" });
		table.setPreferredScrollableViewportSize(new Dimension(this.getWidth(),
				this.getHeight()));
		table.setAutoResizeMode(table.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setRowMargin(2);
		panel.add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	JPanel panel;
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

//	class MyPanel extends JPanel {
//		public MyPanel() {
//			setLayout(new FlowLayout());
//		}
//
//		public void paint(Graphics g) {
//			super.paint(g);
//			ImageIcon img = new ImageIcon("codon_analysis.jpg");
//			int width = img.getIconWidth();
//			int height = img.getIconHeight();
//			// System.out.println(width + "," + height);
//			g.drawImage(img.getImage(), 0, 0, (int) width / 2, 86, this);
//		}
//	}
}
