package com.shlanbao.tzsc.wct.qm.massstem.controller;

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
import com.shlanbao.tzsc.wct.qm.massstem.beans.QmMassStemBean;
import com.shlanbao.tzsc.wct.qm.massstem.service.QmMassStemService;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

/**
 * 丝含梗控制器
 * <li>@author luther.zhang
 * <li>@create 2015-03-28
 */
@Controller
@RequestMapping("/wct/massStem")
public class QmMassStemController extends BaseController{
	@Autowired
	private QmMassStemService service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public DataGrid getList(QmMassStemBean bean,PageParams pageParams){
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
		LoginBean login = (LoginBean) WebContextUtil.getSessionValue(
				request.getSession(),"loginWctZjInfo");//质检用户信息
		try {
			String checkArray = request.getParameter("checkArray");
			String stemArray = request.getParameter("stemArray");
			if(null!=stemArray&&null!=checkArray){
				// 填充BEAN
				QmMassStemBean[] checkBean = (QmMassStemBean[]) JSONUtil.JSONString2ObjectArray(checkArray,QmMassStemBean.class);
				// 填充BEAN
				QmMassStemBean[] firstBean = (QmMassStemBean[]) JSONUtil.JSONString2ObjectArray(stemArray,QmMassStemBean.class);
				
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
