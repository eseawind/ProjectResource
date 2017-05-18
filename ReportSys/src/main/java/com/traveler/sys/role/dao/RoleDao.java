package com.traveler.sys.role.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao {
	List<Map<String,Object>> queryData(Map<String,Object> params) throws Exception;
}
