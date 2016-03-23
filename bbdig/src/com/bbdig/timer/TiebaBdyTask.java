package com.bbdig.timer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.service.TiebaBdyDigService;


@Service("tiebaBdyTask")
public class TiebaBdyTask {
	
	@Autowired
	TiebaBdyDigService bdyDigService;
	

	private static Logger logger = Logger.getLogger(TiebaBdyTask.class);
	
 	
	public void run() {
	 	try {
	 		bdyDigService.digAndSave(  "资源-百度网盘","动漫网盘-动漫", "美剧资源站-美剧","动漫资源-动漫",   "美剧-美剧", "电影-电影");//自动抓取百度云资源
		} catch (Exception e) {
			logger.warn(e);
		} 
		
	}

}
