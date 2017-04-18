package com.shlanbao.tzsc.wct.sch.calendar.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
import com.shlanbao.tzsc.wct.sch.calendar.service.WctCalendarServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 *  排班
 * @author Leejean
 * @create 2014年11月25日下午1:20:44
 */
@Controller
@RequestMapping("/wct/calendar")
public class WctCalendarController extends BaseController {
	@Autowired
	public WctCalendarServiceI wctCalendarService;
	/**
	 * 获取排班
	 * @author Leejean
	 * @create 2014-8-14下午07:03:44
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCurMonthCalendars")
	public Object getCurMonthCalendars(String date,String workshop){
		try {
			return wctCalendarService.getCurMonthCalendars(date, workshop);
		} catch (Exception e) {
			log.error("查询车间排班:"+date+" : "+workshop+"异常", e);
		}
		return null;
	} 
	/**
	 * 新增排班
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveCurMonthCalendars")
	public Json saveCurMonthCalendars(String workshop){
		Json json=new Json();
		try {
			wctCalendarService.saveCurMonthCalendars(workshop);
			json.setMsg("保存成功");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			log.error("保存排班表:"+workshop+"异常", e);
		}
		return json;
	} 
	
	/**
	 * 手动换班
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/manualShift")
	public Json manualShift(String type, String eqpId, String workshop,
			String proWorkId, HttpServletRequest request) {
		Json json=new Json();
		try {
			LoginBean login = (LoginBean) WebContextUtil.getSessionValue(
					request.getSession(),"loginInfo");//质检用户信息
			wctCalendarService.saveManualShift(type,eqpId,workshop,proWorkId,login,request);
			json.setMsg("保存成功");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			log.error("手动换班:"+eqpId+"异常", e);
		}
		return json;
	}
}
