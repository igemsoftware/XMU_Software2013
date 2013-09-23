package frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SBOLValidationException;

import ttec.AlgorithmOne;
import ttec.AlgorithmTwo;
import ttec.ErrorDisplay;
import ttec.IO;
import ttec.OverallManager;

public class EfficiencyFrame extends JFrame {
	public JLayeredPane layeredPanel;

	public JTextField sequence;
	public JButton example;
	public JButton browser;
	public JButton run;
	public JLabel message;
	public JLabel efficiency;
	public JLabel structure;
	public JRadioButton forword;
	public JRadioButton reverse;

	public String type;

	String seqs;
	String matches;

	public JFileChooser jfileChooser;

	public EfficiencyFrame(String str) {
		super("efficiency structure");

		this.type = str;

		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(900, 585);
		setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);

		jfileChooser = new JFileChooser();

		layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 900, 585);

		JPanel panel = new EfficiencyImage();
		panel.setBounds(0, 0, 900, 585);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 900, 585);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		sequence = new JTextField();
		sequence.setBounds(175 - 9, 193 - 29, 486, 32);
		sequence.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panel1.add(sequence);

		if (this.type.equals("ttec")) {
			ImageIcon exampleIcon = new ImageIcon("ttec/example.png");
			example = new JButton(exampleIcon);
			example.addActionListener(new MyAction());
			example.setBounds(674 - 9, 190 - 29, 100, 36);
			panel1.add(example);
		}

		else {
			browser = new JButton("browser");
			browser.addActionListener(new MyAction());
			browser.setBounds(674 - 9, 190 - 29, 100, 36);
			panel1.add(browser);
		}

		message = new JLabel();
		message.setBounds(545, 246, 300, 36);
		panel1.add(message);

		run = new JButton("run");
		run.addActionListener(new MyAction());
		run.setBounds(777 - 9, 190 - 29, 100, 36);
		panel1.add(run);

		forword = new JRadioButton();
		forword.setOpaque(false);
		forword.setBounds(306 - 9, 245 - 29, 20, 20);
		panel1.add(forword);

		reverse = new JRadioButton();
		reverse.setOpaque(false);
		reverse.setBounds(407 - 9, 245 - 29, 20, 20);
		panel1.add(reverse);

		ButtonGroup bg = new ButtonGroup();
		bg.add(forword);
		bg.add(reverse);

		efficiency = new JLabel();
		efficiency.setBounds(308 - 9, 287 - 29, 100, 40);
		panel1.add(efficiency);

		structure = new JLabel();
		structure.setBounds(308 - 9, 330 - 29, 200, 200);
		panel1.add(structure);

		layeredPanel.add(panel1, new Integer(1));
		getContentPane().add(layeredPanel);
		setVisible(true);

		AlgorithmTwo.setStackData();
		AlgorithmTwo.setTStackIData();
		AlgorithmTwo.setTStackHData();
		AlgorithmTwo.setLoopSizeData();
		AlgorithmTwo.setTriloopData();
		AlgorithmTwo.setTetraloopData();
		AlgorithmTwo.setInterior_1x2_Data();
		AlgorithmTwo.setInterior2x2Data();
		// this.addMouseMotionListener(new MouseAdapter()
		// {
		// @Override
		// public void mouseMoved(MouseEvent e)
		// {
		// super.mouseMoved(e);
		// System.out.println("X:" + e.getXOnScreen() + "  Y:"
		// + e.getYOnScreen());
		//
		// }
		// });
	}

	// public static void main(String[] args)
	// {
	// EfficiencyFrame app = new EfficiencyFrame();
	// }

	private class MyAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == example) {
				sequence.setText("AAAAAAACCCCGCGGTTTTCCGCGGGGTTTTTTTTTTTTTTTTTTT");
			}
			if (e.getSource() == browser) {
				int intRetVal = jfileChooser
						.showOpenDialog(EfficiencyFrame.this);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					sequence.setText(jfileChooser.getSelectedFile().getPath());
				}
			}
			if (e.getSource() == run) {
				int j = 0;
				String seq = "";
				if (EfficiencyFrame.this.type.equals("sbol")) {
					String file = EfficiencyFrame.this.sequence.getText();
					try {
						SBOLDocument document = SBOLFactory
								.read(new FileInputStream(file));
						DnaComponent dnaComponent = (DnaComponent) document
								.getContents().iterator().next();
						seq = dnaComponent.getDnaSequence().getNucleotides();
					} catch (SBOLValidationException | IOException e1) {
						e1.printStackTrace();
					}
				}

				else
					seq = EfficiencyFrame.this.sequence.getText();
				if (seq.length() <= 14) {
					EfficiencyFrame.this.message
							.setText("Please input correct sequence!");
					EfficiencyFrame.this.efficiency.setText("00%");
				} else {
					for (int i = 0; i < seq.length(); i++)

						if (seq.charAt(i) != ' ' && seq.charAt(i) != 'u'
								&& seq.charAt(i) != 'c' && seq.charAt(i) != 'g'
								&& seq.charAt(i) != 't' && seq.charAt(i) != 'a'
								&& seq.charAt(i) != 'U' && seq.charAt(i) != 'C'
								&& seq.charAt(i) != 'G' && seq.charAt(i) != 'T'
								&& seq.charAt(i) != 'A') {
							message.setText("Please input correct sequence!");
							efficiency.setText("00%");
							j = 1;
							break;
						}
					if (j == 0) {
						seq = seq.toUpperCase();
						seq = seq.trim();
						if (forword.isSelected() == true)
							IO.Algorithm_Two(seq, true, false, "");
						else {
							IO.Algorithm_Two(seq, false, false, "");
							seq = OverallManager.getForwardTerminatorSequence(
									seq, false);
						}

						if (AlgorithmOne.Warn == false) {

							// setSeqs(seq);
							// setMatches(IO.structure);
							seqs = seq;
							matches = IO.structure;

							efficiency.setText(IO.efficiency + "%");
							structure.setText(IO.structure);
							message.setText("");

						} else {
							message.setText(ErrorDisplay.Warning);
							efficiency.setText("00%");
						}
					}
				}
			}
		}

	}

	class EfficiencyImage extends JPanel {
		public EfficiencyImage() {
			setLayout(new FlowLayout());
		}

		public void paint(Graphics g) {
			super.paint(g);
			ImageIcon img = new ImageIcon("ttec/efficiencyframe.jpg");
			int width = img.getIconWidth();
			int height = img.getIconHeight();
			// System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 900, 565, this);
		}
	}
}
