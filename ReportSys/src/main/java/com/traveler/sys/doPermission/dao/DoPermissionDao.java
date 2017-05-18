package com.traveler.sys.doPermission.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface DoPermissionDao {
	List<Map<String,Object>> queryDataMap(Map<String,Object> params) throws Exception;
}
