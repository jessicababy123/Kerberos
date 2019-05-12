package wgl;

import java.util.Base64;

import APP.Application;
import Client.ConnManger;
import Client.HexUtil;
import Client.SocketConn;
import Message.Message;
import Security.DES.Des;
import Security.RSA.RSA;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

public class BackgroundThread extends Thread {
	
	SocketConn conn = null;
	byte[] message = new byte[8216];
	byte[] buffer = new byte[8216];
	byte[] a=new byte[8216];
	
	public BackgroundThread(){
		super();
		if(Application.cm == null)
			Application.cm = new ConnManger("chatserver");
		conn = Application.cm.getConn();
	}
	
	public void run(){//
		byte[] bytes = new byte[8216];
		
		while(conn.receive(bytes)!=-1){
			
//			System.out.println(HexUtil.encodeToString(bytes));
//			
			try {
				
				
				System.out.println("传回的数据"+HexUtil.encodeToString(bytes));
				System.out.println("我是");
				a=bytes;
				bytes = Des.decrypt(bytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long src = Message.getSourceID(bytes);
			
			String g="";
			for(byte k:Message.getContent(bytes))
			{
				char t=(char)k;
				g=g+t;
			}
			String s = new String(Message.getContent(bytes));
			System.out.println("g是："+g);
//			JSONTokener json=new JSONTokener(s);
//			JSONObject obj =(JSONObject) json.nextValue();
			JSONObject obj=JSONObject.fromObject(g);
			System.out.println(obj.get("sign"));
			
//			JSONArray obj =JSONArray.fromObject(g);
			
//			if(obj.contains("sign")) {
//				JSONObject o= obj.getJSONObject(1);
//				System.out.println("!!!!!!!!!!!!!:"+o);
//			}
//			JSONObject a=obj.getJSONObject(0);			
//		    JSONObject b=obj.getJSONObject(1);
//		    System.out.println(a);
		    
		    

			KeyList l=new KeyList();
			System.out.println(l.PKList.get(String.valueOf(Application.user.getId())));
			try {
				GpChat c = Chat.usrSet.get(src);
				c.displayInfo("密文为："+HexUtil.encodeToString(a));
				c.displayInfo("封包内容为："+s);
//				c.displayInfo("PK:"+obj.get("sign"));
//				c.displayInfo(main);
				byte[] a=RSA.decrypt(l.PKList.get(String.valueOf(c.from.getId())),obj.get("sign").toString().getBytes());
				String f=HexUtil.encodeToString(a);
				c.displayInfo("PK:"+f);
				Chat.usrSet.get(src).setVisible(true);
			} catch (Exception e) {
				
			}
 			
		}
	}
}
