package com.bbdig.service.impl;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import com.bbdig.core.pay.wxpay.MD5;
import com.bbdig.entity.Post;
import com.bbdig.entity.User;
import com.bbdig.service.BttiantangsDigSevice;
import com.bbdig.util.Connecter;
import com.bbdig.util.PostDataUtil;
import com.bbdig.util.X;

public class BttiantangsDigSeviceImpl implements BttiantangsDigSevice {

	@Override
	public int digAndSave(String url, String name) {
		
		String pageSource = Connecter.getPageSource(url, "utf-8");

		Document doc;
		int count = 0;
		try {
			doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
			List<Element> elementList = X.getSubElementList(doc, "//a");

			for (Element e : elementList) {
				Attribute attr = e.getAttribute("href");
				if (attr != null) {
					String href = attr.getValue();
					 if (StringUtils.contains(href, "/movie/") && StringUtils.endsWith(href, "html")) {
						// System.out.println(e.getValue());
						 String htmlUrl = "http://www.bttiantangs.com"+href;
						  System.out.println( htmlUrl );
						 dig(htmlUrl);
					 }
				}
			}
		} catch (Exception e1) {
			//logger.warn("抓取贴吧里的帖子失败");
			e1.printStackTrace();
		}
		
		return 0;
	}
	
	private int dig(String url) {
		String pageSource = Connecter.getPageSource(url, "utf-8");

		Document doc;
		int count = 0;
		try {
			doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
			List<Element> elementList = X.getSubElementList(doc, "//a");

			for (Element e : elementList) {
				Attribute attr = e.getAttribute("href");
				if (attr != null) {
					String href = attr.getValue();
					 if (StringUtils.startsWith(href, "magnet:")) {
						// System.out.println(e.getValue());
						  System.out.println( e.getValue() );
						  System.out.println( href );
					 }
				}
			}
		} catch (Exception e1) {
			//logger.warn("抓取贴吧里的帖子失败");
			e1.printStackTrace();
		}
		
		return 0;
	}
	
	
	public void savePost(String title, String url, Integer userID){
		Post post = new Post();
		post.setTitle( title );
		post.setLink( url );
		post.setCreatedate(DateUtils.addDays(new Date(), 0));//首页让给用户发布的资源
		post.setLastupdatedate(DateUtils.addDays(new Date(), 0));
		post.setUseable(1);
		
		post.setSourcetype(2);//tie ba
		post.setZiyuandou(0);
		post.setOrderbyscore((int)(System.currentTimeMillis()/60000)-240);
		if(new Random().nextInt(10)>5)post.setZiyuandou(1);
		 
		post.setUserId(userID);
		
		post.setTraceopentimes(0);
		post.setTraceuptimes(0);
		
		post.setExt1("bttiantangs.com");
		post.setMd5(MD5.MD5Encode(post.getLink()));
		//PostDataUtil.setDomain(post);
 
		PostDataUtil.setDomain(post);
		
		//postService.insertSelective(post);
	}
	
	
	

}
