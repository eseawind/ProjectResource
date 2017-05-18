package com.traveler.core;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> {
	public static final String  SYSEXCEP="系统异常！请联系管理员";
	public static final String  EMPTYDATA="没有查询到数据！";
	protected   Logger loger = LoggerFactory.getLogger(getType());
	@Autowired
	SqlSessionFactory sessionFactory;
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws SQLException
	 */
	public Connection conn() throws SQLException {
		Connection connection=sessionFactory.openSession().getConnection();
		if(connection ==null || connection.isClosed() ){
			loger.error("获取的连接不可用！", "查询数据DemoService.getConn()", null);
		}
		return connection;
	}
	/**
	 * 获取子类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getType() {  
		Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }  
}
