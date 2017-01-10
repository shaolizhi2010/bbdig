package com.bbdig.service.impl;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.catalina.util.MD5Encoder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.core.pay.wxpay.MD5;
import com.bbdig.entity.Link;
import com.bbdig.entity.Post;
import com.bbdig.entity.PostExample;
import com.bbdig.entity.User;
import com.bbdig.mapper.LinkMapper;
import com.bbdig.mapper.PostMapper;
import com.bbdig.service.BttiantangsDigSevice;
import com.bbdig.service.PostService;
import com.bbdig.util.Connecter;
import com.bbdig.util.PostDataUtil;
import com.bbdig.util.X;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("bttiantangsDigSevice")
public class BttiantangsDigSeviceImpl implements BttiantangsDigSevice {

	@Autowired
	PostMapper postMapper;
	
	@Autowired
	LinkMapper linkMapper;
	
	@Override
	public int digAndSave(String url, Integer userID) {
		
		String pageSource = Connecter.getPageSource(url, "utf-8");

		Document doc;
		int count = 0;
		try {
			doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
			List<Element> elementList = X.getSubElementList(doc, "//a");

			for (Element e : elementList) {
				Attribute attr = e.getAttribute("href");
				String title = e.getValue();
				if (attr != null) {
					String href = attr.getValue();
					 if (StringUtils.contains(href, "/movie/") && StringUtils.endsWith(href, "html")) {
						// System.out.println(e.getValue());
						 String htmlUrl = "http://www.bttiantangs.com"+href;
						  System.out.println( htmlUrl );
						 dig(htmlUrl, title,  userID);
					 }
				}
			}
		} catch (Exception e1) {
			//logger.warn("抓取贴吧里的帖子失败");
			e1.printStackTrace();
		}
		
		return 0;
	}
	
	private int dig(String url,String title, Integer userID) {
		String pageSource = Connecter.getPageSource(url, "utf-8");

		Document doc;
		try {
			doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
			List<Element> elementList = X.getSubElementList(doc, "//a");

			if(CollectionUtils.isNotEmpty(elementList)){
				
				Integer pid = null;
				
				//保存post
				pid = savePost(title, url, userID);	
				
				for (Element e : elementList) {
					Attribute attr = e.getAttribute("href");
					if (attr != null) {
						String href = attr.getValue();
						 if (StringUtils.startsWith(href, "magnet:")) {
							// System.out.println(e.getValue());
							  System.out.println( e.getValue() );
							  System.out.println( href );
							  
							  //保存link
							  saveLink(e.getValue(), href, pid);
						 }
					}
				}
			}
			

		} catch (Exception e1) {
			//logger.warn("抓取贴吧里的帖子失败");
			e1.printStackTrace();
		}
		
		return 0;
	}
	
	
	public Integer savePost(String title, String url, Integer userID){
		
		Post post = new Post();
		
		try {
			post.setTitle(title);
			post.setLink( url );
			post.setCreatedate(DateUtils.addDays(new Date(), 0));//首页让给用户发布的资源
			post.setLastupdatedate(DateUtils.addDays(new Date(), 0));
			post.setUseable(1);
			
			post.setSourcetype(11);//magnet
			post.setZiyuandou(0);
			post.setOrderbyscore((int)(System.currentTimeMillis()/60000)-240);
			//if(new Random().nextInt(10)>5)post.setZiyuandou(1);
			 
			post.setUserId(userID);
			
			post.setTraceopentimes(0);
			post.setTraceuptimes(0);
			
			post.setExt1("bttiantangs.com");
			post.setMd5(MD5.MD5Encode(post.getLink()));
			//PostDataUtil.setDomain(post);
	 
			PostDataUtil.setDomain(post);
			
			postMapper.insertSelective(post);
			return post.getId();
		} 
		catch (Exception e) {
			PostExample ex = new PostExample();
			
			PostExample.Criteria query = ex.createCriteria();
			query.andMd5EqualTo(post.getMd5());
			
			List<Post> list = postMapper.selectByExample(ex);
			
			if(CollectionUtils.isNotEmpty(list)){
				return list.get(0).getId();
			}
			
			return null;
		}

	}
	
	public void saveLink(String title, String url, Integer pID){
		
		try {
			
			Link link = new Link();
			link.setCreatedate(new Date());
			link.setPid(pID);
			link.setTitle(title);
			link.setUrl(url);
			link.setMd5(MD5.MD5Encode(url));
			link.setTs((int)System.currentTimeMillis());

			linkMapper.insert(link);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
