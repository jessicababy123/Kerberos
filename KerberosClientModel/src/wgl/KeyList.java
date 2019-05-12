package wgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import Security.RSA.KeyManger;



public class KeyList {

	public ConcurrentMap<String, String>PKList = new ConcurrentHashMap<String, String>();//用户名，对应的n
	public String An;
	public String SK;
	
	private String C02SK;
	private String C03SK;
	private String C04SK;
	
	public KeyList() {
		KeyManger key= new KeyManger(20);
		An="65537&840724671139533865187731083281";
		SK="447923819249874712139431518197&840724671139533865187731083281";
		
		C02SK="270925167879711228001623936513&606242240075549044691178664549";
		C03SK="507890508481827495983530908113&577414222224848499028882927547";
		C04SK="232470669737939026779609743885&599136036911217488342451539357";
		
		PKList.put("123", "65537&840724671139533865187731083281");
		PKList.put("110", "65537&606242240075549044691178664549");
		PKList.put("1234", "65537&577414222224848499028882927547");
		PKList.put("4567", "65537&599136036911217488342451539357");
		
	}
	
	public static byte[] byteMerger(byte[] bt1, byte[] bt2){ 
	    byte[] bt3 = new byte[bt1.length+bt2.length]; 
	int i=0;
	    for(byte bt: bt1){
	     bt3[i]=bt;
	 i++;
	}
	     
	for(byte bt: bt2){
	  bt3[i]=bt;
	  i++;
	}
	    return bt3; 
	}

	
}
