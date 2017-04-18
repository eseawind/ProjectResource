package com.shlanbao.tzsc.pms.qm.avgrange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.avgrange.beans.AvgRangeBean;
import com.shlanbao.tzsc.pms.qm.avgrange.beans.AvgRangeQueryBean;
import com.shlanbao.tzsc.pms.qm.avgrange.service.AvgRangeService;

/**
 * 平均值-极差控制图分析控制器
 * <li>@author luther.zhang
 * <li>@create 2015-03-02
 */
@Controller
@RequestMapping("/pms/avgRange")
public class AvgRangeController extends BaseController{
	@Autowired
	private AvgRangeService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(AvgRangeQueryBean bean,PageParams pageParams){
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
	@RequestMapping("/getAvgRangeData")
	public AvgRangeBean getAvgRangeData(AvgRangeQueryBean bean){
		try {
			return service.getAvgRangeBean(bean);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
}
