package com.traveler.sys.logger.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface LoggerDao {
	List<Map<String,Object>> queryLoggerList(Map<String,?> params) throws Exception;
}
