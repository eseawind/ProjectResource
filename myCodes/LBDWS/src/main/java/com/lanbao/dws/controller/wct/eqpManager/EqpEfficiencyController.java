package com.lanbao.dws.controller.wct.eqpManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.framework.dal.pagination.Pagination;
import com.lanbao.dws.common.tools.ComboboxType;
import com.lanbao.dws.model.page.WctPage;
import com.lanbao.dws.model.wct.eqpManager.EqpOperatingEfficBean;
import com.lanbao.dws.service.init.InitComboboxData;
import com.lanbao.dws.service.wct.eqpManager.IEqpEfficService;

/**
 * @author shisihai
 * 设备运行率
 */
@Controller
@RequestMapping("wct/eqpEffic")
public class EqpEfficiencyController {
	@Autowired
	InitComboboxData initComboboxData;
	@Autowired
	IEqpEfficService eqpEfficService;
	
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月13日 上午10:21:52 
	* 功能说明 ：设备有效作业率
	 */
	@RequestMapping("/getEqpOperatingEffic")
	public String getHisQtyPagePath(Model model, String url, WctPage wctPage, EqpOperatingEfficBean param) {
		//加载下拉框数据
		initComboboxData.chooseSetComboboxModel(model,ComboboxType.ALLEQPS);
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.EQPCATEGORY, ComboboxType.TEAM,ComboboxType.SHIFT);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询并填充数据集到model
		eqpEfficService.queryEqpOperatingEffic(pagination, param, model);
		 //设置总条数
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
	
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月14日 下午3:07:18 
	* 功能说明 ：设备有效作业率
	 */
	@RequestMapping("/getEffectiveOperatingRateOfEqm")
	public String getEffectiveOperatingRateOfEqm(Model model, String url, WctPage wctPage, EqpOperatingEfficBean param) {
		//加载下拉框数据
		initComboboxData.chooseSetComboboxModel(model,ComboboxType.ALLEQPS);
		initComboboxData.chooseFixCodeComboboxModel(model, ComboboxType.EQPCATEGORY, ComboboxType.TEAM,ComboboxType.SHIFT);
		// 初始化分页
		Pagination pagination = new Pagination(wctPage.getSize(), wctPage.getIndex());
		// 查询并填充数据集到model
		eqpEfficService.queryEffectiveOperatingEffic(pagination, param, model);
		 //设置总条数
		wctPage.setMaxCount(pagination.getTotalRows());
		model.addAttribute("wctPage", wctPage);
		// 回显条件
		model.addAttribute("chooseParams", param);
		// 返回页面
		return url;
	}
}
