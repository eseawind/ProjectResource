package com.traveler.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		ApplicationContextHolder.getInstance().setApplicationContext(applicationContext);
	}


	public static ApplicationContext getApplicationContext() {
        return ApplicationContextHolder.getInstance().getApplicationContext();
    }
	
    public static <T> T getBean(Class<T> clazz) {
    	ApplicationContextHolder applicationContextHolder=ApplicationContextHolder.getInstance();
    	return applicationContextHolder.getApplicationContext().getBean(clazz);
    }
    
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String id) {
    	ApplicationContextHolder applicationContextHolder=ApplicationContextHolder.getInstance();
    	return (T) applicationContextHolder.getApplicationContext().getBean(id);
    }
}
