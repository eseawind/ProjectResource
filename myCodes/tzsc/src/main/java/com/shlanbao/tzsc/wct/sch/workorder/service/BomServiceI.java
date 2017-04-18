package com.shlanbao.tzsc.wct.sch.workorder.service;

import com.shlanbao.tzsc.base.model.DataGrid;


/**
 * BOM业务接口
 * @author Leejean
 * @create 2014年11月18日下午3:53:04
 */
public interface BomServiceI {
	/**
	 * 获取工单的物料清单
	 * @author Leejean
	 * @create 2014年11月20日上午9:03:08
	 * @param workOrderId 工单号
	 * @return 装载bom的datagrid
	 */
	public DataGrid queryBomByWorkOrder(String workOrderId);
}
