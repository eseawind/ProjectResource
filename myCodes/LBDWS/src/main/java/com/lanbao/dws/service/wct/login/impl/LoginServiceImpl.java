package com.lanbao.dws.service.wct.login.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.framework.dal.client.IPaginationDalClient;
import com.lanbao.dws.common.tools.DateUtil;
import com.lanbao.dws.model.wct.login.LoginBean;
import com.lanbao.dws.model.wct.pddisplay.CalendarBean;
import com.lanbao.dws.service.wct.login.ILoginService;

/**
 * WCT系统登录数据交互层
 * 
 * 
 * */
@Service
public class LoginServiceImpl implements ILoginService {
	
	@Autowired
    IPaginationDalClient dalClient;
	
	/**
	 * 【功能说明】：登录时，获取当前机台工单信息
	 * @author Administrator
	 * @date 2016年7月20日14:28:13
	 * */
	@Override
	public LoginBean getWorkOrder(LoginBean bean) {
		bean.setNowTime(DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 //通过当前时间，获得当前班次
		 CalendarBean cdb=dalClient.queryForObject("login.querySysUserByLoginName", bean, CalendarBean.class);
		 bean.setShiftId(cdb.getShift());
		 bean.setShiftName(cdb.getShiftName());
		 bean.setTeamId(cdb.getTeam());
		 bean.setTeamName(cdb.getTeamName());
		 return bean;
	}

}
