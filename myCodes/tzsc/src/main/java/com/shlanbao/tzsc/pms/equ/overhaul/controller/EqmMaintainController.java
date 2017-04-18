package com.shlanbao.tzsc.pms.equ.overhaul.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.EqmMaintain;
import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.spare.controller.InputFilesController;
import com.shlanbao.tzsc.pms.equ.overhaul.beans.EqmMaintainBean;
import com.shlanbao.tzsc.pms.equ.overhaul.service.EqmMaintainServiceI;
import com.shlanbao.tzsc.pms.sys.datadict.beans.SysEqpTypeBean;
import com.shlanbao.tzsc.utils.params.SysEqpTypeBase;

/**
 * 设备检修项目维护控制器 包括添加，编辑，删除，查询设备项目维护
 * @author liuligong
 *
 */
@Controller
@RequestMapping("/pms/overhaul")
public class EqmMaintainController extends BaseController{
	@Autowired
	protected EqmMaintainServiceI eqmMaintainService;
	protected SysEqpTypeBase sysEqpTypeBase;
	
	@RequestMapping("/gotoMaintain")
	public String gotoMaintain()throws Exception{
		return "/pms/equ/overhaul/maintain";
	}
	
	@RequestMapping("/gotoMaintainAdd")
	public String gotoMaintainAdd()throws Exception{
		return "/pms/equ/overhaul/maintainAdd";
	}
	
	@ResponseBody
	@RequestMapping("/addMaintain")
	public Json addMaintain(EqmMaintainBean eqmMaintain)throws Exception{
		Json json = new Json();
		try {
			eqmMaintainService.addEqmMaintain(eqmMaintain);
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
	@RequestMapping("/deleteMaintain")
	public Json deleteMaintain(String id)throws Exception{
		Json json = new Json();
		try {
			eqmMaintainService.deleteEqmMaintainById(id);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	@RequestMapping("/gotoMaintainEdit")
	public String gotoMaintainEdit(String id,HttpServletRequest request)throws Exception{
		request.setAttribute("bean", eqmMaintainService.getEqmMaintainById(id));
		return "/pms/equ/overhaul/maintainEdit";
	}
	@RequestMapping("/gotoMaintainEditSlove")
	public String gotoMaintainEditSlove(String id,HttpServletRequest request)throws Exception{
		request.setAttribute("bean", eqmMaintainService.getEqmMaintainById(id));
		return "/pms/equ/overhaul/maintainEditSolve";
	}
	@ResponseBody
	@RequestMapping("/editMaintain")
	public Json editMaintain(EqmMaintainBean eqmMaintain)throws Exception{
		Json json = new Json();
		try {
			eqmMaintainService.editEqmMaintain(eqmMaintain);
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
	@RequestMapping("/queryMaintain")
	public DataGrid queryMaintain(EqmMaintainBean eqmMaintain,PageParams pageParams)throws Exception{
		try {
			DataGrid gd = eqmMaintainService.queryEqmMaintain(eqmMaintain, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询设备检修项目数据异常。", e);
		}
		return null;
	}
	/**
	 * 导入excel
	 * @param file
	 * @param request
	 * @param type
	 * @param etb
	 * @return
	 */
	@RequestMapping("/inputExeclAndReadWrite")
	@ResponseBody
	 public Json inputExeclAndReadWrite(@RequestParam(value = "importFile", required = false)
	                                       MultipartFile file,HttpServletRequest request) {
			Json json = new Json();
			try {
			String path = request.getSession().getServletContext().getRealPath("upload");
	        String fileName = file.getOriginalFilename();
	        File targetFile = new File(path, fileName);
	        if (!targetFile.exists()) {
	            targetFile.mkdirs();
	        }
	            file.transferTo(targetFile);
	        String url = path + "/" + fileName;
	        String bm=fileName.substring( (fileName.lastIndexOf(".")+1) ,fileName.length());
	        List<EqmMaintainBean> list =null;
	        InputFilesController fl=new InputFilesController();
	        if("xls".equals(bm)){ 
	        	list=fl.readXls2003_3(url);
	        }else if("xlsx".equals(bm)){ 
	        	list=fl.readXlsx2007_3(url);
	        }
	        if(list!=null&&list.size()>0){
	        		eqmMaintainService.inputExeclAndReadWrite(list);
					json.setMsg("导入成功！");
					json.setSuccess(true);
	        	}
			} catch (Exception e) {
				json.setMsg("导入失败！");
				json.setSuccess(false);
				e.printStackTrace();
			}
	     return json;   
	}
	/**
	 * 更新备件的数量信息
	 * 
	 */
	@RequestMapping("/updateSpareParts")
	@ResponseBody
	public void updateSpareParts(String all_id,String use_n,String all_num){
		eqmMaintainService.updateSpareParts(all_id, use_n,all_num);
	}
	
	//润滑部位下拉框
		@RequestMapping("/combobox")
		@ResponseBody
		public List<Combobox> combobox(String equipmentId){
			Map<String, String> retMap;
				Object o[] = code(equipmentId);
				String code=o[0].toString();
				String name=o[1].toString();
				try {
					retMap = sysEqpTypeBase.getDJBoxRoleAlls(code,name);
					List<Combobox> combox = new ArrayList<Combobox>();
					for (Map.Entry<String, String> entry : retMap.entrySet()) {
							Combobox box = new Combobox();
							box.setId(entry.getValue());
							box.setName(entry.getKey());
						    System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
						    combox.add(box);
						  }
					return combox;
					//String jsonf=JSONArray.fromObject(retMap).toString();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return null;
		}
		
		//根据设备ID查询code和设备NAME
			@RequestMapping("/code")
			@ResponseBody
			public Object[] code(String equipmentId){
				return eqmMaintainService.loadToRhBuWeiCode(equipmentId);
			}
}
