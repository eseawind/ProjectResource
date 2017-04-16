package test;

import com.entity.LoggerBean;
import com.entity.LoggerGrade;

public class Test {
public static void main(String[] args) {
	long first=System.currentTimeMillis();
	for(int i=0;i<100000000;i++){
		LoggerBean loggerBean=new LoggerBean(String.valueOf(i), 
				"测试"+1, 
				"操作内容"+1,
				LoggerGrade.INFO, 
				"描述"+1,
				"备注"+1);
		loggerBean.saveLog(loggerBean);
	}
	long last=System.currentTimeMillis();
	System.err.println((last-first)/1000);
}
}
