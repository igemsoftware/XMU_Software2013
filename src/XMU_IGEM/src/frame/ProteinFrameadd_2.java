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
import javax.swing.table.TableColumn;

import protein.Algorithm;
import protein.Cell;

public class ProteinFrameadd_2 extends JFrame {

	private String[] name;
	private String[] content;
	private JButton ok;

	private String input;
	private String cell;
	private int population;
	private int daishu;
	private double crossRate;
	private double varRate;
	private boolean[] map;

	private JTable table;

	private ProteinFrame1 proteinFrame1;

	public ProteinFrameadd_2(ProteinFrame1 proteinFrame1, String input,
			String cell, int population, int daishu, double crossRate,
			double varRate) {
		super();

		setTitle("Chooser");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		
		// 为各项参数赋值
		this.input = input;
		this.cell = cell;
		this.population = population;
		this.daishu = daishu;
		this.crossRate = crossRate;
		this.varRate = varRate;
		map = new boolean[15];
		this.proteinFrame1 = proteinFrame1;

		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(500, 481);
		setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);

		JLayeredPane layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 500, 481);

		JPanel panel = new MyPanel();
		panel.setBounds(0, 0, 500, 481);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 500, 481);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		String[] name = { "ApaI", "BamHI", "Bg1II", "EcoRI", "HindIII", "KpnI",
				"NcoI", "NdeI", "NheI", "NotI", "SacI", "Sa1I", "SphI", "XbaI",
				"XhoI" };
		this.name = name;
		String[] content = { "GGGCCC", "GGATCC", "AGATCT", "GAATTC", "AAGCTT",
				"GGTACC", "CCATGG", "CATATG", "GCTAGC", "GCGGCCGC", "GAGCTC",
				"GTCGAC", "GCATGC", "TCTAGA", "CTCGAG" };
		this.content = content;
		Object showThing[][] = new Object[this.name.length][2];
		for (int i = 0; i < showThing.length; i++) {
			showThing[i][0] = this.name[i];
			showThing[i][1] = this.content[i];
		}

		// 下面加入表格
		table = new JTable(15, 3);

		// 向table中添加数据
		for (int i = 0; i < 15; i++) {
			table.getModel().setValueAt(this.name[i], i, 1);
			table.getModel().setValueAt(this.content[i], i, 2);
		}
		TableColumn aColumn = table.getColumnModel().getColumn(0);
		aColumn.setCellEditor(table.getDefaultEditor(Boolean.class));
		aColumn.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		table.getTableHeader().setVisible(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setBounds(50, 127, 400, 270);
		table.getTableHeader().setReorderingAllowed(false);
		panel1.add(table);
		table.setRowHeight(30);
		JScrollPane jsp = new JScrollPane(table);
		jsp.setBounds(50, 127, 400, 270);
		panel1.add(jsp);

		// 下面加入按钮
		ImageIcon okImage = new ImageIcon("enzyme_ok.jpg");
		ok = new JButton(okImage);
		ok.setBounds(312, 412, 98, 42);
		ok.addActionListener(new MyAction());
		panel1.add(ok);

		layeredPanel.add(panel1, new Integer(1));
		getContentPane().add(layeredPanel);

		setVisible(true);
	}

	class MyPanel extends JPanel {
		public MyPanel() {
			setLayout(new FlowLayout());
		}

		public void paint(Graphics g) {
			super.paint(g);

			ImageIcon img = new ImageIcon("ignoredenzyme.jpg");
			img.getIconWidth();
			img.getIconHeight();
			// System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 500, 481, this);
		}
	}

	class MyAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

			if (arg0.getSource() == ok) {
				boolean[] map = new boolean[15];
				for (int i = 0; i < 15; i++) {
					if (ProteinFrameadd_2.this.table.getValueAt(i, 0) != null)
						map[i] = (boolean) ProteinFrameadd_2.this.table
								.getValueAt(i, 0);
				}
				int count = 0;
				for (int i = 0; i < map.length; i++) {
					if (map[i] == true)
						count++;
				}
				String str[] = new String[count];
				for (int i = 0, k = 0; i < str.length; k++) {
					if (map[k] == true) {
						str[i] = content[k];
						i++;
					}
				}
				Algorithm al = new Algorithm(input, cell, population, daishu,
						crossRate, varRate, str);
				String[] finalCodon = al.getFinalCodon(); // 这是最后的结果
				String[] codons = al.getSerialCodons();
				int[] numBefOpt = al.getCountOfPrimaryCodons();
				double[] proBefOpt = al.getProportionOfPrimaryCodons();
				int[] numAftOpt = al.getCountOfFinalCodons();
				double[] proAftOpt = al.getProportionOfFinalCodons();
				String output = "";
				for (int i = 0; i < finalCodon.length; i++) {
					output += finalCodon[i];
				}
				proteinFrame1.output.setText(output);
				new ProteinFrame2(output, codons, numBefOpt, proBefOpt,
						numAftOpt, proAftOpt);
			}
		}
	}
}
