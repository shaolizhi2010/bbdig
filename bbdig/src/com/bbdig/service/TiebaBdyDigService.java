package com.bbdig.service;

import java.util.List;

import com.bbdig.entity.User;

public interface TiebaBdyDigService {
	//public void digAndSave(String... tiebaNames);
	public void digAndSave(String tiebaName, Integer userID, List<User> robots);
	
	public void digAndSave(String tiebaName, String nickName);
	
	public void digAndSave(String nickName) ;
}
