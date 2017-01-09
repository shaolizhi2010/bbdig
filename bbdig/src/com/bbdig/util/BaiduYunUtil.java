package com.bbdig.util;

import org.apache.commons.lang3.StringUtils;

public class BaiduYunUtil {

	public static boolean validTitle(String title) {

		if (StringUtils.isBlank(title) //
				|| StringUtils.contains(title, "请输入提取") || StringUtils.contains(title, "百度云升级")
				|| StringUtils.contains(title, "页面无法找到") || StringUtils.contains(title, "error 404")
				|| StringUtils.contains(title, "页面不存在") || StringUtils.contains(title, "文件已取消")
				|| StringUtils.contains(title, "链接不存在") || StringUtils.contains(title, ".jpg")
				|| StringUtils.contains(title, ".gif") || StringUtils.contains(title, "302")
				|| StringUtils.contains(title, "微信")|| StringUtils.containsIgnoreCase(title, "v信")
				|| StringUtils.containsIgnoreCase(title, "weixin")
				|| StringUtils.contains(title, "百度云 客户端下载")
				|| StringUtils.contains(title, "505")
				|| StringUtils.equals(title, "百度云 个人专辑 - 云上的日子 你我共享")
				// && StringUtils.contains(info.title, "not allowed")
		) {
			return false;
		}
		return true;
	}
	
	public static boolean validUrl(String url) {
		
		if(StringUtils.isBlank(url)){
			return false;
		}
		
		url = url.trim();
		if ((url.startsWith("http://pan.baidu.com/")
				|| url.startsWith("http://yun.baidu.com/"))
				&& !url.startsWith("http://pan.baidu.com/mbox") // 无标题，滤掉!
				&& !url.startsWith("http://pan.baidu.com/huodong") // 活动，滤掉!
				&& !url.startsWith("http://pan.baidu.com/play") // 无标题，滤掉!
				&& !url.startsWith("http://pan.baidu.com/inbox") // 无标题，滤掉!
				&& !url.startsWith("http://pan.baidu.com/disk/home#") // 无意义
				&& !url.equals("http://pan.baidu.com/disk/home") // 无意义
		) {
			return true;
		}
		
		return false;
	}
	
	public static String trimTitle(String title) {
		title = title.replaceAll("网盘-分享无限制", ""); // 去掉重复字符串
		title = title.replaceAll("99搜盘网", ""); // 去掉重复字符串
		title = title.replaceAll("关注微信", ""); // 去掉重复字符串
		title = title.replaceAll("公众平台", ""); // 去掉重复字符串
		title = title.replaceAll("i_dy666", ""); // 去掉重复字符串
		title = title.replaceAll("西西论坛", ""); // 去掉重复字符串
		title = title.replaceAll("xixidianying.com", ""); // 去掉重复字符串
		title = title.replaceAll("suting123.com", ""); // 去掉重复字符串
		title = title.replaceAll("微信shisankeji", ""); // 去掉重复字符串
		title = title.replaceAll("百度云电影吧", ""); // 去掉重复字符串
		title = title.replaceAll("高清下载吧", ""); // 去掉重复字符串
		title = title.replaceAll("ttmeiju.com", ""); // 去掉重复字符串
		title = title.replaceAll("微博", ""); // 去掉重复字符串
		title = title.replaceAll("@", ""); // 去掉重复字符串
		title = title.replaceAll("【", " ");
		title = title.replaceAll("】", " ");
		title = StringUtils.replace(title, "_免费高速下载|百度云", "")  ;//【百度云】
		title = StringUtils.replace(title, "免费高速下载|百度云", "")  ;//【百度云】
		title = StringUtils.removeEnd(title, "_");
		
		return title;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
