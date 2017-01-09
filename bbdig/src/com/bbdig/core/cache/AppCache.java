package com.bbdig.core.cache;

import java.util.ArrayList;
import java.util.List;

import com.bbdig.entity.TrustDomain;


/**
 * 常用数据缓存 常驻内存
 * 
 * @author shaolizhi
 *
 */
public class AppCache {
	
	//private static Map<String, CacheObject> cacheMap = new ConcurrentHashMap<String, CacheObject>();
	
	private AppCache() {
	}

	private static AppCache appCache = new AppCache();
	
	public static AppCache getInstance() {
		return appCache;
	}
	
	private List <TrustDomain> trustDomains = new ArrayList<>();
	private List<String> trustDomainNames = new ArrayList<>();
	
	public  List<TrustDomain> getTrustDomain(){
		return trustDomains;
	}
	
	public  List<String> getTrustDomainNames(){
		
		return trustDomainNames;
	}
	
	public void initTrustDomain(List<TrustDomain> list){
		trustDomains = list;
		for( TrustDomain domain : list ){
			if( !trustDomainNames.contains(domain.getName()) ){
				trustDomainNames.add(domain.getName());
			}
		}
	}
	
}


