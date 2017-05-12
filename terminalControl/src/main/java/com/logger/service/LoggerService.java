package com.logger.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseCore.service.BaseService;
import com.commonUtil.OperationException;
import com.commonUtil.SysException;
import com.logger.dao.LoggerDao;

@Service
public class LoggerService extends BaseService<LoggerService> {
	@Autowired
	private LoggerDao loggerDAO;

	@Override
	public List<Map<String, Object>> queryMapList(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> result=null;
		try {
			result = loggerDAO.queryMapList(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException("查询日志异常！", e.getCause());
		}
		if(result == null ){
			throw new OperationException("没有查询到日志信息！");
		}
		return result;
	}

	@Override
	public int updateEntity(Map<String, Object> params) throws Exception {
		int result=0;
		try {
			result = loggerDAO.updateEntity(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException("更新日志异常！", e.getCause());
		}
		if(result == 0){
			throw new OperationException("更新记录行数为："+result);
		}
		return result;
	}
	
	
	
}
