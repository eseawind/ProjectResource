package com.shlanbao.tzsc.pms.qm.proc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.base.model.SessionInfo;
import com.shlanbao.tzsc.pms.qm.proc.beans.QmProcFileBean;
import com.shlanbao.tzsc.pms.qm.proc.beans.QmProcManageBean;
import com.shlanbao.tzsc.pms.qm.proc.service.QmProcManageService;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;

/**
 * 工艺规程控制器
 * <li>@author luther.zhang
 * <li>@create 2014-12-25
 */
@Controller
@RequestMapping("/pms/procManage")
public class QmProcManageController extends BaseController{
	@Autowired
	private QmProcManageService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(QmProcManageBean bean,PageParams pageParams){
		try {
			DataGrid grid = service.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	/** 根据ID查询*/
	@ResponseBody
	@RequestMapping("/getListById")
	public DataGrid getListById(String proManageId,PageParams pageParams){
		try {
			DataGrid grid = service.queryListById(proManageId,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	/**
	 * 新增修改页面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/goEditJsp")
	public String goEditJsp(String id,HttpServletRequest request){
		try {
			if(null!=id&&!"".equals(id)){
				request.setAttribute("bean", service.getBeanById(id));
			}else{
				request.setAttribute("bean", new QmProcManageBean());//表示新增
			}
		} catch (Exception e) {
			log.error("跳转到工艺规程编辑页面失败", e);
		}
		return "/pms/quality/proc/procManageEdit";
	}
	/**
	 * 新建修改
	 */
	@ResponseBody
	@RequestMapping("/editProcManage")
	public Json editProcManage(QmProcManageBean bean,HttpServletRequest request){
		Json json=new Json();
		String type = request.getParameter("type");
		SessionInfo info = (SessionInfo) WebContextUtil.getSessionValue(
				request.getSession(), WebContextUtil.SESSION_INFO);//用户信息
		try {
			if("insert".equals(type)){
				bean.setAddUserId(info.getUser().getId());
				bean.setModifyUserId(info.getUser().getId());
				service.addBean(bean);
				json.setMsg("新增工艺规程成功!");
			}else{
				bean.setModifyUserId(info.getUser().getId());
				bean.setReviewUserId(info.getUser().getId());
				service.editBean(bean);
				json.setMsg("编辑工艺规程成功!");
			}
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			if("insert".equals(type)){
				json.setMsg("新增工艺规程失败!");
			}else{
				json.setMsg("编辑工艺规程失败!");
			}	
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 上传
	 */
	@ResponseBody
	@RequestMapping("/uploadProcManage")
	public Json uploadProcManage(QmProcManageBean bean,HttpServletRequest request){
		Json json=new Json();
		String type = request.getParameter("type");
		SessionInfo info = (SessionInfo) WebContextUtil.getSessionValue(
				request.getSession(), WebContextUtil.SESSION_INFO);//用户信息
		try {
			if("insert".equals(type)){
				bean.setAddUserId(info.getUser().getId());
				bean.setModifyUserId(info.getUser().getId());
				service.addBean(bean);
				json.setMsg("新增工艺规程成功!");
			}else{
				bean.setModifyUserId(info.getUser().getId());
				bean.setReviewUserId(info.getUser().getId());
				service.editBean(bean);
				json.setMsg("编辑工艺规程成功!");
			}
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			if("insert".equals(type)){
				json.setMsg("新增工艺规程失败!");
			}else{
				json.setMsg("编辑工艺规程失败!");
			}	
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequestMapping("/deleteProcManage")
	public Json deleteProcManage(String ids){
		Json json=new Json();
		try {
			service.batchDeleteByIds(ids);
			json.setMsg("删除工艺规程成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除工艺规程失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	@RequestMapping("/uploadJsp")
	public String goTofileUploadJsp(HttpServletRequest request){
		return "/pms/quality/proc/fileUpload";
	}
	/**
	 * 文件上传
	 */
	@ResponseBody
	@RequestMapping("/updateFile")
	public Json fileUpdate(String procManageId,HttpSession session,
			 HttpServletRequest request,QmProcFileBean fmBean) {
		Json json = new Json();
		try {
			service.fileUpdate(procManageId,session,request,fmBean);
			json.setMsg("文件上传成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("文件上传异常", e);
			json.setMsg("文件上传失败！");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 审核
	 */
	@ResponseBody
	@RequestMapping("/reviewProcManage")
	public Json reviewProcManage(String ids,String status,HttpSession session){
		Json json=new Json();
		try {
			service.editReview(ids,status,session);
			json.setMsg("审核工艺规程成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("审核工艺规程失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 下发
	 */
	@ResponseBody
	@RequestMapping("/sendProcManage")
	public Json sendProcManage(String ids,String status,HttpSession session){
		Json json=new Json();
		try {
			service.editSend(ids,status,session);
			json.setMsg("下发工艺规程成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("下发工艺规程失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequestMapping("/deleteProcFile")
	public Json deleteProcFile(String ids,HttpSession session){
		Json json=new Json();
		try {
			service.deleteFiles(ids,session);
			json.setMsg("删除工艺规程文档成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除工艺规程文档失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
