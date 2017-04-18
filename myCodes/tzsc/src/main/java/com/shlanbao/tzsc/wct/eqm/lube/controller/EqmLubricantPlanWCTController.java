package com.shlanbao.tzsc.wct.eqm.lube.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.eqp.service.EquipmentsServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
import com.shlanbao.tzsc.wct.eqm.lube.beans.EqmLubricantPlanBeans;
import com.shlanbao.tzsc.wct.eqm.lube.service.EqmLubricantPlansServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 * 
* @ClassName: EqmLubricantController 
* @Description: 设备润滑计划 
* @author luo
* @date 2015年7月8日 下午3:58:32 
*
 */
@Controller
@RequestMapping("/wct/lubrplan")
public class EqmLubricantPlanWCTController {

	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public EqmLubricantPlansServiceI eqmLubricantPlanServiceI;
	
	@Autowired
	private EquipmentsServiceI equipmentsService;
	
	@RequestMapping("/goToAddLubiPlan")
	public String goToAddLubiPlan()throws Exception{
		return "/pms/equ/lubriplan/addLubriPlan";
	}
	@RequestMapping("/goToEditLubiPlan")
	public String goToEditLubiPlan(String id,HttpServletRequest request)throws Exception{
		if(StringUtil.notNull(id)){
			EqmLubricantPlanBeans bean=eqmLubricantPlanServiceI.getBeanByIds(id);
			request.setAttribute("bean", bean);
			return "/pms/equ/lubriplan/editLubriPlan";
		}else{
			return "";
		}
	}
	
	/**
	 * 设备润滑计划新增
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/addLubrplan")
	public Json addLubrplan(EqmLubricantPlanBeans bean,HttpServletRequest request,HttpSession session)throws Exception{
		Json json = new Json();
		try {
			
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
	 * 设备润滑计划修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/editLubrplan")
	public Json editLubrplan(EqmLubricantPlanBeans bean,HttpServletRequest request)throws Exception{
		Json json = new Json();
		try {
			eqmLubricantPlanServiceI.updateBean(bean);
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
	@RequestMapping("/queryLubrplan")
	public DataGrid queryLubrplan(EqmLubricantPlanBeans bean,PageParams pageParams){
		try {
			return eqmLubricantPlanServiceI.queryDataGrid(bean,pageParams);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@ResponseBody
	@RequestMapping("/queryLubrplans")
	public DataGrid queryLubrplans(EqmLubricantPlanBeans bean,int pageIndex,HttpServletRequest request){
		try {
			PageParams pageParams=new PageParams();
			pageParams.setPage(pageIndex);
			pageParams.setRows(10);
			return eqmLubricantPlanServiceI.queryDataGrid(bean,pageParams);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//操作工确认润滑完成，并生成新的润滑计划
	@ResponseBody
	@RequestMapping("/editLubrOperplanById")
	public Json editLubrOperplanById(String plan_id,HttpServletRequest request)throws Exception{
		Json json = new Json();
		try {
			LoginBean login = (LoginBean) WebContextUtil.getSessionValue(request.getSession(),"loginWctEqmInfo");//设备管理员登录
			
			EqmLubricantPlan plan=eqmLubricantPlanServiceI.getBeanById(plan_id);
			plan.setOperatives(new SysUser(login.getUserId()));
			plan.setEtim(new Date());
			plan=eqmLubricantPlanServiceI.saveBean(plan);
			//生产下次计划
			EqmLubricantPlanBeans plans=eqmLubricantPlanServiceI.getBeanByIds(plan_id);
			eqmLubricantPlanServiceI.createLubriPlan(plan.getLub_id(),plans.getEqp_id(),plans.getEqp_code());
			json.setMsg("操作成功!");
			json.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	public void createLubriPlan(String lubri_id,String eqp_id,String eqp_code){
		
	}
	
}
