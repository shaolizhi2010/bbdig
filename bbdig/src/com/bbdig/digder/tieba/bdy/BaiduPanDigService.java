package com.bbdig.digder.tieba.bdy;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import com.bbdig.entity.TiebaDigHistory;
import com.bbdig.service.TiebaDigHistoryService;
import com.bbdig.util.ConditionMap;
import com.bbdig.util.Connecter;
import com.bbdig.util.U;
import com.bbdig.util.WebInfoUtil;
import com.bbdig.util.X;
import com.bbdig.vo.Page;
import com.bbdig.vo.WebInfo; 

public class BaiduPanDigService {
	
	/**
	 * 抓取tieba 内容
	 * 有时候贴吧名字不适合作为tag名字，就在空格后写tagname
	 * 那么如果参数是 "abc吧   abc" ，中间空格前边是贴吧名，后边是tag名，
	 * @param tiebaNames
	 */
	public void digAndSave(String... tiebaNames){
		for(String tiebaStr : tiebaNames){
			String tiebaName = "";
			String tagName = "";
			if(tiebaStr.trim().contains("-")){
				String[] arr = tiebaStr.split("-");
				tiebaName = arr[0].trim();
				tagName = arr[1].trim();
				if(StringUtils.isBlank(tagName)){
					tagName = tiebaName;
				}
			}
			else{
				tiebaName = tiebaStr;
				tagName = tiebaStr;
			}
			digAndSave(tiebaName,tagName);
//			try {
//				Thread.sleep(2*60*1000);//休息n分钟
//			} catch (InterruptedException e) {
//				//empty
//			}
		}
	}
	
	public void digAndSave(String tiebaName, String tagName){ 
		
		List<Page> pages = dig(tiebaName,tagName);
	 
		int savedCount = 0;
		for(Page a : pages){
			try {
				//不存在 防重复 TODO  
				if(true){ 
					
					//TODO save resource
					
					savedCount++;
				}
				else{
					System.out.println("BaiduPanDigService : 资源已存在 " + a.url  );
				}
				
					
			} catch (Exception e) {
				e.printStackTrace();
				//empty
			}
		}
		System.out.println("BaiduPanDigService : " +savedCount + " saved.");
	}

	public List<Page> dig(String tiebaName,String tagName){
		
		List<Page> returnList = new ArrayList<>();
		
		BaiduPanDigService s = new BaiduPanDigService();
		List<Page> tieziList = s.getTieziLinks(tiebaName);
		int digCount = 0 ;
		WebInfoUtil webInfoUtil = new WebInfoUtil();
		
		for(Page t : tieziList){
			String url = t.url;
			String tieziId = url.replace("/p/", "");

			List<Page> ziyuanList = s.getZiyuanLinks(tieziId);
			for(Page z : ziyuanList){
				System.out.println("digging : "+z.url);
				
				try {
					WebInfo info = new WebInfoUtil().info(z.url,false);
					if(StringUtils.isNotBlank(info.title) // 
							&& !StringUtils.contains(info.title, "请输入提取")
							&& !StringUtils.contains(info.title, "百度云升级")
							&& !StringUtils.contains(info.title, "页面无法找到")
							&& !StringUtils.contains(info.title, "error 404")
							&& !StringUtils.contains(info.title, "页面不存在")
							&& !StringUtils.contains(info.title, "文件已取消")
							&& !StringUtils.contains(info.title, "链接不存在")
							&& !StringUtils.contains(info.title, ".jpg")
							&& !StringUtils.contains(info.title, ".gif")
							&& !StringUtils.contains(info.title, "302")
							//&& !StringUtils.contains(info.title, "not allowed")
							){ //加密资源忽略
						Page p = new Page();
						String title = info.title;
						title = title.replaceAll("网盘-分享无限制", ""); //去掉重复字符串
						title = title.replaceAll("99搜盘网", ""); //去掉重复字符串
						title = title.replaceAll("关注微信", ""); //去掉重复字符串
						title = title.replaceAll("公众平台", ""); //去掉重复字符串
						title = title.replaceAll("i_dy666", ""); //去掉重复字符串
						title = title.replaceAll("西西论坛", ""); //去掉重复字符串
						title = title.replaceAll("xixidianying.com", ""); //去掉重复字符串
						title = title.replaceAll("suting123.com", ""); //去掉重复字符串
						title = title.replaceAll("微信shisankeji", ""); //去掉重复字符串
						title = title.replaceAll("百度云电影吧", ""); //去掉重复字符串
						title = title.replaceAll("高清下载吧", ""); //去掉重复字符串
						title = title.replaceAll("ttmeiju.com", ""); //去掉重复字符串
						title = title.replaceAll("微博", ""); //去掉重复字符串
						title = title.replaceAll("@", ""); //去掉重复字符串
						
						if(StringUtils.contains(title, "405")){
							System.out.println("waooo! 405  :"+z.url);
						}
						
						String firstString = title;
						firstString = firstString.trim();
						firstString =StringUtils.trim(firstString);
						firstString =StringUtils.replace(firstString, "[", "");
						firstString =StringUtils.replace(firstString, "【", "");
						
						firstString = StringUtils.substring(title, 0,1);
						
						p.title = title;
						
						p.url = z.url;
						
						
						returnList.add(p);
					}
					//System.out.println(info.title);
					digCount++;
				} catch (Exception e) {
					System.out.println("dig "+z.url + " error : "+e.getMessage());
					continue;
				}
				
			}
			if(digCount> 30){//一次最多取30个
				break;
			}
		}
		return returnList;
	}
	
