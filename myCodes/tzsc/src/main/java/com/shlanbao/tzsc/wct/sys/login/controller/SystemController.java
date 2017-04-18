package com.shlanbao.tzsc.wct.sys.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
import com.shlanbao.tzsc.wct.sys.login.service.SystemServiceI;
/**
 * 系统控制器
 * @author Leejean
 * @create 2014年12月11日上午11:03:07
 * 机台监控是根据设备的型号type自定义跳转到不同页面的<br>
 * 以下为type code与页面跳转规则定义<br>
	1	ZB25<br>		
	2	ZB45<br>		
	3	ZJ17<br>		
	4	ZJ19<br>		
	5	PASSIM<br>		
	6	YP18<br>		
	8	ZL26C<br>		
	7	YP19<br>
	以上为已知型号，如现场有新型号再叠加<br>
	监控页面jsp命名规则即为设备型号名称.jsp
 */
@Controller
@RequestMapping("/wct/system")
public class SystemController extends BaseController {
	@Autowired
	private SystemServiceI systemService;
	/**
	 * 初始化登录信息(机台信息，排班信息)
	 * @author Leejean
	 * @create 2014年12月11日上午11:32:25
	 * @param request
	 * @return
	 */
	@RequestMapping("/initLoginInfo")
	@ResponseBody
	public LoginBean initLoginInfo(HttpServletRequest request){
		return systemService.initLoginInfo(request);
	}
	/**
	 * 登录
	 * @author Leejean
	 * @create 2014年12月11日上午11:32:25
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public LoginBean login(LoginBean loginBean,HttpServletRequest request){
		return systemService.login(loginBean,request);
	}
	/**
	 * 登录人员
	 * @author luther.zhang
	 * @create 2015年03月12日
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginWctZj")
	@ResponseBody
	public LoginBean loginWctZj(LoginBean loginBean,HttpServletRequest request){
		return systemService.loginWctZj(loginBean,request);
	}
	/**
	 * 销毁登录人员
	 * @author luther.zhang
	 * @create 2015年03月12日
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginWctDis")
	@ResponseBody
	public LoginBean loginWctDis(LoginBean loginBean,HttpSession session){
		session.removeAttribute("loginWctZjInfo");
		LoginBean bean = new LoginBean();
		bean.setIsSuccess("true");
		return bean;
	}
	
	/**
	 * 跳转到ISP页面
	 * @author Leejean
	 * @create 2014年12月11日上午11:32:25
	 * @param request
	 * @return
	 */
	@RequestMapping("/gotoIspPage")
	@ResponseBody
	public LoginBean gotoIspPage(LoginBean loginBean,HttpServletRequest request){
		return systemService.login(loginBean,request);
	}
	
	/**
	 * 设备人员登录
	 * @author luo
	 * @create 2015年3月17日 10:45:26
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginWctEqm")
	@ResponseBody
	public LoginBean loginWctEqm(LoginBean loginBean,HttpServletRequest request){
		return systemService.loginWctEqm(loginBean,request);
	}
	
	/**
	 * 设备人员注销
	 * @author luo
	 * @create 2015年3月17日 10:45:26
	 * @param session
	 * @return
	 */
	@RequestMapping("/logOutWctEqm")
	@ResponseBody
	public LoginBean logOutWctEqm(HttpSession session){
		session.removeAttribute("loginWctEqmInfo");
		LoginBean bean = new LoginBean();
		bean.setIsSuccess("false");
		return bean;
	}
	
	/**
	 * WCT一键注销，同时注销机台登录与设备人员、自检人员登录
	 * @author zhanglu
	 * @create 2015年9月24日 
	 * @param session
	 * @return
	 */
	@RequestMapping("/logOutWct")
	@ResponseBody
	public LoginBean logOutWct(HttpSession session){
		session.removeAttribute("loginWctEqmInfo");//设备人员注销
		session.removeAttribute("loginInfo");//机台注销
		session.removeAttribute("loginWctZjInfo");//自检注销
		LoginBean bean = new LoginBean();
		bean.setIsSuccess("true");
		return bean;
	}
	
	
	/**
	 * [功能说明]：刷卡登录
	 * @date 2016年8月30日16:57:15
	 * @author wanchanghuang
	 * 
	 * */
	@RequestMapping("/loginCardNum")
	@ResponseBody
	public String loginCardNum(String cardNum,HttpSession session){
		System.out.println(cardNum);
		return "1";
	}
}
