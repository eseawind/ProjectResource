package com.shlanbao.tzsc.pms.equ.param.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmParam;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.param.service.EqmParamServiceI;


/**
 * 
* @ClassName: EqmParamController 
* @Description: 设备模块参数维护 
* @author luo
* @date 2015年3月11日 上午9:37:50 
*
 */
@Controller
@RequestMapping("/pms/eqmparam")
public class EqmParamController extends BaseController{
	
	@Autowired
	private EqmParamServiceI eqmParamServiceI;
	
	/**
	* @Title: queryIncompleteByBean 
	* @Description: 根据Bean查询
	* @param  bean
	* @param  pageParams
	* @return DataGrid    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryParam")
	public DataGrid queryIncompleteByBean(EqmParam bean,PageParams pageParams){
		
		return eqmParamServiceI.queryBeanGridByBean(bean, pageParams);
	}
	
	//页面跳转
	@RequestMapping("/gotoBean")
	public String gotoDis(){
		return "/pms/equ/param/addOrUpdateBean";
	} 
	/**
	* @Title: gotoEditBean 
	* @Description: 跳转到修改界面
	* @param  request
	* @param  id
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/gotoEditBean")
	public String gotoEditBean(HttpServletRequest request,String id){
		try {
			request.setAttribute("bean",eqmParamServiceI.queryBeanById(id));
			request.setAttribute("id","i");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改设备模块参数维护功能时读取信息异常。", e);
		}
		return "/pms/equ/param/addOrUpdateBean";
	}

	/**
	* @Title: addOrUpdateBean 
	* @Description: 添加或修改Bean 
	* @param @param bean
	* @param @return    设定文件 
	* @return Json    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdateParam")
	public Json addOrUpdateParam(EqmParam bean){
		Json json = new Json();
		try {
			if(eqmParamServiceI.addOrUpdateBean(bean)){
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("操作失败!");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加或修改设备模块参数维护功能信息异常。", e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
