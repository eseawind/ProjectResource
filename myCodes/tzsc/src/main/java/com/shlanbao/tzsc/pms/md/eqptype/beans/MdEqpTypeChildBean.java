package com.shlanbao.tzsc.pms.md.eqptype.beans;

public class MdEqpTypeChildBean {
	private String secId;	//数据字典ID
	private String secCode;
	private String secName;
	private String type;//类型 如：轮保、检修等
	
	
	private String metcId;//绑定主表ID
	private String secPid;//SEC PID-指向 secId
	private String metPid;//MET PID
	private String isCheck;//是否勾选checkBox
	private String del;
	
	private String metId;
	private String metName;
	
	private String queryId;//设备型号 主键ID
	private String queryType;//类型 如：轮保、检修等
	private String queryName;
	public String getMetcId() {
		return metcId;
	}
	public void setMetcId(String metcId) {
		this.metcId = metcId;
	}
	public String getSecPid() {
		return secPid;
	}
	public void setSecPid(String secPid) {
		this.secPid = secPid;
	}
	public String getMetPid() {
		return metPid;
	}
	public void setMetPid(String metPid) {
		this.metPid = metPid;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getSecId() {
		return secId;
	}
	public void setSecId(String secId) {
		this.secId = secId;
	}
	public String getSecCode() {
		return secCode;
	}
	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public String getMetId() {
		return metId;
	}
	public void setMetId(String metId) {
		this.metId = metId;
	}
	public String getMetName() {
		return metName;
	}
	public void setMetName(String metName) {
		this.metName = metName;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
}
