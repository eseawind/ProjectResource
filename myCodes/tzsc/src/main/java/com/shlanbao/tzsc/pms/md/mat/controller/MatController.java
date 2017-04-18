package com.shlanbao.tzsc.pms.md.mat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.mat.beans.MatBean;
import com.shlanbao.tzsc.pms.md.mat.service.MatServiceI;
/**
 * 物料主数据
 * @author Leejean
 * @create 2014年11月25日下午1:22:01
 */
@Controller
@RequestMapping("/pms/mat")
public class MatController extends BaseController {
	@Autowired
	private MatServiceI matService;
	@ResponseBody
	@RequestMapping("/getAllMats")
	public DataGrid getAllMats(MatBean matBean,PageParams pageParams){
		try {
			return matService.getAllMats(matBean,pageParams);
		} catch (Exception e) {
			log.error("查询所有物料异常", e);
		}
		return null;
	}
	@RequestMapping("/goToMatAddJsp")
	public String goToMatAddJsp(String id){
		return "/pms/md/mat/matAdd";
	}
	@ResponseBody
	@RequestMapping("/addMat")
	public Json addMat(MatBean matBean){
		Json json=new Json();
		try {
			matService.addMat(matBean);
			json.setMsg("新增物料成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增物料失败!");
			json.setSuccess(false);
		}
		return json;
	}
	@RequestMapping("/goToMatEditJsp")
	public String goToMatEditJsp(HttpServletRequest request,String id){
		try {
			request.setAttribute("mat", matService.getMatById(id));
		} catch (Exception e) {
			log.error("获取ID:"+id+"的物料失败", e);
		}
		return "/pms/md/mat/matEdit";
	}
	@ResponseBody
	@RequestMapping("/editMat")
	public Json editMat(MatBean matBean){
		Json json=new Json();
		try {
			matService.editMat(matBean);
			json.setMsg("编辑物料成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("编辑物料失败!");
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/deleteMat")
	public Json deleteMat(String id){
		Json json=new Json();
		try {
			matService.deleteMat(id);
			json.setMsg("删除物料成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("删除物料失败!");
		}
		return json;
	}
}
