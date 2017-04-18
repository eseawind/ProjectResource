package com.shlanbao.tzsc.pms.equ.sbglplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EqmSpotchRecodeBean;
import com.shlanbao.tzsc.pms.equ.sbglplan.service.EqmCheckcatPlanServiceI;

@Controller
@RequestMapping("/pms/equc/check")
public class EqmCheckcatPlanController {
	@Autowired
	private EqmCheckcatPlanServiceI eqmCheckcatPlanService;
	/**
	 * 跳转到点检查询信息页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/gotoCheckManage")
	public String gotoCheckManage()throws Exception{
		return "/pms/equ/check/checkInfo";
	}
	
	/**
	 * 查询点修工单
	 * @param wcpBean
	 * @param pageParams
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryPlan")
	public DataGrid queryPlan(EqmSpotchRecodeBean eSRBean,PageParams pageParams){
		try {
			DataGrid gd = eqmCheckcatPlanService.queryPlanInfo(eSRBean,pageParams);
			return gd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
/**
 * 查询详细点修记录	
 * @param czgId
 * @param wxgId
 * @param wxzugId
 * @return
 */
	@ResponseBody
	@RequestMapping("queryInfoById")
	public DataGrid queryInfoById(String id){
		try {
			return eqmCheckcatPlanService.queryInfoById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
