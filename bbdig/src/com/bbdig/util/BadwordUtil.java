package com.bbdig.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bbdig.entity.BadWord;


public class BadwordUtil {
	
	private static Logger logger = Logger.getLogger(BadwordUtil.class);
	
	public static boolean isGood(String str, List<BadWord> badwords){
		
		if(CollectionUtils.isEmpty(badwords)){
			logger.warn("敏感词cache为空");
			return true;
		}
		
		for(BadWord w : badwords ){
			if(StringUtils.contains(str,w.getWord() )){
				return false;
			}
		}
		
		return true;
	}
}
