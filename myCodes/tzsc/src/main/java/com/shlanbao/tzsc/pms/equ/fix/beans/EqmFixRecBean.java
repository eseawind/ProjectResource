package com.shlanbao.tzsc.pms.equ.fix.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

//设备维修记录bean
public class EqmFixRecBean {
	private String id;
	private String equipId;//设备
	private String equipName;
	private String requsrId;//请求用户
	private String requsrName;
	private String resusrId;//响应用户
	private String resusrName;
	private String shiftId;//班次
	private String shiftName;
	private String remark;//备注
	private Long fixtype;//维修类型
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String reqtim;//请求时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String restim;//响应时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String fixtim;//修复时间
	private long sendflag;
	private String state;//状态
	private String appointUsrId;//指定人员ID
	private String appointUsrName;
	private String appointTime;//指派时间
	
	private String bjName;//备件名称
	private String bjNum;//备件使用数量
	private String ghName;//跟换人姓名
	private String isR;//是否反馈
	private String bjType;//备件type
	
	
	public String getBjType() {
		return bjType;
	}
	public void setBjType(String bjType) {
		this.bjType = bjType;
	}
	public String getBjName() {
		return bjName;
	}
	public void setBjName(String bjName) {
		this.bjName = bjName;
	}
	public String getBjNum() {
		return bjNum;
	}
	public void setBjNum(String bjNum) {
		this.bjNum = bjNum;
	}
	public String getGhName() {
		return ghName;
	}
	public void setGhName(String ghName) {
		this.ghName = ghName;
	}
	public String getIsR() {
		return isR;
	}
	public void setIsR(String isR) {
		this.isR = isR;
	}
	public String getId() {
		return id;
	}
	public Long getFixtype() {
		return fixtype;
	}
	public void setFixtype(Long fixtype) {
		this.fixtype = fixtype;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEquipId() {
		return equipId;
	}
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	public String getEquipName() {
		return equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	public String getRequsrId() {
		return requsrId;
	}
	public void setRequsrId(String requsrId) {
		this.requsrId = requsrId;
	}
	public String getRequsrName() {
		return requsrName;
	}
	public void setRequsrName(String requsrName) {
		this.requsrName = requsrName;
	}
	public String getResusrId() {
		return resusrId;
	}
	public void setResusrId(String resusrId) {
		this.resusrId = resusrId;
	}
	public String getResusrName() {
		return resusrName;
	}
	public void setResusrName(String resusrName) {
		this.resusrName = resusrName;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getReqtim() {
		return reqtim;
	}
	public void setReqtim(String reqtim) {
		this.reqtim = reqtim;
	}
	public String getRestim() {
		return restim;
	}
	public void setRestim(String restim) {
		this.restim = restim;
	}
	public String getFixtim() {
		return fixtim;
	}
	public void setFixtim(String fixtim) {
		this.fixtim = fixtim;
	}
	public long getSendflag() {
		return this.sendflag;	
	}
	public void setSendflag(long sendflag) {
		this.sendflag = sendflag;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAppointUsrId() {
		return appointUsrId;
	}
	public void setAppointUsrId(String appointUsrId) {
		this.appointUsrId = appointUsrId;
	}
	public String getAppointUsrName() {
		return appointUsrName;
	}
	public void setAppointUsrName(String appointUsrName) {
		this.appointUsrName = appointUsrName;
	}
	public String getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(String appointTime) {
		this.appointTime = appointTime;
	}
	
}
