package com.shlanbao.tzsc.pms.sch.calendar.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shlanbao.tzsc.base.dao.SchCalendarDaoI;
import com.shlanbao.tzsc.base.mapping.MdShift;
import com.shlanbao.tzsc.base.mapping.MdTeam;
import com.shlanbao.tzsc.base.mapping.SchCalendar;
import com.shlanbao.tzsc.base.service.BaseService;
import com.shlanbao.tzsc.pms.sch.calendar.beans.CalendarBean;
import com.shlanbao.tzsc.pms.sch.calendar.service.CalendarServiceI;
import com.shlanbao.tzsc.utils.tools.BeanConvertor;
@Service
public class CalendarServiceImpl extends BaseService implements CalendarServiceI{
	@Autowired
	private SchCalendarDaoI schCalendarDao;
	@Override
	public List<CalendarBean> getCurMonthCalendars(String date, String workshop)
			throws Exception {
				 String hql = "from SchCalendar o left join fetch o.mdTeam t left join fetch o.mdShift s where 1=1 and CONVERT(varchar(7), o.date, 126)='"+date.substring(0, 7)+"' and o.mdWorkshop.code='"+workshop+"' order by o.mdShift.code asc";
				List<SchCalendar> list=schCalendarDao.query(hql);
				List<CalendarBean> calendarBeans=new ArrayList<CalendarBean>();
				CalendarBean calendarBean = null;
				MdShift shift=null;
				MdTeam team=null;
				for (SchCalendar schCalendar : list) {
					shift=schCalendar.getMdShift();
					team=schCalendar.getMdTeam();
					calendarBean = BeanConvertor.copyProperties(schCalendar, CalendarBean.class);
					calendarBean.setShift(shift==null?"":shift.getName());
					calendarBean.setTeam(team==null?"":team.getName());
					calendarBeans.add(calendarBean);
					calendarBean = null;
				}
				return calendarBeans;
	}

}
