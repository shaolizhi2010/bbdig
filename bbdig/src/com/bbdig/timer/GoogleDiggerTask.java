package com.bbdig.timer;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.core.app.AppConfig;
import com.bbdig.entity.FollowKeyword;
import com.bbdig.entity.FollowKeywordExample;
import com.bbdig.entity.User;
import com.bbdig.mapper.FollowKeywordMapper;
import com.bbdig.service.GoogleSearchSevice;
import com.bbdig.service.UserService;

@Service("googleDiggerTask")
public class GoogleDiggerTask {
	 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GoogleSearchSevice googleSearchSevice;
	
	@Autowired
	private FollowKeywordMapper followKeywordMapper;

	private static Logger logger = Logger.getLogger(GoogleDiggerTask.class);
	
 	
	public void run() {
	 	try {
	 		if(AppConfig.isGoogleTaskRunningFlag()==true){//正在运行
	 			logger.warn("googleTask 运行中 暂不运行");
	 			return;
	 		}
//	 		if(AppConfig.isResourceCheckTaskRunningFlag()==true){
//	 			logger.warn("ResourceCheckTask 运行中 退出");
//	 			return;
//	 		}
	 		AppConfig.setGoogleTaskRunningFlag(true);
	 		

	 		int newDigCount = 0;
	 		int digKeywordCount = 0;
	 		
	 		FollowKeywordExample ex = new FollowKeywordExample();
	 		ex.setOrderByClause("id desc");
	 		
	 		List<FollowKeyword> followKeywords =  followKeywordMapper.selectByExample(ex);
	 		
	 		//优先搜 follow keyword
	 		for(FollowKeyword kw : followKeywords){
	 			if(digKeywordCount>10){
	 				logger.debug("digKeywordCount = " + digKeywordCount +" , return");
	 				logger.debug("newDigCount = " + newDigCount +"");
	 				
	 				break;
	 			}
//	 			if(newDigCount>100){
//	 				logger.debug("newDigCount = " + newDigCount +" , return");
//	 				break;
//	 			}
	 			User u = userService.getOrCreateRobotByNickname(kw.getKeyword());
	 			if(u==null){
	 				continue;
	 			}
	 			int res =  googleSearchSevice.digAndSave(kw.getKeyword(), u.getId(), null);
	 			if(res==-1){//近期处理过
	 				
	 			}
	 			if(res==-2){//抓取
	 				//break;
	 			}
	 			if(res==-3){//链接超时
	 				//break;
	 			}
	 			if(res>=0){ //正常返回
	 				System.out.println("dig ok : "+kw.getKeyword());
	 				digKeywordCount++;
	 				newDigCount+=res;
	 			}
	 			
	 		}
	 		
	 		//follow keyword 处理完，处理普通keyword
	 		List<User> robots = userService.robotList();
	 		
	 		for(User user : robots){
	 			if(digKeywordCount>10){
	 				logger.debug("digKeywordCount = " + digKeywordCount +" , return");
	 				logger.debug("newDigCount = " + newDigCount +"");
	 				
	 				break;
	 			}
//	 			if(newDigCount>100){
//	 				logger.debug("newDigCount = " + newDigCount +" , return");
//	 				break;
//	 			}
	 			int res =  googleSearchSevice.digAndSave(user.getNickname(), user.getId(), null);
	 			if(res==-1){//近期处理过
	 				
	 			}
	 			if(res==-2){//抓取
	 				//break;
	 			}
	 			if(res==-3){//链接超时
	 				//break;
	 			}
	 			if(res>=0){ //正常返回
	 				digKeywordCount++;
	 				newDigCount+=res;
	 			}
	 			
	 		}
	 		
	 		AppConfig.setGoogleTaskRunningFlag(false);
	 		
	 		
		} catch (Exception e) {
			logger.warn(e);
			AppConfig.setGoogleTaskRunningFlag(false);
		} 
	 	AppConfig.setGoogleTaskRunningFlag(false);
	}
	


}
