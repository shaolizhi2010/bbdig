package com.bbdig.util;

import java.util.Random;

public class GoogleIpUtil {
	

	
	public static String ip(){
		  String ip = "216.58.";
		
		int ip3 = 192+ new Random().nextInt(7);
		int ip4 = new Random().nextInt(256);
		
		ip = ip + ip3 + "."+ip4;
		
		return ip;
	}
}
