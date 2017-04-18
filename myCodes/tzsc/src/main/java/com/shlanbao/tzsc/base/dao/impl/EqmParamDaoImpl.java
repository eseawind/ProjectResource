package com.shlanbao.tzsc.base.dao.impl;

import java.util.Hashtable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.shlanbao.tzsc.base.dao.EqmParamDaoI;
import com.shlanbao.tzsc.base.mapping.EqmParam;
/**
 * 
* @ClassName: EqmParamDaoI 
* @Description: 设备模块设备参数维护功能 
* @author luo
* @date 2015年3月11日 上午9:40:03 
*
 */
@Repository
public class EqmParamDaoImpl extends BaseDaoImpl<EqmParam> implements EqmParamDaoI{
	/**
	 * 查询
	 */
	@Override
	public Hashtable<String,Double> queryModulusHT() {
		String hql="from EqmParam o where 1=1 ";
		List<EqmParam> list = this.query(hql);
		Hashtable<String,Double> ht=new Hashtable<String,Double>();
		for(EqmParam b:list){
			ht.put(b.getCode(), b.getModulu());
		}
		return ht;
	}
}
