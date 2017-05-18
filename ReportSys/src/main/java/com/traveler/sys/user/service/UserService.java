package com.traveler.sys.user.service;

import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveler.core.BaseService;
import com.traveler.core.OperationException;
import com.traveler.sys.user.dao.UserDao;
import com.traveler.util.ExceptionUtil;
@Service
public class UserService extends BaseService<UserService> {
	@Autowired
	private UserDao userDao;
	
	public List<Map<String,Object>> queryData(Map<String,Object> params) throws Exception{
		List<Map<String,Object>> rs=null;
		try {
			rs=userDao.queryData(params);
		} catch (Exception e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "userDao.queryData 异常", null);
			throw new SystemException(SYSEXCEP);
		}
		if (rs!=null && rs.size()>0) return rs;
		throw new OperationException(EMPTYDATA);
	}
}
