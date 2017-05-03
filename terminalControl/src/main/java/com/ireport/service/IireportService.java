package com.ireport.service;

import com.baseCore.service.BaseServiceI;

import java.util.List;
import java.util.Map;
/**
 * Created by SShi11 on 5/3/2017.
 */
public interface IireportService extends BaseServiceI {
    /**
     * query for test
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, ?>> queryUsers(Map<String,Object> params,Map<String, Object> model) throws Exception;
}
