package com.shlanbao.tzsc.pms.qm.shape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.qm.shape.beans.ShapeQueryListBean;
import com.shlanbao.tzsc.pms.qm.shape.beans.ShapeStatisticsBean;
import com.shlanbao.tzsc.pms.qm.shape.service.ShapeService;

/**
 * 外观质量统计控制器
 * <li>@author luther.zhang
 * <li>@create 2015-02-27
 */
@Controller
@RequestMapping("/pms/shape")
public class ShapeController extends BaseController{
	@Autowired
	private ShapeService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(ShapeQueryListBean bean,PageParams pageParams){
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
	public ShapeStatisticsBean getStatisticsData(ShapeQueryListBean bean){
		try {
			return service.getStatisticsBean(bean);
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
}
