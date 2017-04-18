package com.lanbao.dws.service.wct.menu.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.lanbao.dws.common.tools.StringUtil;
import com.lanbao.dws.model.wct.menu.WctMenu;
import com.lanbao.dws.service.wct.menu.IWctMenuService;
/**
 * WCT菜单加载
 */
@Service
public class WctMenuServiceImpl implements IWctMenuService{
	@Autowired
    IPaginationDalClient dalClient;

	/**
	 * 查询wct顶部菜单
	 */
	@Override
	public List<WctMenu> queryWctTopMainMenu(int loginType) {
		List<WctMenu> menus=dalClient.queryForList("wctMenu.queryTopMainMenu", null, WctMenu.class);
		StringBuffer url=new StringBuffer();
		for (WctMenu wctMenu : menus) {
			//需要对机台监控的默认主页面修改  //0卷包机组    1 封箱机组     2成型机组    3发射机组
			String module=wctMenu.getModul();
			if(module.equals("JTJK")){
				String mainUrl="/wct/menu/getResultPagePath.htm?menu_url=web_html/wct/home/main_contents";
				switch(loginType){
					case 1:mainUrl="/wct/menu/getResultPagePath.htm?menu_url=web_html/wct/home/fzj";break;
					case 2:mainUrl="/wct/menu/getResultPagePath.htm?menu_url=web_html/wct/home/cxj";break;
					case 3:mainUrl="/wct/menu/getResultPagePath.htm?menu_url=web_html/wct/home/fsj";break;
				}
				wctMenu.setMenu_url(mainUrl);
			}
			//end
			
			url.setLength(0);
			url.append(wctMenu.getMenu_url());
			if(StringUtil.notEmpty(url)){
				wctMenu.setMenu_url(url.append("&modul=").append(wctMenu.getModul()).toString());
			}
		}
		return menus;
	}
	/**
	 * 查询wct左侧主菜单(两级)
	 */
	@Override
	public void queryWctLeftMainMenu(Model model,WctMenu menu) {
		if(menu.getModul()!=null){
			List<WctMenu> mainMenus=dalClient.queryForList("wctMenu.queryLeftMainMenu", menu, WctMenu.class);
			List<WctMenu> childMenus=dalClient.queryForList("wctMenu.queryLeftChildMenu", menu, WctMenu.class);
			for (WctMenu mainMenu : mainMenus) {
				for (WctMenu childMenu : childMenus) {
					if(mainMenu.getId().equals(childMenu.getUpId()) && mainMenu.getModul().equals(childMenu.getModul())){
						mainMenu.getChildMenus().add(childMenu);
					}
				}
				//对子菜单排序
				Collections.sort(mainMenu.getChildMenus());
			}	
			//主菜单排序
			Collections.sort(mainMenus);	
			//对主菜单attr判断，如果为true，表示为默认页面，在model中放置
			model.addAttribute("leftMenu", mainMenus);
		}
	}
	
	
	
	
	
}
