package com.shlanbao.tzsc.wct.ctm.machine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.pms.cos.maintain.service.MaintainServiceI;
import com.shlanbao.tzsc.utils.tools.StringUtil;
import com.shlanbao.tzsc.wct.ctm.machine.beans.CostManager;
import com.shlanbao.tzsc.wct.ctm.machine.service.WctCostManagerServiceI;
import com.shlanbao.tzsc.wct.sch.stat.beans.EqpRuntime;

/**
 * @ClassName: WctCostManager 
 * @Description: 成本管理 实时成本 
 * @author luo
 * @date 2015年1月27日 上午9:26:33 
 *
 */
@Controller
@RequestMapping("/wct/costs")
public class WctCostManagerController extends BaseController{

	@Autowired
	public WctCostManagerServiceI wctCostManagerService;
	
	@Autowired
	public MaintainServiceI maintainService;
	

	/**
	 * 初始化机台实时消耗页面
	 * @author luo
	 * @create 2015年1月27日11:05:07
	 * @return
	 */
	@RequestMapping("/getRollerInputData")
	@ResponseBody
	public CostManager getRollerInputData(String ecode,String orderNumber){
		if(!StringUtil.notNull(ecode)){
			log.error(this.getClass().getName()+"获取设备code失败");
		}
		return wctCostManagerService.getDataSource(Integer.parseInt(ecode), orderNumber);
		//return new CostManager(ecode, MathUtil.getRandomDouble(1, 2, 2), MathUtil.getRandomDouble(3000,3100, 2),  MathUtil.getRandomDouble(2, 4, 2), MathUtil.getRandomDouble(1, 2, 2));
	}
	/**
	 * 初始化初始化机台实时产量页面
	 * @author luo
	 * @create 2015年1月28日13:39:42
	 * @param type 工单类型编号
	 * @return
	 */
	@RequestMapping("/initOutDataPage")
	@ResponseBody
	public EqpRuntime initOutDataPage(String ecode){
		try {
			List<EqpRuntime> list=wctCostManagerService.initOutDataPage(ecode);
			return list.get(0);
		} catch (Exception e) {
			super.setMessage("实例化机组信息异常");
			log.error(super.message, e);
		}
		return null;
	}
	/**
	 * 初始化机台实时消耗页面
	 * @author Leejean
	 * @create 2014年12月19日下午4:24:44
	 * @return
	 */
	@RequestMapping("/initEquipmentRollerInput")
	@ResponseBody
	public CostManager initEquipmentRollerInput(String ecode) throws Exception {
		return wctCostManagerService.initEqpInputInfos(ecode);
	}
}
