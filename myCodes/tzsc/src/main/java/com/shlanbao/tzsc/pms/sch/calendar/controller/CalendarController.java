package com.shlanbao.tzsc.pms.sch.calendar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.pms.sch.calendar.service.CalendarServiceI;
/**
 *  排班
 * @author Leejean
 * @create 2014年11月25日下午1:20:44
 */
@Controller
@RequestMapping("/pms/calendar")
public class CalendarController extends BaseController {
	@Autowired
	public CalendarServiceI calendarService;
	/**
	 * 查询排班
	 */
	@ResponseBody
	@RequestMapping("/getCurMonthCalendars")
	public Object getCurMonthCalendars(String date,String workshop){
		try {
			return calendarService.getCurMonthCalendars(date, workshop);
		} catch (Exception e) {
			log.error("查询车间排班:"+date+" : "+workshop+"异常", e);
		}
		return null;
	} 
}
