package wgl;

import Client.ClientOperation;

public class Constant 
{
	public byte[] Rec1 = new byte[8216];
	public byte[] Rec2 = new byte[8216];
	public byte[] Rec3 = new byte[8216];
	public String send1 = new String();
	public String send2 = new String();
	public String send3 = new String();
	public void translate(byte[] x1,byte[] x2,byte[] x3)//,byte[] x3,byte[] y1,byte[] y2,byte[] y3)
	{
		send1 = ClientOperation.b1;
		int i = 0;
		for(byte b:x1)
		{
			Rec1[i] = b;
			i++;
		}
		
		send2 = ClientOperation.b2;
		int k = 0;
		for(byte b:x2)
		{
			Rec2[k] = b;
			k++;
		}
		
		send3 = ClientOperation.b3;
		k = 0;
		for(byte b:x3)
		{
			Rec3[k] = b;
			k++;
		}
//		send3 = ClientOperation.b3;
		/*Rec2 = x2;	Rec3 = x3;
		send1 = y1;	send2 = y2;	send3 = y3;*/
	}
}
