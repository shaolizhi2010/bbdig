package com.bbdig.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class PaginationUtil {
	
	public static String firstPageParam(HttpServletRequest request ){
		String param = StringUtils.trimToEmpty(request.getQueryString()) ;
		
		param = StringUtils.substringBefore(param, "currentPage");
		
		param += "&currentPage=1";
		
		param  = StringUtils.replace(param, "&&", "&");
		param  = StringUtils.removeStart(param, "&");
		
		return param;
		
	}
	
	public static String prePageParam(HttpServletRequest request,int currentPage){
		String param = StringUtils.trimToEmpty(request.getQueryString()) ;
		
		param = StringUtils.substringBefore(param, "currentPage");
		
		param = param + "&currentPage=" + (currentPage-1<=0?1:currentPage-1) ;
		
		param  = StringUtils.replace(param, "&&", "&");
		param  = StringUtils.removeStart(param, "&");
		
		return param;
		
	}
	
	public static String nextPageParam(HttpServletRequest request,int currentPage){
		String param = StringUtils.trimToEmpty(request.getQueryString()) ;

		param = StringUtils.substringBefore(param, "currentPage");
		
		param = param + "&currentPage=" + (currentPage+1) ;
		param  = StringUtils.replace(param, "&&", "&");
		param  = StringUtils.removeStart(param, "&");
		
		return param;
		
	}

}
