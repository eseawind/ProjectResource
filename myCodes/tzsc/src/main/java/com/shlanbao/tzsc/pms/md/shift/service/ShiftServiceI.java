package com.shlanbao.tzsc.pms.md.shift.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.md.shift.beans.ShiftBean;

public interface ShiftServiceI {
	/**
	 * 查询所有班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:23:14
	 * @return
	 */
	public List<ShiftBean> queryAllShiftsForComboBox() throws Exception ;
	/**
	 * 查询所有班次
	 * @author Leejean
	 * @create 2014年11月26日下午5:42:08
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllShifts(ShiftBean ShiftBean) throws Exception;
	/**
	 * 新增班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param ShiftBean
	 * @throws Exception
	 */
	public void addShift(ShiftBean ShiftBean) throws Exception;
	/**
	 * 编辑班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param ShiftBean
	 * @throws Exception
	 */
	public void editShift(ShiftBean ShiftBean) throws Exception;
	/**
	 * 删除班次
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteShift(String id) throws Exception;
	/**
	 * 根据ID获取班次
	 * @author Leejean
	 * @create 2014年11月26日下午6:43:13
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public ShiftBean getShiftById(String id) throws Exception;
}
