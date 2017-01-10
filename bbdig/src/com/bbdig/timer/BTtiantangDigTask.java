package com.bbdig.timer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.service.BttiantangsDigSevice;

@Service("btdigTask")
public class BTtiantangDigTask {
	
	@Autowired
	BttiantangsDigSevice bttiantangsDigSevice;

	private static Logger logger = Logger.getLogger(BTtiantangDigTask.class);
	
	public void run() {
	 	try {
	 		bttiantangsDigSevice.digAndSave("http://www.bttiantangs.com/list/dianying/index.html",1);
		} catch (Exception e) {
			logger.warn(e);
		} 
		
	}
}
