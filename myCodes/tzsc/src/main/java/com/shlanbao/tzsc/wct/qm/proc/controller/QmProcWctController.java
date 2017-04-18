package com.shlanbao.tzsc.wct.qm.proc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.qm.proc.beans.QmProcWctFileBean;
import com.shlanbao.tzsc.wct.qm.proc.service.QmProcWscService;

/**
 * 工艺规程控制器
 * <li>@author luther.zhang
 * <li>@create 2015-02-13
 */
@Controller
@RequestMapping("/wct/procManage")
public class QmProcWctController extends BaseController{
	@Autowired
	private QmProcWscService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(QmProcWctFileBean bean,int pageIndex){
		try {
			DataGrid grid = service.queryList(bean,pageIndex);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
}
