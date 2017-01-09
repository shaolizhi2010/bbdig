package com.test;

import java.io.StringReader;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import com.bbdig.service.impl.BttiantangsDigSeviceImpl;
import com.bbdig.util.Connecter;
import com.bbdig.util.X;

public class TestMain {

	public static void main(String[] args) {
		
		BttiantangsDigSeviceImpl dig = new BttiantangsDigSeviceImpl();
		
		
		dig.digAndSave("http://www.bttiantangs.com/list/dianying/index.html", "电影");
		
		
		
///movie/38342.html
//		String pageSource = Connecter.getPageSource("http://www.bttiantangs.com/sb/%E7%88%B1%E6%83%85.html", "utf-8");
//
//		Document doc;
//		int count = 0;
//		try {
//			doc = new org.jdom2.input.SAXBuilder().build(new StringReader(pageSource));
//			List<Element> elementList = X.getSubElementList(doc, "//a");
//
//			for (Element e : elementList) {
//				Attribute attr = e.getAttribute("href");
//				if (attr != null) {
//					String href = attr.getValue();
//					//if (StringUtils.startsWith(href, "/p/")) {
//						// System.out.println(e.getValue());
//						  System.out.println(href);
//						 
//					//}
//				}
//			}
//		} catch (Exception e1) {
//			//logger.warn("抓取贴吧里的帖子失败");
//			e1.printStackTrace();
//		}
		
		//	System.out.println( NumberUtils.toInt(str) );
//		for(int i=0;i<100;i++){
//			System.out.println(new Random().nextBoolean());	
//		}
		
		
	}

}
