package wgl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import Message.*;
import Security.DES.Des;
import Security.RSA.RSA;
import APP.Application;
import Databean.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;



public class GpChat extends JFrame implements ActionListener{

	public User from;
	private JTextPane jtWord = new JTextPane();
	private JTextPane TextPane = new JTextPane();
	private JScrollPane scrollTex;
	
	private JButton Exit = new JButton();
	private JButton Send = new JButton();
	Border b = new LineBorder(Color.GRAY, 1);
	public byte[] visa = new byte[10000];
	public byte[] whole = new byte[10000];
	public String k;
	
	public GpChat(final User from){
		this.from = from;
		this.setResizable(false); 		//不能修改大小
		this.setTitle(from.toString());
		this.setSize(507,500);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-350)/2,(int)(screenSize.height-600)/2+45);
		
		JPanel p = new JPanel();
		add(p);
		p.setLayout(null);
		
		TextPane.setForeground(Color.BLACK);
		TextPane.setEnabled(false);
		scrollTex = new JScrollPane(TextPane);
		scrollTex.setBounds(10, 10, 480, 320);
		
		jtWord.setBounds(10, 340, 480, 80);
		jtWord.setFont(new Font("黑体",Font.BOLD,16));
		jtWord.setVisible(true);
		jtWord.setBorder(b);
		jtWord.setBackground(Color.white);
		jtWord.setOpaque(true);	//设置面板透明
		jtWord.addKeyListener(new KeyAdapter(){//键盘监听按钮
			public void keyPressed(KeyEvent e)
			{
		       if(e.getKeyCode()==KeyEvent.VK_ENTER){	//enter键输入文本
		    	   if(jtWord.getText().equals("")){//发送信息不能为空
						JOptionPane.showMessageDialog(null, "发送信息不能为空");
					}	
					else{
						String words = jtWord.getText().trim(); 			
						String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
															
						insert("   " + time  + '\n', 12);
						insert("  我:  " + words  + '\n',18);
						jtWord.setText(null);  
						byte[] message = Message.getRespondMessage(from.getId(), Application.user.getId(), (byte)1, Application.CHAT, words.getBytes());
						
						KeyList l=new KeyList();
						String s=String.valueOf(Application.user.getId());
						byte[] t;
						try {
							t = new sun.misc.BASE64Decoder().decodeBuffer(s);
							k=RSA.encrypt1(l.SK,l.byteMerger(t, l.An.getBytes()));					
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						String g="";
						for(byte j:Message.getContent(message))
						{
							char z=(char)j;
							g=g+z;
						}
						
						JSONObject Json = new JSONObject();  
						JSONArray JsonArray = new JSONArray();  
						  
						Json.put("main", g);//JSONObject对象中添加键值对  
						Json.put("sign", k);
						JsonArray.add(Json);//将JSONObject对象添加到Json数组中  
						
//						JSONArray a=new JSONArray();
//						a.add(g);
//						a.add(k);
//						
						
						JSONObject js1 = new JSONObject();
						js1.put("main", g);
						js1.put("sign", k);
						System.out.println("Transfer:"+js1);
						Message.setContent(whole,js1.toString().getBytes());
						Message.setTargetID(whole, from.getId());
						Message.setMethod(whole, Application.CHAT);
						Message.setSourceID(whole, Application.user.getId());
						Message.setType(whole, (byte)1);
						
						try {
							message = Des.encrypt(whole);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						Application.cm.getConn().send(message);
						
					} 
		       }	          
		   }		             
		 });
		
		Exit.setText("关闭");
		Exit.setFont(new Font("Dialog",0,12));
		Exit.setBounds(100, 430, 100, 30);
		Exit.setBackground(getBackground());
		Exit.setBackground(Color.white);
		Exit.setBorder(b);
		Exit.addActionListener(this);
		
		Send.setText("发送");
		Send.setFont(new Font("Dialog",0,12));
		Send.setBounds(300, 430, 100, 30);
		Send.setBackground(Color.white);
		Send.setVisible(true);
		Send.setBorder(b);
		Send.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Send.addActionListener(this);
		
		p.add(scrollTex);
		p.add(jtWord);
		p.add(Exit);
		p.add(Send);
		setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == Exit){
			setVisible(false);
		}
		if(e.getSource() == Send){
			if(jtWord.getText().equals("")){//发送信息不能为空
				JOptionPane.showMessageDialog(null, "发送信息不能为空");
			}	
			else{
				String words = jtWord.getText().trim(); 			
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());								
				insert("   " + time  + '\n', 12);
				insert("  我:  " + words  + '\n',18);
				jtWord.setText("");
				//此处是消息发送逻辑
				byte[] message = Message.getRespondMessage(from.getId(), Application.user.getId(), (byte)1, Application.CHAT, words.getBytes());
				
				KeyList l=new KeyList();
				String s=String.valueOf(Application.user.getId());
				byte[] t;
				try {
					t = new sun.misc.BASE64Decoder().decodeBuffer(s);
					k=RSA.encrypt1(l.SK,l.byteMerger(t, l.An.getBytes()));					
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				String h="";
				for(byte j:Message.getContent(message))
				{
					char z=(char)j;
					h=h+z;
				}
				
				JSONObject js1 = new JSONObject();
				js1.put("main", h);
				js1.put("sign", k);
				Message.setContent(whole, js1.toString().getBytes());
				Message.setTargetID(whole, from.getId());
				Message.setMethod(whole, Application.CHAT);
				Message.setSourceID(whole, Application.user.getId());
				Message.setType(whole, (byte)1);
				
				try {
					message = Des.encrypt(whole);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Application.cm.getConn().send(message);
				
			}
		}
	}
	
	private void insert(String text ,int textFont)//根据传入的颜色及文字，将文字插入文本域
	{
		 SimpleAttributeSet set = new SimpleAttributeSet();
		 StyleConstants.setFontSize(set, textFont);//设置字体大小
		 StyleConstants.setForeground(set, Color.BLACK);
		 Document doc = TextPane.getStyledDocument();		 
		 try {
				doc.insertString(doc.getLength(), text, set);//插入文字
		 } catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	}
	
	/**
	 * 向对话框显示一条消息
	 * @param words
	 * @param from
	 */
	
	public void displayInfo(String words){
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		insert("   " + time  + '\n', 12);
		insert("  "+from.getName()+":  " + words  + '\n',18);
	}
}
