package com.rockwell.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBHelper;

/**
 * <p>
 * 功能描述：日志处理类 参数说明：第一个：日志、模块 、 操作人       loger.info("日志内容", "操作模块","操作人");
 * </p>
 * shisihai 2016下午5:50:35
 */
public class LoggerInfo extends DBAppender {
	// 日志插入语句
	private static String insertSql = "INSERT INTO GW_REPORT_LOGGERINFO (ID,LEVEL_CODE,MODULE_CODE,DOUSER,CREATE_TIME,NOTE_TEXT) VALUES (?,?, ?, ?,SYSDATE,?)";

	@Override
	public void append(ILoggingEvent eventObject) {
		Object[] baseMsg = eventObject.getArgumentArray();
		if (isSave(baseMsg)) {
			String id = UUID.randomUUID().toString();
			this.save(id, eventObject.getLevel().levelStr, baseMsg[0].toString(), baseMsg[1].toString(),
					eventObject.getMessage());
		}

	}

	/**
	 * <p>
	 * 功能描述：保存操作日志
	 * </p>
	 * 
	 * @param infoLeavel
	 * @param module
	 * @param doUser
	 * @param createDate
	 * @param msg
	 *            shisihai 2016下午5:50:55
	 */
	private void save(String id, String infoLeavel, String module, String doUser, String msg) {
		Connection connection = null;
		PreparedStatement insertStatement = null;
		try {
			connection = super.connectionSource.getConnection();
			insertStatement = connection.prepareStatement(insertSql);
			synchronized (this) {
				// 保存数据
				insertStatement.setObject(1, id);
				insertStatement.setObject(2, infoLeavel);
				insertStatement.setObject(3, module);
				insertStatement.setObject(4, doUser);
				insertStatement.setObject(5, msg);
				insertStatement.execute();
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			DBHelper.closeStatement(insertStatement);
			DBHelper.closeConnection(connection);
		}
	}

	/**
	 * <p>
	 * 功能描述：判断是否需要保存改日志信息
	 * </p>
	 * 
	 * @param baseMsg
	 * @return shisihai 2016下午4:10:35
	 */
	private boolean isSave(Object[] baseMsg) {
		if (baseMsg == null || baseMsg.length != 2) {
			return false;
		} else {
			return true;
		}
	}
}
