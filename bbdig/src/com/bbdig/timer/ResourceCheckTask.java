package com.bbdig.timer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.core.app.AppConfig;
import com.bbdig.service.ResourceCheckService;


@Service("resourceCheckTask")
public class ResourceCheckTask {
	
	@Autowired
	ResourceCheckService resourceCheckService;

	private static Logger logger = Logger.getLogger(ResourceCheckTask.class);
 	
	public void run() {
	 	try {
 
	 		if(AppConfig.isResourceCheckTaskRunningFlag()==true){
	 			logger.warn("ResourceCheckTask 运行中 暂不运行");
	 			return;
	 		}
	 		AppConfig.setResourceCheckTaskRunningFlag(true);
	 		resourceCheckService.check();
	 		AppConfig.setResourceCheckTaskRunningFlag(false);
	 		
	 		
		} catch (Exception e) {
			logger.warn(e);
		} 
		
	}

}
