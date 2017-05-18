package com.traveler.sys.roleMenuPermissionRelation.service;

import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveler.core.BaseService;
import com.traveler.core.OperationException;
import com.traveler.sys.roleMenuPermissionRelation.dao.RoleMenuPermissionRelationDao;
import com.traveler.util.ExceptionUtil;
@Service
public class RoleMenuPermissionRelationService extends BaseService<RoleMenuPermissionRelationService> {
	@Autowired
	private RoleMenuPermissionRelationDao roleMenuPermissionRelationDao;
	
	public List<Map<String,Object>> queryData(Map<String,Object> params) throws Exception{
		List<Map<String,Object>> rs=null;
		try {
			rs=roleMenuPermissionRelationDao.queryData(params);
		} catch (Exception e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "roleMenuPermissionRelationDao.queryData 异常", null);
			throw new SystemException(SYSEXCEP);
		}
		if (rs!=null && rs.size()>0) return rs;
		throw new OperationException(EMPTYDATA);
	}
}
