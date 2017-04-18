package com.shlanbao.tzsc.pms.equ.wcplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.equ.wcplan.beans.EqmWheelCovelParamBean;
import com.shlanbao.tzsc.pms.equ.wcplan.service.EqmWheelCovelParamServiceI;

/**
 * 
* @ClassName: EqmWheelCovelParamBeanController 
* @Description: 保养详情 
* @author luo
* @date 2015年3月13日 下午4:47:33 
*
 */
@Controller
@RequestMapping("/pms/wcpparam")
public class EqmWheelCovelParamBeanController {

	@Autowired
	public EqmWheelCovelParamServiceI eqmWheelCovelParamServiceI;
	
	@ResponseBody
	@RequestMapping("/queryParam")
	public DataGrid queryWcpParam(EqmWheelCovelParamBean bean){
		
		return eqmWheelCovelParamServiceI.queryBeanList(bean);
	}
	
	@ResponseBody
	@RequestMapping("/ceshi")
	public void ceshi(){
		eqmWheelCovelParamServiceI.buildWheelCovelPlan();
	}
}
