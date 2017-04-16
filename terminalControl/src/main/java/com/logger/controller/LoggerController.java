package com.logger.controller;

import java.util.HashMap;
import java.util.Map;

import javax.management.OperationsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baseCore.controller.BaseController;
import com.commonUtil.JsonUtils;
import com.logger.service.LoggerServiceI;

@Controller
@RequestMapping("/logger")
public class LoggerController extends BaseController {
	@Autowired
	private LoggerServiceI loggerService;
	
	@ResponseBody
	@RequestMapping("/returnJSON")
	public String returnJSON(String param,Map<String,Object> rs,HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> params=new HashMap<>();
			//处理逻辑
			rs.put(MSG, loggerService.queryMapList(params));
			rs.put(SUCCESS, true);
		} catch (OperationsException e) {
			e.printStackTrace();
			rs.put(SUCCESS, false);
			rs.put(MSG, e.getMessage());
		}catch (Exception e) {
			//saveLog(request, LoggerGrade.EXECP, QUERY, e.getCause(), "备注");
		}
		return JsonUtils.toJsonIncludeNull(rs);
	}
	
}
