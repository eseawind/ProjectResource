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
	 */
	@RequestMapping("/queryDataNeedConn")
	public String queryDataNeedConn(HttpSession session,HttpServletResponse response){
		try {
			Map<String, Object> params = new HashMap<>();
			List<Map<String, ?>> data = demoService.queryUsers(params);
			ServletContext content=session.getServletContext();
			String jasperPath = content.getRealPath("/") + "ireportDemo/test2.jasper";
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
	 */
	@RequestMapping("/exportDemoJSql")
	public void exportDemoJSql(HttpSession session,HttpServletResponse response,String exportFileSuffix){
		try {
			ServletContext content=session.getServletContext();
			String jasperPath = content.getRealPath("/") + "/ireportDemo/test2.jasper";
			Map<String, Object> fileProperties=new HashMap<>();
			fileProperties.put(ReportExportUtil.EXPORTFILENAME, "测试导出");
			fileProperties.put(ReportExportUtil.EXPORTFILESUFFIX, exportFileSuffix);
			ReportExportUtil.IreportInjectionData(demoService.getConn(), jasperPath, true,fileProperties, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
