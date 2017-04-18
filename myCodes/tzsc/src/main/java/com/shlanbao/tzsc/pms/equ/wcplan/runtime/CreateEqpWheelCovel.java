package com.shlanbao.tzsc.pms.equ.wcplan.runtime;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.shlanbao.tzsc.pms.equ.wcplan.service.EqmWheelCovelParamServiceI;
import com.shlanbao.tzsc.utils.tools.DateUtil;

/**
 * 
* @ClassName: CreateEqpWheelCovel 
* @Description: 生成维保计划单 
* @author luo
* @date 2015年3月16日 下午5:30:37 
*
 */
public class CreateEqpWheelCovel {
	@Autowired
	private EqmWheelCovelParamServiceI wqmWheelCovelParamServiceI;
	
	public void runTime(){
		wqmWheelCovelParamServiceI.buildWheelCovelPlan();
		System.out.println("时间："+DateUtil.formatDateToString(new Date()));
	}
}
