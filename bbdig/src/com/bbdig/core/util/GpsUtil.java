package com.bbdig.core.util;

import java.math.BigDecimal;

public class GpsUtil {
	public static String convert(Double distance){
		
		if(distance == null){
			return "";
		}
		if(distance > 999999900){
			return   "";
		}
		
		if(distance < 1){	//不到一公里
			
			BigDecimal bd = new BigDecimal(distance * 1000);
	
			int dis = bd.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			
			return "距离 "+ dis + " 米";
		}
		else if(distance >= 1){
			BigDecimal bd = new BigDecimal(distance);
			distance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() ;
			return  "距离 "+ distance  + " 公里";
		}
		
		return "";
	}
}
