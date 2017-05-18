package com.traveler.sys.menuPermission.service;

import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveler.core.BaseService;
import com.traveler.core.OperationException;
import com.traveler.sys.menuPermission.dao.MenuPermissionDao;
import com.traveler.util.ExceptionUtil;
@Service
public class MenuPermissionService extends BaseService<MenuPermissionService> {
	@Autowired
	private MenuPermissionDao menuPermissionDao;
	public List<Map<String,Object>> queryMenuPermission(Map<String,Object> params) throws Exception{
		List<Map<String,Object>> rs=null;
		try {
			rs=menuPermissionDao.queryMenuPermission(params);
		} catch (Exception e) {
			loger.error(ExceptionUtil.formatStackTrace(e),"MenuPermissionService.queryMenuPermission 异常",null);
			throw new SystemException(SYSEXCEP);
		}
		if(rs!=null && rs.size()>0)
		return rs;
		throw new OperationException(EMPTYDATA);
	} 
}
