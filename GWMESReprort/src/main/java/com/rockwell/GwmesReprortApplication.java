package com.rockwell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class GwmesReprortApplication {
	private final static Logger loger = LoggerFactory.getLogger(GwmesReprortApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(GwmesReprortApplication.class, args);
		loger.info("报表系统发布完成！","报表系统","系统管理员");
	}
}
