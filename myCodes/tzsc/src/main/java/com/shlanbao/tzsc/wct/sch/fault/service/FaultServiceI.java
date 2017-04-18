package com.shlanbao.tzsc.wct.sch.fault.service;

import java.util.List;

import com.shlanbao.tzsc.wct.sch.fault.beans.FaultBean;

/**
* @ClassName: FaultServiceI 
* @Description: 生产故障详细信息 
* @author luo 
* @date 2015年9月22日 下午4:27:19 
*
 */
public interface FaultServiceI {
	/**
	* @Title: getFaultAllList 
	* @Description: wct故障图表查询
	* @param  eid 设备id
	* @param  shiftId 班次ID
	* @param  teamId 班组ID
	* @param  startTime 开始时间
	* @param  endTime 结束时间
	* @return List<FaultBean>    返回类型 
	* @throws
	 */
	public List<FaultBean> getFaultAllList(String eid,String shiftId,String teamId,String startTime,String endTime,String orderType);
}
