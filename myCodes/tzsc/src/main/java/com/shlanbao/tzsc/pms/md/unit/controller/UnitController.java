package com.shlanbao.tzsc.pms.md.unit.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.md.unit.beans.UnitBean;
import com.shlanbao.tzsc.pms.md.unit.service.UnitServiceI;
/**
 * 计量单位
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/unit")
public class UnitController extends BaseController {
	@Autowired
	public UnitServiceI unitService;
	/**
	 * 查询所有单位
	 */
	@ResponseBody
	@RequestMapping("/getAllUnits")
	public DataGrid getAllUnits(UnitBean unitBean){
		try {
			return unitService.getAllUnits(unitBean);
		} catch (Exception e) {
			log.error("查询所有计量单位异常", e);
		}
		return null;
	}
	/**
	 * 跳转到单位新增页面
	 */
	@RequestMapping("/goToUnitAddJsp")
	public String goToUnitAddJsp(String id){
		return "/pms/md/unit/unitAdd";
	}
	/**
	 * 新增单位
	 */
	@ResponseBody
	@RequestMapping("/addUnit")
	public Json addUnit(UnitBean unitBean){
		Json json=new Json();
		try {
			unitService.addUnit(unitBean);
			json.setMsg("新增计量单位成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增计量单位失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到单位新增页面
	 */
	@RequestMapping("/goToUnitEditJsp")
	public String goToUnitEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("unit", unitService.getUnitById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的计量单位失败", e);
		}
		return "/pms/md/unit/unitEdit";
	}
	/**
	 * 编辑单位
	 */
	@ResponseBody
	@RequestMapping("/editUnit")
	public Json editUnit(UnitBean unitBean){
		Json json=new Json();
		try {
			unitService.editUnit(unitBean);
			json.setMsg("编辑计量单位成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑计量单位失败!");
		}
		return json;
	}
	/**
	 * 删除单位
	 */
	@ResponseBody
	@RequestMapping("/deleteUnit")
	public Json deleteUnit(String id){
		Json json=new Json();
		try {
			unitService.deleteUnit(id);
			json.setMsg("删除计量单位成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除计量单位失败!");
		}
		return json;
	}
}
