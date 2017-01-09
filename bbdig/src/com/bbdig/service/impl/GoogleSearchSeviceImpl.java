package com.bbdig.service.impl;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbdig.App;
import com.bbdig.core.pay.wxpay.MD5;
import com.bbdig.entity.Googledighistory;
import com.bbdig.entity.GoogledighistoryExample;
import com.bbdig.entity.Post;
import com.bbdig.entity.User;
import com.bbdig.mapper.GoogledighistoryMapper;
import com.bbdig.service.GoogleSearchSevice;
import com.bbdig.service.PostService;
import com.bbdig.service.UserService;
import com.bbdig.util.BaiduYunUtil;
import com.bbdig.util.GoogleIpUtil;
import com.bbdig.util.PostDataUtil;
import com.bbdig.util.X;
 
@Service("googleSearchSevice")
public class GoogleSearchSeviceImpl implements GoogleSearchSevice {

	private static Logger logger = Logger.getLogger(GoogleSearchSeviceImpl.class);
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
//	@Autowired
//	Datastore mongoDatastore;
	
	@Autowired
	GoogledighistoryMapper googledighistoryMapper;
	
	private final int Wait_days=3;//每几天抓一次
	
	public int digAndSave(String keywordStr,int userid, List<User> robots ) {
		int newDigCount =0;
		try {
			
			GoogledighistoryExample ex = new GoogledighistoryExample();
			GoogledighistoryExample.Criteria query = ex.createCriteria();
			query.andKeywordEqualTo(keywordStr );
			
			List<Googledighistory> histories = googledighistoryMapper.selectByExample(ex);
			
			Googledighistory h = new Googledighistory();
			
			if(CollectionUtils.isNotEmpty( histories)){
				h = histories.get(0);
				if(h!=null && System.currentTimeMillis() - h.getLastdigtimets().getTime() < Wait_days*86400000   ){
					return -1; //n天内 处理过
				}
			}
			else{
				h.setKeyword(keywordStr);
				//h.setLastdigtimets(System.currentTimeMillis());
				h.setPagenum(1);
				googledighistoryMapper.insert(h);
			}
			
			//https://www.google.com.hk/search?q=  http://api1227.pansou.com
			String keyword = URLEncoder.encode(keywordStr,"utf-8");
			String googleIp = GoogleIpUtil.ip() ;
			String googleAddr = "https://www.google.com.hk";// "http://"+ googleIp;// "https://www.google.com.hk";
			String url = googleAddr+"/search?q="+keyword+"+site%3Ayun.baidu.com";//+"&start="+1;
			
			logger.debug("digging : " + url);
			HttpClient httpclient = App.getInstance().getHttpClient();
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			
			//Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en) AppleWebKit/312.1.1 (KHTML, like Gecko) Safari/312
			//Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en) AppleWebKit/312.1.1 (KHTML, like Gecko) Safari/312"); 
			httpget.setHeader("Accept-Language", "zh-CN");
			httpget.setHeader("Accept-Charset", "UTF-8,GBK;q=0.7");
			//httpget.setHeader("Referer", "www.google.com.hk");  
			
			HttpResponse response = httpclient.execute(httpget);

			if(response.getStatusLine().getStatusCode()==200){//成功
				h.setLastdigtimets(new Date());
				googledighistoryMapper.updateByPrimaryKeySelective(h); //更新最后dig时间
			}
			else {	//抓取出现错误
				System.out.println("抓取数据出现错误 ： " + response.getStatusLine().getStatusCode());
				try {
					//FileUtils.writeStringToFile(new File("c:/gg/google-ex.txt"), googleIp);
					Thread.sleep(300000);//休息5分钟
				} catch (Exception e) {
					// TODO: handle exception
				}
				logger.warn("抓取出现错误 返回code " + response.getStatusLine().getStatusCode());
				
				return -2;
			}
			//Location
			 

			HttpEntity entity = response.getEntity();
			InputStream entityContent = entity.getContent();

			HtmlCleaner hc = new HtmlCleaner();
			TagNode node = hc.clean(entityContent);
			
			String pageSource = getPageSourceFromNode(node);
			
			
			Document doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
			
			List<Element> elementList = X.getSubElementList(doc, "//a");
			
			for(Element e : elementList){
//				String text = e.getText();
				Attribute attr = e.getAttribute("href"); //data-href
				if(attr!=null){
					String href = attr.getValue();
					
					href = StringUtils.substringBetween(href, "?q=","&");
					
					//href = StringUtils.substringBetween(href, "url=","&");//?q=
					
					if(href!=null)href= URLDecoder.decode(href,"utf-8");
					
					if(StringUtils.startsWith(href, "http://pan.baidu.com")
							|| StringUtils.startsWith(href, "http://yun.baidu.com")
							|| StringUtils.startsWith(href, "https://pan.baidu.com")
							|| StringUtils.startsWith(href, "https://yun.baidu.com")
							){
						
//						System.out.println(e.getValue());
//						System.out.println(href);
						
						if (!BaiduYunUtil.validUrl(href)) {
							continue;
						}
						if(!BaiduYunUtil.validTitle(e.getValue())){
							continue;
						}
						String title = BaiduYunUtil.trimTitle(e.getValue()); 
						
						Post post = new Post();
						post.setTitle(  title );
						post.setLink( href );
						post.setCreatedate( DateUtils.addDays(new Date(), -2) );//不上首页
						post.setLastupdatedate(DateUtils.addDays(new Date(), -2));
						post.setUseable(1);
						post.setSourcetype(3);//from google
						post.setUserId(userid);

						post.setZiyuandou(0);
						post.setOrderbyscore((int)System.currentTimeMillis()/60000-240);
						if(new Random().nextBoolean())post.setZiyuandou(1);
						
						post.setTraceopentimes(0);
						post.setTraceuptimes(0);
						
						post.setMd5(MD5.MD5Encode(post.getLink()));
						PostDataUtil.setDomain(post);
						
						try {
							postService.insertSelective(post);
							newDigCount++;
						} catch (Exception ex1) { //
							continue;
						}
					}
				}
			}
			

			
 			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		} catch (Exception e) { //time out 
			System.out.println("GoogleSearch Exception : "+e.getMessage());
			try {
				Thread.sleep(300000);
			} catch (Exception e1) {
			}//休息5分钟
			return -3;
		}
		return newDigCount;
	}
	
	

	public  String getPageSourceFromNode(TagNode node) {
		// long start = System.currentTimeMillis();
		HtmlCleaner hc = new HtmlCleaner();
		CleanerProperties props = hc.getProperties();
		props.setNamespacesAware(false);
		props.setOmitCdataOutsideScriptAndStyle(true);
		props.setOmitComments(true);
		props.setOmitXmlDeclaration(true);
		props.setOmitDoctypeDeclaration(true);
		// PrettyXmlSerializer SimpleXmlSerializer
		SimpleXmlSerializer serializer = new SimpleXmlSerializer(props);
		String pageSource = "";
		try {
			pageSource = serializer.getAsString(node, "UTF-8");
		} catch (Exception e) {
			// no thing
		}
		// pageSource = U.clean(pageSource);
		// L.trace("Connecter getPageSourceFromNode ", " finished, time is " +
		// (System.currentTimeMillis() -start));
		return pageSource;
	}
	 
	public static void main(String[] args) {
//		List<Page> pages = new GoogleSearchSevice().search("权利的游戏",10);
//		for(Page p : pages){
//			System.out.println(p.name);
//			System.out.println(p.url);
//		}
//		System.out.println(pages.size());
	}
	

}
