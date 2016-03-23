package com.bbdig.util;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class Html {
	
	private String cleanedHtml = null;
	private Document doc = null;
	
	private String charset = "";
	private String title = "";
	private String description = "";
	
	public Html(){
		
	}
	
	
	public Html(String cleanedHtml){
		this.cleanedHtml = cleanedHtml;
		//init();
	}
	
	//在需要使用doc时 调用，不在初始化时调用，避免无用的初始化doc， 节省初始化时间
	public void initDoc(){
		try {
			
			long starttime = System.currentTimeMillis();
			doc = new SAXBuilder().build(new StringReader(cleanedHtml));
			
		} catch (Exception e) {
			return;
		}
	}
	
	//clean 过的 html
	public String getPageSource(){
			return cleanedHtml;
	}
	
	public Document getDoc(){
		if(doc == null){
			initDoc();
		}
		return doc;
	}
	
	public String head(String headname){
		
		String value = "";
		
		if(doc == null){
			initDoc();
		}
		if(doc == null) return "";
		
		if("charset".equalsIgnoreCase(headname)){
			if(StringUtils.isNotBlank(charset)){
				return charset;
			}
			String key = "html/head/meta[contains(@http-equiv,'content-type')]/@content";
			value = X.getAttrValue(doc, key);
			value = StringUtils.substringAfterLast(value, "charset=");
			if(StringUtils.isNotBlank(value)) value = StringUtils.upperCase(value);
			return value;
		}
		
		Object o = X.selectSingleNode(doc, "html/head/"+headname);
		if(o!=null){	//<title> abc... </title>	
			Element e = (Element)o;
			value = e.getText();
		}
		else{	// <meta name="description" content="abc.."/>
			String key = "html/head/meta[contains(@name,'"+headname+"')]";
			value = X.getAttrValue(doc, key, "content");
		}
		
		if(StringUtils.isBlank(value)) return "";
		value = value.trim();
		return value;
	}
	
	public String title(){
		if(StringUtils.isNotBlank(title)){
			return title;
		}
		return head("title");
	}
	
	public String description(){
		if(StringUtils.isNotBlank(description)){
			return description;
		}
		return head("description");
	}
	
	public String charser(){
		return charset;
	}
	public void charset(String charset){
		this.charset = charset;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return this.cleanedHtml;
	}

	 
	
	

}