	//抓取帖子
	public List<Page> getTieziLinks(String tiebaName){
		List<Page> tieziList = new ArrayList<>();
		String pageSource  = Connecter.getPageSource("http://tieba.baidu.com/f?kw="+tiebaName+"&ie=utf-8", "utf-8");
		
		Document doc;
		int count=0;
		try {
			doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
			List<Element> elementList = X.getSubElementList(doc, "//a");
			
			for(Element e : elementList){
				Attribute attr = e.getAttribute("href");
				if(attr!=null){
					String href = attr.getValue();
					if(StringUtils.startsWith(href, "/p/")){
						//System.out.println(e.getValue());
					//	System.out.println(href);
						Page p = new Page();
						p.url = href;
						tieziList.add(p);
						if(count++ > 100){//一次最多抓100个，减小系统开销，防止被屏蔽，
							break;
						}
					}
				}
			}
		} catch ( Exception e1) {
			e1.printStackTrace();
		} 
		return tieziList;
	}
	
	public List<Page> getZiyuanLinks(String tieziID ){
		
		//根据这个帖子出现的次数，确定要抓第几页，
		//比如一个帖子，第一次被爬虫发现，那么抓第一页 
		//如果已经是第6次被爬虫发现，那么就不要总抓第一页了，抓第二或第三页。
		//maxrepeatDigTimes 就是每页最多重复抓几次然后调到下一页。这个数字越大，爬虫翻页越慢。
		int maxrepeatDigTimes = 10;
		
		TiebaDigHistoryService hs = new TiebaDigHistoryService();
		TiebaDigHistory h = hs.get(new ConditionMap().add("tieziID", tieziID));
		
		int pageNum = 1;
		if(h== null){
			h = new TiebaDigHistory();
			h.tieziID = tieziID;
		}
		else{
			pageNum = h.foundTimes/maxrepeatDigTimes+1;
		}
		h.pageNum = pageNum;
		h.foundTimes++;
		h.lastDigTime = U.dateTime();
		hs.save(h);
		
		String url  = "http://tieba.baidu.com/p/"+tieziID+"?pn="+pageNum;
		
		String pageSource  = Connecter.getPageSource(url, "utf-8");
		
		List<Page> returnList = new ArrayList<>();
		
		if(StringUtils.isBlank(pageSource)){
			return returnList;
		}
		
		Document doc;
		try {
			doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
			List<Element> elementList = X.getSubElementList(doc, "//a");
			
			int count=0;
			for(Element e : elementList){
				//判断是否是百度盘链接，
				//根据链接文字而不是href，因为用户会直接copy链接，href是百度的中间跳转链接
				if( (e.getText().trim().startsWith("http://pan.baidu.com/")||e.getText().trim().startsWith("http://yun.baidu.com/"))
						&& !e.getText().trim().startsWith("http://pan.baidu.com/mbox") //无标题，滤掉!
						&& !e.getText().trim().startsWith("http://pan.baidu.com/huodong") //活动，滤掉!
						&& !e.getText().trim().startsWith("http://pan.baidu.com/play") //无标题，滤掉!
						&& !e.getText().trim().startsWith("http://pan.baidu.com/inbox") //无标题，滤掉!
						&& !e.getText().trim().startsWith("http://pan.baidu.com/disk/home#") //无意义
						&& !e.getText().trim().equals("http://pan.baidu.com/disk/home") //无意义
						){
					Page p = new Page();
					p.url = e.getText();//
					returnList.add(p);
//					if(count++ > 10){//一帖最多抓10个，减小系统开销，防止被屏蔽，
//						break;
//					}
				}
			}
			
		} catch ( Exception e1) {
			e1.printStackTrace();
		} 
		return returnList;
	}
	
	
//	public WebInfo getHtmlInfo(String url){
//		WebInfo info = new WebInfoUtil().info(url,false);
//	}
	
	public static void main(String[] args) {
		new BaiduPanDigService().digAndSave("资源-百度网盘");
//		U.printList(list);
		//http://tieba.baidu.com/p/3624358624?pid=65347916025&cid=0#65347916025
//		List<Page> list = new BaiduPanDigService().getZiyuanLinks("3624358624" );
//		System.out.println(list.size());
//		U.printList(list);
		
//		WebInfo info = new WebInfoUtil().info("http://pan.baidu.com/s/1f6mU2");
//		
//		System.out.println(info);
		System.out.println("end");
	}

}
