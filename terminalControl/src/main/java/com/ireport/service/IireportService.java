package com.ireport.service;

import java.util.Map;

import com.baseCore.service.BaseServiceI;
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
	Map<String, Object>  queryUsers(Map<String,Object> params,Map<String, Object> model) throws Exception;
	
	/**
	 * 导出
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> ExportUsers(Map<String, Object> params,Map<String, Object> model) throws Exception;
}
