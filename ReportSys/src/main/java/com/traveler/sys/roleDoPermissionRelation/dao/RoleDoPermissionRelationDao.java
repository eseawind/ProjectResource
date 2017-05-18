package com.traveler.sys.roleDoPermissionRelation.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleDoPermissionRelationDao {
	List<Map<String,Object>> queryData(Map<String,Object> params) throws Exception;
}
