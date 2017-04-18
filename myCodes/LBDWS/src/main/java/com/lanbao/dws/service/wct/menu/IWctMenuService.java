package com.lanbao.dws.service.wct.menu;

import java.util.List;

import org.springframework.ui.Model;

import com.lanbao.dws.model.wct.menu.WctMenu;

/**
 * WCT菜单加载
 */
public interface IWctMenuService {
	/**
	 * 查询wct顶部菜单
	 * @param loginType 
	 * @return
	 */
	List<WctMenu> queryWctTopMainMenu(int loginType);
	
	/**
	 * 查询wct左侧主菜单
	 * @return
	 */
	void queryWctLeftMainMenu(Model model,WctMenu menu);

	
}
