package wgl;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class textpass extends JFrame 
{
	public JTextArea ja1 = new JTextArea();
	public JTextArea ja2 = new JTextArea();
	public JTextArea ja3 = new JTextArea();
	
	public textpass()
	{
		this.setTitle("Show");
		this.setBounds(0,200,500,450);
		this.getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel j1 = new JLabel("C&AS");
		j1.setBounds(50,40,50,80);
		j1.setVisible(true);
		JLabel j2 = new JLabel("C&TGS");
		j2.setBounds(50,150,50,80);
		j2.setVisible(true);
		JLabel j3 = new JLabel("C&V");
		j3.setBounds(50,280,50,80);
		j3.setVisible(true);
		
		ja1.setBounds(110,20,350,110);
		ja1.setVisible(true);
		
		ja2.setBounds(110,140,350,110);
		ja2.setVisible(true);
		
		ja3.setBounds(110,260,350,110);
		ja3.setVisible(true);
		
		getContentPane().add(j1);	getContentPane().add(ja1);
		getContentPane().add(j2);	getContentPane().add(ja2);
		getContentPane().add(j3);	getContentPane().add(ja3);
		 
		this.setResizable(false);
	}
	
}

