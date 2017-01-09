package com.bbdig.core.util;

import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonUtil
{
	private static Gson gsonOfDateFormat = null; //default
	private static Gson gsonOfTimeFormat = null;
	
	
	public static Gson getInstance(){
		if(gsonOfDateFormat == null){
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd");
			gsonOfDateFormat = gsonBuilder.create();
		}
		return gsonOfDateFormat;
	}
	
	public static Gson getInstanceOfDateFormat(){
		return getInstance();
	}
	
	public static Gson getInstanceOfTimeFormat(){
		if(gsonOfTimeFormat == null){
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
			gsonOfTimeFormat = gsonBuilder.create();
		}
		return gsonOfTimeFormat;
	}
	
	public static <T> T fromJson(String sourceGson, Class<T> targetClass)
	{
		try
		{
			if(StringUtils.isBlank(sourceGson)){
				return null;
			}
			
			return getInstanceOfTimeFormat().fromJson(sourceGson, targetClass);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T fromJson(String sourceGson, Type type)
	{
		try
		{
			if(StringUtils.isBlank(sourceGson)){
				return null;
			}
			
			return getInstanceOfTimeFormat().fromJson(sourceGson,  type);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
 
	public static String toJson(Object o)
	{
		if(o==null){
			return "";
		}
		return getInstanceOfTimeFormat().toJson(o);
	}
	
	public static String toJsonTime(Object o)
	{
		if(o==null){
			return "";
		}
		return getInstanceOfTimeFormat().toJson(o);
	}

 
}
