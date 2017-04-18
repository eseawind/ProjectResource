package com.shlanbao.tzsc.pms.md.eqptype.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.mapping.MdEqpCategory;
import com.shlanbao.tzsc.base.mapping.MdEqpType;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.eqpcategory.service.MdEqpCategoryServiceI;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeBean;
import com.shlanbao.tzsc.pms.md.eqptype.service.MdEqpTypeServiceI;
import com.shlanbao.tzsc.utils.tools.SystemConstant;


/**
 * 设备类型与型号维护
 * @author liuligong 
 * @Time 2014/11/17 9:49
 */
@Controller
@RequestMapping("/pms/md/eqptype")
public class MdEqpTypeController {
	protected Logger log = Logger.getLogger(this.getClass());
		
		@Autowired
	private MdEqpTypeServiceI mdEqpTypeService;
		@Autowired
		private MdEqpCategoryServiceI mdEqpCategoryService;
	@RequestMapping("/goToMdTypeJsp")
	public String goToMdType(HttpServletRequest request,String id){
		if(null != id){
			try {
				request.setAttribute("mdEqpTypeBean",mdEqpTypeService.getTypeById(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "/pms/md/eqptype/addMdType";
	}
	@RequestMapping("/AddMdTypeJsp")
	public String goToMdType(String cateid,HttpServletRequest request) throws Exception{
		MdEqpCategory category= mdEqpCategoryService.getMdCategoryById(cateid);
		MdEqpTypeBean eqpTypeBean=new MdEqpTypeBean();
		eqpTypeBean.setCategoryId(category.getId());
		eqpTypeBean.setCategoryName(category.getName());
		request.setAttribute("mdEqpTypeBean",eqpTypeBean);
		return "/pms/md/eqptype/addMdType";
	}
	// 设备类型新增
	@ResponseBody
	@RequestMapping("/addMdType")
	public Json addMdType(MdEqpTypeBean mdEqpTypeBean){
		Json json = new Json();
		try {
			mdEqpTypeService.addMdType(mdEqpTypeBean);
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
			DataGrid gd = mdEqpTypeService.queryMdType(mdTypeBean, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询设备型号异常。", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/queryMdEqpType")
	public List<MdEqpTypeBean> queryMdEqpType(){
		try {
			return mdEqpTypeService.queryMdType(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@ResponseBody
	@RequestMapping("/queryMdEqpTypeByCid")
	public List<MdEqpTypeBean> queryMdEqpTypeByCid(String id,HttpServletRequest request){
		try {
			return mdEqpTypeService.queryMdType(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@ResponseBody
	@RequestMapping("/deleteMdType")
	public Json deleteMdType(String id){
		Json json = new Json();
		try {
			mdEqpTypeService.deleteMdType(id);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("删除设备型号异常。", e);
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	//获取卷烟机、包装机和成型机的机型
	@ResponseBody
	@RequestMapping("/queryMdEqpTypeByCategory")
	public List<MdEqpTypeBean> queryMdEqpTypeByCategory(){
		try {
			String[] categorys=new String[3];
			categorys[0]=SystemConstant.CATEGORY_JY_ID;
			categorys[1]=SystemConstant.CATEGORY_BZ_ID;
			categorys[2]=SystemConstant.CATEGORY_CX_ID;
			return mdEqpTypeService.queryMdEqpTypeByCategory(categorys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
