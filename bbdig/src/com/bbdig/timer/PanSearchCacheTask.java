package com.bbdig.timer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.service.PanSearchCacheSevice;

@Service("panSearchCacheTask")
public class PanSearchCacheTask {
	 
	 
	private static Logger logger = Logger.getLogger(PanSearchCacheTask.class);
	
	@Autowired
	private PanSearchCacheSevice panSearchCacheSevice;
 	
	public void run() {
	 	try {
	 		panSearchCacheSevice.digAndSave();
	 		
		} catch (Exception e) {
			logger.warn(e);
		} 
	}
	


}
