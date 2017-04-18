package com.shlanbao.tzsc.pms.sch.stat.beans;
/**
 * PMS导出常用数据的产量类
 * @author TRAVLER
 *
 */
public class RoutputBean {
	private String shift;
	private String team;
	private String eqpName;
	private Double pQty;//计划产量
	private String pSTime;//计划开始时间
	private String pETime;//计划结束时间
	private Double qty;//实际产量
	private Double badQty;//实际剔除量
	private String stime;//实际开始时间
	private String etime;//实际结束时间
	private String runTime;//实际运行时间
	private String stopTime;//实际停机时间
	private String stopTimes;//实际停机次数
	
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public Double getpQty() {
		return pQty;
	}
	public void setpQty(Double pQty) {
		this.pQty = pQty;
	}
	public String getpSTime() {
		return pSTime;
	}
	public void setpSTime(String pSTime) {
		this.pSTime = pSTime;
	}
	public String getpETime() {
		return pETime;
	}
	public void setpETime(String pETime) {
		this.pETime = pETime;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getBadQty() {
		return badQty;
	}
	public void setBadQty(Double badQty) {
		this.badQty = badQty;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getRunTime() {
		return runTime;
	}
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	public String getStopTimes() {
		return stopTimes;
	}
	public void setStopTimes(String stopTimes) {
		this.stopTimes = stopTimes;
	}
}
