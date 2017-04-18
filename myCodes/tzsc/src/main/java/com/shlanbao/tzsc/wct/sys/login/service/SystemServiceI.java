package com.shlanbao.tzsc.wct.sys.login.service;

import javax.servlet.http.HttpServletRequest;

import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * WCT系统模块
 * @author Leejean
 * @create 2014年12月11日上午11:33:07
 */
public interface SystemServiceI {
	/**
	 * 初始化登录信息(机台信息，排班信息)
	 * @author Leejean
	 * @create 2014年12月11日上午11:33:01
	 * @param request
	 * @return
	 */
	public LoginBean initLoginInfo(HttpServletRequest request);
	/**
	 * 登录WCT
	 * @author Leejean
	 * @create 2014年12月12日上午8:28:17
	 * @param loginBean
	 * @return
	 */
	public LoginBean login(LoginBean loginBean,HttpServletRequest request);
	/**
	 * 自检人员登录
	 * @create 20150312
	 * @param loginBean
	 * @return
	 */
	public LoginBean loginWctZj(LoginBean loginBean,HttpServletRequest request);
	
	/**
	 * 设备人员登录
	 * @author luo
	 * @create 2015年3月17日 10:44:37
	 * @param request
	 * @return
	 */
	public LoginBean loginWctEqm(LoginBean loginBean,HttpServletRequest request);
}
