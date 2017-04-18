package com.shlanbao.tzsc.pms.md.workshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.pms.md.workshop.beans.WorkShopBean;
import com.shlanbao.tzsc.pms.md.workshop.service.WorkShopServiceI;
/**
 * 车间
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/workshop")
public class WorkShopController extends BaseController {	
	@Autowired
	public WorkShopServiceI workShopService;
	/**
	 * 查询所有车间
	 */
	@ResponseBody
	@RequestMapping("/getAllWorkShops")
	public DataGrid getAllWorkShops(WorkShopBean workShopBean){
		try {
			return workShopService.getAllWorkShops(workShopBean);
		} catch (Exception e) {
			log.error("查询所有车间异常", e);
		}
		return null;
	}
	/**
	 * 跳转到车间新增页面
	 */
	@RequestMapping("/goToWorkShopAddJsp")
	public String goToWorkShopAddJsp(String id){
		return "/pms/md/workshop/workshopAdd";
	}
	/**
	 * 新增车间
	 */
	@ResponseBody
	@RequestMapping("/addWorkShop")
	public Json addWorkShop(WorkShopBean workShopBean){
		Json json=new Json();
		try {
			workShopService.addWorkShop(workShopBean);
			json.setMsg("新增车间成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增车间失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到车间编辑页面
	 */
	@RequestMapping("/goToWorkShopEditJsp")
	public String goToWorkShopEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("workshop", workShopService.getWorkShopById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的车间失败", e);
		}
		return "/pms/md/workshop/workshopEdit";
	}
	/**
	 * 编辑车间
	 */
	@ResponseBody
	@RequestMapping("/editWorkShop")
	public Json editWorkShop(WorkShopBean workShopBean){
		Json json=new Json();
		try {
			workShopService.editWorkShop(workShopBean);
			json.setMsg("编辑车间成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑车间失败!");
		}
		return json;
	}
	/**
	 * 删除车间
	 */
	@ResponseBody
	@RequestMapping("/deleteWorkShop")
	public Json deleteWorkShop(String id){
		Json json=new Json();
		try {
			workShopService.deleteWorkShop(id);
			json.setMsg("删除车间成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除车间失败!");
		}
		return json;
	}
	
}
