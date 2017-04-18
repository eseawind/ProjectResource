package com.shlanbao.tzsc.pms.equ.resume.controller;

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
import com.shlanbao.tzsc.pms.equ.resume.beans.EqmResumeBean;
import com.shlanbao.tzsc.pms.equ.resume.service.ResumeServiceI;

/**
 * 设备履历管理
 */
@Controller
@RequestMapping("/pms/resume")
public class ResumeController {
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private ResumeServiceI resumeService;
	
	@RequestMapping("/goToAddResume")
	public String goToMdType(HttpServletRequest request,String id){
		if(null != id){
			try {
				request.setAttribute("eqmResumeBean",resumeService.getResumeById(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "/pms/equ/resume/addResume";
	}
	
	
	@ResponseBody
	@RequestMapping("/addResume")
	public Json addResume(EqmResumeBean eqmResumeBean,HttpSession session){
		Json json = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		try {
			resumeService.addOrEditResume(eqmResumeBean,sessionInfo.getUser().getId());
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
	@RequestMapping("/queryResume")
	public DataGrid queryResume(EqmResumeBean eqmResumeBean,PageParams pageParams){
		try {
			DataGrid gd = resumeService.queryResume(eqmResumeBean,pageParams);
			return gd;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@ResponseBody
	@RequestMapping("/deleteEqu")
	public Json deleteEqu(String id){
		Json json = new Json();
		try {
			resumeService.deleResumeById(id);
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
