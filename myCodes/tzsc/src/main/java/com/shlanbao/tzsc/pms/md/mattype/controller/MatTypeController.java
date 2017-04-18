package com.shlanbao.tzsc.pms.md.mattype.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.md.mattype.beans.MatTypeBean;
import com.shlanbao.tzsc.pms.md.mattype.service.MatTypeServiceI;
/**
 * 物料类型
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/mattype")
public class MatTypeController extends BaseController {
	@Autowired
	private MatTypeServiceI matTypeService;
	/**
	 * 查询所有物料类型
	 */
	@ResponseBody
	@RequestMapping("/getAllMatTypes")
	public DataGrid getAllMatTypes(MatTypeBean MatTypeBean){
		try {
			return matTypeService.getAllMatTypes(MatTypeBean);
		} catch (Exception e) {
			log.error("查询所有物料类型异常", e);
		}
		return null;
	}
	/**
	 * 跳转到辅料类型新增页面
	 */
	@RequestMapping("/goToMatTypeAddJsp")
	public String goToMatTypeAddJsp(String id){
		return "/pms/md/matgrp_type/typeAdd";
	}
	/**
	 *新增物料类型
	 */
	@ResponseBody
	@RequestMapping("/addMatType")
	public Json addMatType(MatTypeBean MatTypeBean){
		Json json=new Json();
		try {
			matTypeService.addMatType(MatTypeBean);
			json.setMsg("新增物料类型成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增物料类型失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到物料类编辑页面
	 */
	@RequestMapping("/goToMatTypeEditJsp")
	public String goToMatTypeEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("type", matTypeService.getMatTypeById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的物料类型失败", e);
		}
		return "/pms/md/matgrp_type/typeEdit";
	}
	/**
	 * 编辑物料类型
	 */
	@ResponseBody
	@RequestMapping("/editMatType")
	public Json editMatType(MatTypeBean MatTypeBean){
		Json json=new Json();
		try {
			matTypeService.editMatType(MatTypeBean);
			json.setMsg("编辑物料类型成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑物料类型失败!");
		}
		return json;
	}
	/**
	 * 生产物料类型
	 */
	@ResponseBody
	@RequestMapping("/deleteMatType")
	public Json deleteMatType(String id){
		Json json=new Json();
		try {
			matTypeService.deleteMatType(id);
			json.setMsg("删除物料类型成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除物料类型失败!");
		}
		return json;
	}
	/**
	 * 根据物料组查询物料类型
	 */
	@ResponseBody
	@RequestMapping("/getAllTypesByGrp")
	public DataGrid getAllTypesByGrp(String gid){
		try {
			return matTypeService.getAllTypesByGrp(gid);
		} catch (Exception e) {
			log.error("根据物料组查询物料类型异常", e);
		}
		return null;
	}
}
