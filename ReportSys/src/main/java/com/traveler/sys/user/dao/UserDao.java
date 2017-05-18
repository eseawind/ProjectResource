package com.traveler.sys.user.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
	List<Map<String,Object>> queryData(Map<String,Object> params) throws Exception;
}
