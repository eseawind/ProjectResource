package com.shlanbao.tzsc.pms.equ.wcplan.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class EqmWheelCovelPlanBean {

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
	private String id;
	private String planNo;
	private String planName;
	private String eqmId;//设备id
	private String eqmName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String scheduleDate;
	private String mdShiftId;
	private String mdShiftName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String scheduleEndDate;
	private String maintenanceLength;
	private String maintenanceContent;
	private String maintenanceType;
	private String locationCode;
	private String wheelCoverType;
	private String wheelCoverTypeDes;
	private String wheelParts;
	private String period;
	private String remindCycle;
	private String dutyPeopleId;
	private String dutyPeopleName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String endMaintainDate;
	private String auditUserId;//审核人ID
	private String date1;
	private String date2;
	private String zrId;//主任ID
	private String glId;//车间管理ID
	private String zrName;//主任角色
	private String glName;//车间管理角色
	
	private String attr1;
	private String attr2;
	
	public String getZrId() {
		return zrId;
	}
	public void setZrId(String zrId) {
		this.zrId = zrId;
	}
	public String getGlId() {
		return glId;
	}
	public void setGlId(String glId) {
		this.glId = glId;
	}
	public String getzrName() {
		return zrName;
	}
	public void setzrName(String zrName) {
		this.zrName = zrName;
	}
	public String getglName() {
		return glName;
	}
	public void setglName(String glName) {
		this.glName = glName;
	}
	public String getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getEqmName() {
		return eqmName;
	}
	public String getEqmId() {
		return eqmId;
	}
	public void setEqmId(String eqmId) {
		this.eqmId = eqmId;
	}
	public void setEqmName(String eqmName) {
		this.eqmName = eqmName;
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
	public String getMaintenanceType() {
		return maintenanceType;
	}
	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}
	public String getWheelCoverTypeDes() {
		return wheelCoverTypeDes;
	}
	public void setWheelCoverTypeDes(String wheelCoverTypeDes) {
		this.wheelCoverTypeDes = wheelCoverTypeDes;
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
	public String getScheduleEndDate() {
		return scheduleEndDate;
	}
	public void setScheduleEndDate(String scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}
	public String getDutyPeopleId() {
		return dutyPeopleId;
	}
	public void setDutyPeopleId(String dutyPeopleId) {
		this.dutyPeopleId = dutyPeopleId;
	}
	public String getDutyPeopleName() {
		return dutyPeopleName;
	}
	public void setDutyPeopleName(String dutyPeopleName) {
		this.dutyPeopleName = dutyPeopleName;
	}
	public String getEndMaintainDate() {
		return endMaintainDate;
	}
	public void setEndMaintainDate(String endMaintainDate) {
		this.endMaintainDate = endMaintainDate;
	}
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
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
}
