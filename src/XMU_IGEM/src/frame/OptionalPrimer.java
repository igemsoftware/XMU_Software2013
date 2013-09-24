package frame;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SBOLValidationException;

import codon.Primer;

public class OptionalPrimer extends JFrame {
	public JLayeredPane layeredPanel;

	public JTable table;
	public JButton add;

	public JRadioButton radio1, radio2, radio3, radio4, radio5;
	public int number = -1;

	public CodonFrame1 parent;

	private String[] minp;
	private String[] minq;

	public OptionalPrimer(CodonFrame1 parent) {
		setSize(900, 700);
		this.parent = parent;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);

		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);

		layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 900, 700);

		JPanel panel = new OptionalPrimerImage();
		panel.setBounds(0, 0, 900, 700);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 900, 700);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		table = new JTable(10, 4);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setBounds(192 - 9, 203 - 29, 547, 320);
		table.setRowHeight(32);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
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

		// table.

		ImageIcon addIcon = new ImageIcon("codon/addtfbs.png");
		add = new JButton(addIcon);
		add.addActionListener(new MyAction());
		add.setBounds(644 - 9, 536 - 29, 97, 35);
		panel1.add(add);

		radio1 = new JRadioButton();
		radio1.setSize(40, 40);
		radio1.setBounds(138 - 9, 214 - 29, 50, 50);
		// radio1.setOpaque(false);
		panel1.add(radio1);

		radio2 = new JRadioButton();
		radio2.setSize(40, 40);
		radio2.setBounds(138 - 9, 278 - 29, 50, 50);
		// radio1.setOpaque(false);
		panel1.add(radio2);

		radio3 = new JRadioButton();
		radio3.setSize(40, 40);
		radio3.setBounds(138 - 9, 342 - 29, 50, 50);
		// radio1.setOpaque(false);
		panel1.add(radio3);

		radio4 = new JRadioButton();
		radio4.setSize(40, 40);
		radio4.setBounds(138 - 9, 406 - 29, 50, 50);
		// radio1.setOpaque(false);
		panel1.add(radio4);

		radio5 = new JRadioButton();
		radio5.setSize(40, 40);
		radio5.setBounds(138 - 9, 470 - 29, 50, 50);
		// radio1.setOpaque(false);
		panel1.add(radio5);

		radio1.addActionListener(new MyAction());
		radio2.addActionListener(new MyAction());
		radio3.addActionListener(new MyAction());
		radio4.addActionListener(new MyAction());
		radio5.addActionListener(new MyAction());

		ButtonGroup bg = new ButtonGroup();
		bg.add(radio1);
		bg.add(radio2);
		bg.add(radio3);
		bg.add(radio4);
		bg.add(radio5);

		layeredPanel.add(panel1, new Integer(1));
		getContentPane().add(layeredPanel);
		setVisible(true);

		tovalue();

		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// System.out.println("X:" + e.getXOnScreen()+ "  Y:" +
				// e.getYOnScreen());
				super.mouseMoved(e);
			}
		});
	}

	public void tovalue() {
		String sequence = parent.inputSequence.getText();
		if (parent.type == true) {
			String file = parent.inputSequence.getText();
			try {
				SBOLDocument document = SBOLFactory.read(new FileInputStream(
						file));
				DnaComponent dnaComponent = (DnaComponent) document
						.getContents().iterator().next();
				sequence = dnaComponent.getDnaSequence().getNucleotides();
			} catch (SBOLValidationException | IOException e1) {
				e1.printStackTrace();
			}
		}
		sequence = sequence.toUpperCase();
		TestInput t = new TestInput(sequence);
		if (t.isChangeSuccess() == true) {
			sequence = t.getSeq();
			// System.out.println(sequence);
			Primer p = new Primer(sequence);

			double[] min5 = p.getMin5();
			String[] minp = p.getMinP();
			String[] minq = p.getMinQ();
			this.minp = minp;
			this.minq = minq;
			int[] minppos = p.getMinPPos();
			int[] minqpos = p.getMinQPos();
			for (int i = 0; i < min5.length; i++) {
				table.getModel().setValueAt(minppos[i], 2 * i, 1);
				table.getModel().setValueAt(minqpos[i], 2 * i + 1, 1);

				table.getModel().setValueAt(minp[i].length(), 2 * i, 2);
				table.getModel().setValueAt(minq[i].length(), 2 * i + 1, 2);

				table.getModel().setValueAt(minp[i], 2 * i, 3);
				table.getModel().setValueAt(minq[i], 2 * i + 1, 3);
				// table.getModel().setValueAt(aValue, rowIndex, columnIndex)
				// System.out.println("min["+i+"]="+min5[i]+"\t"+"\t"+minp[i]+"\t"+minq[i]);
			}
		}
	}

	// public static void main(String[] args)
	// {
	// OptionalPrimer app = new OptionalPrimer();
	// }

	class MyAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == add) {
				if (number != -1) {
					String p = OptionalPrimer.this.minp[OptionalPrimer.this.number];
					String q = OptionalPrimer.this.minq[OptionalPrimer.this.number];
					SelectOne selectone = new SelectOne(p, q);
				}
			}
			if (arg0.getSource() == radio1) {
				number = 0;
			}
			if (arg0.getSource() == radio2) {
				number = 1;
			}
			if (arg0.getSource() == radio3) {
				number = 2;
			}
			if (arg0.getSource() == radio4) {
				number = 3;
			}
			if (arg0.getSource() == radio5) {
				number = 4;
			}
		}
	}

	class OptionalPrimerImage extends JPanel {
		public OptionalPrimerImage() {
			setLayout(new FlowLayout());
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			ImageIcon img = new ImageIcon("codon/codon2.jpg");
			int width = img.getIconWidth();
			int height = img.getIconHeight();
			// System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 900, 613, this);
		}
	}
}
