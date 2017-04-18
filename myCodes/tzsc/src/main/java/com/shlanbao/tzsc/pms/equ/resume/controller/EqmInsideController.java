package com.shlanbao.tzsc.pms.equ.resume.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmInsideBean;
import com.shlanbao.tzsc.pms.equ.resume.service.EqmInsideServiceI;
import com.shlanbao.tzsc.utils.tools.JSONUtil;

/**
 * 内部转移记录
 * @author luther.zhang 
 *
 */
@Controller
@RequestMapping("/pms/eqmInside")
public class EqmInsideController {
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private EqmInsideServiceI eqmInsideService;
	
	@RequestMapping("/goToJsp")
	public String goToMdType(HttpServletRequest request,String id){
		try {
			EqmInsideBean bean = new EqmInsideBean();
			bean.setEqmResumeId(id);
			bean.setQueryResumeId(id);
			request.setAttribute("bean",bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/equ/resume/editEqmInside";
	}
	@ResponseBody
	@RequestMapping("/queryList")
	public DataGrid queryList(EqmInsideBean bean,PageParams pageParams){
		try {
			if(null!=bean.getEqmResumeId()&&!"".equals(bean.getEqmResumeId())){
				DataGrid gd = eqmInsideService.queryList(bean, pageParams);
				return gd;
			}else{
				List<EqmInsideBean> lastList = new ArrayList<EqmInsideBean>();
				return new DataGrid(lastList,0L);
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/addOrEditBean")
	public Json addOrEditBean(HttpServletRequest request,HttpSession session){
		Json json = new Json();
		try {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
			String eqmResumeId = request.getParameter("eqmResumeId");
			String reqString = request.getParameter("reqString");
			if(null!=reqString){
				// 填充BEAN
				EqmInsideBean[] changeBean = (EqmInsideBean[]) JSONUtil.JSONString2ObjectArray(reqString,EqmInsideBean.class);
				
				eqmInsideService.addOrEditBean(eqmResumeId,changeBean,sessionInfo.getUser().getId());
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("请选择操作信息!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}


	@ResponseBody
	@RequestMapping("/deleteById")
	public Json deleteById(String id){
		Json json = new Json();
		try {
			eqmInsideService.deleteById(id);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
		
	}
}
