package com.shlanbao.tzsc.pms.sch.shiftexchg.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sch.shiftexchg.beans.ShiftExchgBean;
import com.shlanbao.tzsc.pms.sch.shiftexchg.beans.ShiftExchgDetBean;
import com.shlanbao.tzsc.pms.sch.shiftexchg.service.ShiftExchgServiceI;
/**
 * 换班换牌控制器
 * @author Leejean
 * @create 2014年11月18日下午2:23:51
 */
@Controller
@RequestMapping("/pms/shiftexchg")
public class ShiftExchgController extends BaseController {
	@Autowired
	private ShiftExchgServiceI shiftExchgService;
	/**
	 * 查询所有换班换牌
	 */
	@RequestMapping("/getExchgs")
	@ResponseBody
	public DataGrid getExchgs(ShiftExchgBean shiftExchgBean,PageParams pageParams){
		return shiftExchgService.getExchgs(shiftExchgBean, pageParams);
	}
	/**
	 * 获得明细集合
	 */
	@RequestMapping("/getExchgDetsByExchgId")
	@ResponseBody
	public DataGrid getExchgDetsByExchgId(String id){
		try {
			return shiftExchgService.getExchgDetsByExchgId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除换班
	 */
	@ResponseBody
	@RequestMapping("/deleteExchg")
	public Json deleteExchg(String id){
		Json json=new Json();
		try {
			shiftExchgService.deleteExchg(id);
			json.setMsg("删除成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 编辑换班
	 */
	@ResponseBody
	@RequestMapping("/editExchg")
	public Json editExchg(ShiftExchgBean shiftExchgBean){
		Json json=new Json();
		try {
			shiftExchgService.editExchg(shiftExchgBean);
			json.setMsg("修改成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("修改失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到新增换班页面
	 */
	@RequestMapping("/goToExchgAddJsp")
	public String goToExchgAddJsp(HttpServletRequest request,String workshop,String type){
		request.setAttribute("workshop", workshop);
		request.setAttribute("type", type);
		return "/pms/sch/shiftexchg/exchgAdd";
	}
	/**
	 * 跳转到编辑换班页面
	 */
	@RequestMapping("/goToExchgEditJsp")
	public String goToExchgEditJsp(HttpServletRequest request,String id,String type) throws Exception{
		request.setAttribute("exchg", shiftExchgService.getExchgById(id));
		request.setAttribute("type", type);
		return "/pms/sch/shiftexchg/exchgEdit";
	}
	/**
	 * 跳转到换班明细新增页面
	 */
	@RequestMapping("/goToDetAddJsp")
	public String goToDetAddJsp(HttpServletRequest request,String hoOrder,String id) throws Exception{
		request.setAttribute("workorder", hoOrder);
		request.setAttribute("exchg", id);
		request.setAttribute("boms",JSON.toJSONString(shiftExchgService.getBomsWorkorderId(hoOrder,id)));
		return "/pms/sch/shiftexchg/detAdd";
	}
	/**
	 * 跳转到换班明细编辑页面
	 */
	@RequestMapping("/goToDetEditJsp")
	public String goToDetEditJsp(HttpServletRequest request,String id) throws Exception{
		request.setAttribute("det", shiftExchgService.getExchgDetById(id));
		return "/pms/sch/shiftexchg/detEdit";
	}
	/**
	 * 新增换班
	 */
	@ResponseBody
	@RequestMapping("/addExchg")
	public Json addExchg(ShiftExchgBean shiftExchgBean){
		Json json=new Json();
		try {
			shiftExchgService.addExchg(shiftExchgBean);
			json.setMsg("新增成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 编辑换班明细
	 */
	@ResponseBody
	@RequestMapping("/editExchgDet")
	public Json editExchgDet(ShiftExchgDetBean shiftExchgDetBean){
		Json json=new Json();
		try {
			shiftExchgService.editExchgDet(shiftExchgDetBean);
			json.setMsg("修改成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("修改失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 新增换班明细
	 */
	@ResponseBody
	@RequestMapping("/addExchgDet")
	public Json addExchgDet(ShiftExchgDetBean shiftExchgDetBean){
		Json json=new Json();
		try {
			shiftExchgService.addExchgDet(shiftExchgDetBean);
			json.setMsg("新增成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 *删除换班明细
	 */ 
	@ResponseBody
	@RequestMapping("/deleteExchgDet")
	public Json deleteExchgDet(String id){
		Json json=new Json();
		try {
			shiftExchgService.deleteExchgDet(id);
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
