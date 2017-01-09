package com.bbdig.core.constant;

/**
 * 200 成功
 * 400 错误
 * 500 需登录
  */
public enum ErrorCode {
	  SUCCEED(200,"成功"), 
	  ERROR(400,"操作未成功"),
	  NEED_USER_LOGIN(500,"请先登录") ,
	  NEED_SHOP_LOGIN(600,"请先登录") ,
	  NEED_ADMIN_LOGIN(700,"请先登录") 
	  ;
	
	private int index;
 	private String name;

    private ErrorCode( int index ,String name) {
    	this.index = index;
    	this.name = name;
    }
    
    public String getName() {
        return name;
    }
    public  static String getName(int index){
    	
    	try {
    		for(ErrorCode e : values() ){
    			if(e.getIndex() == index){
    				return e.getName();
    			}
    		}
    		return "未知";
		} catch (Exception e) {
			return "未知";
		}
    	
    	
    }
    
    public int getIndex() {
        return index;
    }
    
    @Override
    public String toString() {
        return  this.name;
    }

}