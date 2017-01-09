package com.bbdig.core.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class MVCUtil {

	private static Logger logger = Logger.getLogger(MVCUtil.class);

	public static void responseText(HttpServletResponse response, String text) {

		try {
			response.setCharacterEncoding("utf-8"); // TODO do it in filter
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(text);
			response.getWriter().flush();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void responseJson(HttpServletResponse response, Object vo) {
		String jsonText = JsonUtil.toJson(vo);
		responseText(response, jsonText);
	}

	public static void responseJsonOfSuccess(HttpServletResponse response, Object vo) {
		responseText(response, getJsonStringOfSuccess(vo));
	}
	
	public static void responseJsonOfSuccessTime(HttpServletResponse response, Object vo) {
		responseText(response, getJsonStringOfSuccessTime(vo));
	}
	
	public static void responseJsonOfError(HttpServletResponse response, Object errorMsg) {
		responseText(response, getJsonStringOfError(errorMsg));
	}
	
	public static void responseJsonOfError(HttpServletResponse response, Object errorMsg,int errorCode) {
		responseText(response, getJsonStringOfError(errorMsg,errorCode));
	}

	public static String getJsonStringOfSuccess(Object data) {

		Map<String, Object> resultJsonObject = new HashMap<String, Object>();
		resultJsonObject.put("code", 200);
		resultJsonObject.put("message", "success");
		resultJsonObject.put("data", data);

		String json = JsonUtil.toJson(resultJsonObject);

		return json;
	}
	
	public static String getJsonStringOfSuccessTime(Object data) {

		Map<String, Object> resultJsonObject = new HashMap<String, Object>();
		resultJsonObject.put("code", 200);
		resultJsonObject.put("message", "success");
		resultJsonObject.put("data", data);

		String json = JsonUtil.toJsonTime(resultJsonObject);

		return json;
	}

	public static String getJsonStringOfError(Object errorMsg) {

		Map<String, Object> resultJsonObject = new HashMap<String, Object>();
		resultJsonObject.put("code", 400);
		resultJsonObject.put("message", "failed");
		resultJsonObject.put("data", errorMsg);

		String json = JsonUtil.toJson(resultJsonObject);

		return json;
	}
	
	public static String getJsonStringOfError(Object errorMsg,int errorCode) {

		Map<String, Object> resultJsonObject = new HashMap<String, Object>();
		resultJsonObject.put("code", errorCode);
		resultJsonObject.put("message", "failed");
		resultJsonObject.put("data", errorMsg);

		String json = JsonUtil.toJson(resultJsonObject);

		return json;
	}

}
