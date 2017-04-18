package com.shlanbao.tzsc.pms.cos.brand.beans;

public class SeachBean {
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String team;//班组
	private String workshop;//车间id
	private String eName;//机组名称
	private String mdMatId;//牌号code
	private String eType;//卷包机、包装机、成型机工单code
	
	public String geteType() {
		return eType;
	}
	public void seteType(String eType) {
		this.eType = eType;
	}
	public String getMdMatId() {
		return mdMatId;
	}
	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	
}
