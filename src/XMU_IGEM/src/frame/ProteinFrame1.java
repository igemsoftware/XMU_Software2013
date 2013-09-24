package frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.DnaSequence;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SBOLValidationException;
import protein.Algorithm;
import protein.Algorithm2;

public class ProteinFrame1 extends JFrame {
	public JLayeredPane layeredPanel;

	public JComboBox<String> method;
	public JComboBox<String> hostcell;
	public JTextArea input;
	public JButton upload;
	public JButton valuesGA;
	public JButton go;
	public JTextArea output;
	public JButton sbol;

	String s3;
	boolean isSbolFile = false;

	int population = 200; // 种群大小
	int daishu = 100; // 遗传代数
	double crossRate = 0.1; // 交叉概率
	double varRate = 0.1;

	// 最终各种结果
	public String[] finalCodon;
	private String[] codons;
	private int[] numBefOpt;
	private double[] proBefOpt;
	private int[] numAftOpt;
	private double[] proAftOpt;

	GAFrame ga = null;

	public JFileChooser jfileChooser;

	public ProteinFrame1() {
		super("protein");

		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(530, 800);
		setLocation((int) (width - this.getWidth()) / 2, 0);

		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);

		layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 530, 820);

		JPanel panel = new ProteinImage();
		panel.setBounds(0, 0, 530, 820);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 530, 820);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		String[] methodValue = new String[] {
				"Method1(Generatic Algorithm, recommend)",
				"Method2(test, not recommend)" };
		method = new JComboBox<String>(methodValue);
		method.setBounds(170 - 9, 157 - 29, 333, 34);
		method.setFocusable(false);
		method.setBackground(Color.WHITE);
		panel1.add(method);

		String[] hcvalue = new String[] { "ECOLI", "PPASTORIS", "LLACTIS",
				"SCEREVISIZE" };
		hostcell = new JComboBox<String>(hcvalue);
		hostcell.setBounds(170 - 9, 210 - 29, 333, 34);
		hostcell.setFocusable(false);
		hostcell.setBackground(Color.WHITE);
		panel1.add(hostcell);

		input = new JTextArea();
		input.setBounds(170 - 9, 262 - 29, 300, 57);
		panel1.add(input);

		ImageIcon uploadIcon = new ImageIcon("upload.png");
		upload = new JButton(uploadIcon);
		upload.setBounds(470 - 9, 262 - 29, 32, 57);
		jfileChooser = new JFileChooser();
		upload.addActionListener(new MyAction());
		panel1.add(upload);

		ImageIcon valuesGAIcon = new ImageIcon("valuesGA.png");
		valuesGA = new JButton(valuesGAIcon);
		valuesGA.addActionListener(new MyAction());
		valuesGA.setBounds(170 - 9, 337 - 29, 197, 45);
		panel1.add(valuesGA);

		ImageIcon goIcon = new ImageIcon("go.png");
		go = new JButton(goIcon);
		go.addActionListener(new MyAction());
		go.setBounds(170 - 9, 416 - 29, 197, 49);
		panel1.add(go);

		output = new JTextArea();
		output.setLineWrap(true);
		output.setBounds(43 - 9, 544 - 29, 454, 105);
		panel1.add(output);
		JScrollPane jsp = new JScrollPane(output);
		jsp.setBounds(43 - 9, 544 - 29, 454, 105);
		panel1.add(jsp);

		sbol = new JButton("SBOL");
		sbol.addActionListener(new MyAction());
		sbol.setBounds(374 - 9, 677 - 29, 104, 28);
		panel1.add(sbol);

		layeredPanel.add(panel1, new Integer(1));
		getContentPane().add(layeredPanel);
		setVisible(true);

		// this.addMouseMotionListener(new MouseAdapter()
		// {
		// @Override
		// public void mouseMoved(MouseEvent e)
		// {
		// System.out.println("X:" + e.getXOnScreen()+ "  Y:" +
		// e.getYOnScreen());
		// super.mouseMoved(e);
		// }
		// });
	}

	public boolean checkgo() {
		if (s3.equals("") || s3 == null)
			return false;
		return true;
	}

	public static void main(String[] args) {
		ProteinFrame1 pf = new ProteinFrame1();
	}

	class ProteinImage extends JPanel {
		public ProteinImage() {
			setLayout(new FlowLayout());
		}

		public void paint(Graphics g) {
			super.paint(g);
			ImageIcon img = new ImageIcon("protein1.jpg");
			int width = img.getIconWidth();
			int height = img.getIconHeight();
			// System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 530, 770, this);
		}
	}

	private class MyAction implements ActionListener {
		private void toSBOL(String seq) {
			DnaComponent dnaComponent = SBOLFactory.createDnaComponent();
			dnaComponent
					.setURI(URI.create("http://example.com/MyDnaComponent"));
			dnaComponent.setDisplayId("MyDnaComponent");
			dnaComponent.setName("myDNA");
			dnaComponent.setDescription("this is dna sequence");
			DnaSequence dnaSequence = SBOLFactory.createDnaSequence();
			dnaSequence.setURI(URI.create("http://example.com/MyDnaSequence"));

			seq = seq.replace("\n", "");
			seq = seq.replace(" ", "");
			dnaSequence.setNucleotides(seq);
			dnaComponent.setDnaSequence(dnaSequence);

			SBOLDocument document = SBOLFactory.createDocument();

			document.addContent(dnaComponent);

			File outputFile = new File("proteinfile/"
					+ dnaComponent.getDisplayId() + ".xml");
			OutputStream output = null;
			try {
				output = new FileOutputStream(outputFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				SBOLFactory.write(document, output);
			} catch (SBOLValidationException | IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == upload) {
				int intRetVal = jfileChooser.showOpenDialog(ProteinFrame1.this);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					input.setText(jfileChooser.getSelectedFile().getPath());
					isSbolFile = true;
				}
			}
			if (arg0.getSource() == valuesGA) {
				ProteinFrame1.this.setEnabled(false);
				ga = new GAFrame(ProteinFrame1.this);
			}
			if (arg0.getSource() == go) {
				if (!isSbolFile)
					s3 = ProteinFrame1.this.input.getText();
				else {
					String file = ProteinFrame1.this.input.getText();
					try {
						SBOLDocument document = SBOLFactory
								.read(new FileInputStream(file));
						DnaComponent dnaComponent = (DnaComponent) document
								.getContents().iterator().next();
						s3 = dnaComponent.getDnaSequence().getNucleotides();
					} catch (SBOLValidationException | IOException e) {
						e.printStackTrace();
					}
				}
				s3 = s3.toUpperCase();
				TestInput t = new TestInput(s3);
				if (t.isChangeSuccess() == true) {
					s3 = t.getSeq();
					String cell = (String) ProteinFrame1.this.hostcell
							.getSelectedItem();
					if (checkgo()) {
						if (ProteinFrame1.this.method.getSelectedIndex() == 0) {
							Algorithm al = new Algorithm(s3, cell, population,
									daishu, crossRate, varRate);
							finalCodon = al.getFinalCodon(); // 这是最后的结果
							codons = al.getSerialCodons();
							numBefOpt = al.getCountOfPrimaryCodons();
							proBefOpt = al.getProportionOfPrimaryCodons();
							numAftOpt = al.getCountOfFinalCodons();
							proAftOpt = al.getProportionOfFinalCodons();
						} else if (ProteinFrame1.this.method.getSelectedIndex() == 1) {
							Algorithm2 al2 = new Algorithm2(s3, cell,
									population, daishu, crossRate, varRate);
							finalCodon = al2.getFinalCodon();
							codons = al2.getSerialCodons();
							numBefOpt = al2.getCountOfPrimaryCodons();
							proBefOpt = al2.getProportionOfPrimaryCodons();
							numAftOpt = al2.getCountOfFinalCodons();
							proAftOpt = al2.getProportionOfFinalCodons();
						}
						String output = "";
						for (int i = 0; i < finalCodon.length; i++) {
							output += finalCodon[i];
						}
						ProteinFrame1.this.output.setText(output);
						new ProteinFrame2(output, codons, numBefOpt, proBefOpt,
								numAftOpt, proAftOpt);
					} else {
						JOptionPane.showMessageDialog(ProteinFrame1.this,
								"wrong input");
					}
				}
			}
			if (arg0.getSource() == sbol) {
				toSBOL(ProteinFrame1.this.output.getText().toLowerCase());
			}
		}
	}
}
