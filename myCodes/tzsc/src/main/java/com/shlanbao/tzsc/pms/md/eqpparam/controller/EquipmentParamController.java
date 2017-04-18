package com.shlanbao.tzsc.pms.md.eqpparam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.md.eqpparam.beans.EquipmentParamBean;
import com.shlanbao.tzsc.pms.md.eqpparam.service.EquipmentParamServiceI;
/**
 * 设备滚轴系数控制器
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/eqpparam")
public class EquipmentParamController extends BaseController {
	@Autowired
	public EquipmentParamServiceI equipmentParamService;
	/**
	 * 获得所有设备滚轴系数
	 */
	@ResponseBody
	@RequestMapping("/getAllEquipmentParams")
	public DataGrid getAllEquipmentParams(EquipmentParamBean EquipmentParamBean){
		try {
			return equipmentParamService.getAllEquipmentParams(EquipmentParamBean);
		} catch (Exception e) {
			log.error("查询所有辅料系数异常", e);
		}
		return null;
	}
	/**
	 * 跳转到设备滚轴系数新增页面
	 */
	@RequestMapping("/goToEquipmentParamAddJsp")
	public String goToEquipmentParamAddJsp(String id){
		return "/pms/md/eqpparam/equipmentParamAdd";
	}
	/**
	 * 新增设备滚轴系数
	 */
	@ResponseBody
	@RequestMapping("/addEquipmentParam")
	public Json addEquipmentParam(EquipmentParamBean EquipmentParamBean){
		Json json=new Json();
		try {
			equipmentParamService.addEquipmentParam(EquipmentParamBean);
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
	 * 跳转到设备滚轴系数编辑页面
	 */
	@RequestMapping("/goToEquipmentParamEditJsp")
	public String goToEquipmentParamEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("eqppparam", equipmentParamService.getEquipmentParamById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的辅料系数失败", e);
		}
		return "/pms/md/eqpparam/equipmentParamEdit";
	}
	/**
	 * 编辑设备滚轴系数
	 */
	@ResponseBody
	@RequestMapping("/editEquipmentParam")
	public Json editEquipmentParam(EquipmentParamBean EquipmentParamBean){
		Json json=new Json();
		try {
			equipmentParamService.editEquipmentParam(EquipmentParamBean);
			json.setMsg("编辑辅料系数成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑辅料系数失败!");
		}
		return json;
	}
	/**
	 * 删除设备滚轴系数
	 */
	@ResponseBody
	@RequestMapping("/deleteEquipmentParam")
	public Json deleteEquipmentParam(String id){
		Json json=new Json();
		try {
			equipmentParamService.deleteEquipmentParam(id);
			json.setMsg("删除辅料系数成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除辅料系数失败!");
		}
		return json;
	}
}
