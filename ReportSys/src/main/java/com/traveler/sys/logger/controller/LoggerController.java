package com.traveler.sys.logger.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.traveler.core.BaseController;
import com.traveler.sys.logger.service.LoggerService;
import com.traveler.util.JsonUtils;

@Controller
@RequestMapping("/logger")
public class LoggerController extends BaseController<LoggerController> {
	@Autowired
	private LoggerService loggerService;
	
	@ResponseBody
	@RequestMapping("/queryLoggerInfo")
	public String queryLoggerInfo(HttpServletRequest request){
		Map<String,Object> rs=new HashMap<>();
		try {
			Map<String, Object> params=paramsMap(request);
			rs.put(MSG, loggerService.queryLogger(params));
			rs.put(SUCCESS, true);
		} catch (Exception e) {
			rs.put(SUCCESS, false);
			rs.put(ERR_MSG, e.getMessage());
		}
		return JsonUtils.toJsonIncludeNull(rs);
	}
}
