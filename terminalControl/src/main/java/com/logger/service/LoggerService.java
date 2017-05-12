package com.logger.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commonUtil.OperationException;
import com.commonUtil.SysException;
import com.logger.dao.LoggerDAO;
import com.logger.service.LoggerServiceI;

@Service
public class LoggerServiceImpl implements LoggerServiceI {
	@Autowired
	private LoggerDAO loggerDAO;

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
