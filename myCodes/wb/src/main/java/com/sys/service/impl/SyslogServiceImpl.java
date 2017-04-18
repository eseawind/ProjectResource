package com.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.bean.SysLog;
import com.sys.dao.ISysLogDao;
import com.sys.service.ISyslogService;

@Service
public class SyslogServiceImpl implements ISyslogService {
	@Autowired
	private ISysLogDao logDao;
	
	/**
	 * <p>功能描述：</p>
	 *@see com.sys.service.ISyslogService#queryLog(com.sys.bean.SysLog)
	 *travler
	 *2017下午4:50:47
	 */
	@Override
	public List<SysLog> queryLog(SysLog log) throws Exception {
		 return logDao.queryLog(log);
	}
	

}
