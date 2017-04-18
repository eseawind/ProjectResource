package com.shlanbao.tzsc.pms.equ.sbglplan.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.mapping.EqmWheelCovelPlan;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EqmPlanBean;
import com.shlanbao.tzsc.pms.equ.sbglplan.beans.EquipmentsBean;
import com.shlanbao.tzsc.pms.equ.sbglplan.service.EqmPlanServiceI;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.BatchWCPlan;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCovelParamBean;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeChildBean;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.JSONUtil;

@Controller
@RequestMapping("/pms/equ/sbglPlan")
public class EqmPlanController {
	@Autowired
	private EqmPlanServiceI eqmPlanService;
	@ResponseBody
	@RequestMapping("/queryEqu")
	public DataGrid queryEqu(EquipmentsBean equBean,PageParams pageParams){
		try {
			DataGrid gd = eqmPlanService.queryEqu(equBean, pageParams);
			return gd;
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 根据设备型号ID查询对应的轮保规则
	 * @param equBean
	 * @param pageParams
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryEqpTypeChild")
	public DataGrid queryEqpTypeChild(EquipmentsBean equBean,PageParams pageParams){
		try {
			if(null!=equBean.getQueryEqpTypeId()&&!"".equals(equBean.getQueryEqpTypeId())){
				DataGrid gd = eqmPlanService.queryEqpTypeChild(equBean, pageParams);
				return gd;
			}else{
				List<MdEqpTypeChildBean> lastList = new ArrayList<MdEqpTypeChildBean>();
				return new DataGrid(lastList,0L);
			}
		} catch (Exception e) {
		}
		return null;
	}
	@ResponseBody
	@RequestMapping("/addWCPlan")
	public Json addWCPlan(EqmPlanBean wcpBean,HttpServletRequest request,HttpSession session){
		Json json = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		try {
			EqmWheelCovelPlan planBean = eqmPlanService.addWCPlan(wcpBean, sessionInfo.getUser().getId());
			if(null!=planBean.getId()&&!"".equals(planBean.getId())){
				eqmPlanService.addEqmWheelCovelParam(planBean,null);
			}
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/batchAddWCPlan")
	public Json batchAddWCPlan(HttpServletRequest request,HttpSession session){
		Json json = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		try {
			
			String reqString = request.getParameter("reqString");
			if(null!=reqString){// 填充BEAN
				BatchWCPlan[] changeBean = (BatchWCPlan[]) JSONUtil.JSONString2ObjectArray(reqString,BatchWCPlan.class);
				for(BatchWCPlan b:changeBean){
					EqmPlanBean wcpBean=new EqmPlanBean();
					wcpBean.setEquipmentId(b.getEqmId());
					wcpBean.setEquipmentMinute(b.getEquipmentMinute());
					wcpBean.setMatId(b.getMatId());
					wcpBean.setMdShiftId(b.getShiftId());
					wcpBean.setScheduleDate(b.getScheduleDate());
					wcpBean.setPlanName(b.getEqmText()+"轮保");
					String planCode=DateUtil.datetoStr(new Date(),"yyyyMMddHHmmss.SSSZ")+"LB";
					wcpBean.setPlanCode(planCode);
					wcpBean.setMaintenanceType("lb");
					wcpBean.setWheelCoverType("0");
					wcpBean.setDel("0");
					wcpBean.setMetcId(b.getRuleId());
					EqmWheelCovelPlan planBean = eqmPlanService.addWCPlan(wcpBean, sessionInfo.getUser().getId());
					if(null!=planBean.getId()&&!"".equals(planBean.getId())){
						eqmPlanService.addEqmWheelCovelParam(planBean,b.getRuleId());
					}
				}
			}
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	@RequestMapping("/goToEditWCPlan")
	public String goToEditWCPlan(HttpServletRequest request,String id){
		try {
			request.setAttribute("wcpBean",eqmPlanService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/equ/wcplan/editWCPlan";
	}
	/**
	 * 修改
	 * @param wcpBean
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editWCPlan")
	public Json editWCPlan(EqmPlanBean wcpBean,HttpServletRequest request,HttpSession session){
		Json json = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		try {
			eqmPlanService.editWCPlan(wcpBean, sessionInfo.getUser().getId());
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/auditing")
	public Json auditing(String id,String status,HttpSession session){
		return operating(id,"2",status,session);
	}
	@ResponseBody
	@RequestMapping("/auditingRun")
	public Json auditingRun(String id,String status,HttpSession session){
		return operating(id,"2",status,session);
	}
	
	private Json operating(String id,String statusId,String status,HttpSession session){
		Json json = new Json();
		try {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
			eqmPlanService.updateWCPlanStatus(id,statusId,sessionInfo.getUser().getId());
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");			
			json.setSuccess(false);
		}
		return json;
	}
	//批量审核--1为审核
	@ResponseBody
	@RequestMapping("/batchAuditing")
	public Json batchAuditing(String ids,String status,HttpSession session){
		return batchOperating(ids,"1",session);
	}
	//批量批准--2为批准
	@ResponseBody
	@RequestMapping("/batchAuditingRun")
	public Json batchAuditingRun(String ids,String status,HttpSession session){
		return batchOperating(ids,"2",session);
	}
	private Json batchOperating(String ids,String statusId,HttpSession session){
		Json json = new Json();
		try {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
			eqmPlanService.batchUpdateWCPlanStatus(ids,statusId,sessionInfo.getUser().getName());
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");			
			json.setSuccess(false);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/queryParam")
	public DataGrid queryWcpParam(EqmWheelCovelParamBean bean){
		
		try {
			return eqmPlanService.queryBeanList(bean);
		} catch (Exception e) {
		}
		return null;
	}
	/*
	 * 修改删除状态
	 */
	@ResponseBody
	@RequestMapping("/deleteWCPlan")
	public Json deleteWCPlan(String id){
		Json json = new Json();
		try {
			eqmPlanService.deleteWCPlan(id);
			json.setMsg("删除设备主数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setMsg("删除设备主数据失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/*
	 * 修为成为编辑状态
	 */
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(String id){
		Json json = new Json();
		try {
			eqmPlanService.edit(id);
			json.setMsg("修改设备主数据状态成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setMsg("修改设备主数据状态失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
