package frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SBOLValidationException;

import codon.NotSigmaSimilarity;
import codon.SeqSimilarity;

public class CodonFrame1 extends JFrame implements MouseMotionListener,
		ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 415562195241152444L;
	public JFileChooser jfileChooser;
	public boolean type;

	public JTextField inputSequence;
	public JButton cal;
	public JRadioButton high, median, low;
	public JTextField input;
	public JButton sbol;
	public JButton promoter;

	public JTextField sigmafactors;
	public JTextField site;
	public JTextField spacer;
	public JTextField strength;

	public JList<String> tf;
	public DefaultListModel<String> tfModel;
	public JList<Integer> sitelist;
	public DefaultListModel<Integer> siteModel;
	public JList<String> motif;
	public DefaultListModel<String> motifModel;
	public JList<Double> mss;
	public DefaultListModel<Double> mssModel;

	public JScrollPane listScrollPane;
	public JScrollPane tfScr;
	public JScrollPane motifScr;
	public JScrollPane siteScr;

	public JLayeredPane layeredPanel;

	public CodonFrame1() {
		super("codon");

		Locale.setDefault(Locale.US);
		
		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(900, 820);
		setLocation((int) (width - this.getWidth()) / 2, 0);

		// 设置图标
		Image logo = Toolkit.getDefaultToolkit().getImage(
				"Xmusoftware_iGEM.png");
		this.setIconImage(logo);

		this.setResizable(false);
		type = false;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		jfileChooser = new JFileChooser();

		layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 900, 820);

		JPanel panel = new MyImage();
		panel.setBounds(0, 0, 900, 820);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 900, 820);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		inputSequence = new JTextField(10);
		inputSequence.setBounds(157 - 9, 176 - 29, 486, 32);
		inputSequence.setBorder(javax.swing.BorderFactory
				.createLineBorder(Color.WHITE));
		panel1.add(inputSequence);

		ImageIcon calIcon = new ImageIcon("codon/calculate.png");
		cal = new JButton(calIcon);
		cal.addActionListener(this);
		cal.setBounds(657 - 9, 173 - 29, 98, 36);
		panel1.add(cal);

		high = new JRadioButton();
		median = new JRadioButton();
		low = new JRadioButton();
		high.setBounds(259 - 9, 224 - 29, 30, 30);
		high.setOpaque(false);
		panel1.add(high);
		median.setBounds(340 - 9, 224 - 29, 30, 30);
		median.setOpaque(false);
		panel1.add(median);
		low.setBounds(443 - 9, 224 - 29, 30, 30);
		low.setOpaque(false);
		panel1.add(low);
		ButtonGroup bg = new ButtonGroup();
		bg.add(high);
		bg.add(median);
		bg.add(low);
		high.addActionListener(this);
		median.addActionListener(this);
		low.addActionListener(this);

		input = new JTextField(5);
		input.setBounds(552 - 9, 224 - 29, 92, 33);
		input.setBorder(javax.swing.BorderFactory.createLineBorder(Color.WHITE));
		panel1.add(input);

		ImageIcon sbolIcon = new ImageIcon("codon/sbol.png");
		sbol = new JButton(sbolIcon);
		sbol.addActionListener(this);
		sbol.setBounds(657 - 9, 222 - 29, 98, 36);
		panel1.add(sbol);

		ImageIcon promoterIcon = new ImageIcon("codon/primer.png");
		promoter = new JButton(promoterIcon);
		promoter.addActionListener(this);
		promoter.setBounds(658 - 9, 269 - 29, 138, 36);
		panel1.add(promoter);

		sigmafactors = new JTextField(15);
		sigmafactors.setBounds(274 - 9, 335 - 29, 473, 29);
		sigmafactors.setBorder(javax.swing.BorderFactory
				.createLineBorder(Color.WHITE));
		sigmafactors.setFocusable(false);
		panel1.add(sigmafactors);

		site = new JTextField(5);
		site.setBounds(182 - 9, 375 - 29, 117, 30);
		site.setBorder(javax.swing.BorderFactory.createLineBorder(Color.WHITE));
		site.setFocusable(false);
		panel1.add(site);

		spacer = new JTextField(5);
		spacer.setBounds(377 - 9, 375 - 29, 117, 30);
		spacer.setBorder(javax.swing.BorderFactory
				.createLineBorder(Color.WHITE));
		spacer.setFocusable(false);
		panel1.add(spacer);

		strength = new JTextField(5);
		strength.setBounds(581 - 9, 375 - 29, 117+8, 30);
		strength.setBorder(javax.swing.BorderFactory
				.createLineBorder(Color.WHITE));
		strength.setFocusable(false);
		panel1.add(strength);

		tfModel = new DefaultListModel<String>();
		tf = new JList<String>(tfModel);
		tf.setCellRenderer(new MyCellRenderer());
		tf.setSelectionBackground(Color.GRAY);
		tf.setBounds(144 - 9, 498 - 29, 97, 226);
		tf.setFixedCellHeight(42);
		tf.setFixedCellWidth(97);
		// listScrollPane.add(tf);
		panel1.add(tf);

		tfScr = new JScrollPane(tf);
		tfScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tfScr.setBounds(144 - 9, 498 - 29, 97 + 4, 226);
		tfScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		panel1.add(tfScr);

		siteModel = new DefaultListModel<Integer>();
		sitelist = new JList<Integer>(siteModel);
		sitelist.setCellRenderer(new MyCellRenderer());
		sitelist.setSelectionBackground(Color.GRAY);
		sitelist.setBounds(244 - 9, 498 - 29, 161, 226);
		sitelist.setFixedCellHeight(42);
		sitelist.setFixedCellWidth(160);
		panel1.add(sitelist);

		siteScr = new JScrollPane(sitelist);
		siteScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		siteScr.setBounds(244 - 9, 498 - 29, 161 + 4, 226);
		siteScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		panel1.add(siteScr);

		motifModel = new DefaultListModel<String>();
		motif = new JList<String>(motifModel);
		motif.setCellRenderer(new MyCellRenderer());
		motif.setSelectionBackground(Color.GRAY);
		motif.setBounds(411 - 9, 498 - 29, 194, 228);
		motif.setFixedCellHeight(42);
		motif.setFixedCellWidth(194);
		panel1.add(motif);

		motifScr = new JScrollPane(motif);
		motifScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		motifScr.setBounds(411 - 9, 498 - 29, 194 + 4, 228);
		motifScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		panel1.add(motifScr);

		mssModel = new DefaultListModel<Double>();
		mss = new JList<Double>(mssModel);
		mss.setCellRenderer(new MyCellRenderer());
		mss.setBounds(611 - 9, 498 - 29, 180, 226);
		mss.setFixedCellHeight(42);
		mss.setFixedCellWidth(145);
		panel1.add(mss);

		listScrollPane = new JScrollPane(mss);
		listScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setBounds(611 - 9, 498 - 29, 180 + 7, 226);
		listScrollPane.getVerticalScrollBar().addMouseMotionListener(this);
		panel1.add(listScrollPane);

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

		// @Override
		// public void mouseDragged(MouseEvent e)
		// {
		// super.mouseDragged(e);
		// int value = listScrollPane.getVerticalScrollBar().getValue();
		//
		// }
	}

	public static void main(String[] args) {
		new CodonFrame1();
	}

	class MyImage extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2598072891501035503L;

		public MyImage() {
			setLayout(new FlowLayout());
		}

		public void paint(Graphics g) {
			super.paint(g);
			ImageIcon img = new ImageIcon("codon/codon.jpg");
			img.getIconWidth();
			img.getIconHeight();
			// System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 900, 820, this);
		}
	}

	class MyCellRenderer extends JLabel implements ListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5670912801400193879L;

		public MyCellRenderer() {
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setText(value.toString());
			setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, false));
			this.setBackground(Color.WHITE);
			this.getBounds().setSize(56, 40);
			return this;
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		int value = listScrollPane.getVerticalScrollBar().getValue();
		motifScr.getVerticalScrollBar().setValue(value);
		siteScr.getVerticalScrollBar().setValue(value);
		tfScr.getVerticalScrollBar().setValue(value);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == promoter) {
			new OptionalPrimer(CodonFrame1.this);
		}
		if (e.getSource() == high) {
			this.input.setText("0.5");
		}
		if (e.getSource() == median) {
			this.input.setText("0.3");
		}
		if (e.getSource() == low) {
			this.input.setText("0.1");
		}
		if (e.getSource() == sbol) {
			int intRetVal = jfileChooser.showOpenDialog(this);
			if (intRetVal == JFileChooser.APPROVE_OPTION) {
				inputSequence.setText(jfileChooser.getSelectedFile().getPath());
			}
			type = true;
		}
		if (e.getSource() == cal) {
			String sequence = this.inputSequence.getText();
			if (type == true) {
				String file = this.inputSequence.getText();
				try {
					SBOLDocument document = SBOLFactory
							.read(new FileInputStream(file));
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
				String sigma1 = ""; // 用于保存输出sigma序列
				int bestStartPoint = 0; // 用于保存输出最佳起始位点
				int bestSpaceLength = 0; // 用于保存输出最佳间隙长度
				double similarity = 0.0; // 用于保存与上面几个对应的相似度
				SeqSimilarity sigmaSimilarity = new SeqSimilarity(sequence);
				sigma1 = sigmaSimilarity.getName();
				bestStartPoint = sigmaSimilarity.getBestStartPoint();
				bestSpaceLength = sigmaSimilarity.getBestSpaceLength();
				similarity = sigmaSimilarity.getSimilarity();
				this.sigmafactors.setText(sigma1);
				this.site.setText(String.valueOf(bestStartPoint));
				this.spacer.setText(String.valueOf(bestSpaceLength));
				this.strength.setText(String.valueOf(similarity));
				System.out.println(similarity);

				double limit = 0.0;
				limit = Double.parseDouble((this.input.getText()));
				NotSigmaSimilarity ns = new NotSigmaSimilarity(sequence, limit);
				String[] name = ns.getNameArray(); // 这三个是输出，分别是名称、开始位点、相似度
				int[] bestPoint = ns.getBestPointArray();
				double[] mSS = ns.getmSSArray();
				String[] motifs = ns.getMotifArray();
				// System.out.println(name);
				for (int i = 0; i < name.length; i++) {
					// System.out.println(name[i]);
					tfModel.addElement(name[i]);
					siteModel.addElement(bestPoint[i]);
					mssModel.addElement(mSS[i]);
					motifModel.addElement(motifs[i]);
				}
			}
		}

	}
}
