package com.shlanbao.tzsc.pms.md.eqptype.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.md.eqptype.beans.MdEqpTypeChildBean;
import com.shlanbao.tzsc.pms.md.eqptype.service.MdEqpTypeChildServiceI;
import com.shlanbao.tzsc.utils.tools.JSONUtil;

/**
 * 添加 设备轮保项
 * @author luther.zhang 20150514
 *
 */
@Controller
@RequestMapping("/pms/md/eqptypeChild")
public class MdEqpTypeChildController {
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private MdEqpTypeChildServiceI mdEqpTypeChildService;
	protected String message="程序异常,请稍后再试.";

	@RequestMapping("/goToLbJsp")
	public String goToLbJsp(HttpServletRequest request,String id){
		MdEqpTypeChildBean bean = new MdEqpTypeChildBean();
		bean.setQueryId(id);
		request.setAttribute("bean",bean);
		return "/pms/md/eqptype/editLunBaoChild";
	}
	@RequestMapping("/goToRhJsp")
	public String goToRhJsp(HttpServletRequest request,String id){
		MdEqpTypeChildBean bean = new MdEqpTypeChildBean();
		bean.setQueryId(id);
		request.setAttribute("bean",bean);
		return "/pms/md/eqptype/editRunHuaChild";
	}
	@RequestMapping("/goToWxJsp")
	public String goToWxJsp(HttpServletRequest request,String id){
		MdEqpTypeChildBean bean = new MdEqpTypeChildBean();
		bean.setQueryId(id);
		request.setAttribute("bean",bean);
		return "/pms/md/eqptype/editWeiXiuChild";
	}
	
	@ResponseBody
	@RequestMapping("/queryMdType")
	public DataGrid queryMdType(MdEqpTypeChildBean mdTypeBean,PageParams pageParams){
		try {
			if(null!=mdTypeBean.getQueryId()&&!"".equals(mdTypeBean.getQueryId())){
				DataGrid gd = mdEqpTypeChildService.queryMdTypeChild(mdTypeBean, pageParams);
				return gd;
			}else{
				List<MdEqpTypeChildBean> lastList = new ArrayList<MdEqpTypeChildBean>();
				return new DataGrid(lastList,0L);
			}
		} catch (Exception e) {
			log.error("查询异常。", e);
		}
		return null;
	}
	/**
	 * 新建修改
	 */
	@ResponseBody
	@RequestMapping("/editMdTypeChild")
	public Json editMdTypeChild(MdEqpTypeChildBean bean,HttpServletRequest request){
		Json json=new Json();
		try {
			String type = request.getParameter("type");//类型
			String isInsert = request.getParameter("isInsert");
			String reqString = request.getParameter("reqString");
			if(null!=reqString){
				// 填充BEAN
				MdEqpTypeChildBean[] beans = (MdEqpTypeChildBean[]) JSONUtil.JSONString2ObjectArray(reqString,MdEqpTypeChildBean.class);
				if("save".equals(isInsert)){
					mdEqpTypeChildService.saveMdTypeChild(type,beans);
					json.setMsg("新增成功!");
					json.setSuccess(true);
				}else if("update".equals(isInsert)){
					mdEqpTypeChildService.editMdTypeChild(type,beans);
					json.setMsg("更新成功!");
					json.setSuccess(true);
				}
			}else{
				json.setMsg("失败");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error(message, e);
			json.setMsg("新增失败!");
			json.setSuccess(false);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping("/getPaulbyEqpType")
	public List<MdEqpTypeChildBean> getPaulbyEqpType(String eqpTypeId,String type,HttpServletRequest request){
		try {
			return mdEqpTypeChildService.getPaulbyEqpType(eqpTypeId, type);
		} catch (Exception e) {
			log.error("查询异常。", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/getPaulbyEqp")
	public List<MdEqpTypeChildBean> getPaulbyEqp(String eqpId,String type,HttpServletRequest request){
		try {
			return mdEqpTypeChildService.getPaulbyEqp(eqpId, type);
		} catch (Exception e) {
			log.error("查询异常。", e);
		}
		return null;
	}
	/**
	 * 根据设备型号ID查询对应的轮保规则
	 * @param equBean
	 * @param pageParams
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryEqpTypeChildByEqp")
	public List<MdEqpTypeChildBean> queryEqpTypeChildByEqp(String eqpid,String type){
		try {
			if(null!=eqpid&&!"".equals(eqpid)){
				return mdEqpTypeChildService.queryEqpTypeChildByEqp(eqpid,type);
			}else{
				List<MdEqpTypeChildBean> lastList = new ArrayList<MdEqpTypeChildBean>();
				return new DataGrid(lastList,0L).getRows();
			}
		} catch (Exception e) {
		}
		return null;
	}
}
