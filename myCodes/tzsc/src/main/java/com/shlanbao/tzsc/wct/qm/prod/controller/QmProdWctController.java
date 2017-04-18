package com.shlanbao.tzsc.wct.qm.prod.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.qm.prod.beans.QmProdFileBean;
import com.shlanbao.tzsc.wct.qm.prod.service.QmProdWctService;

/**
 * 产品规程管理控制器
 * <li>@author luther.zhang
 * <li>@create 2014-01-04
 */
@Controller
@RequestMapping("/wct/qm/prod")
public class QmProdWctController extends BaseController{
	@Autowired
	private QmProdWctService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(QmProdFileBean bean,int pageIndex){
		try {
			DataGrid grid = service.queryList(bean,pageIndex);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
}
