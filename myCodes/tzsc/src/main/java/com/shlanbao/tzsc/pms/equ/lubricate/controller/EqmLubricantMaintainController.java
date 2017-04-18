package com.shlanbao.tzsc.pms.equ.lubricate.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmLubricantMaintain;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.lubricate.beans.EquLubricantMaintainBean;
import com.shlanbao.tzsc.pms.equ.lubricate.service.EqmLubricantMaintainServiceI;

/**
 * 润滑剂维护
 * @author liuligong
 *
 */
@Controller
@RequestMapping("/pms/equ/lubricant")
public class EqmLubricantMaintainController extends BaseController{
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	protected EqmLubricantMaintainServiceI equLubricantMaintainService;
	
	@RequestMapping("/gotoLubricant")
	public String gotoLubricate()throws Exception{
		return "/pms/equ/lubri/lubricant";
	}
	
	@RequestMapping("/gotoLubricantAdd")
	public String gotoLubriAdd()throws Exception{
		return "/pms/equ/lubri/lubricantAdd";
	}
	
	/**
	 * 设备润滑剂新增
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/addLubricant")
	public Json addLubricant(EqmLubricantMaintain equLubricantMaintain)throws Exception{
		Json json = new Json();
		try {
			equLubricantMaintainService.addLubricate(equLubricantMaintain);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 设备润滑列表数据查询
	 * @param object
	 * @param pageParams
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryLubricant")
	public DataGrid queryLubricant(EqmLubricantMaintain equLubricantMaintain,PageParams pageParams)throws Exception{
		try {
			DataGrid gd = equLubricantMaintainService.queryLubricate(equLubricantMaintain, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询设备润滑剂数据异常。", e);
		}
		return null;
	}
	/**
	 * 获取所有的润滑剂
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryAllLubricant")
	public List<EquLubricantMaintainBean> queryAllLubricant(String type,HttpServletRequest request)throws Exception{
		List<EquLubricantMaintainBean> list = equLubricantMaintainService.queryAllLubricant(type);
		return list;
	}
	/**
	 * 根据ID获取对应的集合
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryListById")
	public List<EquLubricantMaintainBean> queryListById(String key,String type,HttpServletRequest request)throws Exception{
		List<EquLubricantMaintainBean> list = equLubricantMaintainService.queryListById(key,type);
		return list;
	}
	
	@RequestMapping("/goToLubricantEdit")
	public String goToLubricantEdit(HttpServletRequest request,String id)throws Exception{
		try {
			request.setAttribute("lubircant",equLubricantMaintainService.getLubricateById(id));
		} catch (Exception e) {
			log.error("编辑设备润滑剂数据异常:",e);
		}
		return "/pms/equ/lubri/lubricantEdit";
	}
	
	@ResponseBody
	@RequestMapping("/deleteLubricant")
	public Json deleteLubricant(String id)throws Exception{
		Json json = new Json();
		try {
			equLubricantMaintainService.deleteLuricateById(id);
			json.setMsg("删除设备润滑剂数据成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(message,e);
			json.setMsg("删除设备润滑剂数据失败!");
			json.setSuccess(false);
		}
		return json;
	}
}
