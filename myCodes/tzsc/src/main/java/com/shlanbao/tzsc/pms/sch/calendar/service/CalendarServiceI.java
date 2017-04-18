package com.shlanbao.tzsc.pms.sch.calendar.service;

import java.util.List;

import com.shlanbao.tzsc.pms.sch.calendar.beans.CalendarBean;


/**
 *  排班
 * @author Leejean
 * @create 2014年11月25日下午1:19:28
 */
public interface CalendarServiceI {
	/**
	 * 查询当月排班
	 * @author Leejean
	 * @create 2014年11月26日下午1:10:08
	 * @param date 月
	 * @param workshop 车间
	 * @return
	 * @throws Exception
	 */
	public List<CalendarBean> getCurMonthCalendars(String date,String workshop) throws Exception;
}
