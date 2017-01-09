package com.bbdig.timer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.entity.BadWord;
import com.bbdig.entity.HotKeywords1dView;
import com.bbdig.entity.HotKeywordsView;
import com.bbdig.service.BadWordService;
import com.bbdig.service.KeywordService;
import com.bbdig.service.TiebaBdyDigService;
import com.bbdig.service.UserService;
import com.bbdig.util.BadwordUtil;

@Service("keywordTask")
public class KeywordTask {
	
	@Autowired
	TiebaBdyDigService bdyDigService;
	
	@Autowired
	private KeywordService keywordService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BadWordService badWordService;

	private static Logger logger = Logger.getLogger(KeywordTask.class);
	
 	
	public void run() {
	 	try {
	 		//每天热搜的前n个关键字 建立robot。利于seo和用户关注
	 		List<HotKeywords1dView> hotKeywords =  keywordService.hotKeywords1Day(0, 100, 5L);
	 		
	 		List<BadWord> badwords = badWordService.list(null);
	 		
//	 		StringBuffer badwordBuffer = new StringBuffer();
//	 		for(BadWord word : badwords){
//	 			badwordBuffer.append(word.getWord()+"-");
//	 		}
	 		
	 		
	 		for( HotKeywords1dView keyword : hotKeywords ){
	 				if(StringUtils.length(keyword.getKeyword())>=2 
	 						&& StringUtils.length(keyword.getKeyword())<10  //长度限制
	 						&& BadwordUtil.isGood(keyword.getKeyword(), badwords) ){ //无脏话
	 					userService.getOrCreateRobotByNickname(keyword.getKeyword() );
	 				}
	 		}
	 		
		} catch (Exception e) {
			logger.warn(e);
		} 
		
	}
	


}
