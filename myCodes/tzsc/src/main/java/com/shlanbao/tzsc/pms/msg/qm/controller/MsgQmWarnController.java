package com.shlanbao.tzsc.pms.msg.qm.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.MsgQmWarn;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.msg.qm.beans.MsgQmWarnBean;
import com.shlanbao.tzsc.pms.msg.qm.service.MsgQmWarnServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;

@Controller
@RequestMapping("/pms/msg/qm")
public class MsgQmWarnController extends BaseController{
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	protected MsgQmWarnServiceI msgQmWarnService;
	
	
	@RequestMapping("/gotoMsgQmWarn")
	public String gotoMsgQmWarn(){
		return "pms/msg/qm/msgQmWarn";
	}
	
	@RequestMapping("/gotoMsgQmWarnForm")
	public String gotoMsgQmWarnForm(String id,HttpServletRequest request){
		try {
			if(StringUtil.notNull(id))
				request.setAttribute("msgQmWarn", msgQmWarnService.getMsgQmWarnById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/msg/qm/msgQmWarnForm";
	}
	
	@RequestMapping("/gotoMsgQmWarnView")
	public String gotoMsgQmWarnView(String id,HttpServletRequest request){
		try {
			if(StringUtil.notNull(id)){
				//更新读取状态
				MsgQmWarn msgQmWarn = new MsgQmWarn();
				msgQmWarn.setId(id);
				msgQmWarn.setSts(1L);
				msgQmWarnService.addMsgQmWarn(msgQmWarn);
				//获取返回数据
				request.setAttribute("msgQmWarn", msgQmWarnService.getMsgQmWarnBeanById(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/msg/qm/msgQmWarnView";
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpdateMsgQmWarn")
	public Json saveOrUpdateMsgQmWarn(MsgQmWarn msgQmWarn){
		Json json = new Json();
		try {
			msgQmWarnService.addMsgQmWarn(msgQmWarn);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			json.setMsg("操作失败!");
			json.setSuccess(false);
			e.printStackTrace();
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/queryMsgQmWarn")
	public DataGrid queryMsgQmWarn(MsgQmWarnBean msgQmWarn,PageParams pageParams){
		try {
			return msgQmWarnService.queryMsgQmWarn(msgQmWarn, pageParams);
		} catch (Exception e) {
			log.error("查询质量告警记录失败!", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/getMsgQmWarns")
	public List<MsgQmWarnBean> getMsgQmWarns(long sts){
		try {
			return msgQmWarnService.getMsgQmWarns(sts);
		} catch (Exception e) {
			log.error("查询质量告警记录列表失败!", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/deleteMsgQmWarn")
	public Json deleteMsgQmWarn(String id){
		Json json = new Json();
		try {
			msgQmWarnService.deleteMsgQmWarn(id);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		} catch (Exception e) {
			json.setMsg("操作失败!");
			json.setSuccess(false);
			e.printStackTrace();
		}
		return json;
	}
}
