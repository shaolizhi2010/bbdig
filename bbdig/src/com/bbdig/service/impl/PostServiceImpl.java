package com.bbdig.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.entity.Post;
import com.bbdig.entity.PostExample;
import com.bbdig.entity.PostView;
import com.bbdig.entity.PostViewExample;
import com.bbdig.exception.BusinessException;
import com.bbdig.mapper.PostMapper;
import com.bbdig.mapper.PostViewMapper;
import com.bbdig.service.PostService;

@Service("postService")
public class PostServiceImpl implements PostService { 

	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private PostViewMapper postViewMapper;
	
	private   static Logger logger = Logger.getLogger(PostServiceImpl.class);
	
	@Override
	public void insertSelective(Post post) throws BusinessException{
		postMapper.insertSelective(post);
	}
	
	@Override
	public void deleteByPrimaryKey(Integer id) throws BusinessException{
		postMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public void updateByPrimaryKeySelective(Post post) throws BusinessException{
		postMapper.updateByPrimaryKeySelective(post);
	}

	@Override
	public List<Post> list(PostExample example,RowBounds bound) throws BusinessException {
		
		example.setOrderByClause("createDate desc");
		return postMapper.selectPage(example, bound);
	}
	
 
	@Override
	public List<Post> list(PostExample example) throws BusinessException {
		
		return postMapper.selectByExample(example);
	}

	@Override
	public int count(PostExample example) throws BusinessException  {
		return postMapper.countByExample(example);
	}

	@Override
	public Post selectByPrimaryKey(Integer id) throws BusinessException {
		return postMapper.selectByPrimaryKey(id);
	}

	
}
