package com.bbdig.core.mongo.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

public class BaseBasicDAO<T, K> extends BasicDAO<T, K> {

	protected Datastore mongoDatastore;

	public T byID(String id) {
		if (StringUtils.isBlank(id) || id.equalsIgnoreCase("null")) {
			return null;
		}
		T t = findOne("_id", new ObjectId(id));
		return t;
	}

	// 根据条件，取一个实体
	public T get(Class<T> C, Map<String, Object> conditions) {

		Query<T> query = mongoDatastore.createQuery(C);

		// filter by conditions
		if (conditions != null && conditions.size() != 0) {
			for (Map.Entry<String, Object> e : conditions.entrySet()) {
				if (e.getValue() == null)
					continue;
				query.filter(e.getKey(), e.getValue());
			}
		}
		return findOne(query);
	}

	public long count(Class<T> C, Map<String, Object> conditions) {
		Query<T> query = mongoDatastore.createQuery(C);

		// filter by conditions
		if (conditions != null && conditions.size() != 0) {
			for (Map.Entry<String, Object> e : conditions.entrySet()) {
				if (e.getValue() == null)
					continue;
				query.filter(e.getKey(), e.getValue());
			}
		}
		return count(query);
	}

	public Query<T> createQuery(Class<T> C, Map<String, Object> conditions, String orderStr, int start, int limit) {
		Query<T> query = mongoDatastore.createQuery(C);

		// filter by conditions
		if (conditions != null && conditions.size() != 0) {
			for (Map.Entry<String, Object> e : conditions.entrySet()) {
				if (e.getValue() == null)
					continue;
				query.filter(e.getKey(), e.getValue());
			}
		}
		// order
		if (StringUtils.isNotBlank(orderStr)) {
			query.order(orderStr);
		}
		// start
		if (start > 0) {
			query.offset(start);
		}
		// limit
		if (limit > 0) {
			query.limit(limit);
		}
		return query;
	}

	// 取list
	public List<T> list(Class<T> C, Map<String, Object> conditions, String orderStr, int limit) {
		return list(C, conditions, orderStr, 0, limit);
	}

	// 取list
	public List<T> list(Class<T> C, Map<String, Object> conditions, String orderStr, int start, int limit) {

		Query<T> query = mongoDatastore.createQuery(C);

		// filter by conditions
		if (conditions != null && conditions.size() != 0) {
			for (Map.Entry<String, Object> e : conditions.entrySet()) {
				if (e.getValue() == null)
					continue;
				if (e.getValue().equals("null")) {
					query.filter(e.getKey(), null);
					continue;
				}
				query.filter(e.getKey(), e.getValue());
			}
		}
		// order
		if (StringUtils.isNotBlank(orderStr)) {
			query.order(orderStr);
		}
		// start
		if (start > 0) {
			query.offset(start);
		}
		// limit
		if (limit > 0) {
			query.limit(limit);
		}

		return find(query).asList();
	}

	public BaseBasicDAO(Class<T> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	public BaseBasicDAO(Class<T> entityClass, MongoClient mongoClient, Morphia morphia, String dbName) {
		super(entityClass, mongoClient, morphia, dbName);
	}

	public BaseBasicDAO(Datastore ds) {
		super(ds);
	}

	public BaseBasicDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
		super(mongoClient, morphia, dbName);
	}

	public Datastore getMongoDatastore() {
		return mongoDatastore;
	}

	public void setMongoDatastore(Datastore mongoDatastore) {
		this.mongoDatastore = mongoDatastore;
	}

}
