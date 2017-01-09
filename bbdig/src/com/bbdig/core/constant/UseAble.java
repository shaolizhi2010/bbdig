package com.bbdig.core.constant;

import java.util.HashMap;
import java.util.Map;

/* 产品是否启用
 */
public enum UseAble { 
 
	YES(1,"启用"),
	NO(400,"停用")
	;
	
	private int index;
 	private String name;

    private UseAble( int index ,String name) {
    	this.index = index;
    	this.name = name;
    }
    
    public String getName() {
        return name;
    }
    public  static String getName(int index){
    	
    	try {
    		for(UseAble e : values() ){
    			if(e.getIndex() == index){
    				return e.getName();
    			}
    		}
    		return "未知";
		} catch (Exception e) {
			return "未知";
		}
    }
    
    public static Map<String,String> toMap(){
    	Map<String, String> map = new HashMap<>();
    	for(UseAble e : values() ){
    		map.put(e.getIndex()+"", e.getName());
    	}
    	return map;
    }
    
    public int getIndex() {
        return index;
    }
    
    @Override
    public String toString() {
        return  this.name;
    }
    


}