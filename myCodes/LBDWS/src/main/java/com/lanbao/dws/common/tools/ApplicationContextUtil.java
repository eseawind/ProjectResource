package com.lanbao.dws.common.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @项目名称 	: 	RDP【快速开发平台】
 * @类名		:	ApplicationContextUtil
 * @类描述	:	TODO 在ApplicationContext环境外获取bean的工具类.
 * @程序员	:	wave_love_snow
 * @版本号	:  	V1.0 
 * @日期		:	2014-8-16 上午11:07:48
 */
public class ApplicationContextUtil implements ApplicationContextAware{
	/**
	 *  向ApplicationContextUtil里设置ApplicationContext.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		ApplicationContextHolder.getInstance().setApplicationContext(applicationContext);
	}


	/**
	 * 
	 * @方法名	:	getApplicationContext
	 * @功能描述	:  	TODO 获得ApplicationContext.
	 * @参数		:	@return   
	 * @返回类型	:	ApplicationContext   
	 * @程序员	:	杨波
	 * @日期时间	:	2014-8-16 上午11:08:14
	 */
	public static ApplicationContext getApplicationContext() {
        return ApplicationContextHolder.getInstance().getApplicationContext();
    }
	
	/**
	 * 
	 * @方法名	:	getBean
	 * @功能描述	:  	TODO 根据class获得bean.
	 * @参数		:	@param clazz
	 * @参数		:	@return   
	 * @返回类型	:	T   
	 * @程序员	:	杨波
	 * @日期时间	:	2014-8-16 上午11:08:24
	 */
    public static <T> T getBean(Class<T> clazz) {
        return ApplicationContextHolder.getInstance().getApplicationContext().getBean(clazz);
    }
    
   /**
    * 
    * @方法名	:	getBean
    * @功能描述	:  	TODO 根据id获得bean.
    * @参数		:	@param id
    * @参数		:	@return   
    * @返回类型	:	T   
    * @程序员	:	杨波
    * @日期时间	:	2014-8-16 上午11:08:50
    */
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String id) {
        return (T) ApplicationContextHolder.getInstance().getApplicationContext().getBean(id);
    }
}
