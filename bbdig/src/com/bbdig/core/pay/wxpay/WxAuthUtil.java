package com.bbdig.core.pay.wxpay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bbdig.core.util.JsonUtil;
import com.bbdig.core.util.RandomStringGenerator;
import com.google.gson.reflect.TypeToken;

public class WxAuthUtil {
	
	 
	
	public static String getAccessToken() throws Exception{
		
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
		+ WxPayConstants.APP_ID
		+ "&secret="
		+ WxPayConstants.APP_SECRET;
		
		
		HttpClient httpClient = new DefaultHttpClient();;
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "text/json");
		
		try {
			HttpResponse res =  httpClient.execute(httpPost);
			
			String resStr = IOUtils.toString( res.getEntity().getContent() );
			
			if(StringUtils.contains(resStr, "access_token")){
				
				Map<String,String> map = JsonUtil.fromJson(resStr, new TypeToken<Map<String,String>>(){}.getType());
				
				return map.get("access_token");
			}
			else{
				return null;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	/*
	 * https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
	 */
	public static String getJsApiToken(String accessToken) throws Exception{
		
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken
		+"&type=jsapi";
		
		
		HttpClient httpClient = new DefaultHttpClient();;
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "text/json");
		
		try {
			HttpResponse res =  httpClient.execute(httpPost);
			
			String resStr = IOUtils.toString( res.getEntity().getContent() );
			
			if(StringUtils.contains(resStr, "ticket")){
				
				Map<String,String> map = JsonUtil.fromJson(resStr, new TypeToken<Map<String,String>>(){}.getType());
				
				return map.get("ticket");
			}
			else{
				return null;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static String signForJs(String jsApiToken,String url,String noncestr,
			long timestamp){
		
		Map<String, Object> map = new HashMap<>();
		map.put("jsapi_ticket", jsApiToken);
		map.put("url", url);
		map.put("noncestr", noncestr);
		map.put("timestamp", timestamp);
		
		return Signature.getSignForJs(map);
	}
	
	public static String signForJs( String url,String jsapi_ticket,String timestamp,String noncestr  ) throws Exception{
		
		WxPayCache.get().getRefreshTimestamp();
		
		Map<String, Object> map = new HashMap<>();
		map.put("jsapi_ticket", jsapi_ticket);
		map.put("url", url);
		map.put("noncestr",noncestr);
		map.put("timestamp",timestamp);
		return Signature.getSignForJs(map);
	}
	
	public static String signForJs( String url ) throws Exception{
		
		refresh();
		
		WxPayCache.get().getRefreshTimestamp();
		
		Map<String, Object> map = new HashMap<>();
		map.put("jsapi_ticket", WxPayCache.get().getJsApiToken());
		map.put("url", url);
		map.put("noncestr",WxPayCache.get().getNonceStr());
		map.put("timestamp",WxPayCache.get().getRefreshTimestamp());
		return Signature.getSignForJs(map);
	}
	
	public static String getOpenID(String code ) throws Exception {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WxPayConstants.APP_ID
				+"&secret="+WxPayConstants.APP_SECRET
				+"&code="+ code
				+"&grant_type=authorization_code";
				
				HttpClient httpClient = new DefaultHttpClient();
				
				HttpPost httpPost = new HttpPost(url);
				httpPost.addHeader("Content-Type", "text/json");
				
				try {
					HttpResponse res =  httpClient.execute(httpPost);
					
					String resStr = IOUtils.toString( res.getEntity().getContent() );
					
					if(StringUtils.contains(resStr, "openid")){//openid
						
						Map<String,String> map = JsonUtil.fromJson(resStr, new TypeToken<Map<String,String>>(){}.getType());
						
						return map.get("openid");
					}
					else{
						return null;
					}
					
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
	}
	
	
	public static WxPayCache refresh(){
		
		WxPayCache cache = WxPayCache.get();
		
		long curTime = System.currentTimeMillis()/1000; //秒
		if(curTime - cache.getRefreshTimestamp() < 3600 ){//每小时刷新一次
			return cache;
		}
		
		try {
			String nonceStr = RandomStringGenerator.getRandomStringByLength(32) ;
			cache.setNonceStr(nonceStr);
			
			long timestamp = System.currentTimeMillis()/1000; //秒
			cache.setRefreshTimestamp( timestamp );
			
			String accessToken = getAccessToken();
			cache.setAccessToken(accessToken);
			
			String jsApiToken = getJsApiToken(accessToken);
			cache.setJsApiToken(jsApiToken);
			
			String jsSign = signForJs(jsApiToken,"http://likexiu.com/pay/wxPay_2.jsp",nonceStr,timestamp);
			cache.setJsSign(jsSign);
			
		} catch (Exception e) {
			//cache.setAccessToken(accessTokenBk);
			//cache.setRefreshTimestamp(timestampBk);
			//cache.setJsApiToken(jsTokenBk);
			//cache.setNonceStr(nonceStrBk);
			
			e.printStackTrace();
		}
		
		return cache;
	}
		

		
		  
 

	public static void main(String[] args) {
		
		String sign = signForJs("kgt8ON7yVITDhtdwci0qeX3HU-_ocsJaDtY7fmuuPQLoA2hMmNK13hQQGxiILQb4sTvQZzpAp4iHva7XN4IDNQ",
				"http://likexiu.com/pay/wxPay_2.jsp", 
				"m7bcb0xl2dp1ygzw217nsyggnaf59cj2", 
				1446709986);
		
		System.out.println(sign);
		
	
		//String accessToken = getAccessToken();
		
		//String res = getOpenID("001fd8e7e61ad878df85e70111ea2b0j");
		
		//String jsApiToken = getJsApiToken( accessToken );
		//kgt8ON7yVITDhtdwci0qeX3HU-_ocsJaDtY7fmuuPQKR_q0Jr0PNJdMtBhKsuuF2bchXpgwuODE7_dMLoW9VSQ
		
		//String singJs = signForJs("1234567");
		
		WxPayCache cache = WxPayCache.get();
		
		System.out.println( JsonUtil.toJson(cache) );
		
		refresh();
		
		cache = WxPayCache.get();
		
		System.out.println( JsonUtil.toJson(cache) );
		
		
		try {
			String openID = getOpenID("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
