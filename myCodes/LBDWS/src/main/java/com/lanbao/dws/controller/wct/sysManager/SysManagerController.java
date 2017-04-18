package com.lanbao.dws.controller.wct.sysManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanbao.dws.model.wct.menu.WctMenu;
import com.lanbao.dws.service.wct.menu.IWctMenuService;


/**
 * 系统管理主控制层
 * @author shisihai
 *
 */
@Controller
@RequestMapping("wct/sysManager")
public class SysManagerController {
	@Autowired
	private IWctMenuService wctMenuservice;
	/**
	 * 获取左侧菜单
	 * @param model
	 * @param menu
	 * @return
	 */
	@RequestMapping("/getResultPagePath")
	public String getResultPagePath( Model model,WctMenu menu){
		wctMenuservice.queryWctLeftMainMenu(model, menu);
		//返回主页面
		return menu.getMenu_url();
	}
}
