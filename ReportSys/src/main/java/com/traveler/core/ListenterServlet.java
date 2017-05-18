package com.traveler.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ListenterServlet implements ServletContextListener {
	protected   Logger loger = LoggerFactory.getLogger(ListenterServlet.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
			loger.info("系统监听初始化", "系统初始化", "SYS");
	}

}
