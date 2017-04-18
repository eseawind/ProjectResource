package com.shlanbao.tzsc.pms.qm.packagezx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.packagezx.beans.PackageZxBean;
import com.shlanbao.tzsc.pms.qm.packagezx.service.PackageZxService;

/**
 * 卷包车间在线物理检验记录控制器
 * <li>@author luther.zhang
 * <li>@create 2015-02-27
 */
@Controller
@RequestMapping("/pms/packageZx")
public class PackageZxController extends BaseController{
	@Autowired
	private PackageZxService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(PackageZxBean bean,PageParams pageParams){
		try {
			DataGrid grid = service.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
}
