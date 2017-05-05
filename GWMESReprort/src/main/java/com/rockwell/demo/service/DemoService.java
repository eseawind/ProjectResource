package com.rockwell.demo.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rockwell.Util.ExceptionUtil;
import com.rockwell.Util.OperationException;
import com.rockwell.demo.dao.DemoDaoI;
@Service
public class DemoService {
	private final static Logger loger = LoggerFactory.getLogger(DemoService.class);
	@Autowired
	private DemoDaoI demoDaoI;
	public List<Map<String, ?>> queryUsers(Map<String, ?> params) throws OperationException{
		List<Map<String, ?>> rs=null;
		try {
			rs=demoDaoI.queryUsers(params);
		} catch (Exception e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "查询数据demoDaoI.queryUsers(params)", null);
			throw new OperationException("查询异常");
		}
		if(rs==null){
			throw new OperationException("没有查询到数据！");
		}
		return rs;
	}
}
