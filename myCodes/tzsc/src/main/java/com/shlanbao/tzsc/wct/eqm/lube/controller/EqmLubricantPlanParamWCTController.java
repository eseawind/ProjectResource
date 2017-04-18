package com.shlanbao.tzsc.wct.eqm.lube.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmLubricantPlan;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.utils.tools.JSONUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
import com.shlanbao.tzsc.wct.eqm.lube.beans.EqmLubricantPlanParamBeans;
import com.shlanbao.tzsc.wct.eqm.lube.service.EqmLubricantPlanParamsServiceI;
import com.shlanbao.tzsc.wct.eqm.lube.service.EqmLubricantPlansServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;
/**
 *  
* @ClassName: EqmLubricantController 
* @Description: 设备润滑计划详细项 
* @author luo
* @date 2015年7月8日 下午3:58:32 
*
 */
@Controller
@RequestMapping("/wct/lubrplanParam")
public class EqmLubricantPlanParamWCTController extends BaseController{

	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public EqmLubricantPlanParamsServiceI eqmLubricantPlanParamServiceI;
	
	@Autowired
	public EqmLubricantPlansServiceI eqmLubricantPlanServiceI;
	/**
	 * 设备润滑计划修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/editLubrPlanParam")
	public Json editLubrPlanParam(EqmLubricantPlanParamBeans bean,HttpServletRequest request)throws Exception{
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
	public DataGrid queryLubrPlanParam(EqmLubricantPlanParamBeans bean,PageParams pageParams){
		try {
			return eqmLubricantPlanParamServiceI.queryDataGrid(bean,pageParams);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping("/queryDataGridByPlanId")
	public DataGrid queryDataGridByPlanId(String plan_id,String type){
		try {
			return eqmLubricantPlanParamServiceI.queryDataGridByPlanId(plan_id,type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@ResponseBody
	@RequestMapping("/lubricantParamEnd")
	public Json lubricantParamEnd(String param_id, HttpServletRequest request){
		Json json=new Json();
		LoginBean login = (LoginBean) WebContextUtil.getSessionValue(request.getSession(),"loginWctEqmInfo");//设备管理员登录
		if(login==null){
			login = (LoginBean) WebContextUtil.getSessionValue(request.getSession(),"loginInfo");//设备管理员登录
		}
		try {
			String eqpArrayList = request.getParameter("param_id");
			if(null!=eqpArrayList){
				// 填充BEAN
				EqmLubricantPlanParamBeans[] beans = (EqmLubricantPlanParamBeans[]) JSONUtil.JSONString2ObjectArray(eqpArrayList,EqmLubricantPlanParamBeans.class);
				eqmLubricantPlanParamServiceI.lubricantParamEnd(beans,login);
				//设备润滑计划润滑工完成
				EqmLubricantPlan plan=eqmLubricantPlanServiceI.getBeanById(beans[0].getPlan_id());
				plan.setLubricating(new SysUser(login.getUserId()));
				eqmLubricantPlanServiceI.saveBean(plan);
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("保存失败!");
				json.setSuccess(false);
			}
		}catch(Exception e){
			log.error(message, e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 查看PMS部署的图片
	 * @param bean
	 * @param request
	 * @return
	 */
	@RequestMapping("/gotoHotspotJsp")
	public String gotoHotspotJsp(EqmLubricantPlanParamBeans bean,HttpServletRequest request){
		try {
			EqmLubricantPlanParamBeans sb = eqmLubricantPlanParamServiceI.getTypeById(bean.getId());
			//sb.setUploadUrl(bean.getUploadUrl());
			//sb.setSetImagePoint(bean.getSetImagePoint());
			request.setAttribute("queryBean",sb);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "/wct/eqm/rh/rhHotspot";
	}
	
	
	/**
	 * 
	 * @param bean
	 * @param request
	 * @return
	 */
	@RequestMapping("/gotoimgPointJsp")
	public String gotoimgPointJsp(EqmLubricantPlanParamBeans bean,HttpServletRequest request){
		try {
			EqmLubricantPlanParamBeans sb = eqmLubricantPlanParamServiceI.piontInfo(bean.getId());
			request.setAttribute("pointInfo",sb);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "/wct/eqm/rh/pointInfo";
	}
	
	/**
	 * 针对部位进行查询
	 * @param rhPart
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findZF")
	public DataGrid findZF(String rhPart){
		try {
			DataGrid list = eqmLubricantPlanParamServiceI.findZF(rhPart);
			return list;
			//return eqmLubricantPlanParamServiceI.findZF(rhPart);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		
	}
	
}
