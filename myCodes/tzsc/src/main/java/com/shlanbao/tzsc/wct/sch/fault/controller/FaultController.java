package com.shlanbao.tzsc.wct.sch.fault.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.controller.BaseController;
import com.shlanbao.tzsc.wct.sch.fault.beans.FaultBean;
import com.shlanbao.tzsc.wct.sch.fault.service.FaultServiceI;
/**
* @ClassName: FaultController 
* @Description: 生产故障详细信息 
* @author luo 
* @date 2015年9月22日 下午4:26:50 
*
 */
@Controller
@RequestMapping("/wct/fault")
public class FaultController extends BaseController {
	@Autowired
	private FaultServiceI faultService;
	/** 
	* @Title: queryFaultChart 
	* @Description: WCT故障明细查询
	* @param eid
	* @param shiftId
	* @param teamId
	* @param startTime
	* @param endTime
	* @return List<FaultBean> 返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryFaultChart")
	public List<FaultBean> queryFaultChart(String eid,String shiftId,String teamId,String startTime,String endTime,String orderType,HttpServletRequest request){
		return faultService.getFaultAllList(eid, shiftId, teamId, startTime, endTime,orderType);
	}
	
	
}
