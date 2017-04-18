package com.shlanbao.tzsc.base.dao;

import java.util.Hashtable;

import com.shlanbao.tzsc.base.mapping.EqmParam;

/**
 * 
* @ClassName: EqmParamDaoI 
* @Description: 设备模块设备参数维护功能 
* @author luo
* @date 2015年3月11日 上午9:28:03 
*
 */
public interface EqmParamDaoI extends BaseDaoI<EqmParam>{
	public Hashtable<String,Double> queryModulusHT();
}
