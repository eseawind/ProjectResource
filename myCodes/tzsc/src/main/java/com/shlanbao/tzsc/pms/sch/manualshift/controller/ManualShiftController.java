package com.shlanbao.tzsc.pms.sch.manualshift.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.base.mapping.MdManualShift;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sch.manualshift.service.ManualShiftServiceI;

@Controller
@RequestMapping("/pms/manualShift")
public class ManualShiftController extends BaseController{
	@Autowired
	private ManualShiftServiceI service;
	/** 查询*/
	@ResponseBody
	@RequestMapping("/getList")
	public MdManualShift getList(MdManualShift bean,PageParams pageParams){
		try {
			return service.getManualShift("1","S");
		} catch (Exception e) {
			log.error(message, e);
		}
		return null;
	}
}
