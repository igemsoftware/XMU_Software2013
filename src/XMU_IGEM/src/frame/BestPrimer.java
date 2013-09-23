package frame;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTable;

public class BestPrimer extends JFrame {
	public JLayeredPane layeredPanel;

	public JTable table;

	// Object[][] p = {{"1121", "Arac", "ATGCATGC"}, {"1122", "Arbc",
	// "ATGCTAGC"}, {"1123", "Arer", "ATGCTTTC"}};

	public SelectOne parent;

	public BestPrimer(SelectOne parent) {

		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(900, 700);
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
		layeredPanel.setBounds(0, 0, 900, 700);

		JPanel panel = new BestPrimerImage();
		panel.setBounds(0, 0, 900, 700);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 900, 700);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		// Mytable mt = new Mytable();
		table = new JTable(10, 6);
		table.setBounds(173 - 9, 201 - 29, 590, 320);
		table.setRowHeight(32);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		panel1.add(table);
		table.getModel().setValueAt("Forward", 0, 0);
		table.getModel().setValueAt("Reverse", 1, 0);
		table.getModel().setValueAt("Forward", 2, 0);
		table.getModel().setValueAt("Reverse", 3, 0);
		table.getModel().setValueAt("Forward", 4, 0);
		table.getModel().setValueAt("Reverse", 5, 0);
		table.getModel().setValueAt("Forward", 6, 0);
		table.getModel().setValueAt("Reverse", 7, 0);
		table.getModel().setValueAt("Forward", 8, 0);
		table.getModel().setValueAt("Reverse", 9, 0);

		layeredPanel.add(panel1, new Integer(1));
		getContentPane().add(layeredPanel);
		setVisible(true);

		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				System.out.println("X:" + e.getXOnScreen() + "  Y:"
						+ e.getYOnScreen());
				super.mouseMoved(e);
			}
		});
	}

	public static void main(String[] args) {
		// BestPrimer app = new BestPrimer();
	}

	class BestPrimerImage extends JPanel {
		public BestPrimerImage() {
			setLayout(new FlowLayout());
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			ImageIcon img = new ImageIcon("codon/bestprimer.jpg");
			int width = img.getIconWidth();
			int height = img.getIconHeight();
			System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 900, 613, this);
		}
	}
}
