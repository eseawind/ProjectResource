package com.shlanbao.tzsc.base.dao;

import java.util.Hashtable;

import com.shlanbao.tzsc.base.mapping.CosIncompleteCoefficient;

/**
 * @ClassName: CosIncompleteCoefficientDaoI 
 * @Description: 参演是考核系数管理
 * @author luo
 * @date 2015年1月22日 下午1:52:22 
 *
 */
public interface CosIncompleteCoefficientDaoI extends BaseDaoI<CosIncompleteCoefficient> {

	public Hashtable<String,Double> queryModulusHT();
}
