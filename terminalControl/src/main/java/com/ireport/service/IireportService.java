package com.ireport.service;

import java.io.OutputStream;
import java.util.List;
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
	
	/**
	 * 查询数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, ?>> queryData(Map<String,Object> params) throws Exception;
	/**
	 * @param data  数据集  List<Map<String, ?>>
	 * @param jasperPath  jasperPath 绝对路径
	 * @param isExport    是否为导出操作   true / false
	 * @param exportFileSuffix  导出文件的后缀  支持 html，pdf，doc，xls
	 * @param response          响应
	 * @param exportFIleName  导出的文件名称
	 * @throws Exception
	 */
	void IreportTools(List<Map<String, ?>> data,String jasperPath,boolean isExport,Map<String,Object> fileProperties,HttpServletResponse response) throws Exception;
}
