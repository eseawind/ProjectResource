package com.shlanbao.tzsc.pms.md.matgrp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.md.matgrp.beans.MatGrpBean;
import com.shlanbao.tzsc.pms.md.matgrp.service.MatGrpServiceI;
/**
 * 物料组控制器
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/matgrp")
public class MatGrpController extends BaseController {
	@Autowired
	private MatGrpServiceI matGrpService;
	/**
	 * 查询所有物料组
	 */
	@ResponseBody
	@RequestMapping("/getAllMatGrps")
	public DataGrid getAllMatGrps(MatGrpBean MatGrpBean){
		try {
			return matGrpService.getAllMatGrps(MatGrpBean);
		} catch (Exception e) {
			log.error("查询所有物料组异常", e);
		}
		return null;
	}
	/**
	 * 跳转到物料组新增页面
	 */
	@RequestMapping("/goToMatGrpAddJsp")
	public String goToMatGrpAddJsp(String id){
		return "/pms/md/matgrp_type/grpAdd";
	}
	/**
	 * 新增物料组
	 */
	@ResponseBody
	@RequestMapping("/addMatGrp")
	public Json addMatGrp(MatGrpBean MatGrpBean){
		Json json=new Json();
		try {
			matGrpService.addMatGrp(MatGrpBean);
			json.setMsg("新增物料组成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增物料组失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到物料组编辑页面
	 */
	@RequestMapping("/goToMatGrpEditJsp")
	public String goToMatGrpEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("grp", matGrpService.getMatGrpById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的物料组失败", e);
		}
		return "/pms/md/matgrp_type/grpEdit";
	}
	/**
	 * 编辑物料组
	 */
	@ResponseBody
	@RequestMapping("/editMatGrp")
	public Json editMatGrp(MatGrpBean MatGrpBean){
		Json json=new Json();
		try {
			matGrpService.editMatGrp(MatGrpBean);
			json.setMsg("编辑物料组成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑物料组失败!");
		}
		return json;
	}
	/**
	 * 删除物料组
	 */
	@ResponseBody
	@RequestMapping("/deleteMatGrp")
	public Json deleteMatGrp(String id){
		Json json=new Json();
		try {
			matGrpService.deleteMatGrp(id);
			json.setMsg("删除物料组成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除物料组失败!");
		}
		return json;
	}
}
