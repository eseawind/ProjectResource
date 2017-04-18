package com.shlanbao.tzsc.base.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.shlanbao.tzsc.base.dao.CosIncompleteStandardDaoI;
import com.shlanbao.tzsc.base.mapping.CosIncompleteStandard;
import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: CosIncompleteStandardDaoImpl 
* @Description: 机台对应班次储烟量维护 Dao实现层
* @author luoliang
* @date 2015-1-4 上午08:42:30 
*
 */
@Repository
public class CosIncompleteStandardDaoImpl extends BaseDaoImpl<CosIncompleteStandard> implements CosIncompleteStandardDaoI {

	@Override
	public double queryBeanByShiftAndEqpType(String shiftId,String equipmentId){
		StringBuffer hql=new StringBuffer();
		hql.append("from CosIncompleteStandard o inner join fetch o.mdEqupType mdtype inner join fetch mdtype.mdEquipments equip  where 1=1 ");
		if(StringUtil.notNull(shiftId)){
			hql.append("and o.mdShift.id='"+shiftId+"' ");
		}
		if(StringUtil.notNull(equipmentId)){
			hql.append("and equip.id='"+equipmentId+"' ");
		}
		List<CosIncompleteStandard> list=this.query(hql.toString());
		if(list.size()>0){
			return list.get(0).getStorage_smoke();
		}
		return 0.0;
	}


}