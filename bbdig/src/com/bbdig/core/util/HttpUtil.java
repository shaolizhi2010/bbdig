package com.bbdig.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpUtil
{
	
	public static String post(String url, Map<String,String> heads, Map<String,String> params) throws Exception
	{
		CloseableHttpClient  httpclient =  HttpClients.custom()
		        .setConnectionManager(new PoolingHttpClientConnectionManager())
		        .build();
		
		HttpPost httpost = new HttpPost(url);
		
		if(MapUtils.isNotEmpty(heads)){
			for( Map.Entry<String, String> e : heads.entrySet() ){
				httpost.setHeader(e.getKey(), e.getValue());
			}
		}
		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		
		if(MapUtils.isNotEmpty(params)){
			for( Map.Entry<String, String> e : params.entrySet() ){
				nvps.add(new BasicNameValuePair(e.getKey(), e.getValue()));
			}
		}
		
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			
			HttpResponse response = httpclient.execute(httpost);
			
			HttpEntity entity = response.getEntity();
			
			String resString = IOUtils.toString( entity.getContent() );
			
			httpclient.close();
			return resString;
				
		} catch (Exception e) {
			httpclient = null;
			throw e;
		} finally {
			httpclient = null;
		}
		
	}
	 
 
	public static void main(String[] args){
		
		Map<String,String> heads = new HashMap<>();
		Map<String,String> params = new HashMap<>();
		
		heads.put("Content-Type", "application/x-www-form-urlencoded");
		
		params.put("key", "81db9141797e62767113bb93274c240f");
		params.put("name", "abcd");
		
		String resString;
		try {
			resString = post("http://yuntuapi.amap.com/datamanage/table/create",heads, params);
			System.out.println("result=" + resString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	 
}
