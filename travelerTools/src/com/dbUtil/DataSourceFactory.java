package com.dbUtil;

import javax.sql.ConnectionPoolDataSource;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;
/**
 * 根据传入的参数确认数据源的数据库类型
 * @author SShi11
 *
 */
public class DataSourceFactory {

	public static ConnectionPoolDataSource createDataSource() throws Exception {
		SQLiteConnectionPoolDataSource dataSource = new SQLiteConnectionPoolDataSource();
		dataSource.setUrl("jdbc:sqlite:C:/Users/Public/workplace/travelerTools/resource/logging.db");
		dataSource.setJournalMode("WAL");
		dataSource.getConfig().setBusyTimeout("10000");
		return dataSource;
	}

}
