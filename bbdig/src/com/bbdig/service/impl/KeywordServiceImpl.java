package com.bbdig.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.entity.HotKeywords1dView;
import com.bbdig.entity.HotKeywords1dViewExample;
import com.bbdig.entity.HotKeywordsView;
import com.bbdig.entity.HotKeywordsViewExample;
import com.bbdig.mapper.HotKeywords1dViewMapper;
import com.bbdig.mapper.HotKeywordsViewMapper;
import com.bbdig.mapper.KeywordsMapper;
import com.bbdig.mapper.UserMapper;
import com.bbdig.service.KeywordService;

@Service("keywordService")
public class KeywordServiceImpl implements KeywordService { 

	@Autowired
	private KeywordsMapper keywordMapper ;
	
	@Autowired
	private HotKeywordsViewMapper hotKeywordsViewMapper;
	
	@Autowired
	HotKeywords1dViewMapper keywords1dViewMapper;
	
	private   static Logger logger = Logger.getLogger(KeywordServiceImpl.class);

	/**
	 * 最热关键字 
	 */
	@Override
	public List<HotKeywordsView> hotKeywords(int start,int limit) {

 		HotKeywordsViewExample keywordExample = new HotKeywordsViewExample();
 		HotKeywordsViewExample.Criteria keywordQuery = keywordExample.createCriteria();
 		
 		keywordQuery.andUseableEqualTo(1);
 		
 		List<HotKeywordsView> hotKeywords = hotKeywordsViewMapper.selectPage(keywordExample, new RowBounds(start, limit));

		return hotKeywords;
	}
	
	/**
	 * 最热关键字 
	 */
	@Override
	public List<HotKeywordsView> hotKeywords(int start,int limit, Long times) {

 		HotKeywordsViewExample keywordExample = new HotKeywordsViewExample();
 		HotKeywordsViewExample.Criteria keywordQuery = keywordExample.createCriteria();
 		
 		keywordQuery.andUseableEqualTo(1);
 		
 		if(times!=null){
 			keywordQuery.andTimesGreaterThan(times);
 		}
 		
 		List<HotKeywordsView> hotKeywords = hotKeywordsViewMapper.selectPage(keywordExample, new RowBounds(start, limit));

		return hotKeywords;
	}
	
	/**
	 * 每日最热关键字
	 * @return
	 */
	@Override
	public List<HotKeywords1dView> hotKeywords1Day(int start,int limit,Long times) {

 		HotKeywords1dViewExample keyword1dExample = new HotKeywords1dViewExample();
 		HotKeywords1dViewExample.Criteria keywordQuery = keyword1dExample.createCriteria();
 		
 		keywordQuery.andUseableEqualTo(1);
 		if(times!=null){
 			keywordQuery.andTimesGreaterThan(times);
 		}
 		
 		List<HotKeywords1dView> hotKeywords = keywords1dViewMapper.selectPage(keyword1dExample, new RowBounds(start, limit));

		return hotKeywords;
	}
	
}
