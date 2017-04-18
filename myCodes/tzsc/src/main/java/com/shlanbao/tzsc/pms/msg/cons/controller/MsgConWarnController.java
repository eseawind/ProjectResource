package com.shlanbao.tzsc.pms.msg.cons.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.MsgConWarn;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.msg.cons.beans.MsgConWarnBean;
import com.shlanbao.tzsc.pms.msg.cons.service.MsgConWarnServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;

/**
 * 物料单耗告警记录维护 
 * @author yangbo
 *
 */
@Controller
@RequestMapping("/pms/msg/con")
public class MsgConWarnController extends BaseController{
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	protected MsgConWarnServiceI msgConWarnService;
	
	
	@RequestMapping("/gotoMsgConWarn")
	public String gotomsgConWarn(){
		return "pms/msg/cons/msgConWarn";
	}
	
	@RequestMapping("/gotoMsgConWarnForm")
	public String gotoMsgConWarnForm(String id,HttpServletRequest request){
		try {
			if(StringUtil.notNull(id))
				request.setAttribute("msgConWarn", msgConWarnService.getMsgConWarnById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/msg/cons/msgConWarnForm";
	}
	
	@RequestMapping("/gotoMsgConWarnView")
	public String gotoMsgConWarnView(String id,HttpServletRequest request){
		try {
			if(StringUtil.notNull(id)){
				//更新读取状态
				MsgConWarn msgConWarn = new MsgConWarn();
				msgConWarn.setId(id);
				msgConWarn.setSts(1L);
				msgConWarnService.addMsgConWarn(msgConWarn);
				//获取返回数据
				request.setAttribute("msgConWarn", msgConWarnService.getMsgConWarnBeanById(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pms/msg/cons/msgConWarnView";
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpdateMsgConWarn")
	public Json saveOrUpdateMsgConWarn(MsgConWarn msgConWarn){
		Json json = new Json();
		try {
			msgConWarnService.addMsgConWarn(msgConWarn);
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
	@RequestMapping("/queryMsgConWarn")
	public DataGrid queryMsgConWarn(MsgConWarnBean msgConWarn,PageParams pageParams,Date startTime,Date endTime){
		try {
			return msgConWarnService.queryMsgConWarn(msgConWarn, pageParams,startTime,endTime);
		} catch (Exception e) {
			log.error("查询无聊单耗告警记录失败!", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/getMsgConWarns")
	public List<MsgConWarnBean> getMsgConWarns(long sts){
		try {
			return msgConWarnService.getMsgConWarns(sts);
		} catch (Exception e) {
			log.error("查询无聊单耗告警记录列表失败!", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/deleteMsgConWarn")
	public Json deleteMsgConWarn(String id){
		Json json = new Json();
		try {
			msgConWarnService.deleteMsgConWarnById(id);
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
