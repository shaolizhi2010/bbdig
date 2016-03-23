package com.bbdig;

import java.util.Arrays;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreProtocolPNames;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

//singleton share by whole server
public class App {
	
	MongoClient client;
	Datastore ds;
	
	private HttpClient httpClient = null;
	
	
	private App() {
	}
	
	public static String basePath = "";
	public static String imgPath = "images/icon/";

	private static App app = new App();

	public static App getInstance() {
		return app;
	}


	public HttpClient getHttpClient(){
		if(httpClient == null){
			PoolingClientConnectionManager poolManager = new PoolingClientConnectionManager();
			poolManager.setMaxTotal(100);
			
			httpClient = new DefaultHttpClient(poolManager);
			httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,  
	                "Mozilla/4.0 (compatible; MSIE 7.0; Win32");  
			httpClient.getParams().setParameter(  
	                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);  
	        
	        // 浏览器兼容性  
			httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,  
	                CookiePolicy.BROWSER_COMPATIBILITY);  
		}
		return httpClient;
	}
	
	public Datastore getDatastore() {

		try {
//			if(client == null){
//				MongoCredential credential = MongoCredential.createMongoCRCredential(mogoUserName, "admin", mogoPassword.toCharArray());
//				ServerAddress address = new ServerAddress("127.0.0.1");
//				client = new MongoClient(address,Arrays.asList(credential));
//			}
			if(ds==null){
				
				MongoCredential credential = MongoCredential.createMongoCRCredential("root", "admin", "cake4you".toCharArray());
				ServerAddress address = new ServerAddress("127.0.0.1");// 123.56.123.54 127.0.0.1
				client = new MongoClient(address,Arrays.asList(credential));
				
//				new Morphia().createDatastore(mongoClient, dbName)
				ds= new Morphia().createDatastore(client, "tgy" );
				
			}
			 
			return ds;
		} catch (Exception e) {
			System.out.println("找不到Mongo数据库");
			return null;
		}
	}

}
