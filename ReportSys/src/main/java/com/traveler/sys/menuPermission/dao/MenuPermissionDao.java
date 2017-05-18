package com.traveler.sys.menuPermission.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface MenuPermissionDao {
	List<Map<String,Object>> queryMenuPermission(Map<String,Object> parmas) throws Exception;
}
