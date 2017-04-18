package com.shlanbao.tzsc.pms.sch.workorder.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.Combobox;
import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.sch.workorder.beans.BomBean;


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
	/**
	 * 新增bom物料清单
	 * @author Leejean
	 * @create 2014年12月25日上午8:32:25
	 * @param bomBean
	 */
	public void addBom(BomBean bomBean);
	/**
	 * 编辑
	 * @author Leejean
	 * @create 2014年12月25日上午8:32:29
	 * @param bomBean
	 */
	public void editBom(BomBean bomBean);
	/**
	 * 根据ID获取Bom
	 * @author Leejean
	 * @create 2014年12月25日上午8:44:10
	 * @param id
	 * @return
	 */
	public BomBean getBomById(String id);
	/**
	 * 删除
	 * @author Leejean
	 * @create 2014年12月25日上午8:32:33
	 * @param id
	 */
	public void deleteBom(String id);
}
