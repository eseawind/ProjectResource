package com.shlanbao.tzsc.pms.cos.param.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.param.beans.CosMatParamBean;
import com.shlanbao.tzsc.pms.cos.param.service.CosMatParamServiceI;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeBean;


@Controller
@RequestMapping("/pms/param")
public class CosMatParamController extends BaseController {
	@Autowired
	private CosMatParamServiceI cosMatParamService;
	
	@RequestMapping("/gotoCosMat")
	private String gotoCosMat(){
		return "/pms/cos/param/addMat";
	}
	
	@RequestMapping("/gotoEditMat")
	public String goToCheckWCPlan(HttpServletRequest request,String id){
		try {
			request.setAttribute("cosMatBean",cosMatParamService.getCosMatById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/cos/param/editMat";
	}
	
	// 辅料新增
	@ResponseBody
	@RequestMapping("/addCosMat")
	public Json addCosMat(CosMatParamBean cosMatParamBean){
		Json json = new Json();
		try {
			cosMatParamService.addCosMat(cosMatParamBean);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	//辅料编辑
	@ResponseBody
	@RequestMapping("/editCosMat")
	public Json editCosMat(CosMatParamBean cosMatParamBean){
		Json json = new Json();
		try {
			cosMatParamService.editCosMat(cosMatParamBean);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/queryMdType")
	public DataGrid queryMdType(MdEqpTypeBean mdTypeBean,PageParams pageParams){
		try {
			DataGrid gd = cosMatParamService.queryMdType(mdTypeBean, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询设备型号异常。", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/queryCosMat")
	public DataGrid queryCosMat(CosMatParamBean cosMatParamBean,PageParams pageParams,HttpServletRequest request){
		try {
			cosMatParamBean.setMdEqpTypeId(String.valueOf(request.getParameter("eqpId")));
			return cosMatParamService.queryCosMat(cosMatParamBean, pageParams);
		} catch (Exception e) {
			log.error("查询设备型号异常。", e);
		}
		return null;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/deleteCosMat")
	public Json deleteCosMat(String id){
		Json json = new Json();
		try {
			cosMatParamService.deleteCosMat(id);
			json.setMsg("删除设备主数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setMsg("删除设备主数据失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
}
