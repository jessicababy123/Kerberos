package wgl;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import APP.Application;
import Client.ConnManger;
import Client.HexUtil;
import Client.SocketConn;
import Client.showPacket;
import Client.PrepareConn;
import Databean.User;
import Message.*;
import Security.DES.Des;

public class Login extends JFrame implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel back;
	private JLabel jt1=new JLabel();
	private JLabel jt2=new JLabel();
	private JTextField jt = new JTextField("�����û���");		//�������г�ʼ���ı����ı������
	private JPasswordField jp=new JPasswordField(20);
	private JButton xa=new JButton();
	private JButton xb=new JButton();
	static textpass tp = new textpass();
	
	public Login()
	{
		this.setResizable(false); 		//�����޸Ĵ�С
		this.getContentPane().setLayout(null);
		this.setTitle("��¼");
		this.setSize(450,350);
		
		//��������λ�ã��ǶԻ������
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-350)/2,(int)(screenSize.height-600)/2+45);
		
		back=new JLabel();
		ImageIcon icon=new ImageIcon(this.getClass().getResource("��¼.jpg"));
		back.setIcon(icon);
		back.setBounds(-0, 0, 450, 350);
		
		jt.setForeground(Color.gray);
		jt.setBounds(95, 100, 150, 30);
		jt.setFont(new Font("Serif",Font.PLAIN,12));
		
		
		jt1.setBounds(40, 90, 80, 50);
		jt1.setFont(new Font("����",Font.PLAIN,16));
		jt1.setForeground(Color.BLACK);
		jt1.setText("�û���:");
		
		//���������
		jp.setFont(new Font("Serif",Font.PLAIN,12));
		jp.setBounds(95, 150, 150, 30);
		jp.setVisible(true);
		
		jt2.setBounds(40, 140, 80, 50);
		jt2.setFont(new Font("����",Font.PLAIN,16));
		jt2.setForeground(Color.BLACK);
		jt2.setText("��  � ");
		
		xa.setText("��½");
		xa.setFont(new Font("Dialog",0,12));
		xa.setBounds(95, 200, 150, 30);
		xa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		xa.setBackground(getBackground());
		xa.setBackground(Color.white);
		Border b = new LineBorder(Color.white, 2); 
		xa.setBorder(b);
		xa.setVisible(true);

		
		xb.setText("ע��");
		xb.setFont(new Font("Dialog",0,12));
		xb.setBounds(185, 200, 60, 30);
		xb.setBackground(Color.WHITE);
		xb.setVisible(false);
		xb.setBorder(b);
		xb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		xa.addActionListener(this);
		xb.addActionListener(this);
		
	
		this.getContentPane().add(jt);
		this.getContentPane().add(jt1);
		this.getContentPane().add(jt2);
		this.getContentPane().add(jp);	
		this.getContentPane().add(xa);
		this.getContentPane().add(xb);
		
		this.getContentPane().add(back);
		this.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new Login();
		tp.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Constant ct = new Constant();
		// TODO Auto-generated method stub
		if(e.getSource()==xa){//����İ�ť�ǵ�¼	
			
			String usr=jt.getText().toString();	//��ȡ�ı�������			
			char[] passwords = jp.getPassword();			
			String password =String.valueOf(passwords);	//��ȡ���������
			
			String Content=usr+password;
			
			if(usr.equals("")||password.equals(""))
			{
				//System.out.println("������������Ϣ!");
				JOptionPane.showMessageDialog(null, "������������Ϣ!");
			}
			else
			{
				xb.setVisible(false);
				xa.setText("���ڵ�½...");
				xa.setBounds(95, 200, 150, 30);
				this.setVisible(true);
				
				long usrId = Long.parseLong(jt.getText());
				boolean goon = false;
				try {
					if(PrepareConn.returnKerberos(usrId, password,ct))
					{
						//StringBuffer Str = new StringBuffer();
						String s1 = showPacket.Show1(ct.Rec1);
						String s2=showPacket.Show1(ct.Rec2);
						String s3=showPacket.Show1(ct.Rec3);
						
						tp.ja1.append("C���͸�AS�ı���Ϊ��"+"\n"+ct.send1+"\n"+"AS���͸�C�ı���Ϊ��"+"\n"+s1);
						tp.ja2.append("C���͸�TGS�ı���Ϊ��"+"\n"+ct.send2+"\n"+"TGS���͸�c�ı���Ϊ��"+"\n"+s2);
						tp.ja3.append("C���͸�V�ı���Ϊ��"+"\n"+ct.send3+"\n"+"V���͸�c�ı���Ϊ��"+"\n"+s3);
						/*for(byte b: ct.Rec1)
						{
							Str.append(b); //���ζ�ȡ�����Ԫ��
						}
						tp.ja1.setText(Str.toString());*/
						goon = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null, "��֤����!");
					}
				} 
				catch (Exception e2) 
				{
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				if(goon)
				{
				Application.cm = new ConnManger("chatserver");
				SocketConn conn = Application.cm.getConn();
				if(conn == null){
					System.out.println("null");
				}
				byte[] buffer = new byte[8216];
				byte[] message = Message.getRespondMessage(Application.PSERVERCHAT, usrId, (byte)1, Application.ON_LINE, null);
				try 
				{
					System.out.println(new String(message));
					message = Des.encrypt(message); //Des����
				} 
				catch (IOException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				catch (Exception e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				conn.send(message);
				conn.receive(buffer);
				try 
				{
					buffer = Des.decrypt(buffer); //Des����
				} 
				catch (IOException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				catch (Exception e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(Message.getRespond(buffer) == Application.SUCCESS)
				{
					Application.user = new User(usrId, new String(Message.getContent(buffer)));
					
					message = Message.getRespondMessage(Application.PSERVERCHAT, usrId, (byte)1, Application.GET_FRIEND, null);
					try 
					{
						
						message = Des.encrypt(message); //Des����
					} catch (IOException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					catch (Exception e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					conn.send(message);
					conn.receive(buffer);
					try 
					{
						buffer = Des.decrypt(buffer); //Des����
					} 
					catch (IOException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					catch (Exception e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					this.setVisible(false);
					List<User> user = ContentTool.getUsser(Message.getContent(buffer));
					Chat main =  Chat.getInstance(Application.user.getName(),user);
				}
				else
				{//������ʾ
					JOptionPane.showMessageDialog(null, "����������һ������!");
				}
				}
			}
		}
		else if(e.getSource()==xb)
		{//����İ�ť��b2
			new Regist();
			setVisible(false);
		}
	}
}
