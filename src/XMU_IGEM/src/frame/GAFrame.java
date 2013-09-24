package frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GAFrame extends JFrame
{
	public JLayeredPane layeredPanel;
	
	public JTextField ps;
	public JTextField ha;
	public JTextField mp;
	public JTextField cp;
	
	public JButton confirm;
	public JButton reset;
	public JButton cancel;
	
	public ProteinFrame1 parent;
	
	public String[] finalCodon;
	
	public GAFrame()
	{
		init();
	}
	
	public GAFrame(ProteinFrame1 proteinFrame1)
	{
		parent = proteinFrame1;
		
		init();
	}
	
	public void init()
	{
//		setSize(405, 400);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setBounds(parent.getBounds().width/2 - 400/2, parent.getBounds().height/2 - 385/2, 405, 400);
		
		
		layeredPanel = new JLayeredPane();
		layeredPanel.setBounds(0, 0, 400, 385);
		
		JPanel panel = new GAImage();
		panel.setBounds(0,0, 400, 385);
		layeredPanel.add(panel, new Integer(0));
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(0,0, 400, 385);
		panel1.setLayout(null);
		panel1.setOpaque(false);
		
		ps = new JTextField();
		ps.setText(String.valueOf(parent.population));
		ps.setBounds(222-9, 118-29, 130, 34);
		ps.setBackground(Color.WHITE);
		panel1.add(ps);
		
		ha = new JTextField();
		ha.setText(String.valueOf(parent.daishu));
		ha.setBounds(222-9, 162-29, 130, 34);
		ha.setBackground(Color.WHITE);
		panel1.add(ha);
		
		mp = new JTextField();
		mp.setText(String.valueOf(parent.varRate));
		mp.setBounds(222-9, 206-29, 130, 34);
		mp.setBackground(Color.WHITE);
		panel1.add(mp);
		
		cp = new JTextField();
		cp.setText(String.valueOf(parent.crossRate));
		cp.setBounds(222-9, 249-29, 130, 34);
		cp.setBackground(Color.WHITE);
		panel1.add(cp);
		
		ImageIcon confirmIcon = new ImageIcon("confirm.png");
		confirm = new JButton(confirmIcon);
		confirm.addActionListener(new MyAction());
		confirm.setBounds(39-9, 326-29, 108, 49);
		panel1.add(confirm);
		
		ImageIcon resetIcon = new ImageIcon("reset.png");
		reset = new JButton(resetIcon);
		reset.addActionListener(new MyAction());
		reset.setBounds(158 -9, 326-29, 108, 49);
		panel1.add(reset);
		
		ImageIcon cancelIcon = new ImageIcon("cancel.png");
		cancel = new JButton(cancelIcon);
		cancel.addActionListener(new MyAction());
		cancel.setBounds(278-9, 326-29, 108, 49);
		panel1.add(cancel);
		
		layeredPanel.add(panel1, new Integer(1));
		getContentPane().add(layeredPanel);
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				parent.setEnabled(true);
				GAFrame.this.dispose();
				super.windowClosing(e);
			}
		});
	}

	public static void main(String[] args)
	{
		GAFrame ga = new GAFrame();
	}
	
	class GAImage extends JPanel
	{
		public GAImage()
		{
			setLayout(new FlowLayout());
		}

		public void paint(Graphics g)
		{
			super.paint(g);
			ImageIcon img = new ImageIcon("GA.png");
			int width = img.getIconWidth();
			int height = img.getIconHeight();
//			System.out.println(width + "," + height);
			g.drawImage(img.getImage(), 0, 0, 394,376, this);
		}
	}
	
	private class MyAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if(arg0.getSource() == confirm)
			{
				String s3 = parent.input.getText();
				String cell = (String)parent.hostcell.getSelectedItem();
				
				int population=Integer.parseInt(GAFrame.this.ps.getText());			//种群大小
				int  daishu = Integer.parseInt(GAFrame.this.ha.getText());				//遗传代数
				double crossRate= Double.parseDouble(GAFrame.this.cp.getText());			//交叉概率
				double varRate = Double.parseDouble(GAFrame.this.mp.getText());				//变异概率
				
				parent.population = population;
				parent.daishu = daishu;
				parent.crossRate = crossRate;
				parent.varRate = varRate;
				
//				Algorithm al = new Algorithm(s3,cell,population,daishu,crossRate,varRate);
//				finalCodon = al.getFinalCodon();				//这是最后的结果
//				System.out.println(finalCodon[0]);
//				String output=null;
//				for(int i  = 0 ; i < finalCodon.length ; i++)
//				{
//					output += finalCodon[i];
//				}
//				parent.output.setText(output);
				
				parent.setEnabled(true);
				GAFrame.this.dispose();
			}
			if(arg0.getSource() == reset)
			{
				GAFrame.this.ps.setText("200");
				GAFrame.this.ha.setText("100");
				GAFrame.this.mp.setText("0.1");
				GAFrame.this.cp.setText("0.1");
			}
			if(arg0.getSource() == cancel)
			{
				parent.setEnabled(true);
				GAFrame.this.dispose();
//				GAFrame.this.setVisible(false);
			}
		}
	}
}
