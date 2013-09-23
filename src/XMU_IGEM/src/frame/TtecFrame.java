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

import frame.ProteinFrame1.ProteinImage;

public class TtecFrame extends JFrame {
	public JLayeredPane layeredPanel;

	public JButton ttec;
	public JButton sbol;

	public TtecFrame() {
		super("ttec");

		// 设置屏幕居中
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(418, 560);
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
		layeredPanel.setBounds(0, 0, 418, 560);

		JPanel panel = new TtecImage();
		panel.setBounds(0, 0, 418, 560);
		layeredPanel.add(panel, new Integer(0));

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 418, 560);
		panel1.setLayout(null);
		panel1.setOpaque(false);

		ImageIcon ttecIcon = new ImageIcon("ttec/ttec.png");
		ttec = new JButton(ttecIcon);
		ttec.addActionListener(new MyAction());
		ttec.setBounds(39 - 9, 177 - 29, 100, 46);
		panel1.add(ttec);

		ImageIcon sbolIcon = new ImageIcon("ttec/sbol.png");
		sbol = new JButton(sbolIcon);
		sbol.addActionListener(new MyAction());
		sbol.setBounds(39 - 9, 237 - 29, 100, 46);
		panel1.add(sbol);

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
		TtecFrame app = new TtecFrame();
	}

	private class MyAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == ttec) {
				EfficiencyFrame eff = new EfficiencyFrame("ttec");
			}
			if (arg0.getSource() == sbol) {
				EfficiencyFrame eff = new EfficiencyFrame("sbol");
			}
		}
	}

	class TtecImage extends JPanel {
		public TtecImage() {
			setLayout(new FlowLayout());
		}

		public void paint(Graphics g) {
			super.paint(g);
			ImageIcon img = new ImageIcon("ttec/ttecframe.jpg");
			int width = img.getIconWidth();
			int height = img.getIconHeight();
			System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 418, 556, this);
		}
	}
}
