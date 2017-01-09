package com.bbdig.core.app;

/**
 * singleton share by whole server
 * 
 * @author shao
 */
public class AppConfig {

	private AppConfig() {
	}

	private static AppConfig app = new AppConfig();

	private static String basePath = "";//本地路径
	
	private static String baseWebPath = ""; // 服务器url
	
	private static boolean productFlag = false;//是否是生产环境

	private static boolean tiebaDdyTaskRunningFlag = false;//
	
	private static boolean googleTaskRunningFlag = false;// 
	
	private static boolean ResourceCheckTaskRunningFlag = false;// 
	
	
	
	public static AppConfig getInstance() {
		return app;
	}
	
	public static String get(String path){
		return AppProperties.getProperty(path);
	}

	public static String getBasePath() {
		return basePath;
	}
	
	public static void setBasePath(String path){
		basePath = path;
	}
	
	public static String getApplicationVersion(){
		return AppProperties.getProperty("application.version");
	}
	
	/**
	 * 网页的版本
	 * @return
	 */
	public static String getWebVersion(){
//		return AppProperties.getProperty("application.web.version");
		return System.currentTimeMillis()+"";//TODO 测试
	}
	
	/**
	 * 图片跟路径
	 * @return
	 */
	public static String getPicBasePath(){
		return AppProperties.getProperty("application.path.pic");
	}

	public static boolean isProductFlag() {
		return productFlag;
	}

	public static void setProductFlag(boolean flag) {
		 productFlag = flag;
	}

	public static String getBaseWebPath() {
		return baseWebPath;
	}

	public static void setBaseWebPath(String path) {
		baseWebPath = path;
	}

	public static boolean isTiebaDdyTaskRunningFlag() {
		return tiebaDdyTaskRunningFlag;
	}
	


	public static void setTiebaDdyTaskRunningFlag(boolean tiebaDdyTaskRunningFlag) {
		AppConfig.tiebaDdyTaskRunningFlag = tiebaDdyTaskRunningFlag;
	}

	public static boolean isResourceCheckTaskRunningFlag() {
		return ResourceCheckTaskRunningFlag;
	}

	public static void setResourceCheckTaskRunningFlag(boolean resourceCheckTaskRunningFlag) {
		ResourceCheckTaskRunningFlag = resourceCheckTaskRunningFlag;
	}

	public static boolean isGoogleTaskRunningFlag() {
		return googleTaskRunningFlag;
	}

	public static void setGoogleTaskRunningFlag(boolean googleTaskRunningFlag) {
		AppConfig.googleTaskRunningFlag = googleTaskRunningFlag;
	}
	
	
}
