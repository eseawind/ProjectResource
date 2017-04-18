package com.shlanbao.tzsc.pms.equ.runtime.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.equ.effective.beans.EffectiveGraphBean;
import com.shlanbao.tzsc.pms.equ.runtime.beans.EqpRunTimeBean;

/**
 * 
 * @ClassName: EqpRunTimeServiceI 
 * @Description: 设备运行统计 
 * @author luo
 * @date 2015年3月5日 下午3:10:15 
 *
 */
public interface EqpRunTimeServiceI {
	/**
	* @Title: queryEqpRunTimeByBean 
	* @Description: 设备运行统计查询
	* @param bean
	* @param pageParams
	* @return
	 */
	public DataGrid queryEqpRunTimeByBean(EqpRunTimeBean bean, PageParams pageParams);
	
	/**
	* @Title: queryEqpRunTimeChart 
	* @Description: 设备运行统计查询chart
	* @param bean
	* @param pageParams
	* @return
	 */
	public EffectiveGraphBean queryEqpRunTimeChart(EqpRunTimeBean bean, PageParams pageParams);
	
}
