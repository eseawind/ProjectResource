package com.shlanbao.tzsc.pms.equ.eqpCheck.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.eqpCheck.bean.EqmCheckPlanBean;
import com.shlanbao.tzsc.pms.equ.eqpCheck.service.EqmCheckServiceI;


@Controller
@RequestMapping("/pms/eqmcheck")
public class EqmCheckController {
	@Autowired
	protected EqmCheckServiceI eqmCheckServiceI;
	@ResponseBody
	@RequestMapping("/batchEqmCheckPlan")
	public Json batchEqmCheckPlan(EqmCheckPlanBean eqmCheckPlan,HttpSession session){
		Json json = new Json();
		try {
			eqmCheckServiceI.batchAdd(eqmCheckPlan);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 查询
	 * 2015.9.16--张璐
	 * @param eqmCheckPlan
	 * @param pageParams
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryEqmCheckPlan")
	public DataGrid queryEqmCheckPlan(EqmCheckPlanBean eqmCheckPlan,PageParams pageParams){
		try {
			return eqmCheckServiceI.queryEqmCheckPlan(eqmCheckPlan,pageParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 批量删除用户
	 */
	@ResponseBody
	@RequestMapping("/batchDeleteEqmCheckPlan")
	public Json batchDeleteEqmCheckPlan(String ids){
		Json json=new Json();
		try {
			eqmCheckServiceI.batchDeleteEqmCheckPlan(ids);
			json.setMsg("批量删除数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("批量删除数据失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 查询计划详细
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryEqmCheckPlanParam")
	public DataGrid queryEqmCheckPlanParam(String pid){
		try {
			return eqmCheckServiceI.queryEqmCheckPlanParam(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
