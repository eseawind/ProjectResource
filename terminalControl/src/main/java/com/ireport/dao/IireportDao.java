package com.ireport.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by SShi11 on 5/3/2017.
 */
@Repository
public interface IireportDao {
    List<Map<String,?>> queryUsers(Map<String,Object> params);
}
