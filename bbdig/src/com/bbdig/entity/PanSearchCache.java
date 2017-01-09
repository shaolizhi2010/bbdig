package com.bbdig.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class PanSearchCache  {
	
	@Id
	public ObjectId id;
	
	
	public String key;
	//public Date createDateTTL = new Date();
	
	public String name;
//	public String description;
	//public String comment;
	
	//public String pid;//folderID
	public String url;
	
	//public int sortOrder;//排序编号，1排在第一位。。。
	
	
	//public String iconPath;
	
	//所有人的userid， owner的userid
	//public String userID;
}
