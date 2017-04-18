package com.shlanbao.tzsc.base.dao;

import com.shlanbao.tzsc.base.mapping.CosPartWeight;
/**
 * 
* @ClassName: CosIncompleteStandardDaoI 
* @Description: 烟支重量维护 Dao层
* @author luoliang
* @date 2015-1-4 上午08:41:55 
*
 */
public interface CosPartWeightDaoI extends BaseDaoI<CosPartWeight> {

	/**
	* @Title: queryWeightByMatCode 
	* @Description: 根据牌号获取烟支重量
	* @param  matCode
	* @return double   返回类型 
	* @throws
	 */
	public double queryWeightByMatCode(String matCode);
}
