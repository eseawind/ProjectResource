package com.shlanbao.tzsc.pms.equ.runtime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveGraphBean;
import com.shlanbao.tzsc.pms.equ.runtime.beans.EqpRunTimeBean;
import com.shlanbao.tzsc.pms.equ.runtime.service.EqpRunTimeServiceI;
/**
 * 
 * @ClassName: EqpRunTimeController 
 * @Description: 设备运行时间统计 
 * @author luo
 * @date 2015年3月5日 下午3:22:44 
 *
 */
@Controller
@RequestMapping("/pms/eqpRun")
public class EqpRunTimeController extends BaseController {

	@Autowired
	private EqpRunTimeServiceI eqpRunTimeServiceI;
	
	/**
	 * 
	* @Title: queryEqpRunTime 
	* @Description: 根据条件查询设备运行时间
	* @param bean
	* @return
	 */
	@ResponseBody
	@RequestMapping("/queryEqpRunTime")
	public DataGrid queryEqpRunTime(EqpRunTimeBean bean, PageParams pageParams){
		return eqpRunTimeServiceI.queryEqpRunTimeByBean(bean,pageParams);
	}
	
	/**
	 * 
	* @Title: queryEqpRunTime 
	* @Description: 根据条件查询设备运行时间
	* @param bean
	* @return
	 */
	@ResponseBody
	@RequestMapping("/queryEqpRunTimeChart")
	public EffectiveGraphBean queryEqpRunTimeChart(EqpRunTimeBean bean, PageParams pageParams){
		return eqpRunTimeServiceI.queryEqpRunTimeChart(bean,pageParams);
	}
}
