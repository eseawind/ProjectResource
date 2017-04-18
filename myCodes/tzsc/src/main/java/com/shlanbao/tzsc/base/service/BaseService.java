package com.shlanbao.tzsc.base.service;

import org.apache.log4j.Logger;

import com.shlanbao.tzsc.utils.tools.BeanConvertor;


/**
 * Service基类
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:15:42
 */
public class BaseService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger log = Logger.getLogger(this.getClass());
	protected String message="操作失败.";
	protected BeanConvertor beanConvertor = new BeanConvertor();
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
