package com.lanbao.dws.model.wct.dac;

import java.util.Date;

public class OutputBean {
	private String dbOutputId;
	private String workorder;
	private String stim;
	private Double qty;
	private Double badQty;
	private Double runTime;
	private Double stopTime;
	private long stopTimes;
	private Date nowDateTime;
	private Double dacQty;
	
	
	public double getDacQty() {
		return dacQty;
	}
	public void setDacQty(double dacQty) {
		this.dacQty = dacQty;
	}
	public OutputBean() {
		super();
	}
	public OutputBean(String dbOutputId, Double qty, Double badQty, Double runTime, Double stopTime, Long stopTimes,
			Date nowDateTime) {
		this.dbOutputId=dbOutputId;
		this.qty=qty;
		this.badQty=badQty;
		this.runTime=runTime;
		this.stopTime=stopTime;
		this.stopTimes=stopTimes;
		this.nowDateTime=nowDateTime;
	}
	public OutputBean(String dbOutputId, Double qty, Double badQty, Double runTime, Double stopTime, Long stopTimes,
			Date nowDateTime,Double dacQty) {
		this.dbOutputId=dbOutputId;
		this.qty=qty;
		this.badQty=badQty;
		this.runTime=runTime;
		this.stopTime=stopTime;
		this.stopTimes=stopTimes;
		this.nowDateTime=nowDateTime;
		this.dacQty=dacQty;
	}
	public String getDbOutputId() {
		return dbOutputId;
	}
	public void setDbOutputId(String dbOutputId) {
		this.dbOutputId = dbOutputId;
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
	public Double getRunTime() {
		return runTime;
	}
	public void setRunTime(Double runTime) {
		this.runTime = runTime;
	}
	public Double getStopTime() {
		return stopTime;
	}
	public void setStopTime(Double stopTime) {
		this.stopTime = stopTime;
	}
	public long getStopTimes() {
		return stopTimes;
	}
	public void setStopTimes(long stopTimes) {
		this.stopTimes = stopTimes;
	}
	public Date getNowDateTime() {
		return nowDateTime;
	}
	public void setNowDateTime(Date nowDateTime) {
		this.nowDateTime = nowDateTime;
	}
	public String getWorkorder() {
		return workorder;
	}
	public void setWorkorder(String workorder) {
		this.workorder = workorder;
	}
	public String getStim() {
		return stim;
	}
	public void setStim(String stim) {
		this.stim = stim;
	}
	
	
	
}
