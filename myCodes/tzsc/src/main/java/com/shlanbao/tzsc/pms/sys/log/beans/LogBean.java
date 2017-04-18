package com.shlanbao.tzsc.pms.sys.log.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 日志Bean
 * <li>@author Leejean
 * <li>@create 2014-7-5下午05:36:17
 */
public class LogBean {
	private String id;//id
	private String name;//用户名
	private String ip;//IP
	private String sys;//系统
	private String optname;//操作名称
	private String success;//操作结果
	private String params;//参数描述
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String date;//操作时间
	private String date2;
	private Long execTime;
	private Long del;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSys() {
		return sys;
	}
	public void setSys(String sys) {
		this.sys = sys;
	}
	public String getOptname() {
		return optname;
	}
	public void setOptname(String optname) {
		this.optname = optname;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public LogBean() {
		// TODO Auto-generated constructor stub
	}
	public LogBean(String id, String name, String ip, String sys, String optname,
			String success, String params,Long execTime, String date) {
		super();
		this.id = id;
		this.name = name;
		this.ip = ip;
		this.sys = sys;
		this.optname = optname;
		this.success = success;
		this.params = params;
		this.execTime = execTime;
		this.date = date;
	}
	public LogBean(String name, String ip, String sys, String optname,
			String success, String params,Long execTime, Long del) {
		super();
		this.name = name;
		this.ip = ip;
		this.sys = sys;
		this.optname = optname;
		this.success = success;
		this.params = params;
		this.execTime = execTime;
		this.del = del;
	}
	@Override
	public String toString() {
		return "LogBean [id=" + id + ", name=" + name + ", ip=" + ip + ", sys="
				+ sys + ", optname=" + optname + ", success=" + success
				+ ", params=" + params + ", date=" + date + "]";
	}
	
	
	public Long getExecTime() {
		return execTime;
	}
	public void setExecTime(Long execTime) {
		this.execTime = execTime;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
}
