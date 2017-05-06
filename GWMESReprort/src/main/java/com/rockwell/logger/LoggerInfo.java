package com.rockwell.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.UUID;

import com.rockwell.Util.DateUtils;
import com.rockwell.Util.StringUtils;

import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBHelper;

/**
 * 系统自动日志记录
 * <p>
 * 功能描述：日志处理类 参数说明：第一个：日志、模块 、 操作人       loger.info("日志内容", "操作模块","操作人");
 * </p>
 * @author SShi11
 */
public class LoggerInfo extends DBAppender {
	// 日志插入语句
	private static String insertSql = "INSERT INTO GW_REPORT_LOGGERINFO (ID,LEVEL_CODE,MODULE_CODE,DOUSER,CREATE_TIME,NOTE_TEXT) VALUES (?,?, ?, ?,?,?)";
	@Override
	public void append(ILoggingEvent eventObject) {
		Object[] baseMsg = eventObject.getArgumentArray();
		if (isSave(baseMsg)) {
			String id = UUID.randomUUID().toString();
			this.save(
					id, 
					eventObject.getLevel().levelStr,
					StringUtils.toStr(baseMsg[0]),
					StringUtils.toStr(baseMsg[1]),
					DateUtils.nowTime(new Date(), DateUtils.COMMONPATTERN),
					eventObject.getMessage()
					);
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
	 * @author SShi11
	 */
	private void save(String id, String infoLeavel, String module, String doUser,String nowTime, String msg) {
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
				insertStatement.setObject(5, nowTime);
				insertStatement.setObject(6, msg);
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
	 * @return
	 * @author SShi11
	 */
	private boolean isSave(Object[] baseMsg) {
		if (baseMsg == null || baseMsg.length != 2) {
			return false;
		} else {
			return true;
		}
	}
}
