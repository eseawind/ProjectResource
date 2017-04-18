package com.shlanbao.tzsc.pms.md.eqpparam.service;

import com.shlanbao.tzsc.base.model.DataGrid;
import com.shlanbao.tzsc.pms.md.eqpparam.beans.EquipmentParamBean;


/**
 * 滚轴系数
 * @author Leejean
 * @create 2014年11月25日下午1:22:15
 */
public interface EquipmentParamServiceI {
	/**
	 * 查询所有滚轴系数
	 * @author Leejean
	 * @create 2014年11月26日下午5:42:08
	 * @return
	 * @throws Exception
	 */
	public DataGrid getAllEquipmentParams(EquipmentParamBean equipmentParam) throws Exception;
	/**
	 * 新增滚轴系数
	 * @author Leejean
	 * @create 2014年11月26日下午4:28:56
	 * @param equipmentParam
	 * @throws Exception
	 */
	public void addEquipmentParam(EquipmentParamBean equipmentParam) throws Exception;
	/**
	 * 编辑滚轴系数
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:04
	 * @param equipmentParam
	 * @throws Exception
	 */
	public void editEquipmentParam(EquipmentParamBean equipmentParam) throws Exception;
	/**
	 * 删除滚轴系数
	 * @author Leejean
	 * @create 2014年11月26日下午4:29:12
	 * @param id
	 * @throws Exception
	 */
	public void deleteEquipmentParam(String id) throws Exception;
	/**
	 * 根据ID获取滚轴系数
	 * @author Leejean
	 * @create 2014年11月26日下午6:43:13
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public EquipmentParamBean getEquipmentParamById(String id) throws Exception;

}
