package com.bbdig.core.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;

import com.bbdig.core.constant.ErrorCode;
import com.bbdig.core.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

public class RestClient {
	
	
	private static Logger logger = Logger.getLogger(RestClient.class);
	
	public static String buildURL(HttpRequest request,String path){
		
		String queryString = "";
		return queryString;
	}
	
	public  static   Object getData(String url,Type type,  HttpServletRequest request, HttpServletResponse response) {
		
		try {
			URL urlObj = null;
		 
			String rootPath = request.getScheme()+"://"+"localhost"+":"+
					request.getServerPort()+request.getContextPath();//request.getServerName()
			
		//	System.out.println("rootPath : " + rootPath);
			
			urlObj = new URL(rootPath + url);//URL_PREFIX

			URLConnection connection = urlObj.openConnection();
			
			//set session and cookie
			//set session
			String sessionStr = "";
			if(request!=null && request.getSession(false)!=null ){
				sessionStr = "JSESSIONID=" + request.getSession(false).getId() + ";";
			}
			
			String cookieStr = "";
			Cookie[] cookies = request.getCookies(); 
			if(cookies != null){
				for(Cookie cookie : cookies){
					if(StringUtils.equalsIgnoreCase("JSESSIONID", cookie.getName().trim())){
						continue;
					}
					cookieStr += (cookie.getName()+"="+cookie.getValue()+";");
				}
			}
			
			connection.setRequestProperty(
				    "Cookie", sessionStr+ cookieStr);
			
			connection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			String line;
			String result = "";
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
			
 
			
			Map<String,Object> map = JsonUtil.fromJson(result, new TypeToken<Map<String,Object>>(){}.getType());
			
			if( StringUtils.contains( String.valueOf(map.get("code")), "200") && map.get("data")!=null ){
				String dataJson = JsonUtil.toJson(map.get("data")) ;
				return JsonUtil.fromJson(dataJson, type);
				
			}
			else if(StringUtils.contains( String.valueOf(map.get("code")), ""+ErrorCode.NEED_USER_LOGIN.getIndex()) ){//需要用户登录
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				return null;
			}
			else if(StringUtils.contains( String.valueOf(map.get("code")), ""+ErrorCode.NEED_SHOP_LOGIN.getIndex()) ){//需要商家登录
				response.sendRedirect(request.getContextPath()+"/shop/login.jsp");
				return null;
			}
			
			return null;
			
//			Map<String,Object> map = new Gson().fromJson(result, new TypeToken<Map<String,Object>>(){}.getType());
//
//			return JsonUtil.toJson();
//			
//			if( StringUtils.contains( String.valueOf(map.get("code")), "200")  && map.get("data")!=null ){
//				return JsonUtil.toJson(map.get("data")) ;
//			}
//			else{
//				return JsonUtil.toJson(map.get("data")) ;
//			}
			
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return null;
		}
 
	}
	
}
