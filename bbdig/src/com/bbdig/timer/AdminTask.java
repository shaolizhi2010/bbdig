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

@Service("adminTask")
public class AdminTask {
	
	@Autowired
	private UserService userService;

	private static Logger logger = Logger.getLogger(AdminTask.class);
	
 	
	public void run() {
	 	try {
 
	 		
		} catch (Exception e) {
			logger.warn(e);
		} 
		
	}
	


}
