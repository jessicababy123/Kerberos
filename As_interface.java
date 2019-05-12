package as_server;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class As_interface extends JFrame
{
	public As_interface()
	{
		JFrame frame = new JFrame("As_server");
		frame.setResizable(false);
		frame.setLocation(900, 300);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置背景图
		JPanel jp = (JPanel)frame.getContentPane();
		ImageIcon background = new ImageIcon(this.getClass().getResource("Background.jpg"));
		JLabel label = new JLabel(background);
		label.setBounds(0, 0,1000,800);
		jp.setLayout(new GridLayout());
		jp.add("center",label);
		
		JLabel jt1 = new JLabel("加密过程");
		
		frame.setSize(background.getIconWidth(),background.getIconHeight());
		frame.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		As_interface as = new As_interface();
	}
}
