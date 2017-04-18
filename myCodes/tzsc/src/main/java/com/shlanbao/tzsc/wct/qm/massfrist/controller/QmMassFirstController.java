package com.shlanbao.tzsc.wct.qm.massfrist.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.shlanbao.tzsc.wct.qm.massfrist.beans.QmMassFirstBean;
import com.shlanbao.tzsc.wct.qm.massfrist.service.QmMassFirstService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

/**
 * 首检记录控制器
 * <li>@author luther.zhang
 * <li>@create 2015-03-16
 */
@Controller
@RequestMapping("/wct/massFrist")
public class QmMassFirstController extends BaseController{
	@Autowired
	private QmMassFirstService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(QmMassFirstBean bean,PageParams pageParams){
		try {
			DataGrid grid = service.queryList(bean,pageParams);
			return grid;
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
	
	/**
	 * 新建修改
	 */
	@ResponseBody
	@RequestMapping("/editBean")
	public Json editBean(String jsonArray, HttpServletRequest request) {
		Json json=new Json();
		LoginBean login = (LoginBean) WebContextUtil.getSessionValue(request.getSession(),"loginWctZjInfo");//质检用户信息
		try {
			String checkArray = request.getParameter("checkArray");
			String firstArray = request.getParameter("firstArray");
			if(null!=firstArray&&null!=checkArray){
				// 填充BEAN
				QmMassFirstBean[] checkBean = (QmMassFirstBean[]) JSONUtil.JSONString2ObjectArray(checkArray,QmMassFirstBean.class);
				// 填充BEAN
				QmMassFirstBean[] firstBean = (QmMassFirstBean[]) JSONUtil.JSONString2ObjectArray(firstArray,QmMassFirstBean.class);
				
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
	 * 批量删除
	 */
	@ResponseBody
	@RequestMapping("/deleteByIds")
	public Json deleteByIds(String ids, HttpServletRequest request){
		Json json=new Json();
		try {
			String delIds = request.getParameter("ids");
			LoginBean login = (LoginBean) WebContextUtil.getSessionValue(
					request.getSession(),"loginWctZjInfo");//质检用户信息
			service.deleteByIds(delIds,login);
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
	 * 查询/填写钢印信息
	 */
	@ResponseBody
	@RequestMapping("/fillSteelSeal")
	public Json fillSteelSeal(String jsonArray,HttpServletRequest request ){
		Json json=new Json();
		boolean flag=false;
		LoginBean login = (LoginBean) WebContextUtil.getSessionValue(request.getSession(),"loginWctZjInfo");
		try {
			String checkArray = request.getParameter("params");
			QmMassFirstBean checkBean =  (QmMassFirstBean) JSONUtil.JSONString2Bean(checkArray, QmMassFirstBean.class);
			json=service.filledSteelSeal(checkBean,login);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("钢印信息保存失败！");
			json.setSuccess(false);
		}
		return json;
	}
	
	
}
