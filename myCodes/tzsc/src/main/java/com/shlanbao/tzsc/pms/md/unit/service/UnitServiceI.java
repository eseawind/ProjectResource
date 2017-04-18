package com.shlanbao.tzsc.pms.md.unit.service;

import java.util.List;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.md.unit.beans.UnitBean;

public interface UnitServiceI {
	/**
	 *  查询所有单位 combobox
	 * @author Leejean
	 * @create 2014年11月26日下午4:23:14
	 * @return
	 */
	public List<UnitBean> queryAllUnitsForComboBox() throws Exception;
	/**
	 * 获得所有单位 datagrid
	 * @author Leejean
	 * @create 2014年11月26日下午6:00:05
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllUnits(UnitBean unitBean) throws Exception;
	/**
	 * 新增单位
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param UnitBean
	 * @throws Exception
	 */
	public void addUnit(UnitBean unitBean) throws Exception;
	/**
	 * 编辑单位
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param UnitBean
	 * @throws Exception
	 */
	public void editUnit(UnitBean unitBean) throws Exception;
	/**
	 * 删除单位
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteUnit(String id) throws Exception;
	/**
	 * 根据id获得计量单位
	 * @author Leejean
	 * @create 2014年11月26日下午7:14:00
	 * @param id
	 * @return
	 */
	public UnitBean getUnitById(String id) throws Exception;
}
