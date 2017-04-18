package com.shlanbao.tzsc.pms.sch.manualshift.service;

import java.util.List;

import com.shlanbao.tzsc.base.mapping.MdManualShift;


/**
 *  查询当前运行工单 确定当前班次
 */
public interface ManualShiftServiceI {
	/**
	 * 查询当前运行工单 确定当前班次
	 * @throws Exception
	 */
	public MdManualShift getManualShift(String eqpId,String state) throws Exception;
	/**
	 * 根据sql语句更新
	 * @param sql
	 * @param obj
	 * @throws Exception
	 */
	public void updateInfo(String sql,List<Object> obj) throws Exception;

}
