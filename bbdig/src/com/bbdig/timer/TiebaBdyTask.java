package com.bbdig.timer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bbdig.digder.tieba.bdy.BaiduPanDigService;

@Service("tiebaBdyTask")
public class TiebaBdyTask {

	private static Logger logger = Logger.getLogger(TiebaBdyTask.class);
	
 	
	public void run() {
	 	try {
	 		new BaiduPanDigService().digAndSave(  "资源-百度网盘","动漫网盘-动漫", "美剧资源站-美剧","动漫资源-动漫",   "美剧-美剧", "电影-电影");//自动抓取百度云资源
		} catch (Exception e) {
			logger.warn(e);
		} 
		
	}

}
