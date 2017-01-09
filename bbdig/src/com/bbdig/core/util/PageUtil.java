package com.bbdig.core.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;


public class PageUtil {
	
	public static String alert(String msg){
		return("<script>alert('"+msg+"')</script>");
	}
	public static String backHistory(){
		return("<script> window.history.back() </script>");
	}
	
	public static String genSelectTag(Map<String,String> elements,String name, HttpServletRequest request){
		
		String selected = request.getParameter(name);
		
		StringBuffer buff = new StringBuffer();
		buff.append("<select id='biz-"+name+"' name = '"+name+"' > ");
		
		buff.append("<option value='' >全部</option>");//全部
		
		for(Map.Entry<String, String> e : elements.entrySet()){
			if(StringUtils.equalsIgnoreCase(selected, e.getKey())){
				buff.append("<option value='"+e.getKey()+"' selected='selected' >" +e.getValue() + "</option>");
			}
			else{
				buff.append("<option value='"+e.getKey()+"' >" +e.getValue() + "</option>");
			}
					
		}
		
		buff.append("</select>");
		
		return buff.toString();
	}
	
}
