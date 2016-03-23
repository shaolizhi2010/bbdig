//package com.bbdig.dao;
//
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.bson.types.ObjectId;
//import org.mongodb.morphia.query.Query;
//
//import com.bbdig.App;
//import com.bbdig.entity.TiebaDigHistory;
//
//public class TiebaDigHistoryDao extends BaseBasicDAO<TiebaDigHistory, ObjectId> {
//
//	public TiebaDigHistoryDao() {
//		super(TiebaDigHistory.class, App.getInstance().getDatastore());
//	}
//	  
//	
//	//根据link url 取 link
//	public List<TiebaDigHistory> getByUrl(String url,int pageNum) {
//		url = StringUtils.trim(url);
//		Query<TiebaDigHistory> query = App.getInstance().getDatastore()
//				.createQuery(TiebaDigHistory.class)
//				.filter("url", url).filter("pageNum", pageNum);
//
//		return find(query).asList();
//	}
//}
