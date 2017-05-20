package com.sys.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baseCore.controller.BaseController;
import com.commonUtil.JsonUtils;
import com.commonUtil.MenuUtils;
import com.commonUtil.Module;
import com.sys.bean.Menu;

@Controller
@RequestMapping("/sys")
public class SysController extends BaseController<SysController> {
	@RequestMapping("/goTorefreshMenuCache")
	public String goTorefreshMenuCache(){
		return "/sys/sysFunctions.jsp";
	}
	
	/**
	 * <p>功能描述：刷新系统菜单</p>
	 *@param request
	 *@param session
	 *@param rs
	 *@return
	 *作者：SShi11
	 *日期：Apr 24, 2017 10:00:04 PM
	 */
	@ResponseBody
	@RequestMapping("/refreshMenuCache")
	public String refreshMenuCache(HttpServletRequest request,HttpSession session,Map<String,Object> rs){
		try {
			List<Menu> menus=MenuUtils.initMenu(Module.M);
			session.getServletContext().setAttribute("menuList", menus);
			rs.put(SUCCESS, true);
			rs.put(MSG, "系统菜单刷新完成！");
		} catch (Exception e) {
			e.printStackTrace();
			rs.put(SUCCESS, false);
			rs.put(ERRMSG, "系统菜单刷新异常！");
		}
		return JsonUtils.toJsonIncludeNull(rs);
	}
}
