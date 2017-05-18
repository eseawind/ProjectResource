package com.traveler.sys.userRoleRelation.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRelationDao {
	List<Map<String,Object>> queryData(Map<String,Object> params) throws Exception;
}
