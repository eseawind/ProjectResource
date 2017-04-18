package com.shlanbao.tzsc.utils.tools;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @项目名称 	: 	RDP【快速开发平台】
 * @类名		:	ApplicationContextHolder
 * @类描述	:	TODO 保存ApplicationContext的单例.
 * @程序员	:	杨波
 * @版本号	:  	V1.0 
 * @日期		:	2014-8-16 上午11:06:24
 */
public class ApplicationContextHolder {
    private static ApplicationContextHolder instance = new ApplicationContextHolder();

    private ApplicationContext applicationContext;

    /**
     * 
     * @方法名	:	getApplicationContext
     * @功能描述	:  	TODO 获取Spring上下文
     * @参数		:	@return   
     * @返回类型	:	ApplicationContext   
     * @程序员	:	杨波
     * @日期时间	:	2014-8-16 上午11:06:47
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 
     * @方法名	:	setApplicationContext
     * @功能描述	:  	TODO 设置Spring 上下文
     * @参数		:	@param applicationContext   
     * @返回类型	:	void   
     * @程序员	:	杨波
     * @日期时间	:	2014-8-16 上午11:07:01
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 
     * @方法名	:	getInstance
     * @功能描述	:  	TODO 获取实例化
     * @参数		:	@return   
     * @返回类型	:	ApplicationContextHolder   
     * @程序员	:	杨波
     * @日期时间	:	2014-8-16 上午11:07:26
     */
    public static ApplicationContextHolder getInstance() {
        return instance;
    }
}
