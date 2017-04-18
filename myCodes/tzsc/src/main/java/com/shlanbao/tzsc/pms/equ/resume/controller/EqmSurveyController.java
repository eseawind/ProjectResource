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
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmSurveyBean;
import com.shlanbao.tzsc.pms.equ.resume.service.EqmSurveyServiceI;
import com.shlanbao.tzsc.utils.tools.JSONUtil;

/**
 * 调查记录表
 * @author luther.zhang
 */
@Controller
@RequestMapping("/pms/eqmSurvey")
public class EqmSurveyController {
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private EqmSurveyServiceI eqmSurveyService;
	
	@RequestMapping("/goToJsp")
	public String goToMdType(HttpServletRequest request,String id){
		try {
			EqmSurveyBean bean = new EqmSurveyBean();
			bean.setEqmResumeId(id);
			request.setAttribute("bean",bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/equ/resume/editEqmSurvey";
	}

	@ResponseBody
	@RequestMapping("/queryList")
	public DataGrid queryList(EqmSurveyBean bean,PageParams pageParams){
		try {
			if(null!=bean.getEqmResumeId()&&!"".equals(bean.getEqmResumeId())){
				DataGrid gd = eqmSurveyService.queryList(bean, pageParams);
				return gd;
			}else{
				List<EqmSurveyBean> lastList = new ArrayList<EqmSurveyBean>();
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
				EqmSurveyBean[] changeBean = (EqmSurveyBean[]) JSONUtil.JSONString2ObjectArray(reqString,EqmSurveyBean.class);
				eqmSurveyService.addOrEditBean(eqmResumeId,changeBean,sessionInfo.getUser().getId());
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
			eqmSurveyService.deleteById(id);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
}