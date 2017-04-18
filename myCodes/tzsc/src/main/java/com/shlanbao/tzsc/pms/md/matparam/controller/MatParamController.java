package com.shlanbao.tzsc.pms.md.matparam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.md.matparam.beans.MatParamBean;
import com.shlanbao.tzsc.pms.md.matparam.service.MatParamServiceI;
/**
 * 辅料系数
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/matparam")
public class MatParamController extends BaseController {
	@Autowired
	public MatParamServiceI matParamService;
	/**
	 * 查询所有辅料计数系数
	 */
	@ResponseBody
	@RequestMapping("/getAllMatParams")
	public DataGrid getAllMatParams(MatParamBean MatParamBean){
		try {
			return matParamService.getAllMatParams(MatParamBean);
		} catch (Exception e) {
			log.error("查询所有辅料系数异常", e);
		}
		return null;
	}
	/**
	 * 跳转到辅料计数系数新增页面
	 */
	@RequestMapping("/goToMatParamAddJsp")
	public String goToMatParamAddJsp(String id){
		return "/pms/md/matparam/matParamAdd";
	}
	/**
	 * 新增辅料计数系数
	 */
	@ResponseBody
	@RequestMapping("/addMatParam")
	public Json addMatParam(MatParamBean MatParamBean){
		Json json=new Json();
		try {
			matParamService.addMatParam(MatParamBean);
			json.setMsg("新增辅料系数成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增辅料系数失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到辅料计数系数编辑页面
	 */
	@RequestMapping("/goToMatParamEditJsp")
	public String goToMatParamEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("matParam", matParamService.getMatParamById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的辅料系数失败", e);
		}
		return "/pms/md/matparam/matParamEdit";
	}
	/**
	 * 编辑辅料计数系数
	 */
	@ResponseBody
	@RequestMapping("/editMatParam")
	public Json editMatParam(MatParamBean MatParamBean){
		Json json=new Json();
		try {
			matParamService.editMatParam(MatParamBean);
			json.setMsg("编辑辅料系数成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑辅料系数失败!");
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/deleteMatParam")
	public Json deleteMatParam(String id){
		Json json=new Json();
		try {
			matParamService.deleteMatParam(id);
			json.setMsg("删除辅料系数成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除辅料系数失败!");
		}
		return json;
	}
}
