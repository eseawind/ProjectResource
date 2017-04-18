package com.shlanbao.tzsc.wct.msg.warn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.shlanbao.tzsc.wct.msg.warn.beans.ConWarnBean;
import com.shlanbao.tzsc.wct.msg.warn.beans.MsgInfoBean;
import com.shlanbao.tzsc.wct.msg.warn.beans.QmWarnBean;
import com.shlanbao.tzsc.wct.msg.warn.service.ConWarnServiceI;
import com.shlanbao.tzsc.wct.msg.warn.service.QmWarnServiceI;
import com.shlanbao.tzsc.wct.msg.warn.service.WctMsgInfoServiceI;

@Controller
@RequestMapping("/wct/msg/carn")
public class WarnWctController extends BaseController {
	@Autowired
	private QmWarnServiceI qmWarnServiceI;
	@Autowired
	private ConWarnServiceI conWarnServiceI;
	@Autowired
	private WctMsgInfoServiceI wctMsgInfoServiceI;
	/**
	 * 查询全部质量告警
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getQmWarnList")
	public DataGrid getQmWarnList(QmWarnBean qmWarnBean, int pageIndex,
			HttpServletRequest session) {
		try {
			DataGrid gt = qmWarnServiceI.getAll(qmWarnBean, pageIndex, session);

			return gt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@ResponseBody
	@RequestMapping("/getQmUpdate")
	public Json getQmUpdate(String id) {
		Json json = new Json();
		try {
			qmWarnServiceI.updateQmStsAndTWO(id);
			json.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
		}

		return json;
	}
	
	
	/**
	 * 查询全部单耗告警
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getConWarnList")
	public DataGrid getConWarnList(ConWarnBean conWarnBean, int pageIndex,
			HttpSession session) {
		try {
			DataGrid gt = conWarnServiceI.getConList(conWarnBean, pageIndex, session);
			return gt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@ResponseBody
	@RequestMapping("/getConUpdate")
	public Json getConUpdate(String id) {
		Json json = new Json();
		try {
			conWarnServiceI.updateConAndStsTwo(id);
			json.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
		}

		return json;
	}
	/**
	 * 查询全部通知
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getInfoWarnList")
	public DataGrid getInfoWarnList(MsgInfoBean infoBean, int pageIndex,
			HttpSession session) {
		try {
			DataGrid gt = wctMsgInfoServiceI.getInfoList(infoBean, pageIndex, session);
			return gt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@ResponseBody
	@RequestMapping("/getInfoUpdate")
	public Json getInfoUpdate(String id) {
		Json json = new Json();
		try {
			wctMsgInfoServiceI.updateInfoFlagAndFour(id);
			json.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
		}

		return json;
	}
}
