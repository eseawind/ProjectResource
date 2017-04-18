package com.shlanbao.tzsc.pms.cos.spare.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shlanbao.tzsc.base.dao.CosSparePartsDaoI;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.spare.beans.CosSparePartsBean;
import com.shlanbao.tzsc.pms.cos.spare.service.CosSparePartsServiceI;

@Controller
@RequestMapping("/pms/spare")
public class CosSparePartsController {
	@Autowired
	public CosSparePartsServiceI cosSparePartsServiceI;
	@RequestMapping("/gotoUpdateBean")
	public String gotoUpdateBean(HttpServletRequest request,String id) throws Exception{
		request.setAttribute("bean", cosSparePartsServiceI.getBeanById(id));
		request.setAttribute("id", "i");
		return "/pms/equ/spare/editSpareParts";
	}
	//WCT备品备件查询
	@ResponseBody
	@RequestMapping("/queryData")
	public DataGrid queryDataGrid(CosSparePartsBean bean,PageParams pageParams) throws Exception{
		
		return cosSparePartsServiceI.queryGridByBean(bean, pageParams);
	}
	
	//PMS备品备件查询
	@ResponseBody
	@RequestMapping("/queryDataPMS")
	public DataGrid queryDataGridPMS(CosSparePartsBean bean,PageParams pageParams) throws Exception{
		
		return cosSparePartsServiceI.queryGridByBeanPMS(bean, pageParams);
	}
	
	@ResponseBody
	@RequestMapping("/updateBean")
	public Json updateBean(CosSparePartsBean bean) throws Exception{
		Json json=new Json();
		try{
			if(cosSparePartsServiceI.saveOrUpdateBean(bean)){
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}
		}catch(Exception e){
			json.setMsg("操作失败!");
			json.setSuccess(false);
			return json;
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/addBean")
	public Json addBean(CosSparePartsBean bean,PageParams pageParams) throws Exception{
		Json json=new Json();
		try{
			if(cosSparePartsServiceI.saveOrUpdateBean(bean)){
				json.setMsg("操作成功!");
				json.setSuccess(true);
			}
		}catch(Exception e){
			json.setMsg("操作失败!");
			json.setSuccess(false);
			return json;
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/deleteBean")
	public Json deleteBean(String id) throws Exception{
		Json json=new Json();
		try{
			cosSparePartsServiceI.deleteById(id);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		}catch(Exception e){
			json.setMsg("操作失败!");
			json.setSuccess(false);
			return json;
		}
		return json;
	}
	
	/**
	 * 【功能说明】：备品备件批量导入功能- 跳转导入选择界面
	 * @author wchuang
	 * @time 2015年8月2日14:50:16
	 * @param id
	 * @param request
	 * @return path -页面路径
	 * 
	 * */
	@RequestMapping("/inputExcelDatas")
	public String inputExcelDatas(HttpServletRequest request,String id) {
		return "/pms/equ/spare/iframe_excel";
	}
	
	/**
	 * 【功能说明】：备品备件批量导入功能- 添加解析excel
	 * @author wchuang
	 * @time 2015年8月2日14:50:16
	 * @param id
	 * @param request
	 * @return path -页面路径
	 * 
	 * */
	@RequestMapping("/inputExeclAndReadWrite")
	@ResponseBody
	 public Json inputExeclAndReadWrite(@RequestParam(value = "importFile", required = false)
	                                       MultipartFile file,HttpServletRequest request, CosSparePartsBean cspb) {
		    Json json=new Json();   
			try {
				String path = request.getSession().getServletContext().getRealPath("upload");
				String fileName = file.getOriginalFilename();
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
				    targetFile.mkdirs();
				}
				try {
				    file.transferTo(targetFile);
				} catch (Exception e) {
				    e.printStackTrace();
				}
				String url = path + "/" + fileName;
				String bm=fileName.substring( (fileName.lastIndexOf(".")+1) ,fileName.length());
				List<CosSparePartsBean> list =null;
				InputFilesController fl=new InputFilesController();
				if("xls".equals(bm)){ 
					list=fl.readXls2003(url);
				}else if("xlsx".equals(bm)){ 
					list=fl.readXlsx2007(url);
				}
				if(list!=null&&list.size()>0){
					 cosSparePartsServiceI.batchInputExeclAndReadWrite(list,cspb);
					 json.setMsg("导入成功!");
					 json.setSuccess(true);
				}
			} catch (Exception e) {
				json.setMsg("导入失败!");
	        	 json.setSuccess(false);
				e.printStackTrace();
			}
			return json;
	}


}
