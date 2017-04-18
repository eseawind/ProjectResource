package com.shlanbao.tzsc.wct.eqm.stat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlanbao.tzsc.wct.eqm.stat.bean.StatEqpRunBean;
import com.shlanbao.tzsc.wct.eqm.stat.service.StatEqpRunServiceI;

/**
 * 
 * @ClassName: StatController 
 * @Description: 设备运行记录 
 * @author luo
 * @date 2015年2月4日 上午8:51:01 
 *
 */
@Controller
@RequestMapping("/wct/eqpRun")
public class StatEqpRunController {
	
	@Autowired
	public StatEqpRunServiceI statEqpRunServiceI;
	
	/**
	 * 
	* @Title: getBean 
	* @Description: 设备运行统计 
	* @param @param bean
	* @param @return    设定文件 
	* @return StatEqpRunBean    返回类型 
	* @throws
	 */
	@RequestMapping("/getEqpRunData")
	@ResponseBody
	public StatEqpRunBean getEqpRunData(StatEqpRunBean bean){
		return statEqpRunServiceI.getStatEqpRunBean(bean, 6);
	}
	
	/**
	 * 
	* @Title: getEqpEfficiencyData 
	* @Description: 设备有效作业率
	* @param @param bean
	* @param @param type
	* @param @return    设定文件 
	* @return StatEqpRunBean    返回类型 
	* @throws
	 */
	@RequestMapping("/getEqpEfficiency")
	@ResponseBody
	public StatEqpRunBean getEqpEfficiencyData(StatEqpRunBean bean,int type){
		return statEqpRunServiceI.getEqpEfficiencyData(bean, 6,type);
	}
	/**
	 * 
	* @Title: getEqpUtilizationData 
	* @Description: 设备综合利用率 
	* @param @param bean
	* @param @return    设定文件 
	* @return StatEqpRunBean    返回类型 
	* @throws
	 */
	@RequestMapping("/getEqpUtilizationData")
	@ResponseBody
	public StatEqpRunBean getEqpUtilizationData(StatEqpRunBean bean){
		return statEqpRunServiceI.getEqpUtilizationData(bean, 6);
	}
	
	/**
	 * 
	* @Title: getEqpUtilizationData 
	* @Description: 设备运行效率 
	* @param @param bean
	* @param @return    设定文件 
	* @return StatEqpRunBean    返回类型 
	* @throws
	* shisihai
	* 2015-10-9
	 */
	@RequestMapping("/getEqpRateData")
	@ResponseBody
	public StatEqpRunBean getEqpRateData(StatEqpRunBean bean,int type){
		return statEqpRunServiceI.getEqpRateData(bean, 6,type);
	}

}
