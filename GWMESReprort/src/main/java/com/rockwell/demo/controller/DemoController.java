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
public class DemoController {
	@Autowired
	private DemoService demoService;
	
	@RequestMapping("/demo/queryData")
	public String queryData(HttpSession session,HttpServletResponse response){
		try {
			Map<String, Object> params = new HashMap<>();
			List<Map<String, ?>> data = demoService.queryUsers(params);
			ServletContext content=session.getServletContext();
			String jasperPath = content.getRealPath("/") + "/templates/ireportDemo/test.jasper";
			ReportExportUtil.IreportTools(data, jasperPath, false,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return  "/templates/ireportDemo/test.html";
	}
}
