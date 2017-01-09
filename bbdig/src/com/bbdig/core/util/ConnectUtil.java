package com.bbdig.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ConnectUtil {
	public static String connect(String urlStr,String charset){
		URL url = null;
		try {
			url = new URL(urlStr);
			
			URLConnection connection = url.openConnection();
			connection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
			String line;
			String result = "";
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
			return result;
			
		} catch (Exception e) {
			
			return "";
		}
	}
}
