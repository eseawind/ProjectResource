package com.rockwell.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rockwell.Util.ReportExportUtil;
import com.rockwell.demo.service.DemoService;
/**
 * 测试报表导出Controller
 * @author SShi11
 *
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
	@Autowired
	private DemoService demoService;
	
	/**
	 * 无数据源查询
	 * @param session
	 * @param response
	 * @return
	 * @author SShi11
	 */
	@RequestMapping("/queryData")
	public String queryData(HttpSession session,HttpServletResponse response){
		try {
			Map<String, Object> params = new HashMap<>();
			List<Map<String, ?>> data = demoService.queryUsers(params);
			ServletContext content=session.getServletContext();
			String jasperPath = content.getRealPath("/") + "ireportDemo/test.jasper";
			ReportExportUtil.IreportInjectionData(data, jasperPath, false,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return  "ireportDemo/test";
	}
	
	/**
	 * 无数据源导出
	 * @param session
	 * @param response
	 * @author SShi11
	 */
	@RequestMapping("/exportDemo")
	public void exportDemo(HttpSession session,HttpServletResponse response,String exportFileSuffix){
		try {
			Map<String, Object> params = new HashMap<>();
			List<Map<String, ?>> data = demoService.queryUsers(params);
			ServletContext content=session.getServletContext();
			String jasperPath = content.getRealPath("/") + "/ireportDemo/test.jasper";
			Map<String, Object> fileProperties=new HashMap<>();
			fileProperties.put(ReportExportUtil.EXPORTFILENAME, "测试导出");
			fileProperties.put(ReportExportUtil.EXPORTFILESUFFIX, exportFileSuffix);
			ReportExportUtil.IreportInjectionData(data, jasperPath, true,fileProperties, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 无数据源查询
	 * @param session
	 * @param response
	 * @return
	 * @author SShi11
	 */
	@RequestMapping("/queryDataJSql")
	public String queryDataJSql(HttpSession session,HttpServletResponse response){
		try {
			ServletContext content=session.getServletContext();
			String jasperPath = content.getRealPath("/") + "ireportDemo/test2.jasper";
			//报表参数设置
			Map<String, Object> fileProperties=new HashMap<>();
			//sql 查询条件
			Map<String,Object> sqlParams=new HashMap<>();
			sqlParams.put("conditions", " user_name like '%1%' ");
			//报表页面显示的数据
			Map<String,Object> showInfoParams=new HashMap<>();
			showInfoParams.put("showText", "用户名包括  1 ");
			
			fileProperties.put("sqlParams", sqlParams);
			fileProperties.put("showInfoParams", showInfoParams);
			
			ReportExportUtil.IreportInjectionData(demoService.getConn(), jasperPath, false,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return  "ireportDemo/test";
	}
	
	/**
	 * 无数据源导出
	 * @param session
	 * @param response
	 * @author SShi11
	 */
	@RequestMapping("/exportDemoJSql")
	public void exportDemoJSql(HttpSession session,HttpServletResponse response,String exportFileSuffix){
		try {
			ServletContext content=session.getServletContext();
			String jasperPath = content.getRealPath("/") + "/ireportDemo/test2.jasper";
			//报表导出文件的参数设置
			Map<String, Object> fileProperties=new HashMap<>();
			fileProperties.put(ReportExportUtil.EXPORTFILENAME, "测试使用conn导出");
			fileProperties.put(ReportExportUtil.EXPORTFILESUFFIX, exportFileSuffix);
			//sql 查询条件
			Map<String,Object> sqlParams=new HashMap<>();
			sqlParams.put("conditions", " user_name like '%1%' ");
			//报表页面显示的数据
			Map<String,Object> showInfoParams=new HashMap<>();
			showInfoParams.put("showText", "用户名包括  1 ");
			
			fileProperties.put("sqlParams", sqlParams);
			fileProperties.put("showInfoParams", showInfoParams);
			//导出
			ReportExportUtil.IreportInjectionData(demoService.getConn(), jasperPath, true,fileProperties, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
