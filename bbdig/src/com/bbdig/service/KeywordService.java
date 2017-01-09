package com.bbdig.service;

import java.util.List;

import com.bbdig.entity.HotKeywords1dView;
import com.bbdig.entity.HotKeywordsView;

public interface KeywordService {
	  
	public List<HotKeywordsView> hotKeywords(int start,int limit) ;
	
	public List<HotKeywordsView> hotKeywords(int start,int limit, Long times);
	
	public List<HotKeywords1dView> hotKeywords1Day(int start,int limit,Long times);
	
}
