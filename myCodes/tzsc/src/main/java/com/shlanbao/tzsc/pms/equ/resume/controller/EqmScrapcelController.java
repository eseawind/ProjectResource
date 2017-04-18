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
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmScrapcelBean;
import com.shlanbao.tzsc.pms.equ.resume.service.EqmScrapcelServiceI;
import com.shlanbao.tzsc.utils.tools.JSONUtil;

/**
 * 报废清理记录表
 * @author luther.zhang
 */
@Controller
@RequestMapping("/pms/eqmScrapcel")
public class EqmScrapcelController {
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private EqmScrapcelServiceI eqmScrapcelService;
	
	@RequestMapping("/goToJsp")
	public String goToMdType(HttpServletRequest request,String id){
		try {
			EqmScrapcelBean bean = new EqmScrapcelBean();
			bean.setEqmResumeId(id);
			request.setAttribute("bean",bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/equ/resume/editEqmScrapcel";
	}
	
	@ResponseBody
	@RequestMapping("/queryList")
	public DataGrid queryList(EqmScrapcelBean bean,PageParams pageParams){
		try {
			if(null!=bean.getEqmResumeId()&&!"".equals(bean.getEqmResumeId())){
				DataGrid gd = eqmScrapcelService.queryList(bean, pageParams);
				return gd;
			}else{
				List<EqmScrapcelBean> lastList = new ArrayList<EqmScrapcelBean>();
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
				EqmScrapcelBean[] changeBean = (EqmScrapcelBean[]) JSONUtil.JSONString2ObjectArray(reqString,EqmScrapcelBean.class);
				eqmScrapcelService.addOrEditBean(eqmResumeId,changeBean,sessionInfo.getUser().getId());
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
			eqmScrapcelService.deleteById(id);
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
