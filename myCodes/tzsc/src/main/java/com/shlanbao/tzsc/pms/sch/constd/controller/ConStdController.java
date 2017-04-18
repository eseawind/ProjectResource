package com.shlanbao.tzsc.pms.sch.constd.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sch.constd.beans.ConStdBean;
import com.shlanbao.tzsc.pms.sch.constd.service.ConStdServiceI;
/**
 * 标准单耗
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/constd")
public class ConStdController extends BaseController {
	@Autowired
	public ConStdServiceI conStdService;
	/**
	 * 查询所有标准单耗
	 */
	@ResponseBody
	@RequestMapping("/getAllConStds")
	public DataGrid getAllConStds(ConStdBean ConStdBean,PageParams pageParams){
		try {
			return conStdService.getConStds(ConStdBean,pageParams);
		} catch (Exception e) {
			log.error("查询所有标准单耗异常", e);
		}
		return null;
	}
	/**
	 * 跳转到标准单耗新增页面
	 */
	@RequestMapping("/goToConStdAddJsp")
	public String goToConStdAddJsp(String id){
		return "/pms/sch/constd/constdAdd";
	}
	/**
	 * 新增标准单耗
	 */
	@ResponseBody
	@RequestMapping("/addConStd")
	public Json addConStd(ConStdBean ConStdBean){
		Json json=new Json();
		try {
			conStdService.addConStd(ConStdBean);
			json.setMsg("新增标准单耗成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增标准单耗失败!");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 跳转到标准单耗编辑页面
	 */
	@RequestMapping("/goToConStdEditJsp")
	public String goToConStdEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("constd", conStdService.getConStdById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的标准单耗失败", e);
		}
		return "/pms/sch/constd/constdEdit";
	}
	/**
	 * 编辑标准单耗
	 */
	@ResponseBody
	@RequestMapping("/editConStd")
	public Json editConStd(ConStdBean ConStdBean){
		Json json=new Json();
		try {
			conStdService.editConStd(ConStdBean);
			json.setMsg("编辑标准单耗成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑标准单耗失败!");
		}
		return json;
	}
	/**
	 * 生产标准单耗
	 */
	@ResponseBody
	@RequestMapping("/deleteConStd")
	public Json deleteConStd(String id){
		Json json=new Json();
		try {
			conStdService.deleteConStd(id);
			json.setMsg("删除标准单耗成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除标准单耗失败!");
		}
		return json;
	}
}
