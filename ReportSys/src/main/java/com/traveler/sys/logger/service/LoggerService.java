package com.traveler.sys.logger.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveler.core.BaseService;
import com.traveler.core.OperationException;
import com.traveler.sys.logger.dao.LoggerDao;
import com.traveler.util.ExceptionUtil;

@Service
public class LoggerService extends BaseService<LoggerService> {
	@Autowired
	private LoggerDao loggerDao;

	public List<Map<String, Object>> queryLogger(Map<String,Object> params) throws Exception {
		List<Map<String, Object>> rs = null;
		try {
			rs = loggerDao.queryLoggerList(params);
		} catch (Exception e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "loggerDao.queryLoggerList 出错！", null);
			throw new OperationException("查询系统日志异常！");
		}
		if (rs != null && rs.size() > 0)
			return rs;
		throw new OperationException(EMPTYDATA);

	}

}
