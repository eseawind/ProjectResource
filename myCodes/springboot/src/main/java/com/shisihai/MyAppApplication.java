package com.shisihai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * <p>功能描述：启动类</p>
 *shisihai
 *2016下午7:16:48
 */
@SpringBootApplication
public class MyAppApplication {
	private final static Logger loger = LoggerFactory.getLogger(MyAppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
		loger.info("系统发布成功", "系统发布","系统管理员");
	}
}
