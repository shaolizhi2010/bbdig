package com.bbdig.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.core.pay.wxpay.MD5;
import com.bbdig.entity.Post;
import com.bbdig.entity.Tiebadighistory;
import com.bbdig.entity.TiebadighistoryExample;
import com.bbdig.entity.User;
import com.bbdig.mapper.TiebadighistoryMapper;
import com.bbdig.service.PostService;
import com.bbdig.service.TiebaBdyDigService;
import com.bbdig.service.UserService;
import com.bbdig.util.BaiduYunUtil;
import com.bbdig.util.Connecter;
import com.bbdig.util.PostDataUtil;
import com.bbdig.util.WebInfoUtil;
import com.bbdig.util.X;
import com.bbdig.vo.Page;
import com.bbdig.vo.WebInfo;

@Service("tiebaBdyDigService")
public class TiebaBdyDigServiceImpl implements TiebaBdyDigService { 

	private static Logger logger = Logger.getLogger(TiebaBdyDigServiceImpl.class);

	// 最多抓多少个帖子
	private int digTieziMax = 100;

	// @Autowired
	// TiebaDigHistoryService digHistoryService;

	//@Autowired
	//private Datastore mongoDatastore;
	
	@Autowired
	TiebadighistoryMapper tiebaDighistoryMapper;
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;

//	/**
//	 * @param tiebaNames
//	 */
//	public void digAndSave(String... tiebaNames) {
//		for (String tiebaName : tiebaNames) {
//			digAndSave(tiebaName);
//		}
//	}
	
	public void digAndSave(String nickName) {
		User user = userService.getOrCreateRobotByNickname(nickName);
		if(user!=null){
			digAndSave(nickName, user.getId(), null);
		}
		
	}
	
	public void digAndSave(String tiebaName, String nickName) {
		User user = userService.getOrCreateRobotByNickname(nickName);
		if(user!=null){
			digAndSave(tiebaName, user.getId(), null);
		}
		
	}

	public void digAndSave(String tiebaName, Integer userID, List<User> robots) {

		List<Page> pages = dig(tiebaName);

		int savedCount = 0;
		if(CollectionUtils.isNotEmpty(pages)){
			
			
			for (Page a : pages) {
				try {
					//   防重复   uniq处理
					Post post = new Post();
					post.setTitle(a.title);
					post.setLink(a.url );
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
					
					post.setExt1(tiebaName);
					post.setMd5(MD5.MD5Encode(post.getLink()));
					//PostDataUtil.setDomain(post);
					if(CollectionUtils.isNotEmpty(robots)){
						for(User r : robots){
							if(StringUtils.contains(a.title, r.getNickname())){ //包含某热门关键字,则保存在这个关键字对应robot下
								post.setUserId(r.getId());
								break;
							}
						}
					}
					PostDataUtil.setDomain(post);
					
					postService.insertSelective(post);
					
					savedCount++;
				} catch (Exception e) {
					logger.warn("保存资源 ： " + a.url +"失败 ： " + e.getMessage());
					//e.printStackTrace();
					// empty
				}
			}		 
		}
	
		System.out.println("BaiduPanDigService : " + savedCount + " saved.");
	}

	public List<Page> dig(String tiebaName) {

		WebInfoUtil infoUtil = new WebInfoUtil();

		List<Page> returnList = new ArrayList<>();

		List<String> tieziList = getTieziLinks(tiebaName);
		int digCount = 0;

		for (String url : tieziList) {
			String tieziId = url.replace("/p/", "");

			List<String> ziyuanList = getZiyuanLinks(tieziId);
			for (String zurl : ziyuanList) {
				logger.debug("digging : " + zurl);
				try {
					Thread.sleep(1000);//休息下
				} catch (Exception e) {
					// TODO: handle exception
				}

				try {
					WebInfo info = infoUtil.info(zurl, false);
					if (BaiduYunUtil.validTitle(info.title)) {
						Page p = new Page();

						p.title = BaiduYunUtil.trimTitle(info.title); 
						p.url = zurl;// + "&dgts="+System.currentTimeMillis();//url变一下 seo

						returnList.add(p);
					}
					// System.out.println(info.title);
					digCount++;
				} catch (Exception e) {
					System.out.println("dig " + zurl + " error : " + e.getMessage());
					continue;
				}
			}
			if (digCount > 30) {// 一次最多取30个
				break;
			}
		}
		return returnList;
	}

