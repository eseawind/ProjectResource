package com.shlanbao.tzsc.pms.md.eqpcategory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.mapping.MdEqpCategory;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.eqpcategory.beans.MdEqpCategoryBean;
import com.shlanbao.tzsc.pms.md.eqpcategory.service.MdEqpCategoryServiceI;


/**
 * 设备类型维护
 * @author liuligong 
 * @Time 2014/11/17 9:49
 */
@Controller
@RequestMapping("/pms/md/eqpcategory")
public class MdEqpCategoryController {
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	protected MdEqpCategoryServiceI mdEqpCategoryService;
	
	@RequestMapping("/gotoMdCategoryJsp")
	public String gotoMdCategory(String id,HttpServletRequest request){
		try {
			request.setAttribute("mdCategory", mdEqpCategoryService.getMdCategoryById(id));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "/pms/md/eqpcategory/editCategory";
	}
	@RequestMapping("/addMdCategoryJsp")
	public String addMdCategory(){
		return "/pms/md/eqpcategory/addMdCategory";
	}
	
	@ResponseBody
	@RequestMapping("/addMdCategory")
	public Json addMdCategory(MdEqpCategory mdEqpCategory){
		Json json = new Json();
		try {
			mdEqpCategoryService.addMdCategory(mdEqpCategory);
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
	@RequestMapping("/updatecategory")
	public Json updateCategory(MdEqpCategoryBean mdEqpCategoryBean){
		Json json = new Json();
		try {
			mdEqpCategoryService.updateCategory(mdEqpCategoryBean);
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
	@RequestMapping("/deleteMdCategory")
	public Json deleteMdCategory(String id){
		Json json = new Json();
		try {
			mdEqpCategoryService.deleteMdCategory(id);
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
	@RequestMapping("/queryMdCategory")
	public DataGrid queryMdCategory(MdEqpCategory mdEqpCategory,PageParams pageParams){
		try {
			DataGrid gd = mdEqpCategoryService.queryMdCategory(mdEqpCategory, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询设备类型失败", e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询设备类型列表，供combobox使用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryMdEqpCategory")
	public List<MdEqpCategoryBean> queryMdEqpCategory(){
		try {
			return mdEqpCategoryService.queryMdCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
