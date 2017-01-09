package com.bbdig.core.interceptor;

import java.sql.Connection;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;

import com.bbdig.core.constant.Constants;
import com.bbdig.core.ext.Dialect;
import com.bbdig.core.ext.MySql5Dialect;
import com.bbdig.core.ext.OracleDialect;
import com.bbdig.core.util.ReflectHelper;
import com.bbdig.core.util.Tools;


@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor
{

	private final static Log log = LogFactory.getLog(PaginationInterceptor.class);
	// private static Dialect.Type databaseType = null; //数据库方言
	private static Dialect dialect = null;
	private static String pageSqlId = ""; // mapper.xml中需要拦截的ID(正则匹配)

	public Object intercept(Invocation invocation) throws Throwable
	{
		if (invocation.getTarget() instanceof RoutingStatementHandler)
		{
			RoutingStatementHandler statementHandler_ = (RoutingStatementHandler) invocation.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler_,
					"delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate,
					"mappedStatement");
			if (mappedStatement.getId().matches(pageSqlId))
			{ // 拦截需要分页的SQL
				StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
				BoundSql boundSql = statementHandler.getBoundSql();
				MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);
				RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
				if (rowBounds == null || rowBounds == RowBounds.DEFAULT)
				{
					return invocation.proceed();
				}
				String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
				int offset = 0;
				if(rowBounds.getOffset()>0){
					offset = rowBounds.getOffset();
				}
				int limit = Constants.PAGESIZE;
				if(rowBounds.getLimit()>0){
					limit = rowBounds.getLimit();
				}
				metaStatementHandler.setValue("delegate.boundSql.sql",
						dialect.getLimitString(originalSql, offset, limit));
				metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
				metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
				if (log.isInfoEnabled())
				{
					log.info("生成分页SQL : " + boundSql.getSql());
				}
			}
		}

		return invocation.proceed();
	}

	public Object plugin(Object target)
	{
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties)
	{
		String databaseTypeStr = properties.getProperty("dialect");
		if (Tools.isEmpty(databaseTypeStr))
		{
			try
			{
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e)
			{
				e.printStackTrace();
			}
		} else
		{
			Dialect.Type databaseType = Dialect.Type.valueOf(databaseTypeStr.toUpperCase());
			switch (databaseType)
			{
			case MYSQL:
				dialect = new MySql5Dialect();
				break;
			case ORACLE:
				dialect = new OracleDialect();
				break;
			}
		}
		pageSqlId = properties.getProperty("pageSqlId");
		if (Tools.isEmpty(pageSqlId))
		{
			try
			{
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e)
			{
				e.printStackTrace();
			}
		}
	}

}
