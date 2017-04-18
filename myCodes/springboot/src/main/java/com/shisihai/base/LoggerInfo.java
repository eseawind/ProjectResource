package com.shisihai.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBHelper;

/**
 * <p>功能描述：日志处理类
 * 参数说明：第一个：日志   第二个：模块   第三个：   操作人
 * loger.info("系统发布成功", "系统发布","系统管理员");
 * </p>
 *shisihai
 *2016下午5:50:35
 */
public class LoggerInfo extends DBAppender {
	// 日志插入语句
	private static String insertSql = "INSERT INTO loggerInfo (level_code,module_code,doUser,create_time,note_text) VALUES (?, ?, ?, ?,?)";
	
	@Override
	public void append(ILoggingEvent eventObject) {
		Object[] baseMsg = eventObject.getArgumentArray();
		if (isSave(baseMsg)) {
			this.save(eventObject.getLevel().levelStr, baseMsg[0].toString(), baseMsg[1].toString(), new Date(), eventObject.getMessage());
		}

	}
	/**
	 * <p>功能描述：保存操作日志</p>
	 *@param infoLeavel
	 *@param module
	 *@param doUser
	 *@param createDate
	 *@param msg
	 *shisihai
	 *2016下午5:50:55
	 */
	private void save(String infoLeavel,String module,String doUser,Date createDate,String msg) {
		Connection connection = null;
		PreparedStatement insertStatement = null;
		try {
			connection = super.connectionSource.getConnection();
			insertStatement = connection.prepareStatement(insertSql);
			synchronized (this) {
				// 保存数据
				insertStatement.setString(1, infoLeavel);
				insertStatement.setString(2, module);
				insertStatement.setString(3, doUser);
				insertStatement.setObject(4, createDate);
				insertStatement.setString(5, msg);
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
