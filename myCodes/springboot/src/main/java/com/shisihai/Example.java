package com.shisihai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration  
public class Example {  
    private final Logger loger=LoggerFactory.getLogger(this.getClass());
      
    @RequestMapping("/hello/{myName}")  
    String index(@PathVariable String myName) {  
    	loger.info("日志消息", "模块","操作人信息");
        return "Hello "+myName+"!!!";  
    }  
}  
