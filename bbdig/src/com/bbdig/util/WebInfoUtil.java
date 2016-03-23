package com.bbdig.util;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bbdig.vo.WebInfo;

public class WebInfoUtil {
 

	public WebInfo info(String url) {
		return info(url,true);
	}
	public WebInfo info(String url, boolean getIconFlag) {

		WebInfo info = new WebInfo();

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = null;
		
		try {
			long starttime = System.currentTimeMillis();
//			HttpClient httpclient = App.getInstance().getHttpClient();

//			HttpGet httpget = null;
//			
//			String baseUrl = URLUtils.getBaseUrl(url);
//			String formatedUrl = URLUtils.getFormatedUrl(url);
//			//formatedUrl = URLEncoder.encode(formatedUrl, "UTF-8");
//			httpget = new HttpGet(formatedUrl);
//			httpget.setHeader("Accept",
//					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//			 httpget.setHeader("Accept-Language", "zh-CN");
//			httpget.setHeader("Accept-Charset", "UTF-8");
//			httpget.setHeader("Referer", baseUrl); // 模拟浏览器//TODO
			
		
			
			String baseUrl = URLUtils.getBaseUrl(url);
			String formatedUrl = URLUtils.getFormatedUrl(url);
			//formatedUrl = URLEncoder.encode(formatedUrl, "UTF-8");
			httppost = new HttpPost(formatedUrl);
			httppost.setHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httppost.setHeader("Accept-Language", "zh-CN");
			httppost.setHeader("Accept-Charset", "UTF-8");
			httppost.setHeader("Referer", url); // 模拟浏览器//TODO

			// L.trace(null,"Connecter start download page, time is " +
			// starttime );
			HttpResponse response = httpclient.execute(httppost);
			
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			// System.out.println( "链接时间 "+(
			// System.currentTimeMillis()-starttime));

			// int size = is.available();
			byte[] buffer = new byte[10240];
			is.read(buffer, 0, 10240);
			is.close();
			String contents = new String(buffer);

			// System.out.println(contents);

			String charset = getCharset(contents);
			info.charset = charset;

			if (StringUtils.isBlank(charset)) {
				charset = "utf-8";
			}
			String contentNew = new String(buffer, charset);
			contentNew = StringUtils.lowerCase(contentNew);
			info.title = StringUtils.trim(getTitle(contentNew));
			info.description = StringUtils.trim(getDescription(contentNew));
			
			httppost.releaseConnection();
			
			
			// icon操作放在后边，加快前台返回速度
			//if(getIconFlag){//TODO for test 
			//	info.iconPath = StringUtils.trim(getIconPath(contentNew,url));
			//}
			

			//System.out.println( "WebInfoUtil 总共时间 "+(
			// System.currentTimeMillis()-starttime));
			//System.out.println("info.iconPath "+ info.iconPath);
			return info;
		} catch (Exception e) {
			System.out.println("WebInfoUtil Exception : "+e.getMessage());
			//e.printStackTrace();
			return null;
		} finally{
			httpclient = null;
			httppost = null;
		}
	}

	
	public String getHref(String str){
		String href = "";
		if(str.contains("href=\"")){//双引号
			 href =  StringUtils.substringBetween(str, "href=\"","\"");
		}
		else if(str.contains("href=\'")){//单引号
			 href =  StringUtils.substringBetween(str, "href=\'","\'");
		}
		return href;
	}
	
	public String getTitle(String str) {
		String title = null;
		title = StringUtils.substringBetween(str, "<title>", "</title>");

		return title;
	}

	public String getDescription(String str1) {
		String description = null;

		String str = StringUtils.substringAfter(str1, "\"description\"");
		if (StringUtils.isBlank(str)) { // 单引号
			str = StringUtils.substringAfter(str1, "\'description\'");
			description = StringUtils.substringBetween(str, "content=\'", "\'");
			return description;
		} else { // 双引号
			description = StringUtils.substringBetween(str, "content=\"", "\"");
			return description;
		}

	}

	public String getCharset(String line) {

		String charsetStr = "";
		if(line.contains("script")){
			line = StringUtils.substringBefore(line, "script");//一般charset设置都在script之前，排除掉script中charset设置的干扰	
		}
		if (StringUtils.substringBetween(line, "harset=\"", "\"") != null) {// 找到
			// charset，c可能大些
			// 略去
			charsetStr = StringUtils.substringBetween(line, "harset=\"", "\"");
			if (U.validateCharset(charsetStr))
				return charsetStr;

		}
		if (StringUtils.substringBetween(line, "harset=", "\"") != null) {// 找到
			// charset，c可能大些
			// 略去
			charsetStr = StringUtils.substringBetween(line, "harset=", "\"");
			if (U.validateCharset(charsetStr))
				return charsetStr;
		}
		if (StringUtils.lowerCase(line).contains("gb2312")) {
			charsetStr = "gb2312";
			return charsetStr;
		}
		if (StringUtils.lowerCase(line).contains("gbk")) {
			charsetStr = "gbk";
			return charsetStr;
		}
		if (StringUtils.lowerCase(line).contains("utf-8")) {
			charsetStr = "utf-8";
			return charsetStr;
		}
		if (StringUtils.lowerCase(line).contains("iso-8859-1")) {
			charsetStr = "iso-8859-1";
			return charsetStr;
		}

		return null;

	}

}
