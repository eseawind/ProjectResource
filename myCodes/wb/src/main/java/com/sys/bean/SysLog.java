package com.sys.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>功能描述：日志记录</p>
 *travler
 *2017下午1:21:23
 */
public class SysLog implements Serializable{
	private static final long serialVersionUID = -3305534048653317317L;
	private String userName;
	private Date logDate;
	private int logType;
	private String msg;
	private String ip;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public int getLogType() {
		return logType;
	}
	public void setLogType(int logType) {
		this.logType = logType;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
