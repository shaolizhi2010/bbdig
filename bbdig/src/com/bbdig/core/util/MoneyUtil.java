package com.bbdig.core.util;

public class MoneyUtil {
	public static String fenToYuan(Integer fen) {
		if(fen==null){
			return "0.00";
		}
		double priceDouble = fen * 0.01;
		String price = new java.text.DecimalFormat("#0.00").format(priceDouble);
		return price;

	}
}
