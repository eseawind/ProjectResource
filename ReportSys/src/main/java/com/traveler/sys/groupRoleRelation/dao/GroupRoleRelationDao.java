package com.traveler.sys.groupRoleRelation.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface GroupRoleRelationDao {
	List<Map<String,Object>> queryData(Map<String,Object> params) throws Exception;
}
