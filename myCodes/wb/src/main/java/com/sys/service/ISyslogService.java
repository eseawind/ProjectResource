package com.sys.service;

import java.util.List;

import com.sys.bean.SysLog;

public interface ISyslogService {
	
	/**
	 * <p>功能描述：查询系统日志</p>
	 *@param log
	 *@return
	 *@throws Exception
	 *travler
	 *2017下午4:50:25
	 */
	List<SysLog> queryLog(SysLog log) throws Exception;
}
