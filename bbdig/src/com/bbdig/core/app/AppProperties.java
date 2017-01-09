package com.bbdig.core.app;

import java.util.Properties;

public class AppProperties {

	static Properties properties = new Properties();
	static boolean loaded = false;

	static {
		loadProperties();
	}

	public static Properties getProperties() {
		//loadProperties();
		return properties;
	}

	public static String getProperty(String key) {
		//loadProperties();
		return  properties.getProperty(key);
	}

	private static void loadProperties() {
		if (!loaded) {
			try {
				properties.load(AppProperties.class
						.getResourceAsStream("/app-config.properties"));
				loaded = true;
			} catch (Exception e) {
				System.out.println(	"load property file error : " + e.getMessage());
			}
		}
	}
}
