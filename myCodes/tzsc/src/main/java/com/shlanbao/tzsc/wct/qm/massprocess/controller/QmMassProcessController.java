package com.shlanbao.tzsc.wct.qm.massprocess.controller;

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
import com.shlanbao.tzsc.utils.tools.JSONUtil;
import com.shlanbao.tzsc.utils.tools.WebContextUtil;
import com.shlanbao.tzsc.wct.qm.massprocess.beans.QmMassFilterProcessBean;
import com.shlanbao.tzsc.wct.qm.massprocess.beans.QmMassGlycerineProcessBean;
import com.shlanbao.tzsc.wct.qm.massprocess.beans.QmMassProcessBean;
import com.shlanbao.tzsc.wct.qm.massprocess.service.QmMassFilterProcessService;
import com.shlanbao.tzsc.wct.qm.massprocess.service.QmMassGlycerineProcessService;
import com.shlanbao.tzsc.wct.qm.massprocess.service.QmMassProcessService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

/**
 * 过程自检记录控制器
 * <li>@author luther.zhang
 * <li>@create 2015-03-18
 */
@Controller
@RequestMapping("/wct/massProcess")
public class QmMassProcessController extends BaseController{
	@Autowired
	private QmMassProcessService service;
	@Autowired
	private QmMassFilterProcessService filterService;
	@Autowired
	private QmMassGlycerineProcessService glycerineService;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(QmMassProcessBean bean,PageParams pageParams){
		try {
			DataGrid grid = service.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	/** 查询  滤棒的过程检测在 QM_MASS_FILTER_PROCESS表里面，丝束的检测在QM_MASS_PROCESS表里面，甘油的在新表里面*/
	@ResponseBody
	@RequestMapping("/getFilterList")
	public DataGrid getFilterList(QmMassFilterProcessBean bean,PageParams pageParams){
		try {
			DataGrid grid = filterService.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	/**
	 * 甘油施加量查询
	 * @param bean
	 * @param pageParams
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGlycerineList")
	public DataGrid getGlycerineList(QmMassGlycerineProcessBean bean,PageParams pageParams){
		try {
			DataGrid grid = glycerineService.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/editBean")
	public Json editBean(String jsonArray, HttpServletRequest request,HttpSession session) {
		Json json=new Json();
		LoginBean login = (LoginBean) WebContextUtil.getSessionValue(
				request.getSession(),"loginWctZjInfo");//质检用户信息
		try {
			String checkArray = request.getParameter("checkArray");
			String processArray = request.getParameter("processArray");
			if(null!=processArray&&null!=checkArray){
				// 填充BEAN
				QmMassProcessBean[] checkBean = (QmMassProcessBean[]) JSONUtil.JSONString2ObjectArray(checkArray,QmMassProcessBean.class);
				// 填充BEAN
				QmMassProcessBean[] firstBean = (QmMassProcessBean[]) JSONUtil.JSONString2ObjectArray(processArray,QmMassProcessBean.class);
				
				service.editBean(checkBean,firstBean,login);
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("保存失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 成型机修改
	 */
	@ResponseBody
	@RequestMapping("/editFilterBean")
	public Json filterEditBean(String jsonArray, HttpServletRequest request,HttpSession session) {
		Json json=new Json();
		LoginBean login = (LoginBean) WebContextUtil.getSessionValue(
				request.getSession(),"loginWctZjInfo");//质检用户信息
		try {
			String checkArray = request.getParameter("checkArray");
			String processArray = request.getParameter("processArray");
			if(null!=processArray&&null!=checkArray){
				// 填充BEAN
				QmMassFilterProcessBean[] checkBean = (QmMassFilterProcessBean[]) JSONUtil.JSONString2ObjectArray(checkArray,QmMassFilterProcessBean.class);
				// 填充BEAN
				QmMassFilterProcessBean[] firstBean = (QmMassFilterProcessBean[]) JSONUtil.JSONString2ObjectArray(processArray,QmMassFilterProcessBean.class);
				
				filterService.editBean(checkBean,firstBean,login);
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("保存失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 甘油施加量修改
	 */
	@ResponseBody
	@RequestMapping("/editGlycerineBean")
	public Json glycerineEditBean(String jsonArray, HttpServletRequest request,HttpSession session) {
		Json json=new Json();
		LoginBean login = (LoginBean) WebContextUtil.getSessionValue(
				request.getSession(),"loginWctZjInfo");//质检用户信息
		try {
			String checkArray = request.getParameter("checkArray");
			String processArray = request.getParameter("processArray");
			if(null!=processArray&&null!=checkArray){
				// 填充BEAN
				QmMassGlycerineProcessBean[] checkBean = (QmMassGlycerineProcessBean[]) JSONUtil.JSONString2ObjectArray(checkArray,QmMassGlycerineProcessBean.class);
				// 填充BEAN
				QmMassGlycerineProcessBean[] firstBean = (QmMassGlycerineProcessBean[]) JSONUtil.JSONString2ObjectArray(processArray,QmMassGlycerineProcessBean.class);
				
				glycerineService.editBean(checkBean,firstBean,login);
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("保存失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	
	/**
	 * 成型机删除
	 */
	@ResponseBody
	@RequestMapping("/deleteFilterByIds")
	public Json deleteFilterByIds(String ids, HttpServletRequest request){
		Json json=new Json();
		try {
			String delIds = request.getParameter("ids");
			filterService.deleteByIds(delIds);
			json.setMsg("删除成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 甘油施加量删除
	 */
	@ResponseBody
	@RequestMapping("/deleteGlycerineByIds")
	public Json deleteGlycerineByIds(String ids, HttpServletRequest request){
		Json json=new Json();
		try {
			String delIds = request.getParameter("ids");
			glycerineService.deleteByIds(delIds);
			json.setMsg("删除成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequestMapping("/deleteByIds")
	public Json deleteByIds(String ids, HttpServletRequest request){
		Json json=new Json();
		try {
			String delIds = request.getParameter("ids");
			service.deleteByIds(delIds);
			json.setMsg("删除成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
