package com.shlanbao.tzsc.pms.equ.sbglplan.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class EquRhPlanBean {
	private String id;//计划主键ID
	private String planId;//计划主键ID 这里2个都是
	private String eqmId;//设备id
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String scheduleEndDate;
	private String maintenanceLength;
	private String locationCode;
	private String wheelCoverTypeDes;
	private String wheelParts;
	private String period;
	private String remindCycle;
	private String dutyPeopleId;
	private String dutyPeopleName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String endMaintainDate;
	private String planCode;//计划编号
	private String planName;//计划名称
	private String equipmentLxId;//设备类型
	private String equipmentLxName;
	private String equipmentXhId;//设备型号
	private String equipmentXhName;
	private String equipmentId;//设备ID
	private String equipmentName;
	private String maintenanceType;//轮保类别
	private String equipmentMinute;//轮保时长
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String scheduleDate;//轮保日期
	private String matId;//牌号
	private String mdShiftId;//班次
	private String maintenanceContent;//备注
	private String metcId;//轮保部位;绑定的 项目大类(父节点，根据该节点可以查询出说有 保养项)
	private String wheelCoverType;//工单状态
	private String del;
	private String planner;//制定人
	
	private String eqmName;
	private String mdShiftName;
	private String planNo;//编号
	private String date1;
	private String date2;
	
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getEquipmentLxId() {
		return equipmentLxId;
	}
	public void setEquipmentLxId(String equipmentLxId) {
		this.equipmentLxId = equipmentLxId;
	}
	public String getEquipmentLxName() {
		return equipmentLxName;
	}
	public void setEquipmentLxName(String equipmentLxName) {
		this.equipmentLxName = equipmentLxName;
	}
	public String getEquipmentXhId() {
		return equipmentXhId;
	}
	public void setEquipmentXhId(String equipmentXhId) {
		this.equipmentXhId = equipmentXhId;
	}
	public String getEquipmentXhName() {
		return equipmentXhName;
	}
	public void setEquipmentXhName(String equipmentXhName) {
		this.equipmentXhName = equipmentXhName;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getMaintenanceType() {
		return maintenanceType;
	}
	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}
	public String getEquipmentMinute() {
		return equipmentMinute;
	}
	public void setEquipmentMinute(String equipmentMinute) {
		this.equipmentMinute = equipmentMinute;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getMatId() {
		return matId;
	}
	public void setMatId(String matId) {
		this.matId = matId;
	}
	public String getMdShiftId() {
		return mdShiftId;
	}
	public void setMdShiftId(String mdShiftId) {
		this.mdShiftId = mdShiftId;
	}
	public String getMaintenanceContent() {
		return maintenanceContent;
	}
	public void setMaintenanceContent(String maintenanceContent) {
		this.maintenanceContent = maintenanceContent;
	}
	public String getMetcId() {
		return metcId;
	}
	public void setMetcId(String metcId) {
		this.metcId = metcId;
	}
	public String getWheelCoverType() {
		return wheelCoverType;
	}
	public void setWheelCoverType(String wheelCoverType) {
		this.wheelCoverType = wheelCoverType;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getPlanner() {
		return planner;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
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
	public String getEqmName() {
		return eqmName;
	}
	public void setEqmName(String eqmName) {
		this.eqmName = eqmName;
	}
	public String getMdShiftName() {
		return mdShiftName;
	}
	public void setMdShiftName(String mdShiftName) {
		this.mdShiftName = mdShiftName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqmId() {
		return eqmId;
	}
	public void setEqmId(String eqmId) {
		this.eqmId = eqmId;
	}
	public String getScheduleEndDate() {
		return scheduleEndDate;
	}
	public void setScheduleEndDate(String scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}
	public String getMaintenanceLength() {
		return maintenanceLength;
	}
	public void setMaintenanceLength(String maintenanceLength) {
		this.maintenanceLength = maintenanceLength;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getWheelCoverTypeDes() {
		return wheelCoverTypeDes;
	}
	public void setWheelCoverTypeDes(String wheelCoverTypeDes) {
		this.wheelCoverTypeDes = wheelCoverTypeDes;
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
	
}
