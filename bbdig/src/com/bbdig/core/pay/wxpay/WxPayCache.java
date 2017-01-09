package com.bbdig.core.pay.wxpay;

import java.io.Serializable;

public class WxPayCache implements   Serializable{
	
	private long refreshTimestamp;
	
	private String accessToken = "";
	
	//private long accessTokenTimestamp = 0;
	
	private String jsApiToken = "";
	
	//private long jsApiTokenTimestamp = 0;
	
	private String nonceStr = ""; //
	
	private String jsSign = "";	//调用js需要的签名
	
	private static WxPayCache cache = new WxPayCache();
	
	private WxPayCache(){}
	
	public static WxPayCache get(){
		return cache;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

//	public long getAccessTokenTimestamp() {
//		return accessTokenTimestamp;
//	}
//
//	public void setAccessTokenTimestamp(long accessTokenTimestamp) {
//		this.accessTokenTimestamp = accessTokenTimestamp;
//	}

	public String getJsApiToken() {
		return jsApiToken;
	}

	public void setJsApiToken(String jsApiToken) {
		this.jsApiToken = jsApiToken;
	}

//	public long getJsApiTokenTimestamp() {
//		return jsApiTokenTimestamp;
//	}
//
//	public void setJsApiTokenTimestamp(long jsApiTokenTimestamp) {
//		this.jsApiTokenTimestamp = jsApiTokenTimestamp;
//	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public long getRefreshTimestamp() {
		return refreshTimestamp;
	}

	public void setRefreshTimestamp(long refreshTimestamp) {
		this.refreshTimestamp = refreshTimestamp;
	}

	public String getJsSign() {
		return jsSign;
	}

	public void setJsSign(String jsSign) {
		this.jsSign = jsSign;
	}
	
	
	
	
}
