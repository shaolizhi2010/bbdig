package com.bbdig.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.bbdig.entity.BadWord;
import com.bbdig.entity.BadWordExample;
import com.bbdig.exception.BusinessException;

public interface BadWordService {
	public void insertSelective(BadWord badWord) throws BusinessException;
	public void updateByPrimaryKeySelective(BadWord badWord) throws BusinessException;
	public BadWord selectByPrimaryKey(Integer id) throws BusinessException;
	public List<BadWord> list(BadWordExample example) throws BusinessException;
	public int count(BadWordExample example) throws BusinessException;
	public void deleteByPrimaryKey(Integer id) throws BusinessException;
}
