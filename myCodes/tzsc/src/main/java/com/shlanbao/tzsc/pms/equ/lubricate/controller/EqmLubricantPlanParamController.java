package com.shlanbao.tzsc.pms.equ.lubricate.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EqmLubricantPlanParamBean;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantPlanParamServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: EqmLubricantController 
* @Description: 设备润滑计划详细项 
* @author luo
* @date 2015年7月8日 下午3:58:32 
*
 */
@Controller
@RequestMapping("/pms/lubrplanParam")
public class EqmLubricantPlanParamController {

	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public EqmLubricantPlanParamServiceI eqmLubricantPlanParamServiceI;
	
	@RequestMapping("/goToAddLubiPlanParam")
	public String goToAddLubiPlanParam()throws Exception{
		return "/pms/equ/lubriplan/addLubriPlanParam";
	}
	@RequestMapping("/goToEditLubiPlanParam")
	public String goToEditLubiPlanParam(String id,HttpServletRequest request)throws Exception{
		if(StringUtil.notNull(id)){
			EqmLubricantPlanParamBean bean=eqmLubricantPlanParamServiceI.getBeanByIds(id);
			request.setAttribute("bean", bean);
			return "/pms/equ/lubriplan/editLubriPlanParam";
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
	@RequestMapping("/addLubrPlanParam")
	public Json addLubrPlanParam(EqmLubricantPlanParamBean bean,HttpServletRequest request,HttpSession session)throws Exception{
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
	@RequestMapping("/editLubrPlanParam")
	public Json editLubrPlanParam(EqmLubricantPlanParamBean bean,HttpServletRequest request)throws Exception{
		Json json = new Json();
		try {
			eqmLubricantPlanParamServiceI.updateBean(bean);
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
	@RequestMapping("/queryLubrPlanParam")
	public DataGrid queryLubrPlanParam(EqmLubricantPlanParamBean bean,PageParams pageParams){
		try {
			return eqmLubricantPlanParamServiceI.queryDataGrid(bean,pageParams);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping("/queryDataGridByPlanId")
	public DataGrid queryDataGridByPlanId(String plan_id){
		try {
			return eqmLubricantPlanParamServiceI.queryDataGridByPlanId(plan_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
