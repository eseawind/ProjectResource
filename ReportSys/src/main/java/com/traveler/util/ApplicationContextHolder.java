package com.traveler.util;

import org.springframework.context.ApplicationContext;

public class ApplicationContextHolder {
	  private static ApplicationContextHolder instance = new ApplicationContextHolder();

	    private ApplicationContext applicationContext;

	    public ApplicationContext getApplicationContext() {
	        return applicationContext;
	    }

	    public void setApplicationContext(ApplicationContext applicationContext) {
	        this.applicationContext = applicationContext;
	    }

	    public static ApplicationContextHolder getInstance() {
	        return instance;
	    }
}
