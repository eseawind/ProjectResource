package com.shlanbao.tzsc.pms.md.shift.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.md.shift.beans.ShiftBean;
import com.shlanbao.tzsc.pms.md.shift.service.ShiftServiceI;
/**
 *	班次
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/shift")
public class ShiftController extends BaseController {
	@Autowired
	private ShiftServiceI shiftService;
	/**
	 * 查询班次
	 */
	@ResponseBody
	@RequestMapping("/getAllShifts")
	public DataGrid getAllShifts(ShiftBean shiftBean){
		try {
			return shiftService.getAllShifts(shiftBean);
		} catch (Exception e) {
			log.error("查询所有班次异常", e);
		}
		return null;
	}
	/**
	 * 跳转到新增班次页面
	 */
	@RequestMapping("/goToShiftAddJsp")
	public String goToShiftAddJsp(String id){
		return "/pms/md/shift/shiftAdd";
	}
	/**
	 * 新增班次
	 */
	@ResponseBody
	@RequestMapping("/addShift")
	public Json addShift(ShiftBean shiftBean){
		Json json=new Json();
		try {
			shiftService.addShift(shiftBean);
			json.setMsg("新增班次成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增班次失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到班次编辑页面
	 */
	@RequestMapping("/goToShiftEditJsp")
	public String goToShiftEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("shift", shiftService.getShiftById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的班次失败", e);
		}
		return "/pms/md/shift/shiftEdit";
	}
	/**
	 * 编辑班次
	 */
	@ResponseBody
	@RequestMapping("/editShift")
	public Json editShift(ShiftBean shiftBean){
		Json json=new Json();
		try {
			shiftService.editShift(shiftBean);
			json.setMsg("编辑班次成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑班次失败!");
		}
		return json;
	}
	/**
	 * 删除班次
	 */
	@ResponseBody
	@RequestMapping("/deleteShift")
	public Json deleteShift(String id){
		Json json=new Json();
		try {
			shiftService.deleteShift(id);
			json.setMsg("删除班次成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除班次失败!");
		}
		return json;
	}
}
