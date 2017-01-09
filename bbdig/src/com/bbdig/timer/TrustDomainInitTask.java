package com.bbdig.timer;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.core.cache.AppCache;
import com.bbdig.entity.TrustDomain;
import com.bbdig.mapper.TrustDomainMapper;

@Service("trustDomainInitTask")
public class TrustDomainInitTask {
	
	@Autowired
	private TrustDomainMapper trustDomainMapper;
	
	private static Logger logger = Logger.getLogger(TrustDomainInitTask.class);
	
	public void run() {
	 	try {
	 	 
	 		List<TrustDomain> domains = trustDomainMapper.selectByExample (null);
			
			if(CollectionUtils.isNotEmpty( domains)){
				AppCache.getInstance().initTrustDomain(domains);
				logger.debug("域名白名单已加载");
			}
			
		} catch (Exception e) {
			logger.warn(e);
		} 
		
	}

}
