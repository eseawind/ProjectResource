package com.shlanbao.tzsc.pms.sys.datadict.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.mapping.DocFilemanage;
import com.shlanbao.tzsc.base.mapping.SysEqpCategory;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpCategoryBean;
import com.shlanbao.tzsc.pms.sys.datadict.service.SysEqpCategoryServiceI;

/**
 * 数据字典小类维护
 */
@Controller
@RequestMapping("/pms/sys/eqpcategory")
public class SysEqpCategoryController {
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	protected SysEqpCategoryServiceI mdEqpCategoryService;
	@RequestMapping("/gotoMdCategoryJsp")
	public String gotoMdCategory(String id,HttpServletRequest request){
		try {
			request.setAttribute("mdCategory", mdEqpCategoryService.getMdCategoryById(id));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "/pms/sys/datadict/editCategory";
	}
	@RequestMapping("/addMdCategoryJsp")
	public String addMdCategory(){
		return "/pms/sys/datadict/addMdCategory";
	}
	
	@ResponseBody
	@RequestMapping("/addMdCategory")
	public Json addMdCategory(SysEqpCategory mdEqpCategory){
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
	public Json updateCategory(SysEqpCategoryBean mdEqpCategoryBean){
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
	public DataGrid queryMdCategory(SysEqpCategory mdEqpCategory,PageParams pageParams){
		try {
			DataGrid gd = mdEqpCategoryService.queryMdCategory(mdEqpCategory, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询异常", e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询，供combobox使用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryMdEqpCategory")
	public List<SysEqpCategoryBean> queryMdEqpCategory(){
		try {
			return mdEqpCategoryService.queryMdCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping("/uploadJsp")
	public String goTofileUploadJsp(HttpServletRequest request){
		return "/pms/sys/datadict/fileUpload";
	}
	/**
	 * 批量导入日润滑数据
	 * @param request
	 * @return
	 */
	
	@RequestMapping("/DatadictinputExcel")
	public String DatadictinputExcel(HttpServletRequest request){
		return "/pms/sys/datadict/iframe_excel";
	}
	
	
	/**
	 * 文件上传
	 */
	@ResponseBody
	@RequestMapping("/updateFile")
	public Json fileUpdate(String id,HttpSession session,
			 HttpServletRequest request,SysEqpCategoryBean fmBean) {
		Json json = new Json();
		try {
			DocFilemanage doc = mdEqpCategoryService.fileUpdate(id,session,request,fmBean);
			if(null!=doc){//回写
				mdEqpCategoryService.updateCategory(doc);
			}
			json.setMsg("文件上传成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("文件上传异常", e);
			json.setMsg("文件上传失败！");
			json.setSuccess(false);
		}
		return json;
	}
	
	@RequestMapping("/lookMdCategoryJsp")
	public String lookMdCategoryJsp(String uploadUrl,HttpServletRequest request){
		try {
			SysEqpCategoryBean cate = new SysEqpCategoryBean();
			cate.setUploadUrl(uploadUrl);
			request.setAttribute("mdCategory", cate);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "/pms/sys/datadict/lookMdCategory";
	}
	
}
