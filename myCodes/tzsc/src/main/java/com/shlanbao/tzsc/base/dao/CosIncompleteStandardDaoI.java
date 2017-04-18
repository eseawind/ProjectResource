package com.shlanbao.tzsc.base.dao;

import com.shlanbao.tzsc.base.mapping.CosIncompleteStandard;
/**
 * 
* @ClassName: CosIncompleteStandardDaoI 
* @Description: 机台对应班次储烟量维护 Dao层
* @author luoliang
* @date 2015-1-4 上午08:41:55 
*
 */
public interface CosIncompleteStandardDaoI extends BaseDaoI<CosIncompleteStandard> {
	/**
	* @Title: queryBeanByShiftAndEqpType 
	* @Description: 根据班次ID和设备ID获取储烟量
	* @param @param shiftId
	* @param @param equipmentId
	* @param @return    设定文件 
	* @return double    返回类型 
	* @throws
	 */
	public double queryBeanByShiftAndEqpType(String shiftId,String equipmentId);
}
