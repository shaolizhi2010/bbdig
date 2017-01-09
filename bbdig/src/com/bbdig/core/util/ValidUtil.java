package com.bbdig.core.util;

import org.apache.commons.lang3.StringUtils;

public class ValidUtil {
	
	//TODO
	public static String validMobile(String moblie) {
		if(StringUtils.isBlank(moblie)){
			return "需填写手机号";
		}
		if(moblie.length()!=11){
			return "请填写正确的手机号";
		}
		return null;
	}
	
	public static String validID(String id) {
		if(StringUtils.isBlank(id)){
			return "需用户编号";
		}
		
		//TODO check if it is num
		
		return null;
	}
	
	/**
	 * 判断文件扩展名
	 * jsp bat等
	 * 
	 * @param id
	 * @return
	 */
	public static String validFileExtName(String filename) {
		if(StringUtils.isBlank(filename)){
			return "文件名不能为空";
		}
		
		if(StringUtils.endsWithIgnoreCase(filename, ".jsp")
			||	StringUtils.endsWithIgnoreCase(filename, ".js")
			||	StringUtils.endsWithIgnoreCase(filename, ".html")
			||	StringUtils.endsWithIgnoreCase(filename, ".xhtml")
			||	StringUtils.endsWithIgnoreCase(filename, ".htm")
			||	StringUtils.endsWithIgnoreCase(filename, ".jspx")
			||	StringUtils.endsWithIgnoreCase(filename, ".dll")
			||	StringUtils.endsWithIgnoreCase(filename, ".bat")
			||	StringUtils.endsWithIgnoreCase(filename, ".msi")
			||	StringUtils.endsWithIgnoreCase(filename, ".exe")
			||	StringUtils.endsWithIgnoreCase(filename, ".json")
			||	StringUtils.endsWithIgnoreCase(filename, ".cer")
			||	StringUtils.endsWithIgnoreCase(filename, ".txt")
			||	StringUtils.endsWithIgnoreCase(filename, ".xml")
				){
			return "文件类型不合法";
		}
		
		return null;
	}
	
//	public static String validShopID(String id) {
//		if(StringUtils.isBlank(id)){
//			return "需商家编号";
//		}
//		
//		//TODO check if it is num
//		
//		return null;
//	}
	
	//TODO
//	public static String validUserNickname(String nickName) {
//		if(StringUtils.isBlank(nickName)){
//			return "用户昵称不能为空";
//		}
//		if(nickName.length()<=3){
//			return "用户昵称长度需大于3";
//		}
//		if(nickName.length()>=20){
//			return "用户昵称长度需小于20";
//		}
//		return null;
//	}
}
