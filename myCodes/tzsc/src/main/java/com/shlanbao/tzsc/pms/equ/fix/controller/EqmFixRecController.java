package com.shlanbao.tzsc.pms.equ.fix.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmFixRec;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.fix.beans.EqmFixRecBean;
import com.shlanbao.tzsc.pms.equ.fix.service.EqmFixRecServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;

/**
 * 设备维修记录
 * @author yangbo
 *
 */
@Controller
@RequestMapping("/pms/fixrec")
public class EqmFixRecController extends BaseController{
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	protected EqmFixRecServiceI eqmFixRecService;
	@RequestMapping("/gotoFixRec")
	public String gotoFixRec(){
		return "/pms/equ/fix/fixrec";
	}
	
	@RequestMapping("/gotoFixRecEdit")
	public String gotoFixRecEdit(String id,HttpServletRequest request){
		try {
			if(StringUtil.notNull(id))
				request.setAttribute("fixRecBean", eqmFixRecService.getFixRecById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/equ/fix/fixrecEdit";
	}
	@RequestMapping("/gotoFixRecAdd")
	public String gotoFixRecAdd(){
		return "/pms/equ/fix/fixrecAdd";
	}
	
	@ResponseBody
	@RequestMapping("/addFixRec")
	public Json addFixRec(EqmFixRecBean eqmFixRecBean){
		Json json = new Json();
		try {
			eqmFixRecService.addFixRec(eqmFixRecBean);
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
	@RequestMapping("/queryFixRec")
	public DataGrid queryFixRec(EqmFixRecBean eqmFixRecBean,PageParams pageParams){
		try {
			DataGrid gd = eqmFixRecService.queryFixRec(eqmFixRecBean, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询设备维修记录失败!", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/deleteFixRec")
	public Json deleteFixRec(String id){
		Json json = new Json();
		try {
			eqmFixRecService.deleteFixRec(id);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
