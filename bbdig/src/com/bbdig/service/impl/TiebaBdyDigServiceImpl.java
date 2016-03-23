package com.bbdig.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.entity.TiebaDigHistory;
import com.bbdig.service.TiebaBdyDigService;
import com.bbdig.timer.TiebaBdyTask;
import com.bbdig.util.BaiduYunUtil;
import com.bbdig.util.ConditionMap;
import com.bbdig.util.Connecter;
import com.bbdig.util.U;
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

	@Autowired
	private Datastore mongoDatastore;

	/**
	 * @param tiebaNames
	 */
	public void digAndSave(String... tiebaNames) {
		for (String tiebaName : tiebaNames) {
			digAndSave(tiebaName);
		}
	}

	public void digAndSave(String tiebaName) {

		List<Page> pages = dig(tiebaName);

		int savedCount = 0;
		for (Page a : pages) {
			try {
				// TODO save resource 防重复 TODO uniq处理
				savedCount++;
			} catch (Exception e) {
				e.printStackTrace();
				// empty
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
				System.out.println("digging : " + zurl);

				try {
					WebInfo info = infoUtil.info(zurl, false);
					if (BaiduYunUtil.validTitle(info.title)) {
						Page p = new Page();

						p.title = BaiduYunUtil.trimTitle(info.title);
						p.url = zurl;

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
		int maxrepeatDigTimes = 10;

		TiebaDigHistory h = mongoDatastore.createQuery(TiebaDigHistory.class).filter("tieziID", tieziID).get();
		
		int pageNum = 1;
		if (h == null) {
			h = new TiebaDigHistory();
			h.tieziID = tieziID;
		} else {
			pageNum = h.foundTimes / maxrepeatDigTimes + 1;
		}
		h.pageNum = pageNum;
		h.foundTimes++;
		h.lastDigTime = U.dateTime();
		mongoDatastore.save(h);

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
		new TiebaBdyDigServiceImpl().digAndSave("资源-百度网盘");
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
