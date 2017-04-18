package com.shlanbao.tzsc.wct.isp.roller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.data.runtime.handler.FaultDataCalc;
import com.shlanbao.tzsc.wct.isp.roller.beans.RollerData;
import com.shlanbao.tzsc.wct.isp.roller.service.RollerIspServiceI;
/**
 * 卷烟机实时监控
 * @author Leejean
 * @create 2014年12月31日上午9:26:28
 */
@Controller
@RequestMapping("/wct/isp/roller")
public class RollerIspController extends BaseController {
	@Autowired
	private RollerIspServiceI rollerIspService;
	/**
	 * 初始化卷烟机基本信息
	 * @author Leejean
	 * @create 2014年12月31日上午8:41:58
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initRollerBaseInfo")
	public RollerData initRollerBaseInfo(String equipmentCode){
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return rollerIspService.initRollerBaseInfo(equipmentCode);
		}else{
			return new RollerData();
		}
	}
	
	/**
	 * 获取卷烟机实时数据
	 * @author Leejean
	 * @create 2014年12月31日上午8:42:07
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRollerData")
	public RollerData getRollerData(String equipmentCode){
		if(null!=equipmentCode&&!"".equals(equipmentCode)){
			return rollerIspService.getRollerData(equipmentCode);
		}else{
			return new RollerData();	
		}
	}
	
	@ResponseBody
	@RequestMapping("/getEqpError")
	public List<String[]> getEqpError(String equipmentCode){
		return FaultDataCalc.getInstance().getFaultList(equipmentCode);
	}
}
