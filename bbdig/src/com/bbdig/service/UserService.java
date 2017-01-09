package com.bbdig.service;

import java.util.List;

import com.bbdig.entity.User;

public interface UserService {
	 
	public User createRobotUser(String nickName);
	public User getByNickname(String nickName);
	public User getOrCreateRobotByNickname(String nickName);
	public List<User> robotList();
}
