package com.shlanbao.tzsc.wct.eqm.covelplan.beans;

import java.util.Date;



import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class EqpWCPBean {
	private String id;
	private String sysUserByDutyPeople;
	private String sysUserByDutyPeopleName;
	private String sysUserByCreate;
	private String sysUserByCreateName;
	private String mdEquipmentId;
	private String equipmentName;
	private String mdShiftId;
	private String mdShiftName;
	private String workShopId;
	private String workShopName;
	
	private String planNo;
	private String planName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String scheduleDate;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String scheduleEndDate;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String startTime;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String endTime;
	private String maintenanceLength;
	private String maintenanceContent;
	private String planner;
	private String maintenanceType;
	private String locationCode;
	private String wheelCoverType;
	private String wheelParts;
	private String period;
	private String remindCycle;
	private String dutyPeopleName;
	private Date endMaintainDate;
	private String del;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String createDate;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;

	private String czgUserId;//操作工ID
	private String czgUserName;//操作工姓名
	private String czgDate;//操作工操作时间
	private String lbgUserId;//轮保工ID
	private String lbgUserName;//轮保工姓名
	private String lbgDate;//轮保工操作时间
	
	private String shgUserId;//审核工姓名
	private String shgUserName;//审核工姓名
	private String shgDate;//审核工操作时间
	private String 	isCzgFinsh;//操作工是否完成
	private String 	isLbgFinsh;//轮保工是否完成
	private String 	isShgFinsh;//审核工是否完成
	private String modifyDate;//修改时间
	
	
	public String getWorkShopId() {
		return workShopId;
	}
	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getSysUserByDutyPeopleName() {
		return sysUserByDutyPeopleName;
	}
	public void setSysUserByDutyPeopleName(String sysUserByDutyPeopleName) {
		this.sysUserByDutyPeopleName = sysUserByDutyPeopleName;
	}
	
	public String getSysUserByDutyPeople() {
		return sysUserByDutyPeople;
	}
	public void setSysUserByDutyPeople(String sysUserByDutyPeople) {
		this.sysUserByDutyPeople = sysUserByDutyPeople;
	}
	public String getSysUserByCreate() {
		return sysUserByCreate;
	}
	public void setSysUserByCreate(String sysUserByCreate) {
		this.sysUserByCreate = sysUserByCreate;
	}
	public String getSysUserByCreateName() {
		return sysUserByCreateName;
	}
	public void setSysUserByCreateName(String sysUserByCreateName) {
		this.sysUserByCreateName = sysUserByCreateName;
	}
	
	public String getMdEquipmentId() {
		return mdEquipmentId;
	}
	public void setMdEquipmentId(String mdEquipmentId) {
		this.mdEquipmentId = mdEquipmentId;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getMdShiftId() {
		return mdShiftId;
	}
	public void setMdShiftId(String mdShiftId) {
		this.mdShiftId = mdShiftId;
	}
	public String getMdShiftName() {
		return mdShiftName;
	}
	public void setMdShiftName(String mdShiftName) {
		this.mdShiftName = mdShiftName;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getScheduleEndDate() {
		return scheduleEndDate;
	}
	public void setScheduleEndDate(String scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
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
	public String getMaintenanceLength() {
		return maintenanceLength;
	}
	public void setMaintenanceLength(String maintenanceLength) {
		this.maintenanceLength = maintenanceLength;
	}
	public String getMaintenanceContent() {
		return maintenanceContent;
	}
	public void setMaintenanceContent(String maintenanceContent) {
		this.maintenanceContent = maintenanceContent;
	}
	public String getPlanner() {
		return planner;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	public String getMaintenanceType() {
		return maintenanceType;
	}
	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getWheelCoverType() {
		return wheelCoverType;
	}
	public void setWheelCoverType(String wheelCoverType) {
		this.wheelCoverType = wheelCoverType;
	}
	public String getWheelParts() {
		return wheelParts;
	}
	public void setWheelParts(String wheelParts) {
		this.wheelParts = wheelParts;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getRemindCycle() {
		return remindCycle;
	}
	public void setRemindCycle(String remindCycle) {
		this.remindCycle = remindCycle;
	}
	public String getDutyPeopleName() {
		return dutyPeopleName;
	}
	public void setDutyPeopleName(String dutyPeopleName) {
		this.dutyPeopleName = dutyPeopleName;
	}
	public Date getEndMaintainDate() {
		return endMaintainDate;
	}
	public void setEndMaintainDate(Date endMaintainDate) {
		this.endMaintainDate = endMaintainDate;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public String getAttr3() {
		return attr3;
	}
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	public String getAttr4() {
		return attr4;
	}
	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}
	public String getCzgUserId() {
		return czgUserId;
	}
	public void setCzgUserId(String czgUserId) {
		this.czgUserId = czgUserId;
	}
	public String getCzgUserName() {
		return czgUserName;
	}
	public void setCzgUserName(String czgUserName) {
		this.czgUserName = czgUserName;
	}
	public String getLbgUserId() {
		return lbgUserId;
	}
	public void setLbgUserId(String lbgUserId) {
		this.lbgUserId = lbgUserId;
	}
	public String getLbgUserName() {
		return lbgUserName;
	}
	public void setLbgUserName(String lbgUserName) {
		this.lbgUserName = lbgUserName;
	}
	public String getCzgDate() {
		return czgDate;
	}
	public void setCzgDate(String czgDate) {
		this.czgDate = czgDate;
	}
	public String getLbgDate() {
		return lbgDate;
	}
	public void setLbgDate(String lbgDate) {
		this.lbgDate = lbgDate;
	}
	public String getShgUserId() {
		return shgUserId;
	}
	public void setShgUserId(String shgUserId) {
		this.shgUserId = shgUserId;
	}
	public String getShgUserName() {
		return shgUserName;
	}
	public void setShgUserName(String shgUserName) {
		this.shgUserName = shgUserName;
	}
	public String getShgDate() {
		return shgDate;
	}
	public void setShgDate(String shgDate) {
		this.shgDate = shgDate;
	}
	public String getIsCzgFinsh() {
		return isCzgFinsh;
	}
	public void setIsCzgFinsh(String isCzgFinsh) {
		this.isCzgFinsh = isCzgFinsh;
	}
	public String getIsLbgFinsh() {
		return isLbgFinsh;
	}
	public void setIsLbgFinsh(String isLbgFinsh) {
		this.isLbgFinsh = isLbgFinsh;
	}
	public String getIsShgFinsh() {
		return isShgFinsh;
	}
	public void setIsShgFinsh(String isShgFinsh) {
		this.isShgFinsh = isShgFinsh;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
