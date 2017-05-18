package com.traveler.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.traveler.sys.menu.service.MenuService;
import com.traveler.util.ApplicationContextUtil;
import com.traveler.util.ExceptionUtil;
@WebServlet("/InitParams")
public class InitParams extends HttpServlet {
	/**
	 * <p>功能描述：</p>
	 *作者：SShi11
	 *日期： Apr 19, 2017 11:26:30 PM
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger=LoggerFactory.getLogger(InitParams.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		MenuService menuService=ApplicationContextUtil.getBean(MenuService.class);
		try {
			List<Map<String,Object>> menuMapList=menuService.initSysMenu();
			SysCache.SYS_MENU.clear();
			SysCache.SYS_MENU.addAll(menuMapList);
		} catch (Exception e) {
			logger.error(ExceptionUtil.formatStackTrace(e),"系统初始化","SYS");
		}
		logger.info("系统菜单初始化完成","系统初始化","SYS");
		
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
