package frame;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import codon.Primer2;
import frame.OptionalPrimer.OptionalPrimerImage;

public class SelectOne extends JFrame {
	public JLayeredPane layeredPanel;

	public JTable table;
	public JButton ok;

	public OptionalPrimer parent;

	Object[][] v;

	// {{"1121", "Arac", "ATGCATGC"}, {"1122", "Arbc", "ATGCTAGC"}, {"1123",
	// "Arer", "ATGCTTTC"}};

	public SelectOne(OptionalPrimer parent) {

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

		this.parent = parent;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);

		layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 900, 700);

		JPanel panel = new SelectOneImage();
		panel.setBounds(0, 0, 900, 700);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 900, 700);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		tovalue();

		Mytable mt = new Mytable();
		table = new JTable(mt);
		table.setBounds(245 - 9, 162 - 29, 459, 370);
		table.setRowHeight(32);
		panel1.add(table);

		JScrollPane jsp = new JScrollPane(table);
		jsp.setBounds(246 - 9, 162 - 29, 459, 370);
		panel1.add(jsp);

		ImageIcon okIcon = new ImageIcon(
				"codon/ok.png");
		ok = new JButton(okIcon);
		ok.addActionListener(new MyAction());
		ok.setBounds(603 - 9, 537 - 29, 97, 35);
		panel1.add(ok);

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

	public void tovalue() {
		Primer2 p = new Primer2();
		String[] id = p.getId();
		String[] name = p.getName();
		String[] seq = p.getSeq();
		v = new Object[id.length][3];
		for (int i = 0; i < id.length; i++) {
			// table.getModel().setValueAt(id[i], i, 0);
			// table.getModel().setValueAt(name[i], i, 1);
			// table.getModel().setValueAt(seq[i], i, 2);
			v[i][0] = id[i];
			v[i][1] = name[i];
			v[i][2] = seq[i];
		}
		// table.getModel().setValueAt(, rowIndex, columnIndex)
		for (int i = 0; i < id.length; i++)
			System.out.println(id[i] + "\t" + name[i] + "\t" + seq[i]);

	}

	// public static void main(String[] args)
	// {
	// SelectOne app = new SelectOne();
	// }

	class MyAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == ok) {
				BestPrimer bestPrimer = new BestPrimer(SelectOne.this);
			}
		}
	}

	class Mytable extends AbstractTableModel {

		String[] colname = { "ID", "TF", "TFBS" };

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public int getRowCount() {
			return v.length;
		}

		@Override
		public String getColumnName(int column) {
			return colname[column];
		}

		@Override
		public Object getValueAt(int row, int col) {
			return v[row][col];
		}

	}

	class SelectOneImage extends JPanel {
		public SelectOneImage() {
			setLayout(new FlowLayout());
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			ImageIcon img = new ImageIcon(
					"codon/selectone.jpg");
			int width = img.getIconWidth();
			int height = img.getIconHeight();
			System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 900, 613, this);
		}
	}
}
