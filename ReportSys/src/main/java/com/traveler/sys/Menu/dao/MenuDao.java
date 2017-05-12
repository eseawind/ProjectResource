package com.traveler.sys.Menu.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao {
	List<Map<String, Object>> queryMenu(Map<String, ?> params) throws Exception;
}
