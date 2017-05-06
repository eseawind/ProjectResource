package com.rockwell;
/**
 * 数据源监控/druid/wall.html
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class GwmesReportApplication {
	private final static Logger loger = LoggerFactory.getLogger(GwmesReportApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(GwmesReportApplication.class, args);
		loger.info("报表系统发布完成！","报表系统","系统管理员");
	}
}
