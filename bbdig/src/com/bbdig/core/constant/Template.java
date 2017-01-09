package com.bbdig.core.constant;

public enum Template {
    RED(1,"红色"), GREEN(2,"绿色" );
    // 成员变量
 	private int index;
 	private String name;

    private Template( int index ,String name) {
    	this.index = index;
    	this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public int getIndex() {
        return index;
    }
    
    @Override
    public String toString() {
        return this.index + "_" + this.name;
    }

}