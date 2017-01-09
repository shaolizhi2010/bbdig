package com.bbdig.core.util;

import java.util.Random;

public class OrderUtil {
	
	public static String ORDER_PREFIX = "10";
	public static String SHOP_RECHARGE_PREFIX = "15";
	
	public static Random r = new Random();
	
	/**
	 * 生成订单流水号
	 * 
	 * @return
	 */
	public static String genOrderNo(){
		
		int rand1 = r.nextInt(10);
		int rand2 = r.nextInt(10);
		
		return ORDER_PREFIX + (System.currentTimeMillis()-1400000000000L) +rand1 + rand2 ;
		
	}
	
	/**
	 * 生成商户充值订单流水号
	 * 
	 * @return
	 */
	public static String genShopRechargeNo(){
		
		int rand1 = r.nextInt(10);
		int rand2 = r.nextInt(10);
		
		return SHOP_RECHARGE_PREFIX + (System.currentTimeMillis()-1400000000000L) +rand1 + rand2 ;
	}
	
}
