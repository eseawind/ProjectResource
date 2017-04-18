package com.shlanbao.tzsc.base.dao.impl;

import java.util.Hashtable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.shlanbao.tzsc.base.dao.CosIncompleteCoefficientDaoI;
import com.shlanbao.tzsc.base.mapping.CosIncompleteCoefficient;
/**
 * @ClassName: CosIncompleteCoefficientDaoImpl 
 * @Description: 参演是考核系数管理
 * @author luo
 * @date 2015年1月22日 下午1:52:22 
 *
 */
@Repository
public class CosIncompleteCoefficientDaoImpl extends BaseDaoImpl<CosIncompleteCoefficient> implements CosIncompleteCoefficientDaoI{
	/**
	 * 查询
	 */
	@Override
	public Hashtable<String,Double> queryModulusHT() {
		String hql="from CosIncompleteCoefficient o where 1=1 ";
		List<CosIncompleteCoefficient> list = this.query(hql);
		Hashtable<String,Double> ht=new Hashtable<String,Double>();
		for(CosIncompleteCoefficient b:list){
			ht.put(b.getCode(), b.getModulus());
		}
		return ht;
	}
}
