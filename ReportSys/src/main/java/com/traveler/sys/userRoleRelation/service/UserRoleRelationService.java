package com.traveler.sys.userRoleRelation.service;

import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveler.core.BaseService;
import com.traveler.core.OperationException;
import com.traveler.sys.userRoleRelation.dao.UserRoleRelationDao;
import com.traveler.util.ExceptionUtil;
@Service
public class UserRoleRelationService extends BaseService<UserRoleRelationService> {
	@Autowired
	private UserRoleRelationDao userRoleRelationDao;
	
	public List<Map<String,Object>> queryData(Map<String,Object> params) throws Exception{
		List<Map<String,Object>> rs=null;
		try {
			rs=userRoleRelationDao.queryData(params);
		} catch (Exception e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "userRoleRelationDao.queryData 异常", null);
			throw new SystemException(SYSEXCEP);
		}
		if (rs!=null && rs.size()>0) return rs;
		throw new OperationException(EMPTYDATA);
	}
}
