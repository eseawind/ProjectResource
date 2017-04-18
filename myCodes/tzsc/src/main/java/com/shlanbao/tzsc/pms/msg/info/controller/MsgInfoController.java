package com.shlanbao.tzsc.pms.msg.info.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.MsgInfo;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.msg.info.beans.MsgInfoBean;
import com.shlanbao.tzsc.pms.msg.info.service.MsgInfoServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;

/**
 * 机台通知信息管理控制类 增删改查操作
 * @author yangbo
 *
 */
@Controller
@RequestMapping("/pms/msg")
public class MsgInfoController extends BaseController{
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	protected MsgInfoServiceI msgInfoService;
	
	@RequestMapping("/gotoMsgInfo")
	public String gotoMsgInfo(HttpServletRequest request){
		//设置当前用户的操作 控制审批或者签发按钮的显示
		try {
			request.setAttribute("msgOperator", msgInfoService.getCurrUserOperator());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/msg/info/msgInfo";
	}
	
	@RequestMapping("/gotoMsgInfoForm")
	public String gotoMsgInfoForm(String id,HttpServletRequest request){
		try {
			if(StringUtil.notNull(id))
				request.setAttribute("msgInfo", msgInfoService.getMsgInfoById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/msg/info/msgInfoForm";
	}
	@RequestMapping("/gotoMsgInfoApprove")
	public String gotoMsgInfoApprove(String id,HttpServletRequest request){
		try {
			if(StringUtil.notNull(id))
				request.setAttribute("msgInfo", msgInfoService.getMsgInfoBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/msg/info/msgInfoApprove";
	}
	@RequestMapping("/gotoMsgInfoIssuer")
	public String gotoMsgInfoIssuer(String id,HttpServletRequest request){
		try {
			if(StringUtil.notNull(id))
				request.setAttribute("msgInfo", msgInfoService.getMsgInfoBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/msg/info/msgInfoIssuer";
	}
	
	@RequestMapping("/openMsgInfoView")
	public String openMsgInfoView(String id,HttpServletRequest request){
		try {
			if(StringUtil.notNull(id)){
				request.setAttribute("msgInfo", msgInfoService.getMsgInfoBeanById(id));
				request.setAttribute("msgInfos", msgInfoService.getMsgInfoApprove(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/msg/info/msginfoView";
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpdateMsgInfo")
	public Json saveOrUpdateMsgInfo(MsgInfo msgInfo){
		Json json = new Json();
		try {
			Json st= msgInfoService.addMsgInfo(msgInfo);
			if(st.isSuccess()==false){
				json.setMsg(st.getMsg());
				json.setSuccess(false);		
			}else{		
				json.setMsg("操作成功");
				json.setSuccess(true);
			}
		} catch (Exception e) {
			json.setMsg("操作失败");
			json.setSuccess(false);
			e.printStackTrace();
		}
		return json;
	}
	
	
	/**
	 * 保存审批信息，更改机台通知信息发布状态
	 * @param msgInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveMsgInfo")
	public Json saveMsgInfo(MsgInfo msgInfo){
		Json json = new Json();
		try {
			msgInfoService.saveMsgInfo(msgInfo);
			json.setMsg("操作成功");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("操作失败");
			json.setSuccess(false);
			e.printStackTrace();
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/queryMsgInfo")
	public DataGrid queryMsgInfo(MsgInfoBean msgInfo,PageParams pageParams){
		try {
			DataGrid gd = msgInfoService.queryMsgInfo(msgInfo, pageParams);
			return gd;
		} catch (Exception e) {
			log.error("查询机台通知信息失败!", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/deleteMsgInfo")
	public Json deleteMsgInfo(String id){
		Json json = new Json();
		try {
			msgInfoService.deleteMsgInfoById(id);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("操作失败");
			e.printStackTrace();
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getMsgInfo")
	public List<MsgInfoBean> getMsgInfo(){
		try {
			return msgInfoService.getMsgInfo();
		} catch (Exception e) {
			log.error("获取通知信息失败!", e);
		}
		return null;
	}
}
