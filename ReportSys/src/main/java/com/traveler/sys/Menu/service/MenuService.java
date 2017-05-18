package com.traveler.sys.menu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveler.core.BaseService;
import com.traveler.core.OperationException;
import com.traveler.sys.menu.dao.MenuDao;
import com.traveler.util.ExceptionUtil;
import com.traveler.util.StringUtils;

@Service
public class MenuService extends BaseService<MenuService> {
	@Autowired
	private MenuDao menuDao;

	public List<Map<String, Object>> queryMenu(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> rs = null;
		try {
			rs = menuDao.queryMenu(params);
		} catch (Exception e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "MenuService.queryMenu 系统菜单查询异常！", "SYS");
			throw new SystemException(SYSEXCEP);
		}
		if (rs != null && rs.size() > 0)
			return rs;
		throw new OperationException(EMPTYDATA);

	}

	public List<Map<String, Object>> initSysMenu() throws Exception {
		List<Map<String, Object>> mainMenu = null;
		// 查询主菜单
		Map<String, Object> mainParams = new HashMap<>();
		mainParams.put("isFirst", 1);
		mainParams.put("del", 0);
		try {
			mainMenu = menuDao.queryMenu(mainParams);
			if (mainMenu == null || mainMenu.size() == 0)
				throw new OperationException("系统菜单不存在！初始化菜单失败！");
			//递归加载子菜单
			for (Map<String, Object> map : mainMenu) {
				findChildMenus(map);
			}

		} catch (Exception e) {
			loger.error(ExceptionUtil.formatStackTrace(e), "查询系统第一层菜单异常！", "SYS");
			throw new SystemException(SYSEXCEP);
		}

		return mainMenu;
	}

	/**
	 * 递归加载子菜单
	 * @param mainMenuMap
	 * @throws Exception
	 */
	private void findChildMenus(Map<String, Object> mainMenuMap) throws Exception {
		if (!StringUtils.toStr(mainMenuMap.get("HASCHILDS")).equals("1"))
			return;
		List<Map<String, Object>> childMenu = null;
		Map<String, Object> childSqlParams = new HashMap<>();
		childSqlParams.put("pid", mainMenuMap.get("ID"));
		childSqlParams.put("del", 0);
		childMenu = menuDao.queryMenu(childSqlParams);
		mainMenuMap.put("childs", childMenu);
		if (childMenu != null && childMenu.size() > 0) {
			for (Map<String, Object> map : childMenu) {
				findChildMenus(map);
			}
		}
	}

}
