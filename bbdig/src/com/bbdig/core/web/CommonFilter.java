package com.bbdig.core.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.bbdig.core.util.MVCUtil;

@WebFilter("/*")
public class CommonFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// 解决百度搜索出 tangguoyun.com域名
		// 301 redirect
		// String requestPage = req.getRequestURI();
		// String queryString = (req.getQueryString() == null ? "" :
		// "?"+req.getQueryString());
		//
		// if(req.getRequestURL().indexOf("tangguoyun.com") >=0){
		// res.setStatus(301);
		// res.setHeader( "Location",
		// "http://www.likexiu.com"+requestPage+queryString);
		// res.setHeader( "Connection", "close" );
		// }
		//

		Map<String, String[]> map = req.getParameterMap();
		for (String key : map.keySet()) {
			String values[] = map.get(key);
			for (int i = 0; i < values.length; i++) {
				
				//values[i] = StringEscapeUtils.escapeJson(values[i]);
				//values[i] = StringEscapeUtils.escapeJava(values[i]);
				//values[i] = StringEscapeUtils.escapeEcmaScript(values[i]);
				//values[i] = StringEscapeUtils.escapeHtml3(values[i]);
				//values[i] = StringEscapeUtils.escapeHtml4(values[i]);
				
//				values[i] = StringUtils.replace(values[i], "(","");
//				values[i] = StringUtils.replace(values[i], ")","");
//				values[i] = StringUtils.replace(values[i], "<","");
//				values[i] = StringUtils.replace(values[i], ">","");
//				values[i] = StringUtils.replace(values[i], "'","");
//				values[i] = StringUtils.replace(values[i], ";","");
				//values[i] = StringUtils.replace(values[i], "%","");
				//values[i] = StringUtils.replace(values[i], "&","");
				
				if(StringUtils.contains(values[i], "'") 
						//|| StringUtils.contains(values[i], "(") 
						//|| StringUtils.contains(values[i], ")") 
						|| StringUtils.contains(values[i], "<") 
						|| StringUtils.contains(values[i], ">") 
						){
					
					MVCUtil.responseJsonOfError(res, "不能提交 <>' 等字符，请重新编辑");
					return;
				}
				
			}
			//map.put(key, values);
		}

		// req.setCharacterEncoding("UTF-8");

		// res.setCharacterEncoding("UTF-8");

		// very useful
		res.setContentType("text/plain; charset=utf-8");

		chain.doFilter(req, res);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
