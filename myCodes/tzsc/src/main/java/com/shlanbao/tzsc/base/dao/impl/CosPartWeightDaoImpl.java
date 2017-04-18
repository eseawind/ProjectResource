package com.shlanbao.tzsc.base.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.shlanbao.tzsc.base.dao.CosPartWeightDaoI;
import com.shlanbao.tzsc.base.mapping.CosPartWeight;
/**
 * 
* @ClassName: CosIncompleteStandardDaoImpl 
* @Description: 烟支重量维护 Dao实现层
* @author luoliang
* @date 2015-1-5 上午08:42:30 
*
 */
@Repository
public class CosPartWeightDaoImpl extends BaseDaoImpl<CosPartWeight> implements CosPartWeightDaoI {

	/**
	* @Title: queryWeightByMatCode 
	* @Description: 根据牌号获取烟支重量
	* @param @param matCode
	* @param @return    设定文件 
	* @return double    返回类型 
	* @throws
	 */
	@Override
	public double queryWeightByMatCode(String matCode){
		String hql="from CosPartWeight o where o.mdMat.name='"+matCode+"' ";
		List<CosPartWeight> beans=this.query(hql);
		if(beans.size()>0){
			return beans.get(0).getWeight();
		}
		return 0.0;
	}
	


}