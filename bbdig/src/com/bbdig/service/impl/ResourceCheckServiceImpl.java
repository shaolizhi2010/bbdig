package com.bbdig.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.core.constant.Constants;
import com.bbdig.entity.Post;
import com.bbdig.entity.PostExample;
import com.bbdig.mapper.PostMapper;
import com.bbdig.service.ResourceCheckService;
import com.bbdig.util.BaiduYunUtil;
import com.bbdig.util.WebInfoUtil;
import com.bbdig.vo.WebInfo;

@Service("resourceCheckService")
public class ResourceCheckServiceImpl implements ResourceCheckService {

	private static Logger logger = Logger.getLogger(ResourceCheckServiceImpl.class);
	
	private int scanRecordCount = 100;
	
	@Autowired
	private PostMapper postMapper;
	
	WebInfoUtil infoUtil = new WebInfoUtil();
	
	@Override
	public void check() {
		
		PostExample example = new PostExample();
		
		PostExample.Criteria query = example.createCriteria();
		query.andUseableEqualTo(Constants.BOOLEAN_TRUE);
		query.andBlockflagEqualTo(Constants.BOOLEAN_FALSE);
		//query.andLastupdatedateLessThan(DateUtils.addDays(new Date(), -3) );//n天没人回复过 
		
		RowBounds bound =new RowBounds(0, scanRecordCount);
		
		example.setOrderByClause(" createDate asc "); //先查时间最久的
		
		List<Post> list = postMapper.selectPage(example, bound);
		
		for(Post p : list){
			String url = p.getLink();
			
			if(StringUtils.startsWith(url, "http://pan.baidu.com/share/link")){//check
				if(checkBaiduYun(url) == false){
					postMapper.deleteByPrimaryKey(p.getId()); 
				}
			}
		}
		
//		 example = new PostExample();
//		query = example.createCriteria();
//		query.andUseableEqualTo(Constants.BOOLEAN_FALSE);
//		query.andCreatedateLessThan( DateUtils.addDays(new Date(), -15) );
//		//query.andBlockflagEqualTo(Constants.BOOLEAN_FALSE);
//		
//		example.setOrderByClause("createDate asc");
//		
//		list = postMapper.selectPage(example, bound);
//		for(Post p : list){
//			postMapper.deleteByPrimaryKey(p.getId()); 
//		}
	}
	
	
	private boolean checkBaiduYun(String url){
		
		if (BaiduYunUtil.validUrl(url)) { //查百度云
			WebInfo info = infoUtil.info(url, false);
			if (!BaiduYunUtil.validTitle(info.title)) { //已失效
				return false;
			}
		}
		return true;
	}

}
