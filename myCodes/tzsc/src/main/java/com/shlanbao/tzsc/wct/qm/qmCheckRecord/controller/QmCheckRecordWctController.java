package com.shlanbao.tzsc.wct.qm.qmCheckRecord.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfo;
import com.shlanbao.tzsc.base.mapping.QualityCheckInfoParams;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.wct.qm.prod.beans.QmProdFileBean;
import com.shlanbao.tzsc.wct.qm.prod.service.QmProdWctService;
import com.shlanbao.tzsc.wct.qm.qmCheckRecord.beans.qmCheckRecordBean;
import com.shlanbao.tzsc.wct.qm.qmCheckRecord.service.QmCheckRecordWctService;

/**
 * 产品规程管理控制器
 * <li>@author luther.zhang
 * <li>@create 2014-01-04
 */
@Controller
@RequestMapping("/wct/qm/qmRecord")
public class QmCheckRecordWctController extends BaseController{
	@Autowired
	private QmCheckRecordWctService service;

	/**
	 * 
	 *说明：查询质检信息概要
	 * @param bean
	 * @param pageIndex
	 * @return
	 * shisihai  
	 * 20152015年12月30日上午10:49:13
	 */
	@ResponseBody
	@RequestMapping("/getSummaryList")
	public DataGrid getList(qmCheckRecordBean bean,int pageIndex){
		try {
			DataGrid grid = service.getSummaryList(bean, pageIndex);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	/**
	 * 查询质检信息扣分详细
	 *说明：
	 * @param id
	 * @param pageIndex
	 * @return
	 * shisihai  
	 * 20152015年12月30日上午10:49:53
	 */
	@ResponseBody
	@RequestMapping("/getDeailedList")
	public DataGrid getList(String id){
		try {
			DataGrid grid = service.getDetailedList(id);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
}
