package com.shlanbao.tzsc.wct.qm.self.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.qm.self.beans.QmSelfCheckStripBean;
import com.shlanbao.tzsc.wct.qm.self.service.QmSelfWctService;

/**
 * 外观质量检验记录控制器
 * <li>@author luther.zhang
 * <li>@create 2015-01-05
 */
@Controller
@RequestMapping("/wct/selfCheckStrip")
public class QmSelfWctController extends BaseController{
	@Autowired
	private QmSelfWctService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(QmSelfCheckStripBean bean,int pageIndex){
		try {
			DataGrid grid = service.queryList(bean,pageIndex);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
}
