package com.traveler.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.traveler.util.ExceptionUtil;


public class ListenterServlet implements ServletContextListener {
	protected   Logger loger = LoggerFactory.getLogger(ListenterServlet.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		arg0.getServletContext().removeAttribute("baseURL");
		arg0.getServletContext().removeAttribute("resRoot");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute("baseURL", "/ReportSys");
		arg0.getServletContext().setAttribute("resRoot", "/ReportSys/style");
		try {
		//	arg0.getServletContext().setAttribute("menuList",MenuUtils.initMenu(Module.M));
			loger.error("菜单初始化完成", "系统初始化", null);
		} catch (Exception e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "初始化菜单异常！", null);
			e.printStackTrace();
		}
	}

}
