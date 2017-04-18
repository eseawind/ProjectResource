package com.shlanbao.tzsc.wct.sch.stat.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * 工单产出数据 
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class OutputBean {
	private String id;
	private String date;
	private String shift;//班次
	private String team;//班组
	private String equipment;//设备
	private String workorder;//工单号
	private String mat;//牌号
	private Double qty;//产量
	private Double badQty;//剔除量
	private String unit;//产量单位
	private Double runTime;//运行时间
	private Double stopTime;//停机时间
	private Long stopTimes;//停机次数
	private Long isFeedback;//是否反馈
	@DateFmtAnnotation(fmtPattern="MM-dd HH:mm:ss")
	private String feedbackTime;//反馈时间
	private String feedbackUser;//反馈人
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getWorkorder() {
		return workorder;
	}
	public void setWorkorder(String workorder) {
		this.workorder = workorder;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public Long getStopTimes() {
		return stopTimes;
	}
	public void setStopTimes(Long stopTimes) {
		this.stopTimes = stopTimes;
	}
	public Long getIsFeedback() {
		return isFeedback;
	}
	public void setIsFeedback(Long isFeedback) {
		this.isFeedback = isFeedback;
	}
	public String getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	public String getFeedbackUser() {
		return feedbackUser;
	}
	public void setFeedbackUser(String feedbackUser) {
		this.feedbackUser = feedbackUser;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
