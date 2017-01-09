package com.bbdig.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class MapUtil {
	
	private static Logger logger = Logger.getLogger(MapUtil.class);
	
	/**
	 * 根据 经纬度 取 详细地址
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	/*
	public static String getAddressByGps(double longitude, double latitude) {
		
		if(longitude<=0 || latitude<=0){
			return "";
		}

		String url = "http://api.map.baidu.com/geocoder/v2/?ak=yYZvBTsGFpjvDu7EMcQUGmzy&callback=renderReverse&location="
				+ latitude + "," + longitude + "&output=xml&pois=0";
		String result = ConnectUtil.connect(url,"utf-8");
		if (StringUtils.isNotBlank(result)) {
			return StringUtils.substringBetween(result, "<formatted_address>","</formatted_address>");
		}
		
		return "";

	}
	*/
	
	
	
	/**
	 * 根据 详细地址 取 经纬度 
	 * @return
	 */
	public static Map<String,Double> getGpsByAddress(String address) {
		
		Map<String, Double> resultMap = new HashMap<String, Double>();
		
		try {
			if(StringUtils.isBlank(address)){
				return resultMap;
			}

			address = URLEncoder.encode(address,"utf-8");
			
			String url = "http://restapi.amap.com/v3/geocode/geo?address="+address+"&output=xml&key=81db9141797e62767113bb93274c240f" ;
					//"http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=xml&ak=yYZvBTsGFpjvDu7EMcQUGmzy&callback=showLocation";
			
			
			String result = ConnectUtil.connect(url,"utf-8");
			if (StringUtils.isNotBlank(result)) {
				String location = StringUtils.substringBetween(result, "<location>","</location>");
				if( StringUtils.isNotBlank(location) ){
					String longitude =  location.split("[,]")[0];
					String latitude =  location.split("[,]")[1];
					
					resultMap.put("longitude", Double.parseDouble(longitude));
					resultMap.put("latitude", Double.parseDouble(latitude));
				}

				return  resultMap;
				
			}
		} catch (Exception e) {
			logger.warn("取经纬度失败：" + address);
		}
	
		return resultMap;

	}
	
	
	/**
	 * 取当前位置和poi
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public static String getLocationInfoByGps(double longitude, double latitude,String poiType) {
		
		if(longitude<=0 || latitude<=0){
			return "";
		}

		String url = "http://restapi.amap.com/v3/geocode/regeo?output=json&key=81db9141797e62767113bb93274c240f&radius=300&extensions=all&location="
				+ longitude + "," + latitude + "";
		if(StringUtils.isNotBlank(poiType)){
			url = url + "&poitype="+poiType;
		}
		String result = ConnectUtil.connect(url,"utf-8");
		
		return result;
	}
	/**
	 * 根据关键词取当前位置
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getLocationInfoByKeywords(double longitude, double latitude,String keywords) throws UnsupportedEncodingException {
		keywords = URLEncoder.encode(keywords,"utf-8");
		
		if(longitude<=0 || latitude<=0){
			return "";
		}
		
		String url = "http://restapi.amap.com/v3/place/around?output=json&key=81db9141797e62767113bb93274c240f&radius=3000&extensions=all&keywords="
				+ keywords + "&location=" + longitude + "," + latitude + "";
		
		String result = ConnectUtil.connect(url,"utf-8");
		
		return result;
	}
	/**
	 * 根据关键词取全大连
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getLocationInfoByKeywordsDl(String keywords) throws UnsupportedEncodingException {
		keywords = URLEncoder.encode(keywords,"utf-8");
		String url = "http://restapi.amap.com/v3/place/text?output=json&city=dalian&key=81db9141797e62767113bb93274c240f&extensions=all&keywords="
				+ keywords +"";
		
		String result = ConnectUtil.connect(url,"utf-8");
		
		return result;
	}
	
}
