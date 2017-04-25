package com.baseCore.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.entity.LoggerBean;
import com.entity.Module;
import com.menu.MenuUtils;

public class ListenterServlet implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		arg0.getServletContext().removeAttribute("baseURL");
		arg0.getServletContext().removeAttribute("resRoot");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute("baseURL", "/terminalControl");
		arg0.getServletContext().setAttribute("resRoot", "/terminalControl/style");
		try {
			arg0.getServletContext().setAttribute("menuList",MenuUtils.initMenu(Module.M));
			System.out.println("--------------初始化菜单完成！------------------");
		} catch (Exception e) {
			e.printStackTrace();
			LoggerBean logger=new LoggerBean();
			logger.setOperatation("系统菜单初始化异常！");
			logger.setContent(e.getCause().toString());
			logger.saveLog(logger);
		}
	}

}
