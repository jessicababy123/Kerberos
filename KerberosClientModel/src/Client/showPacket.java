package Client;


import Message.Message;

/**
 * 数据报文的拆分
 * @author YXC
 *
 */
public class showPacket {
	
	public static String Show(byte[] message) 
	{
		String a=Integer.toHexString((Message.getMethod(message)) & 0xff);
		String b=Integer.toHexString((Message.getType(message)) & 0xff);
		String c=String.valueOf(Message.getSourceID(message));
		String d=String.valueOf(Message.getTargetID(message));
		String g="";
		for(byte k:Message.getContent(message))
		{
			char t=(char)k;
			g=g+t;
		}
		String e=a+b+c+d+g;
		return e;
	}
	
	public static String Show1(byte[] message) 
	{
			String a=Integer.toHexString((Message.getMethod(message)) & 0xff);
			String b=Integer.toHexString((Message.getType(message)) & 0xff);
			String c=String.valueOf(Message.getSourceID(message));
			String d=String.valueOf(Message.getTargetID(message));
			String e=HexUtil.encodeToString(Message.getContent(message));
			return a+b+c+d+e;
	}
}
