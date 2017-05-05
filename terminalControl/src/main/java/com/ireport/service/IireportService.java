package com.ireport.service;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.baseCore.service.BaseServiceI;

import net.sf.jasperreports.engine.JasperPrint;
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
	
	String  queryUsers2(Map<String,Object> params,Map<String, Object> model,ServletContext content) throws Exception;
	
	/**
	 * 导出
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> ExportUsers(Map<String, Object> params,Map<String, Object> model) throws Exception;
	void ExportUsers2(Map<String, Object> params,Map<String, Object> model,ServletContext content,HttpServletResponse response) throws Exception;
}
