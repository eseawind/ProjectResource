package com.shlanbao.tzsc.pms.equ.sbglplan.beans;

public class EqmPlanBean {
	private String planId;//计划主键ID
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
	private String scheduleDate;//轮保日期
	private String matId;//牌号
	private String mdShiftId;//班次
	private String maintenanceContent;//备注
	private String metcId;//轮保部位;绑定的 项目大类(父节点，根据该节点可以查询出说有 保养项)
	private String wheelCoverType;//工单状态
	private String del;
	private String planner;//制定人
	
	private String date_plan1;
	private String date_plan2;
	
	public String getDate_plan1() {
		return date_plan1;
	}
	public void setDate_plan1(String date_plan1) {
		this.date_plan1 = date_plan1;
	}
	public String getDate_plan2() {
		return date_plan2;
	}
	public void setDate_plan2(String date_plan2) {
		this.date_plan2 = date_plan2;
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
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
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
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanner() {
		return planner;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	
}
