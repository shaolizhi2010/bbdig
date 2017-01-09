package com.bbdig.util;

import org.apache.commons.lang3.StringUtils;

import com.bbdig.core.cache.AppCache;
import com.bbdig.entity.Post;
import com.bbdig.entity.PostView;
import com.bbdig.entity.TrustDomain;

public class PostDataUtil {
	
	static WebInfoUtil webInfoUtil = new WebInfoUtil();
	
	public static String title(String title) {
		if (StringUtils.isBlank(title)) {
			return "";
		}
		if (StringUtils.length(title) > 30) {
			return StringUtils.substring(title, 0, 30) + "..";
		}
		return title;
	}

	public static String title(PostView post) {
		String title = post.getTitle();
		title = StringUtils.trimToEmpty(title);
		
		title = title(title);
		
		//标记网盘类型
		if(StringUtils.isNotBlank(post.getDomainName()) && !StringUtils.contains(title, post.getDomainName())){//防止重复
			title = title + "["+  post.getDomainName()  +"]";
		}
		
		
//		for(TrustDomain domain: AppCache.getInstance().getTrustDomain()){
//			if( StringUtils.indexOf(post.getLink(), domain.getDomain()) >=0 
//					&& StringUtils.indexOf(post.getLink(), domain.getDomain()) <10 ){//包含白名单
//				
//				if(!StringUtils.contains(title, domain.getName())){//防止重复
//					title = title + "["+domain.getName()+"]";
//					return title;
//				}
//			}
//		}
		
		if (post.getZiyuandou() > 0) {
			title = title + "[" + post.getZiyuandou() + "豆]";
		}
		 
		return title;
	}
	
	public static boolean isTrustDomain(String url){
		//TODO 检查是否在白名单里
		for(TrustDomain domain: AppCache.getInstance().getTrustDomain()){
			if( StringUtils.indexOf(url, domain.getDomain()) >=0 && StringUtils.indexOf(url, domain.getDomain()) <10 ){//包含白名单
				return true;
			}
		}
		return false;
	}
	
	//set and return domain id
	public static Integer setDomain(Post post){
		//TODO 检查是否在白名单里
		for(TrustDomain domain: AppCache.getInstance().getTrustDomain()){
			if( StringUtils.indexOf(post.getLink(), domain.getDomain()) >=0 
					&& StringUtils.indexOf(post.getLink(), domain.getDomain()) <10 ){//包含白名单
				post.setDomainId(domain.getId());
				return domain.getId();
			}
		}
		return null;
	}

	public static String descrip(String descrip) {
		if (StringUtils.isBlank(descrip)) {
			return "";
		}
		if (StringUtils.length(descrip) > 100) {
			return StringUtils.substring(descrip, 0, 100) + "..";
		}
		return descrip;
	}

	public static Post dealLinkAndTitle(Post post) {
		String link = post.getLink();
		String title = post.getTitle();
		String psd = "";
		
		if(StringUtils.isBlank(link)){
			return post;
		}
		link = StringUtils.trim(link);
		
		//取link 和 密码
		//https://yunpan.cn/cqptU8MryQD62 （提取码：00c0）
		//链接: http://pan.baidu.com/s/1o8tg7bk 密码: q4u4
		if(StringUtils.contains(link, " ")){//
			String[] strs = StringUtils.split(link);
			for(String str : strs){
				str = StringUtils.trim(str);
				if(StringUtils.contains(str, "http")){
					link =  str ;
				}
				else if(str.matches("[0-9A-Za-z]*")){//密码
					psd = str;
				}
				else if(StringUtils.contains(str, "提取码")){
					psd = StringUtils.substringBetween(str, "（提取码：", "）");
				}
			}
		}

		// title
		if(StringUtils.isBlank(title) ){//用户没写标题
			title = link;
		}
		
		//加 密码
		if(StringUtils.isNotBlank(psd) && !StringUtils.contains(title, psd)){//有密码 标题里没写
			title =    title + "[密码:" + psd+"]";
		}
		
		post.setLink(link);
		post.setTitle(title);
		return post;
	}

}
