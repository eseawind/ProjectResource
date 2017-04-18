package com.shlanbao.tzsc.pms.qm.passrate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.passrate.beans.PassRateBean;
import com.shlanbao.tzsc.pms.qm.passrate.beans.PassRateQueryBean;
import com.shlanbao.tzsc.pms.qm.passrate.service.PassRateService;

/**
 * 合格率统计控制器
 * <li>@author luther.zhang
 * <li>@create 2015-03-02
 */
@Controller
@RequestMapping("/pms/passRate")
public class PassRateController extends BaseController{
	@Autowired
	private PassRateService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(PassRateQueryBean bean,PageParams pageParams){
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
	@RequestMapping("/getStatisticsData")
	@ResponseBody
	public PassRateBean getStatisticsData(PassRateQueryBean bean){
		try {
			return service.getPassRateBean(bean);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
}
