package com.bbdig.service;

import java.util.List;

import com.bbdig.entity.User;

public interface GoogleSearchSevice {
	public int digAndSave(String keyword,int userID, List<User> robots);
}
