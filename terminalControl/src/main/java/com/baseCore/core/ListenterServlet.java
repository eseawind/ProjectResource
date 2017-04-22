package com.baseCore.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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

	}

}
