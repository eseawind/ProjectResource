package com.shlanbao.tzsc.wct.sch.workorder.service;

import com.shlanbao.tzsc.base.model.DataGrid;


/**
 * 工艺业务接口
 * @author Leejean
 * @create 2014年11月18日下午3:53:04
 */
public interface CraftServiceI {
	/**
	 * 获取工单的Craft工艺参数
	 * @author Leejean
	 * @create 2014年11月20日上午9:03:08
	 * @param workOrderId 工单ID
	 * @return 装载Craft的datagrid
	 */
	public DataGrid queryCraftByWorkOrder(String workOrderId);
}
