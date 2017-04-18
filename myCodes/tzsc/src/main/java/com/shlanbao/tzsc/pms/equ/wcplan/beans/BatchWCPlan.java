package com.shlanbao.tzsc.pms.equ.wcplan.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class BatchWCPlan {

	private String eqmId;//设备id
	private String eqmText;
	private String scheduleDate;//计划开始时间
	private String shiftText;//
	private String shiftId;//班次id
	private String matId;//牌号
	private String matText;
	private String equipmentMinute;//计划时长
	private String ruleId;//保养规则
	private String ruleText;
	private String date_plan1;
	private String date_plan2;
	private String eqp_category;//设备类型
	private String eqp_typeId ;//设备机型
	private String eqp_typeName;

	public String getEqp_category() {
		return eqp_category;
	}
	public void setEqp_category(String eqp_category) {
		this.eqp_category = eqp_category;
	}
	public String getEqp_typeId() {
		return eqp_typeId;
	}
	public void setEqp_typeId(String eqp_typeId) {
		this.eqp_typeId = eqp_typeId;
	}
	public String getEqp_typeName() {
		return eqp_typeName;
	}
	public void setEqp_typeName(String eqp_typeName) {
		this.eqp_typeName = eqp_typeName;
	}
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
	public String getEqmText() {
		return eqmText;
	}
	public void setEqmText(String eqmText) {
		this.eqmText = eqmText;
	}
	public String getShiftText() {
		return shiftText;
	}
	public void setShiftText(String shiftText) {
		this.shiftText = shiftText;
	}
	public String getMatText() {
		return matText;
	}
	public void setMatText(String matText) {
		this.matText = matText;
	}
	public String getRuleText() {
		return ruleText;
	}
	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getEquipmentMinute() {
		return equipmentMinute;
	}
	public void setEquipmentMinute(String equipmentMinute) {
		this.equipmentMinute = equipmentMinute;
	}
	public String getEqmId() {
		return eqmId;
	}
	public void setEqmId(String eqmId) {
		this.eqmId = eqmId;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getMatId() {
		return matId;
	}
	public void setMatId(String matId) {
		this.matId = matId;
	}
	
}
