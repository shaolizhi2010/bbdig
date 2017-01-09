package com.bbdig.core.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.google.gson.Gson;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PrintSqlInterceptor implements Interceptor {

	private final static Log log = LogFactory.getLog(PrintSqlInterceptor.class);

	public Object intercept(Invocation invocation) throws Throwable {
		// if (invocation.getTarget() instanceof RoutingStatementHandler)
		// {

		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		BoundSql boundSql = statementHandler.getBoundSql();

		if (log.isInfoEnabled()) {
			String sql = boundSql.getSql();
			sql = sql.replaceAll("\\s{2,}", " ");

			sql = sql.replaceAll("\r", "");
			sql = sql.replaceAll("\n", "");
			sql = sql.replaceAll("\t", "");
			
			

			String param = new Gson().toJson(boundSql.getParameterObject());

//			param = param.replaceAll("\\u003d", "=");
//			param = param.replaceAll("\\u003c", "<");
//			param = param.replaceAll("\\u003e", ">");
			
			log.info("SQL 	: " +sql);
			log.info("Param : " + param);
		}
		// }

		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {

	}

}
