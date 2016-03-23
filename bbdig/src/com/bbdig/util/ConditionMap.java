package com.bbdig.util;

import java.util.HashMap;

public class ConditionMap extends HashMap<String, Object>{
//	Map<String,String> conditions = new HashMap<>();
	
	public ConditionMap add(String key, Object value){
		this.put(key, value);
		return this;
	}
}
