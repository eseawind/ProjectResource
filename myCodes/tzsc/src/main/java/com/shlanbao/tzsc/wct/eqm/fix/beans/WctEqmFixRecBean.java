package com.shlanbao.tzsc.wct.eqm.fix.beans;

import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

//设备维修记录bean
public class WctEqmFixRecBean {
	private String id;
	private String planId;//计划ID
	private String planName;
	private String planParamId;//计划对应的维护项ID
	private String planParamName;
	//设备
	private String equipId;
	private String equipmentCode;
	private String equipName;
	//请求用户
	private String requsrId;
	private String requsrName;
	//响应用户
	private String resusrId;
	private String resusrName;
	//班次
	private String shiftId;
	private String shiftName;
	//其他信息
	private String remark;
	private Long fixtype; //修复类型
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String reqtim;//请求时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String restim;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String fixtim;//处理完成时间 修复时间
	private String fixUsrId;//完成人员
	private String fixUsrName;//完成人员
	private long sendflag;
	private String state;//状态
	private String appointUsrId;//指定人员ID
	private String appointUsrName;
	private String appointTime;//指派时间
	private String sysEqpTypeId;//数据字典对应的ID
	
	private String sbUserId;//当前登录人
	private String wctEqmFixRecId;//主键ID
	private String wxclState;//状态
	private String wxclRemark;//备注
	
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
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanParamId() {
		return planParamId;
	}
	public void setPlanParamId(String planParamId) {
		this.planParamId = planParamId;
	}
	public String getPlanParamName() {
		return planParamName;
	}
	public void setPlanParamName(String planParamName) {
		this.planParamName = planParamName;
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
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getSysEqpTypeId() {
		return sysEqpTypeId;
	}
	public void setSysEqpTypeId(String sysEqpTypeId) {
		this.sysEqpTypeId = sysEqpTypeId;
	}
	public String getFixUsrId() {
		return fixUsrId;
	}
	public void setFixUsrId(String fixUsrId) {
		this.fixUsrId = fixUsrId;
	}
	public String getFixUsrName() {
		return fixUsrName;
	}
	public void setFixUsrName(String fixUsrName) {
		this.fixUsrName = fixUsrName;
	}
	public String getSbUserId() {
		return sbUserId;
	}
	public void setSbUserId(String sbUserId) {
		this.sbUserId = sbUserId;
	}
	public String getWctEqmFixRecId() {
		return wctEqmFixRecId;
	}
	public void setWctEqmFixRecId(String wctEqmFixRecId) {
		this.wctEqmFixRecId = wctEqmFixRecId;
	}
	public String getWxclState() {
		return wxclState;
	}
	public void setWxclState(String wxclState) {
		this.wxclState = wxclState;
	}
	public String getWxclRemark() {
		return wxclRemark;
	}
	public void setWxclRemark(String wxclRemark) {
		this.wxclRemark = wxclRemark;
	}
	
}
