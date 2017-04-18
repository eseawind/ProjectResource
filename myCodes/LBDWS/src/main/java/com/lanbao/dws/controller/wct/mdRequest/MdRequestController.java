package com.lanbao.dws.controller.wct.mdRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 物料请求主控制层
 * @author shisihai
 *
 */
@Controller
@RequestMapping("wct/mdRequest")
public class MdRequestController {
	/**
	 * 获取左侧菜单
	 * @param model
	 * @param menu
	 * @return
	 */
	@RequestMapping("/getResultPagePath")
	public String getResultPagePath( Model model,String url){
		return url;
	}
}
