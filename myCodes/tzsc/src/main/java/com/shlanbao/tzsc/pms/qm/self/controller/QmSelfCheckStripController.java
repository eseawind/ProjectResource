package com.shlanbao.tzsc.pms.qm.self.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.self.beans.QmSelfCheckStripBean;
import com.shlanbao.tzsc.pms.qm.self.service.QmSelfCheckStripService;

/**
 * 外观质量检验记录控制器
 * <li>@author luther.zhang
 * <li>@create 2015-01-05
 */
@Controller
@RequestMapping("/pms/selfCheckStrip")
public class QmSelfCheckStripController extends BaseController{
	@Autowired
	private QmSelfCheckStripService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(QmSelfCheckStripBean bean,PageParams pageParams){
		try {
			DataGrid grid = service.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
}