	// 抓取帖子
	public List<String> getTieziLinks(String tiebaName) {
		List<String> tieziList = new ArrayList<>();
		String pageSource = Connecter.getPageSource("http://tieba.baidu.com/f?kw=" + tiebaName + "&ie=utf-8", "utf-8");

		Document doc;
		int count = 0;
		try {
			doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
			List<Element> elementList = X.getSubElementList(doc, "//a");

			for (Element e : elementList) {
				Attribute attr = e.getAttribute("href");
				if (attr != null) {
					String href = attr.getValue();
					if (StringUtils.startsWith(href, "/p/")) {
						// System.out.println(e.getValue());
						// System.out.println(href);
						tieziList.add(href);
						if (count++ > digTieziMax) {// 一次最多抓 个，减小系统开销，防止被屏蔽，
							break;
						}
					}
				}
			}
		} catch (Exception e1) {
			logger.warn("抓取贴吧里的帖子失败");
			e1.printStackTrace();
		}
		return tieziList;
	}

	public List<String> getZiyuanLinks(String tieziID) {

		// 根据这个帖子出现的次数，确定要抓第几页，
		// 比如一个帖子，第一次被爬虫发现，那么抓第一页
		// 如果已经是第6次被爬虫发现，那么就不要总抓第一页了，抓第二或第三页。
		// maxrepeatDigTimes 就是每页最多重复抓几次然后调到下一页。这个数字越大，爬虫翻页越慢。
		int maxrepeatDigTimes = 3;
		
		TiebadighistoryExample ex = new TiebadighistoryExample();
		TiebadighistoryExample.Criteria query = ex.createCriteria();
		query.andTieziidEqualTo(tieziID);
		
		List<Tiebadighistory> histries = tiebaDighistoryMapper.selectByExample(ex);
		
		Tiebadighistory h = new Tiebadighistory();
		int pageNum = 1;
		
		if(CollectionUtils.isNotEmpty(histries)){ //old
			h = histries.get(0);
			pageNum = h.getFoundtimes() / maxrepeatDigTimes + 1;
			h.setPagenum( pageNum);
			h.setFoundtimes(h.getFoundtimes()+1);
			h.setLastdigtime(new Date());
			
			tiebaDighistoryMapper.updateByPrimaryKeySelective(h);
		}
		else{ //new
			h.setTieziid(tieziID);
			h.setPagenum( 1);
			h.setFoundtimes(1);
			h.setLastdigtime(new Date());
			tiebaDighistoryMapper.insert(h);
		}

		String url = "http://tieba.baidu.com/p/" + tieziID + "?pn=" + pageNum;

		String pageSource = Connecter.getPageSource(url, "utf-8");

		List<String> returnList = new ArrayList<>();

		if (StringUtils.isBlank(pageSource)) {
			return returnList;
		}

		Document doc;
		try {
			doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
			List<Element> elementList = X.getSubElementList(doc, "//a");

			int count = 0;
			for (Element e : elementList) {
				// 判断是否是百度盘链接，
				// 根据链接文字而不是href，因为用户会直接copy链接，href是百度的中间跳转链接
				if (BaiduYunUtil.validUrl(e.getText())) {
					returnList.add(e.getText().trim());
				}
			}

		} catch (Exception e1) {
			logger.warn("抓取帖子里的资源失败");
			e1.printStackTrace();
		}
		return returnList;
	}

	public static void main(String[] args) {
		//new TiebaBdyDigServiceImpl().digAndSave("资源-百度网盘");
		// U.printList(list);
		// http://tieba.baidu.com/p/3624358624?pid=65347916025&cid=0#65347916025
		// List<Page> list = new
		// BaiduPanDigService().getZiyuanLinks("3624358624" );
		// System.out.println(list.size());
		// U.printList(list);

		// WebInfo info = new
		// WebInfoUtil().info("http://pan.baidu.com/s/1f6mU2");
		//
		// System.out.println(info);
		System.out.println("end");
	}

}
