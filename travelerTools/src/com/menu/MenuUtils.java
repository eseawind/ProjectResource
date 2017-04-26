package com.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.commonUtil.StringUtils;
import com.dbUtil.ExecuteSql;
import com.entity.Menu;
import com.entity.Module;

public class MenuUtils {
	/**
	 * <p>功能描述：初始化系统菜单</p>
	 *@return
	 *作者：SShi11
	 *日期：Apr 24, 2017 10:07:09 AM
	 */
	public  static List<Menu> initMenu(Module module) throws Exception{
		List<Menu> menus=new ArrayList<Menu>();
		String sql="SELECT ID,CONTEXT,URL_CONTEXT,MODULE,DEL,PID,GREAD,HASCHILDS FROM SYS_MENU WHERE PID IS NULL AND MODULE =? and DEL=0 ORDER BY GREAD";
		List<Map<String,Object>> result=ExecuteSql.queryBySql(sql, Module.getVal(module));
		Menu menu=null;
		for (Map<String, Object> map : result) {
			menu=new Menu(
					StringUtils.toInt(map.get("ID")), 
					StringUtils.toStr(map.get("CONTEXT")), 
					StringUtils.toStr(map.get("URL_CONTEXT")),
					StringUtils.toStr(map.get("MODULE")),
					StringUtils.toInt(map.get("DEL")),
					StringUtils.toInt(map.get("PID")), 
					StringUtils.toInt(map.get("GREAD")),
					StringUtils.toInt(map.get("HASCHILDS"))
					);
			menus.add(menu);
		}
		//子菜单
		sql="SELECT ID,CONTEXT,URL_CONTEXT,MODULE,DEL,PID,GREAD,HASCHILDS FROM SYS_MENU WHERE PID = ? and DEL=0  ORDER BY GREAD";
		findChildMenu(menus, sql, result);
		return menus;
	}
	
	/**
	 * <p>功能描述：递归加载子菜单</p>
	 *@param menus
	 *@param sql
	 *@param result
	 *@throws Exception
	 *作者：SShi11
	 *日期：Apr 24, 2017 1:52:49 PM
	 */
	private static void findChildMenu(List<Menu> menus, String sql,List<Map<String, Object>> result) throws Exception {
		for (Menu childMenu : menus) {
			if(childMenu.getHaschilds()==0) continue;
			result=ExecuteSql.queryBySql(sql, childMenu.getId());
			Menu menu=null;
			for (Map<String, Object> map : result) {
				menu=new Menu(
						StringUtils.toInt(map.get("ID")), 
						StringUtils.toStr(map.get("CONTEXT")), 
						StringUtils.toStr(map.get("URL_CONTEXT")),
						StringUtils.toStr(map.get("MODULE")),
						StringUtils.toInt(map.get("DEL")),
						StringUtils.toInt(map.get("PID")), 
						StringUtils.toInt(map.get("GREAD")),
						StringUtils.toInt(map.get("HASCHILDS"))
						);
				childMenu.getChildMenus().add(menu);
			}
		  findChildMenu(childMenu.getChildMenus(), sql, result);
		}
	}
	
	public static void main(String[] args) throws Exception {
		initMenu(Module.M);
	}
}
