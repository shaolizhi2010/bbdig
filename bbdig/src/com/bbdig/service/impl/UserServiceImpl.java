package com.bbdig.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.core.constant.Constants;
import com.bbdig.entity.User;
import com.bbdig.entity.UserExample;
import com.bbdig.mapper.PostMapper;
import com.bbdig.mapper.PostViewMapper;
import com.bbdig.mapper.UserMapper;
import com.bbdig.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService { 

	@Autowired
	private UserMapper userMapper ;
 
	
	private   static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Override
	public User createRobotUser(String nickName) {
		if(StringUtils.length(nickName)>40){
			return null;
		}
		User newUser = new User();
			newUser.setNickname(nickName);
			newUser.setCreatedate(new Date());
			newUser.setIsrobot(1);
			newUser.setUseable(1);
			newUser.setAge(0);
			newUser.setSex(1);
			newUser.setFanscount(0);
			newUser.setFollowcount(0);
			newUser.setPostcount(0);
			newUser.setScore(0);
			newUser.setScorecount(0);
			newUser.setLogintimes(0);
			newUser.setLastlogindate(new Date());
			newUser.setIsadmin(0);
			newUser.setMute(0);
			
			
			
			newUser.setDescrip(nickName+"百度云, " 
							+nickName+"百度网盘, " 
							+nickName+"百度资源, " 
							+nickName+"下载"
							);
			
			userMapper.insert( newUser );
			
			return newUser;
	}
	@Override
	public User getByNickname(String nickName){
		UserExample userExample = new UserExample();
		UserExample.Criteria userQuery = userExample.createCriteria();
		
		userQuery.andNicknameEqualTo(nickName);
		List<User> users = userMapper.selectByExample(userExample);
		if(CollectionUtils.isEmpty(users)){
			return null;
		}
		else{
			return users.get(0);
		}
	}
	@Override
	public User getOrCreateRobotByNickname(String nickName){
		
		User newUser = getByNickname(nickName);
		if(newUser==null){
			return createRobotUser(nickName);
		}
		else if(newUser.getIsrobot()==1 && newUser.getUseable()==1 ){//是robot 并且可以用
			return newUser;
		}
//		else{	//名字被占了
//			nickName = nickName + Constants.nicknameExt;
//		}
//		
//		
//		newUser = getByNickname(nickName);
//		if(newUser==null){
//			return createRobotUser(nickName);
//		}
//		else if(newUser.getIsrobot()==1){
//			return newUser;
//		}
//		else{
//			//又被占了。。。放弃！ 可能性很小
//			logger.warn( "用户名：" + nickName +" 被占了 创建失败！！！");
//		}
		
		return null;
	}
	
	@Override
	public List<User> robotList(){
		UserExample userExample = new UserExample();
		UserExample.Criteria userQuery = userExample.createCriteria();
		
		userQuery.andIsrobotEqualTo(Constants.BOOLEAN_TRUE);
		userQuery.andUseableEqualTo(Constants.BOOLEAN_TRUE);
		
		userExample.setOrderByClause(" createDate desc ");
		
		List<User> users = userMapper.selectByExample(userExample);
		return users;
	}
}
