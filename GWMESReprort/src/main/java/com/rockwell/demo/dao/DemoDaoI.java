package com.rockwell.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
/**
 * 测试报表导出DAO
 * @author SShi11
 */
@Mapper
public interface DemoDaoI {
	List<Map<String, ?>> queryUsers(Map<String, ?> params) throws Exception;
}
