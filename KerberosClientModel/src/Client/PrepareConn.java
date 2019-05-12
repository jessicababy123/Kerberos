package Client;

import wgl.Constant;

public class PrepareConn {

	public static boolean returnKerberos(long id , String pwd,Constant c) throws Exception{
		byte[] receiveBuffer1 = new byte[8216];
		byte[] receiveBuffer2 = new byte[8216];
		
		receiveBuffer1=ClientOperation.clientToAS(id, 2);
//		c.translate(receiveBuffer1);
		
		receiveBuffer2=ClientOperation.clientToTgs(id,pwd,3,receiveBuffer1);
//		c.translate(receiveBuffer2);
		
		byte num=ClientOperation.clientToPSever(id,receiveBuffer2);
		c.translate(receiveBuffer1,receiveBuffer2,ClientOperation.receiveV);
		
		if (num==7)
		{
			return true;	
		}
		return false;
	}
}
