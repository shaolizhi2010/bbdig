package com.bbdig.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.entity.BadWord;
import com.bbdig.entity.BadWordExample;
import com.bbdig.exception.BusinessException;
import com.bbdig.mapper.BadWordMapper;
import com.bbdig.service.BadWordService;

@Service("badWordService")
public class BadWordServiceImpl implements BadWordService { 

	@Autowired
	private BadWordMapper badWordMapper;
 
	private   static Logger logger = Logger.getLogger(BadWordServiceImpl.class);
	
	@Override
	public void insertSelective(BadWord badWord) throws BusinessException{
		badWordMapper.insertSelective(badWord);
	}
	
	@Override
	public void deleteByPrimaryKey(Integer id) throws BusinessException{
		badWordMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public void updateByPrimaryKeySelective(BadWord badWord) throws BusinessException{
		badWordMapper.updateByPrimaryKeySelective(badWord);
	}

	@Override
	public List<BadWord> list(BadWordExample example) throws BusinessException {
		return badWordMapper.selectByExample(example);
	}

	@Override
	public int count(BadWordExample example) throws BusinessException  {
		return badWordMapper.countByExample(example);
	}

	@Override
	public BadWord selectByPrimaryKey(Integer id) throws BusinessException {
		return badWordMapper.selectByPrimaryKey(id);
	}

	
}
