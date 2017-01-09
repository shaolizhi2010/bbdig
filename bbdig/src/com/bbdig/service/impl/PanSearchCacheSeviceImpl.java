//package com.bbdig.service.impl;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//import org.apache.log4j.Logger;
//import org.mongodb.morphia.Datastore;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.bbdig.entity.PanSearchCache;
//import com.bbdig.entity.Post;
//import com.bbdig.service.PanSearchCacheSevice;
//import com.bbdig.service.PostService;
//import com.bbdig.service.UserService;
//import com.bbdig.util.BaiduYunUtil;
// 
//@Service("panSearchCacheSevice")
//public class PanSearchCacheSeviceImpl implements PanSearchCacheSevice {
//
//	private static Logger logger = Logger.getLogger(PanSearchCacheSeviceImpl.class);
//	
//	@Autowired
//	PostService postService;
//	
//	@Autowired
//	UserService userService;
//	
//	@Autowired
//	Datastore mongoDatastore;
//	
//	
//	public int digAndSave() {
//		int newDigCount =0;
//		try {
//			
//			List<PanSearchCache> list = mongoDatastore.createQuery(PanSearchCache.class).limit(1000).asList();
// 
//			for(PanSearchCache cache : list){
//				
//				try {
//					mongoDatastore.delete(cache);
//				} catch (Exception e) {
//					logger.debug(e.getMessage());
//				}
//				
//				try {
//					if (!BaiduYunUtil.validUrl(cache.url)) {
//						continue;
//					}
//					if(!BaiduYunUtil.validTitle(cache.name)){
//						continue;
//					}
//					String title = BaiduYunUtil.trimTitle(cache.name); 
//					
//					Post post = new Post();
//					post.setTitle(  title );
//					post.setLink( cache.url );
//					post.setCreatedate(new Date(116,3,1) );//不上首页
//					post.setLastupdatedate(new Date(116,3,1));
//					post.setUseable(1);
//					post.setSourcetype(3);//from google
//					post.setUserId(2);
//
//					post.setZiyuandou(0);
//					post.setOrderbyscore( (int) (new Date(116,3,1).getTime()/60000));
//					if(new Random().nextBoolean())post.setZiyuandou(1);
//					
//					post.setTraceopentimes(0);
//					post.setTraceuptimes(0);
//					//post.setmd
//					
//					post.setExt1("old system");
//					
//					postService.insertSelective(post);
//					
//					
//				} catch (Exception e) {  
//					logger.debug(e.getMessage());
//					//System.out.println(e.getMessage());
//				}
//				
//
//				
//			}
//			
//		} catch (Exception e) { //time out 
//			logger.warn(e.getMessage());
//		}
//		return newDigCount;
//	}
//	
//	public static void main(String[] args) {
////		List<Page> pages = new GoogleSearchSevice().search("权利的游戏",10);
////		for(Page p : pages){
////			System.out.println(p.name);
////			System.out.println(p.url);
////		}
////		System.out.println(pages.size());
//	}
//	
//
//}
