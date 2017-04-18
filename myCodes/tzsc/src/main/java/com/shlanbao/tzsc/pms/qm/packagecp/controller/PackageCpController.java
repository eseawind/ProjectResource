package com.shlanbao.tzsc.pms.qm.packagecp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.packagecp.beans.PackageCpBean;
import com.shlanbao.tzsc.pms.qm.packagecp.service.PackageCpService;

/**
 * 卷包车间成品物理检验记录控制器
 * <li>@author luther.zhang
 * <li>@create 2015-02-26
 */
@Controller
@RequestMapping("/pms/packageCp")
public class PackageCpController extends BaseController{
	@Autowired
	private PackageCpService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(PackageCpBean bean,PageParams pageParams){
		try {
			DataGrid grid = service.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
}
