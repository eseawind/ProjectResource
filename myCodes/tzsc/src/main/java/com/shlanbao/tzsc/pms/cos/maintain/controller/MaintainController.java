package com.shlanbao.tzsc.pms.cos.maintain.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.mapping.CosMatPriceMaintain;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.cos.maintain.beans.MaintainBean;
import com.shlanbao.tzsc.pms.cos.maintain.beans.UnitBean;
import com.shlanbao.tzsc.pms.cos.maintain.service.MaintainServiceI;


@Controller
@RequestMapping("/pms/maintain")
public class MaintainController {
	@Autowired
	private MaintainServiceI maintainService;
	
	@RequestMapping("/gotoAddCosMaintain")
	public String gotoAddCosMaintain(){
		return "/pms/cos/maintain/addCosMaintain";
	}
	
	@RequestMapping("/gotoEditCosMaintain")
	public String gotoEditCosMaintain(HttpServletRequest request,String id){
		try {
			request.setAttribute("maintain",maintainService.getMaintainBean(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/cos/maintain/editCosMaintain";
	}
	

	@ResponseBody
	@RequestMapping("/queryCosMaintain")
	public DataGrid queryCosMaintain(MaintainBean maintainBean,PageParams pageParams){
		try {
			DataGrid gd = maintainService.queryCosMaintain(maintainBean,pageParams);
			return gd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@ResponseBody
	@RequestMapping("/addCosMaintain")
	public Json addCosMaintain(MaintainBean maintainBean){
		Json json = new Json();
		try {
			if(maintainService.getMaintainListByBean(maintainBean).size()>0){
				json.setMsg("此牌号的单价成本信息已存在!");
				json.setSuccess(false);
				return json;
			}
			maintainService.addCosMaintain(maintainBean);
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
	@RequestMapping("/editCosMaintain")
	public Json editCosMaintain(MaintainBean maintainBean){
		Json json = new Json();
		try {
			//验证该牌号的单价信息是否已经存在
			List<CosMatPriceMaintain> list=maintainService.getMaintainListByBean(maintainBean);
			String id=maintainBean.getId();
			maintainBean.setId(null);
			if(list.size()>0){
				for(CosMatPriceMaintain m:list){
					if(!m.getId().equals(id)){
						json.setMsg("此牌号的单价成本信息已存在!");
						json.setSuccess(false);
						return json;
					}
				}
			}
			maintainBean.setId(id);
			maintainService.editCosMaintain(maintainBean);
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
	@RequestMapping("/delCosMaintain")
	public Json delCosMaintain(String id){
		Json json = new Json();
		try {
			maintainService.delCosMaintain(id);
			json.setMsg("操作成功!");
			json.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setMsg("操作失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/queryAllUnit")
	public List<UnitBean> queryAllUnit(){
		try {
			return maintainService.queryAllUtil();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
