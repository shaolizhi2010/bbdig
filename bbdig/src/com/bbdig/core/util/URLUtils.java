package com.bbdig.core.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;


public class URLUtils {
	
	public static boolean isValid(String url){
		if(StringUtils.isBlank(url)){
			return false;
		}
		if (url.matches("^.+[.].+$")) {// 是url
			return true;
		}
		return false;
	}
	
	public static boolean isValid2(String url){
		if(StringUtils.isBlank(url)){
			return false;
		}
		try {
			URL url1 =  new URL(url );
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}
	/**
	 * 将不规范的url 转为 规范的, http开头的
	 * @param url
	 * @return
	 */
	public static String standard(String url){
		if(url == null){
			return "";
		}
		if(!url.startsWith("http")){
			url = "http://"+url;
		}
		return url;
	}
	
	public static String getShopNameFromUrl(String url){
		String domain = getdomain(url);
		String[] subs = domain.split("\\.");
		for(int i= subs.length;i>=1;i--){
			if(!"com".equalsIgnoreCase(subs[i-1]) 
					&& !"cn".equalsIgnoreCase(subs[i-1]) 
					&& !"net".equalsIgnoreCase(subs[i-1]) 
					&& !"org".equalsIgnoreCase(subs[i-1]) 
					&& !"info".equalsIgnoreCase(subs[i-1])
					&& !"edu".equalsIgnoreCase(subs[i-1])
					&& !"hk".equalsIgnoreCase(subs[i-1])){
				
				//如果不是顶级域名后缀，则是shop的name
				return subs[i-1];
			}
			
		}
		return "";
	}
	
	public static String getdomain(String url){

		if(!url.startsWith("http")  ){
			if(url.startsWith("www")){
				return "http://"+url;
			}
			else{
				return "http://www."+url;	
			}
			
		}
		return StringUtils.substringBetween(url, "http://", "/");
	}
	
	public static String getFormatedUrl(String url){

		if(url.startsWith("http")  ){
			 
			return url;
		}
		else if( url.startsWith("https")){
			 
			return url;
		}
		else{
			return "http://"+url;
		}
	}
	
	public static String getBaseUrl(String url){
		
		String domain = "";
		if(url.startsWith("http://")  ){
			url = url.replace("http://", "");
			if(url.contains("/")){
				domain = StringUtils.substringBefore(url, "/");
			}
			else{
				domain = url;
			}
			
			return "http://"+domain;
		}
		else if( url.startsWith("https://")){
			url = url.replace("https://", "");
			if(url.contains("/")){
				domain = StringUtils.substringBefore(url, "/");
			}
			else{
				domain = url;
			}
			
			return "https://"+domain;
		}
		else{
			 
			if(url.contains("/")){
				domain = StringUtils.substringBefore(url, "/");
			}
			else{
				domain = url;
			}
			
			return "http://"+domain;
		}
		
		
	}
 
 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
