package com.shlanbao.tzsc.pms.qm.prod.controller;

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
import com.shlanbao.tzsc.pms.qm.prod.beans.QmProdFileBean;
import com.shlanbao.tzsc.pms.qm.prod.beans.QmProdManageBean;
import com.shlanbao.tzsc.pms.qm.prod.service.QmProdManageService;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;

/**
 * 产品规程管理控制器
 * <li>@author luther.zhang
 * <li>@create 2014-01-04
 */
@Controller
@RequestMapping("/pms/prodManage")
public class QmProdManageController extends BaseController{
	@Autowired
	private QmProdManageService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(QmProdManageBean bean,PageParams pageParams){
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
				request.setAttribute("bean", new QmProdManageBean());//表示新增
			}
		} catch (Exception e) {
			log.error("跳转到产品规程编辑页面失败", e);
		}
		return "/pms/quality/prod/prodManageEdit";
	}
	/**
	 * 新建修改
	 */
	@ResponseBody
	@RequestMapping("/editProdManage")
	public Json editProdManage(QmProdManageBean bean,HttpServletRequest request){
		Json json=new Json();
		String type = request.getParameter("type");
		SessionInfo info = (SessionInfo) WebContextUtil.getSessionValue(
				request.getSession(), WebContextUtil.SESSION_INFO);//用户信息
		try {
			if("insert".equals(type)){
				bean.setAddUserId(info.getUser().getId());
				bean.setModifyUserId(info.getUser().getId());
				service.addBean(bean);
				json.setMsg("新增产品规程成功!");
			}else{
				bean.setModifyUserId(info.getUser().getId());
				bean.setReviewUserId(info.getUser().getId());
				service.editBean(bean);
				json.setMsg("编辑产品规程成功!");
			}
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			if("insert".equals(type)){
				json.setMsg("新增产品规程失败!");
			}else{
				json.setMsg("编辑产品规程失败!");
			}	
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 上传
	 */
	@ResponseBody
	@RequestMapping("/uploadProdManage")
	public Json uploadProdManage(QmProdManageBean bean,HttpServletRequest request){
		Json json=new Json();
		String type = request.getParameter("type");
		SessionInfo info = (SessionInfo) WebContextUtil.getSessionValue(
				request.getSession(), WebContextUtil.SESSION_INFO);//用户信息
		try {
			if("insert".equals(type)){
				bean.setAddUserId(info.getUser().getId());
				bean.setModifyUserId(info.getUser().getId());
				service.addBean(bean);
				json.setMsg("新增产品规程成功!");
			}else{
				bean.setModifyUserId(info.getUser().getId());
				bean.setReviewUserId(info.getUser().getId());
				service.editBean(bean);
				json.setMsg("编辑产品规程成功!");
			}
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			if("insert".equals(type)){
				json.setMsg("新增产品规程失败!");
			}else{
				json.setMsg("编辑产品规程失败!");
			}	
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequestMapping("/deleteProdManage")
	public Json deleteProdManage(String ids){
		Json json=new Json();
		try {
			service.batchDeleteByIds(ids);
			json.setMsg("删除产品规程成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除产品规程失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	@RequestMapping("/uploadJsp")
	public String goTofileUploadJsp(HttpServletRequest request){
		return "/pms/quality/prod/fileUpload";
	}
	/**
	 * 文件上传
	 */
	@ResponseBody
	@RequestMapping("/updateFile")
	public Json fileUpdate(String prodManageId,HttpSession session,
			 HttpServletRequest request,QmProdFileBean fmBean) {
		Json json = new Json();
		try {
			service.fileUpdate(prodManageId,session,request,fmBean);
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
	@RequestMapping("/reviewProdManage")
	public Json reviewProdManage(String ids,String status,HttpSession session){
		Json json=new Json();
		try {
			service.editReview(ids,status,session);
			json.setMsg("审核产品规程成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("审核产品规程失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 下发
	 */
	@ResponseBody
	@RequestMapping("/sendProdManage")
	public Json sendProdManage(String ids,String status,HttpSession session){
		Json json=new Json();
		try {
			service.editSend(ids,status,session);
			json.setMsg("下发产品规程成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("下发产品规程失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequestMapping("/deleteProdFile")
	public Json deleteProdFile(String ids,HttpSession session){
		Json json=new Json();
		try {
			service.deleteFiles(ids,session);
			json.setMsg("删除产品规程文档成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除产品规程文档失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
