package com.entity;

public enum LoggerGrade {
	DEBUG,INFO,WARN,EXECP,ERROR;
	
	public static String getVal(LoggerGrade grad){
		String reS=null;
		switch (grad) {
			case DEBUG:
				reS="DEBUG";
				break;
			case INFO:
				reS="INFO";
				break;
			case WARN:
				reS="WARN";
				break;
			case EXECP:
				reS="EXECP";
				break;
			case ERROR:
				reS="ERROR";
				break;
			default:
				reS="DEBUG";
				break;
		}
		return reS;
	}
}
