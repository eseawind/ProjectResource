package com.shlanbao.tzsc.pms.sch.workorder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.sch.workorder.beans.BomBean;
import com.shlanbao.tzsc.pms.sch.workorder.service.BomServiceI;
/**
 * BOM制器
 * @author Leejean
 * @create 2014年11月18日下午2:23:51
 */
@Controller
@RequestMapping("/pms/bom")
public class BomController extends BaseController {
	@Autowired
	private BomServiceI bomService;
	/**
	 * 根据工单获得bom明细
	 */
	@ResponseBody
	@RequestMapping("/getBomByWorkOrder")
	public DataGrid getBomByWorkOrder(String workOrderId){
		try {
			return bomService.queryBomByWorkOrder(workOrderId);
		} catch (Exception e) {
			log.error("获取工单["+workOrderId+"]物料清单异常",e);
		}
		return null;
	}
	/**
	 * 跳转到bom新增页面
	 */
	@RequestMapping("/goToBomAddJsp")
	public String goToBomAddJsp(HttpServletRequest request, String orderId){
		request.setAttribute("orderId", orderId);
		return "/pms/sch/bom/bomAdd";
	}
	/**
	 * 新增bom
	 */
	@ResponseBody
	@RequestMapping("/addBom")
	public Json addBom(BomBean bomBean){
		Json json = new Json();
		try {
			bomService.addBom(bomBean);
			json.setSuccess(true);
			json.setMsg("新增成功!");
		} catch (Exception e) {
			log.error("新增失败", e);
			json.setMsg("新增失败!");
		}
		return json;
	}
	/**
	 * 跳转到bom编辑页面
	 */
	@RequestMapping("/goToBomEditJsp")
	public String goToBomEditJsp(HttpServletRequest request, String id){
		request.setAttribute("bom", bomService.getBomById(id));
		return "/pms/sch/bom/bomEdit";
	}
	/**
	 * 编辑bom
	 */
	@ResponseBody
	@RequestMapping("/editBom")
	public Json editBom(BomBean bomBean){
		Json json = new Json();
		try {
			bomService.editBom(bomBean);
			json.setSuccess(true);
			json.setMsg("编辑成功!");
		} catch (Exception e) {
			log.error("编辑失败", e);
			json.setMsg("编辑失败!");
		}
		return json;
	}
	/**
	 * 删除bom
	 */
	@ResponseBody
	@RequestMapping("/deleteBom")
	public Json deleteBom(String id){
		Json json = new Json();
		try {
			bomService.deleteBom(id);
			json.setSuccess(true);
			json.setMsg("删除成功!");
		} catch (Exception e) {
			log.error("删除失败", e);
			json.setMsg("删除失败!");
		}
		return json;
	}
}
