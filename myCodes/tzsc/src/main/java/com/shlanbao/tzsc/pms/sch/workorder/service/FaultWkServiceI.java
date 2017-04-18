package com.shlanbao.tzsc.pms.sch.workorder.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.base.model.PageParams;
import com.shlanbao.tzsc.pms.sch.workorder.beans.FaultWkBean;

/**
 * 
* @ClassName: FaultWkServiceI 
* @Description: 设备运行故障信息 
* @author luo 
* @date 2015年10月10日 上午10:05:39 
*
 */
public interface FaultWkServiceI {
	/**
	* @Title: queryFaultWkGrid 
	* @Description: PMS 机台故障查询
	* @param bean 查询条件
	* @param pageParams 翻页条件
	* @return DataGrid    返回类型 
	* @throws
	 */
	public DataGrid queryFaultWkGrid(FaultWkBean bean,PageParams pageParams);
	
	/**
	* @Title: exportFaultWkList 
	* @Description: 根据条件查询所有故障列表
	* @param 查询条件
	* @return List<FaultWkBean>    返回类型 
	* @throws
	 */
	public List<FaultWkBean> exportFaultWkList(FaultWkBean bean);
}
