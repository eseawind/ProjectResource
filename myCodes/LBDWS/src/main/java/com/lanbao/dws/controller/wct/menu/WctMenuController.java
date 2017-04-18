package com.lanbao.dws.controller.wct.menu;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.model.wct.menu.WctMenu;
import com.lanbao.dws.service.wct.menu.IWctMenuService;


@Controller
@RequestMapping("/wct/menu")
public class WctMenuController {
	@Autowired
	private IWctMenuService wctMenuservice;
	/**
	 * 获取左侧菜单
	 * @param model
	 * @param menu
	 * @return
	 */
	@RequestMapping("/getResultPagePath")
	public String getResultPagePath( Model model,WctMenu menu,String defualUrl,HttpSession session){
		LoginBean loginBean=(LoginBean) session.getAttribute("loginInfo");
		//卷包机菜单  type0 、  封箱机   1 、   成型机2、		通用 -1
		int loginType=loginBean.getLoginType();
		menu.setType(loginType);
		wctMenuservice.queryWctLeftMainMenu(model, menu);
		model.addAttribute("defualUrl", defualUrl);
		//返回主页面
		return menu.getMenu_url();
	}
}
