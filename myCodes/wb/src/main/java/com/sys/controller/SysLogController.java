package com.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.GridData;
import com.sys.bean.SysLog;
import com.sys.service.ISyslogService;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {
	@Autowired
	private ISyslogService logService;
	
	@ResponseBody
	@RequestMapping("/queryLog")
	public GridData queryLog(HttpServletRequest request,HttpServletResponse response,SysLog log){
		try {
			GridData grid=new GridData(20L, logService.queryLog(log));
			return grid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
