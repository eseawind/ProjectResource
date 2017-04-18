package com.shlanbao.tzsc.pms.qm.better.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.better.beans.BetterManageBean;
import com.shlanbao.tzsc.pms.qm.better.beans.BetterManageQueryBean;
import com.shlanbao.tzsc.pms.qm.better.service.BetterManageService;

/**
 * 质量改善管理控制器
 * <li>@author luther.zhang
 * <li>@create 2015-03-10
 */
@Controller
@RequestMapping("/pms/betterManage")
public class BetterManageController extends BaseController{
	@Autowired
	private BetterManageService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(BetterManageQueryBean bean,PageParams pageParams){
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
	@ResponseBody
	@RequestMapping("/getBeanData")
	public BetterManageBean getBeanData(BetterManageQueryBean bean){
		try {
			return service.getBeanData(bean);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
}
