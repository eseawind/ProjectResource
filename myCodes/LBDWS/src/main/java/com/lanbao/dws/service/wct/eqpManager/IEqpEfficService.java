package com.lanbao.dws.service.wct.eqpManager;

import org.springframework.ui.Model;

import com.ibm.framework.dal.pagination.Pagination;
import com.lanbao.dws.model.wct.eqpManager.EqpOperatingEfficBean;

public interface IEqpEfficService {
	/**
	* @author 作者 : shisihai
	* @version 创建时间：2016年7月14日 上午11:05:01 
	* 功能说明 ：查询设备运行效率
	 */
	void queryEqpOperatingEffic(Pagination pagination,EqpOperatingEfficBean bean,Model model);
	/**
	 * <p>功能描述：设备有效作业率</p>
	 *@param pagination
	 *@param bean
	 *@param model
	 *shisihai
	 *2016上午11:05:36
	 */
	void queryEffectiveOperatingEffic(Pagination pagination,EqpOperatingEfficBean bean,Model model);
}
