//package com.bbdig.service.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.bson.types.ObjectId;
//import org.mongodb.morphia.Datastore;
//import org.mongodb.morphia.DatastoreImpl;
//import org.mongodb.morphia.Key;
//import org.mongodb.morphia.query.Query;
//import org.mongodb.morphia.query.QueryResults;
//import org.mongodb.morphia.query.UpdateOperations;
//import org.mongodb.morphia.query.UpdateResults;
//import org.springframework.stereotype.Service;
//
//import com.bbdig.dao.TiebaDigHistoryDao;
//import com.bbdig.entity.TiebaDigHistory;
//import com.bbdig.service.TiebaDigHistoryService;
//import com.mongodb.DBCollection;
//import com.mongodb.WriteConcern;
//import com.mongodb.WriteResult;
//
//@Service("tiebaDigHistoryService")
//public class TiebaDigHistoryServiceImpl implements TiebaDigHistoryService{
//
//	TiebaDigHistoryDao dao = new TiebaDigHistoryDao();
//
//	public TiebaDigHistory byID(String id) {
//		return dao.byID(id);
//	}
//
//	public List<TiebaDigHistory> getByUrl(String url, int pageNum) {
//		return dao.getByUrl(url, pageNum);
//	}
//
//	public TiebaDigHistory get(Map<String, Object> conditions) {
//		return dao.get(TiebaDigHistory.class, conditions);
//	}
//
//	public long count(Map<String, Object> conditions) {
//		return dao.count(TiebaDigHistory.class, conditions);
//	}
//
//	public Query<TiebaDigHistory> createQuery(Map<String, Object> conditions,
//			String orderStr, int start, int limit) {
//		return dao.createQuery(TiebaDigHistory.class, conditions, orderStr,
//				start, limit);
//	}
//
//	public int hashCode() {
//		return dao.hashCode();
//	}
//
//	public List<TiebaDigHistory> list(Map<String, Object> conditions,
//			String orderStr, int limit) {
//		return dao.list(TiebaDigHistory.class, conditions, orderStr, limit);
//	}
//
//	public List<TiebaDigHistory> list(Map<String, Object> conditions,
//			String orderStr, int start, int limit) {
//		return dao.list(TiebaDigHistory.class, conditions, orderStr, start,
//				limit);
//	}
//
//	public DatastoreImpl getDs() {
//		return dao.getDs();
//	}
//
//	public Class<TiebaDigHistory> getEntityClazz() {
//		return dao.getEntityClazz();
//	}
//
//	public boolean equals(Object obj) {
//		return dao.equals(obj);
//	}
//
//	public DBCollection getCollection() {
//		return dao.getCollection();
//	}
//
//	public Query<TiebaDigHistory> createQuery() {
//		return dao.createQuery();
//	}
//
//	public UpdateOperations<TiebaDigHistory> createUpdateOperations() {
//		return dao.createUpdateOperations();
//	}
//
//	public Class<TiebaDigHistory> getEntityClass() {
//		return dao.getEntityClass();
//	}
//
//	public Key<TiebaDigHistory> save(TiebaDigHistory entity) {
//		return dao.save(entity);
//	}
//
//	public Key<TiebaDigHistory> save(TiebaDigHistory entity, WriteConcern wc) {
//		return dao.save(entity, wc);
//	}
//
//	public UpdateResults updateFirst(Query<TiebaDigHistory> q,
//			UpdateOperations<TiebaDigHistory> ops) {
//		return dao.updateFirst(q, ops);
//	}
//
//	public UpdateResults update(Query<TiebaDigHistory> q,
//			UpdateOperations<TiebaDigHistory> ops) {
//		return dao.update(q, ops);
//	}
//
//	public WriteResult delete(TiebaDigHistory entity) {
//		return dao.delete(entity);
//	}
//
//	public WriteResult delete(TiebaDigHistory entity, WriteConcern wc) {
//		return dao.delete(entity, wc);
//	}
//
//	public WriteResult deleteById(ObjectId id) {
//		return dao.deleteById(id);
//	}
//
//	public WriteResult deleteByQuery(Query<TiebaDigHistory> q) {
//		return dao.deleteByQuery(q);
//	}
//
//	public TiebaDigHistory get(ObjectId id) {
//		return dao.get(id);
//	}
//
//	public List<ObjectId> findIds() {
//		return dao.findIds();
//	}
//
//	public List<ObjectId> findIds(Query<TiebaDigHistory> q) {
//		return dao.findIds(q);
//	}
//
//	public List<ObjectId> findIds(String key, Object value) {
//		return dao.findIds(key, value);
//	}
//
//	public Key<TiebaDigHistory> findOneId() {
//		return dao.findOneId();
//	}
//
//	public Key<TiebaDigHistory> findOneId(String key, Object value) {
//		return dao.findOneId(key, value);
//	}
//
//	public Key<TiebaDigHistory> findOneId(Query<TiebaDigHistory> query) {
//		return dao.findOneId(query);
//	}
//
//	public boolean exists(String key, Object value) {
//		return dao.exists(key, value);
//	}
//
//	public boolean exists(Query<TiebaDigHistory> q) {
//		return dao.exists(q);
//	}
//
//	public long count() {
//		return dao.count();
//	}
//
//	public long count(String key, Object value) {
//		return dao.count(key, value);
//	}
//
//	public long count(Query<TiebaDigHistory> q) {
//		return dao.count(q);
//	}
//
//	public TiebaDigHistory findOne(String key, Object value) {
//		return dao.findOne(key, value);
//	}
//
//	public TiebaDigHistory findOne(Query<TiebaDigHistory> q) {
//		return dao.findOne(q);
//	}
//
//	public QueryResults<TiebaDigHistory> find() {
//		return dao.find();
//	}
//
//	public QueryResults<TiebaDigHistory> find(Query<TiebaDigHistory> q) {
//		return dao.find(q);
//	}
//
//	public Datastore getDatastore() {
//		return dao.getDatastore();
//	}
//
//	public void ensureIndexes() {
//		dao.ensureIndexes();
//	}
//
//	public String toString() {
//		return dao.toString();
//	}
//
//}
