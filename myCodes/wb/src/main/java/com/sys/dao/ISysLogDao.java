package com.sys.dao;

import java.util.List;

import com.sys.bean.SysLog;

public interface ISysLogDao {
	/**
	 * <p>功能描述：查询日志记录</p>
	 *@param log
	 *@return	List<SysLog>
	 *travler
	 *2017下午1:23:17
	 */
	List<SysLog> queryLog(SysLog log) throws Exception;
}
