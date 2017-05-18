package com.traveler.sys.group.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao {
	List<Map<String,Object>> queryData(Map<String,Object> params) throws Exception;
}
