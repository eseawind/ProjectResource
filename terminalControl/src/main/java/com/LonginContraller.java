package com;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baseCore.controller.BaseController;

@Controller
@RequestMapping("/index")
public class LonginContraller extends BaseController {

	/**
	 * <p>功能描述：初始化系统参数</p>
	 *@param session
	 *@param request
	 *@return
	 *作者：SShi11
	 *日期：Apr 18, 2017 2:27:06 PM
	 */
	@RequestMapping("/initSys")
	public String initSys(HttpSession session,HttpServletRequest request){
		session.setAttribute("baseURL", "/terminalControl");
		return "/index";
	}
}
