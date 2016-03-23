package com.bbdig.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class U {

	public static final String pattern_dateTime = "yyyy-MM-dd HH:mm:ss";
	public static final String pattern_dateTime_withoutYear = "MM-dd HH:mm:ss";
	public static final String pattern_date = "yyyy-MM-dd";

	public static String toUnicode(String s){
		if(StringUtils.isBlank(s)){
			return s;
		}
		StringBuffer us = new StringBuffer();
		char[] ss = s.toCharArray();
		for(int i=0;i<ss.length;i++){
			String sss = "\\u" + Integer.toHexString(ss[i] | 0x10000).substring(1);
			us.append(sss);
		}
		return us.toString();
	}
	
	public static String addLinkForMessage(String msg){
		if(StringUtils.isBlank(msg) || !msg.contains("http")){
			return msg;
		}
		String[] subs = msg.split("[ ]");
		StringBuffer sb = new StringBuffer();
		for(String sub : subs){
			if(sub.contains("http")){
				String sub1 = StringUtils.substringBefore(sub, "http");
				String sub2 = StringUtils.substringAfter(sub, "http");
				sub2 = "<a href='http"+sub2+"' target='_blank'>http"+sub2+"</a>";
				sb.append(sub1+sub2);
			}else{
				sb.append(sub);
			}
			
		}
		return sb.toString();
	}
	
	/**
	 * 去掉可能产生注入攻击的字符 替换成空格
	 */
	public static String filterCharacter(String str) {// 过滤特殊字符
		str = StringUtils.replace(str, "(", " ");
		str = StringUtils.replace(str, ")", " ");
		str = StringUtils.replace(str, "<", " ");
		str = StringUtils.replace(str, ">", " ");
		str = StringUtils.replace(str, "'", " ");
		str = StringUtils.replace(str, ";", " ");
		return str;
	}

	public static String randomColor() {
		int i = new Random().nextInt(5);
		switch (i) {
		case 0:
			return "#d9534f"; // red
		case 1:
			return "#428bca";// blue
		case 2:
			return "#5cB85c";// green
		case 3:
			return "#58c0de";// light blue
		case 4:
			return "#f0ad4e";// orange

		default:
			return "#5cB85c";// default green
		}
	}



//	public static String shortURL(String url, int len) {
//		if (url == null)
//			return "";
//		url = StringUtils.trim(url);
//		url = url.replace("http://", "");
//		url = url.replace("https://", "");
//		url = url.replace("www.", "");
//
//		url = StringUtils.substring(url, 0, len);
//
//		return url;
//	}
	
	/*
	public static void refreshSession(HttpSession session) {
		
		  session.removeAttribute("fid");
		  session.removeAttribute("userFolders");
		 session.removeAttribute("curFolder"); session.removeAttribute("fid");
		 session.removeAttribute("folders"); session.removeAttribute("pages");
		 
	}*/

	public static <T> T fromReqJson(HttpServletRequest req, Class<T> clazz) {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(req.getInputStream()));
			String json = "";
			if (br != null) {
				json = br.readLine();
			}
			// StringEscapeUtils.escapeHtml4(json);
			json = U.filterCharacter(json);
			T t = new Gson().fromJson(json, clazz);
			return t;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Map  requestToMap(HttpServletRequest req) {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(req.getInputStream()));
			String json = "";
			if (br != null) {
				json = br.readLine();
			}

			Map<String, String> t = new Gson().fromJson(json, HashMap.class);
			return t;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void message(HttpServletResponse res, String message) {

		try {
			res.getOutputStream().write(message.getBytes("UTF-8"));
			res.getOutputStream().flush();
			//res.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String cookie(HttpServletRequest req, String key) {
		if (req == null || req.getCookies() == null
				|| req.getCookies().length <= 0) {
			return null;
		}
		for (Cookie c : req.getCookies()) {
			if (c.getName() != null && c.getName().equals(key)) {
				return c.getValue();
			}
		}
		return null;
	}

	//
	public static void cookie(HttpServletRequest req, HttpServletResponse res,
			String key, String value) {
		Cookie c = new Cookie(key, value);
		// c.setDomain(C.domain);
		res.addCookie(c);
	}

	public static void forward(ServletRequest req, ServletResponse res,
			String path) {
		try {
			req.getRequestDispatcher(path).forward(req, res);
		} catch (Exception e) {
			System.out.println("forward to " + path + " exception" + " : " + e.getMessage());
			//e.printStackTrace();
		}
	}
	
	public static void redirect(ServletRequest req, ServletResponse res,
			String path) {
		try {
			HttpServletResponse response = (HttpServletResponse)res;
			response.sendRedirect(path);
		} catch (Exception e) {
			System.out.println("redirect to " + path + " exception" + " : " + e.getMessage());
			//e.printStackTrace();
		}
	}

	// TODO redirect
	// public static void forward(ServletRequest req, ServletResponse res,
	// String path) {
	// try {
	// req.getRequestDispatcher("").;
	// } catch (Exception e) {
	// System.out.println("forward to " + path + " exception");
	// e.printStackTrace();
	// }
	// }

	public static String paramString(Object obj, String key) {
		Object value = param(obj, key, String.class);
		if (value == null) {
			return "";
		}
		return value.toString();

	}

	public static List paramList(Object obj, String key) {
		Object value = param(obj, key, List.class);
		if (value != null && value instanceof List) {
			return (List) value;
		}
		return new ArrayList<>();
	}

	public static <T> T param(Object obj, String key, Class<T> clazz) {
		if (obj != null) {
			if (obj instanceof HttpServletRequest) {
				HttpServletRequest req = (HttpServletRequest) obj;
				// attribute
				if (req.getAttribute(key) != null) {
					return (T) req.getAttribute(key);
				}
				// parameter
				else if (req.getParameter(key) != null) {
					return (T) req.getParameter(key);
				}
				// session
				else if (req.getSession() != null
						&& req.getSession().getAttribute(key) != null) {
					return (T) req.getSession().getAttribute(key);
				}
				// cookie
				else if (cookie(req, key) != null) {
					return (T) cookie(req, key);
				}

			} else if (obj instanceof HttpSession) {
				HttpSession session = (HttpSession) obj;
				if (session.getAttribute(key) != null) {
					return (T) session.getAttribute(key);
				}
				// else if(session.getValue(key)!=null){//deprecation
				// return session.getValue(key);
				// }
			}

		}
		return null;
	}

	public static BigDecimal toNum(String str) throws Exception {

		BigDecimal num = new BigDecimal(str);
		num = num.setScale(2, BigDecimal.ROUND_HALF_UP);
		return num;

	}

	public static String shortString(String str, int length) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		if (str.length() < length) {
			return str;
		}
		return StringUtils.substring(str, 0, length) + "..";
	}

	// 是否是乱码
	public static boolean isNotMessCode(String s) {
		if (StringUtils.isBlank(s))
			return true;
		if(StringUtils.contains(s, "�") || StringUtils.contains(s, "æ")){
			return false;
		}
		
		for (int i = 0; i < s.length(); i++) {

			int code = (int) s.charAt(i);

			if (code >= 0x0391 && code <= 0xFFE5 || // 汉字
					code >= 0x0000 && code <= 0x00FF) // asc
			{
				continue;
			} else {
				return false;
			}

		}
		return true; // 所有字符都符合标准 return true;
	}

	public static List subList(List list, int length) {
		if (list == null) {
			return new ArrayList();
		}
		if (list.size() < length) {
			return list;
		}
		return list.subList(0, length);

	}

	public static String abs(String str) {
		if (StringUtils.isBlank(str)) {
			return "0";
		}
		str = str.trim();
		if (str.startsWith("-")) {
			str = str.substring(1);
		}
		return str;

	}

	public static String toMMdd(String timestamp) {
		if (StringUtils.isBlank(timestamp)) {
			return "";
		}
		Date date = new Date(Long.parseLong(timestamp));

		// Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
		return sdf.format(date);

	}

	public static String toDate(String timestamp) {
		if (StringUtils.isBlank(timestamp)) {
			return "";
		}
		Date date = new Date(Long.parseLong(timestamp));

		// Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(date);

	}

	public static BigDecimal toBigDecimal(String amout) {
		if (StringUtils.isBlank(amout)) {
			amout = "0";
		}
		return new BigDecimal(amout);
	}

	public static String toString(Object o) {
		if (o == null) {
			return "";
		} else {
			return o.toString();
		}
	}

	public static String toJson(Object o) {
		if (o == null) {
			return "";
		}

		return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
				.create().toJson(o);
	}

	public static boolean isMoney(String money) {
		if (StringUtils.isBlank(money)) {
			return false;
		}
		if (money.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
			return true;
		}
		return false;
	}

	public static boolean isEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}
		if (email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			return true;
		}
		return false;
	}

	//取之前 或之后几天的 日期
	public static String dateTime(int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE,days);//把日期往后增加.整数往后推,负数往前移动 
		SimpleDateFormat sdf = new SimpleDateFormat(pattern_dateTime);
		return sdf.format(cal.getTime());
	}
	
	// current date time
	public static String dateTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern_dateTime);
		return sdf.format(cal.getTime());
	}

	public static String dateTimeWithOutYear(String datetime) {
		if (StringUtils.isNotBlank(datetime)) {
			try {
				return StringUtils.substringAfter(datetime, "-");
			} catch (Exception e) {
				// not date, do nothing
			}
		}
		return datetime;
	}
	
	public static String date(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern_date);
		return sdf.format(cal.getTime());
	}
	
	//without year and second
	public static String dateTimeShort(String datetime) {
		if (StringUtils.isNotBlank(datetime)) {
			try {
//				String today = U.date();
//				if(datetime.contains(today)){//当天的，省略掉日期
//					datetime = datetime.replace(today, "");
//					return StringUtils.substringBeforeLast(datetime,":");
//				}
//				else{
					return  StringUtils.substringBeforeLast(StringUtils.substringAfter(datetime, "-"), ":");  
//				}
				
			} catch (Exception e) {
				// not date, do nothing
			}
		}
		if(StringUtils.isBlank(datetime)){
			return "";
		}
		return datetime;
	}

	// parse timestamp to yyyy-MM-dd HH:mm:ss SSS
	public static String dateTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern_dateTime);
		return sdf.format(timestamp);
	}

	// parse yyyy-MM-dd HH:mm:ss SSS to timestamp
	public static long dateTime(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern_dateTime);
		try {
			return sdf.parse(source).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	public static String curTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss SSS");
		return sdf.format(cal.getTime());
	}

	// public static String curDate(){
	// Calendar cal = Calendar.getInstance();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// return sdf.format(cal.getTime()) ;
	// }

	public static int parseInt(Object obj) {
		String str = String.valueOf(obj);
		return parseInt(str);
	}
	
	// str -> int
	public static int parseInt(String str) {
		if (StringUtils.isBlank(str)) {
			return 0;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return 0;
	}

	public static Map jsonToMap(String json) {
		Map jsonMap = new Gson().fromJson(json, new TypeToken<Map>() {
		}.getType());
		if (jsonMap != null) {
			return jsonMap;
		}
		return new HashMap<String, String>();
	}

	public static List<Map> jsonToListMap(String json) {
		List<Map> jsonListMap = new Gson().fromJson(json,
				new TypeToken<List<Map>>() {
				}.getType());
		if (jsonListMap != null) {
			return jsonListMap;
		}
		return new ArrayList<Map>();
	}

	public static String jsonValue(String json, String key) {

		Object obj = jsonToMap(json).get(key);
		if (obj != null) {
			return (String) obj;
		}
		return "";
	}

	public static String clean(String str) {
		str = str.replaceAll("lang=\"zh-cn\"", "");

		// 排除大小写干扰
		str = str.replaceAll("Head", "head");
		str = str.replaceAll("HEAD", "head");
		str = str.replaceAll("Description", "description");
		str = str.replaceAll("DESCRIPTION", "description");
		str = str.replaceAll("Keyword", "keyword");
		str = str.replaceAll("KEYWORD", "keyword");
		str = str.replaceAll("Content-Type", "content-type");

		return str;
	}

	public static void printList(List list) {
		for (Object o : list) {
			System.out.println(new Gson().toJson(o));
		}
	}

	public static void printArray(Object[] arr) {
		for (Object o : arr) {
			System.out.println(o.toString());
		}
	}

	public static void printMap(Map map) {
		// //L.trace("U.printMap : ", "print map begin");
		for (Object o : map.entrySet()) {
			Map.Entry entry = (Map.Entry) o;
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		// //L.trace("U.printMap : ", "print map end");
	}

	public String getRulePath() {
		String path = this.getClass().getClassLoader().getResource("")
				.getPath();
		path = StringUtils.substringBeforeLast(path, "target");
		return path;
	}

	public static boolean validateCharset(String charset) {
		if ("gbk".equalsIgnoreCase(charset)) {
			return true;
		}
		if ("utf-8".equalsIgnoreCase(charset)) {
			return true;
		}
		if ("iso-8859-1".equalsIgnoreCase(charset)) {
			return true;
		}
		if ("gb2312".equalsIgnoreCase(charset)) {
			return true;
		}
		return false;
	}

	public static String getFieldNameFromMethod(String methodName, String sub) {
		String fieldName = null;
		if (StringUtils.isNotBlank(methodName)) {
			fieldName = StringUtils.substringAfter(methodName, sub);
			fieldName = StringUtils.uncapitalize(fieldName);
		}
		return fieldName;
	}

	public static BigDecimal add(String s1, String s2) throws Exception {

		BigDecimal b1 = new BigDecimal(s1);
		BigDecimal b2 = new BigDecimal(s2);

		BigDecimal result = b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
		return result;

	}

	public static BigDecimal sub(String s1, String s2) throws Exception {

		BigDecimal b1 = new BigDecimal(s1);
		BigDecimal b2 = new BigDecimal(s2);

		BigDecimal result = b1.subtract(b2).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		return result;

	}

	public static BigDecimal multiply(String s1, String s2) throws Exception {

		BigDecimal b1 = new BigDecimal(s1);
		BigDecimal b2 = new BigDecimal(s2);

		BigDecimal result = b1.multiply(b2).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		return result;

	}

	public static BigDecimal divide(String s1, String s2) throws Exception {

		BigDecimal b1 = new BigDecimal(s1);
		BigDecimal b2 = new BigDecimal(s2);

		BigDecimal result = b1.divide(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
		return result;

	}

	public static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			// 过滤 js

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

	// 过滤掉js中的 大小括号
	public static String js2Text(String inputString) {

		inputString = inputString.replaceAll("[{]", "");
		inputString = inputString.replaceAll("[}]", "");
		inputString = inputString.replaceAll("[(]", "");
		inputString = inputString.replaceAll("[)]", "");

		return inputString;

	}

	// public static boolean isProduct() {
	// if (P.getProperty("isProduct").equals("true")) {
	// return true;
	// } else {
	// return false;
	// }
	// // }
	//
	public static String randomNum(int length) { // length表示生成字符串的长度
		if (length == 0) {
			return "";
		}
		String base = "123456789";// 0123456789 //abcdefghijklmnopqrstuvwxyz
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
