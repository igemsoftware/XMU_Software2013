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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SBOLValidationException;

import codon.Rbs;

public class RBSFrame extends JFrame {
	public JFileChooser jfileChooser;

	public boolean type;

	public JLayeredPane layeredPanel;

	public JTextField input;
	public JComboBox choice;
	public JButton sbol;
	public JButton cal;

	public JTextArea sdSeq;
	public JTextArea site;
	public JTextArea startCodon;
	public JTextArea strength;

	public RBSFrame() {
		super("RBS");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(900, 556);
		setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// this.setResizable(false);
		getContentPane().setLayout(null);

		jfileChooser = new JFileChooser();
		type = false;

		layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 900, 556);

		JPanel panel = new RBSImage();
		panel.setBounds(0, 0, 900, 556);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 900, 556);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		input = new JTextField();
		input.setBounds(102 - 9, 128, 337, 52);
		input.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panel1.add(input);

		choice = new JComboBox();
		choice.setBounds(435, 128, 152, 52);
		choice.setBorder(BorderFactory.createLineBorder(Color.white));
		choice.setBackground(Color.white);
		choice.setOpaque(false);
		panel1.add(choice);
		choice.addItem("ATG");
		choice.addItem("TTG");
		choice.addItem("GTG");
		choice.addItem("NULL");

		ImageIcon sbolicon = new ImageIcon("rbssbol.png");
		sbol = new JButton(sbolicon);
		sbol.addActionListener(new MyAction());
		sbol.setBounds(608 - 9, 159 - 29, 100, 47);
		panel1.add(sbol);

		ImageIcon calicon = new ImageIcon("rbscal.png");
		cal = new JButton(calicon);
		cal.addActionListener(new MyAction());
		cal.setBounds(715 - 9, 159 - 29, 100, 47);
		panel1.add(cal);

		sdSeq = new JTextArea();
		sdSeq.setBounds(116 - 9, 345 - 29, 152, 79);
		sdSeq.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panel1.add(sdSeq);

		site = new JTextArea();
		site.setBounds(295 - 9, 345 - 29, 146, 79);
		site.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panel1.add(site);

		startCodon = new JTextArea();
		startCodon.setBounds(467 - 9, 345 - 29, 162, 79);
		startCodon.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panel1.add(startCodon);

		strength = new JTextArea();
		strength.setBounds(663 - 9, 345 - 29, 142, 79);
		strength.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panel1.add(strength);

		layeredPanel.add(panel1, new Integer(1));
		getContentPane().add(layeredPanel);
		setVisible(true);

		// this.addMouseMotionListener(new MouseAdapter() {
		// @Override
		// public void mouseMoved(MouseEvent e) {
		// System.out.println("X:" + e.getXOnScreen() + "  Y:"
		// + e.getYOnScreen());
		// super.mouseMoved(e);
		// }
		// });
	}

	public static void main(String[] args) {
		RBSFrame rbs = new RBSFrame();
	}

	private class MyAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == sbol) {
				int intRetVal = jfileChooser.showOpenDialog(RBSFrame.this);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					input.setText(jfileChooser.getSelectedFile().getPath());
				}
				type = true;
			}
			if (arg0.getSource() == cal) {
				String seq = RBSFrame.this.input.getText();
				if (type == true) {
					String file = RBSFrame.this.input.getText();
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
				seq = seq.toUpperCase();
				TestInput t = new TestInput(seq);
				if (t.isChangeSuccess() == true) {
					seq = t.getSeq();
					String selected = (String) RBSFrame.this.choice
							.getSelectedItem();
					if (selected.equals("NULL"))
						selected = null;
					Rbs r = new Rbs(seq,
							(String) RBSFrame.this.choice.getSelectedItem());
					RBSFrame.this.sdSeq.setText(r.getSeq());
					RBSFrame.this.sdSeq.setText(seq.substring(
							r.getBestStartPoint() - 1, r.getBestStartPoint()
									- 1 + r.getBestSpaceLength()));
					RBSFrame.this.site.setText(String.valueOf(r
							.getBestSpaceLength()));
					RBSFrame.this.startCodon.setText(String.valueOf(r
							.getBestStartPoint()));
					RBSFrame.this.strength.setText(String.valueOf(r
							.getSimilarity()));

					// System.out.println(r.getBestStartPoint() + "\t"
					// + r.getBestSpaceLength() + "\t" + r.getSeq() + "\t"
					// + r.getSimilarity());
				}
			}
		}
	}

	class RBSImage extends JPanel {
		public RBSImage() {
			setLayout(new FlowLayout());
		}

		public void paint(Graphics g) {
			super.paint(g);
			ImageIcon img = new ImageIcon("RBS.jpg");
			int width = img.getIconWidth();
			int height = img.getIconHeight();
			// System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, width, height, this);
		}
	}
}
