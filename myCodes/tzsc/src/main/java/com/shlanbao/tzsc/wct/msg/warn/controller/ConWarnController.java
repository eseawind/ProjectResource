package com.shlanbao.tzsc.wct.msg.warn.controller;

import java.util.List;






import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;

import com.shlanbao.tzsc.wct.msg.warn.beans.ConWarnBean;
import com.shlanbao.tzsc.wct.msg.warn.beans.MsgInfoBean;
import com.shlanbao.tzsc.wct.msg.warn.beans.QmWarnBean;
import com.shlanbao.tzsc.wct.msg.warn.service.ConWarnServiceI;
import com.shlanbao.tzsc.wct.msg.warn.service.WctMsgInfoServiceI;
import com.shlanbao.tzsc.wct.msg.warn.service.QmWarnServiceI;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/msg/conwarn")
public class ConWarnController extends BaseController {
	@Autowired
	private ConWarnServiceI conWarnServiceI;
	@Autowired
	private QmWarnServiceI qmWarnServiceI;
	@Autowired
	private WctMsgInfoServiceI msgInfoserviceI;
	/**
	 * 单耗告警
	 */
	@ResponseBody
	@RequestMapping("/getConByCode")
	public List<ConWarnBean> getConByCode(HttpSession session){
		LoginBean  bean=  (LoginBean) session.getAttribute("loginInfo");
		if(bean!=null){
			List<ConWarnBean> conWarnBeans= conWarnServiceI.getByEquipmentName(bean.getEquipmentName());
			return conWarnBeans;
		}
		return null;
	
	}
	/**
	 * 质量告警
	 */
	@ResponseBody
	@RequestMapping("/getQmByCode")
	public List<QmWarnBean> getQmByCode(HttpSession session){
		LoginBean  bean=  (LoginBean) session.getAttribute("loginInfo");
		if(bean!=null){
			List<QmWarnBean> qmWarnBeans= qmWarnServiceI.getAll(bean.getEquipmentName());
			return qmWarnBeans;
		}
		return null;
	
	}
	
	/**
	 * 系统通知
	 */
	@ResponseBody
	@RequestMapping("/getMsgInfo")
	public List<MsgInfoBean> getMsgInfo(){		
		List<MsgInfoBean> msgInfos=null;
		try {
			msgInfos= msgInfoserviceI.getAll();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return msgInfos;
	
	}
	
}
