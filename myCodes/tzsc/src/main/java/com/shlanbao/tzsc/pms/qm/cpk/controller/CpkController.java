package com.shlanbao.tzsc.pms.qm.cpk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.cpk.beans.CpkBean;
import com.shlanbao.tzsc.pms.qm.cpk.beans.CpkQueryListBean;
import com.shlanbao.tzsc.pms.qm.cpk.service.CpkService;

/**
 * CPK统计控制器
 * <li>@author luther.zhang
 * <li>@create 2015-03-03
 */
@Controller
@RequestMapping("/pms/cpk")
public class CpkController extends BaseController{
	@Autowired
	private CpkService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(CpkQueryListBean bean,PageParams pageParams){
		try {
			DataGrid grid = service.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/getCpkData")
	@ResponseBody
	public CpkBean getStatisticsData(CpkQueryListBean bean){
		try {
			return service.getCpkBean(bean);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
}
