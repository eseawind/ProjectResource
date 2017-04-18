package com.shisihai.base;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * <p>功能描述：自定义静态资源注册</p>
 *shisihai
 *2016下午8:44:42
 */
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        /*registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	        super.addResourceHandlers(registry);*/
	    }
}
