package com.shlanbao.tzsc.pms.qm.moldingcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.moldingcp.beans.MoldingCpBean;
import com.shlanbao.tzsc.pms.qm.moldingcp.service.MoldingCpService;

/**
 * 成型车间成品物理检验记录控制器
 * <li>@author luther.zhang
 * <li>@create 2015-02-27
 */
@Controller
@RequestMapping("/pms/moldingCp")
public class MoldingCpController extends BaseController{
	@Autowired
	private MoldingCpService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(MoldingCpBean bean,PageParams pageParams){
		try {
			DataGrid grid = service.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
}
