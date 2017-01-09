package com.bbdig.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.bbdig.entity.Post;
import com.bbdig.entity.PostExample;
import com.bbdig.entity.PostView;
import com.bbdig.entity.PostViewExample;
import com.bbdig.exception.BusinessException;

public interface PostService {
	public void insertSelective(Post post) throws BusinessException;
	public void updateByPrimaryKeySelective(Post post) throws BusinessException;
	public Post selectByPrimaryKey(Integer id) throws BusinessException;
	public List<Post> list(PostExample example)  throws BusinessException;
	public List<Post> list(PostExample example,RowBounds bound)  throws BusinessException;
	public int count(PostExample example) throws BusinessException;
	public void deleteByPrimaryKey(Integer id) throws BusinessException;
	
	
}
