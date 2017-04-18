package com.shlanbao.tzsc.pms.equ.eqpCheck.bean;

import java.util.Date;

public class EqmCheckPlanBean {
	private String id;
	private String shift;//班次
	private String team;//班组
	private String date_plan1;//点检计划开始日期
	private String date_plan2;//点检计划结束日期
	private String mdEqpCategory;//设备类型
	private String del;//删除标识
	private String name;//一级名
	private String status;//完成状态 0 未完成  1完成
	private String dicID;//点检标准
	private String planNo;//计划编号
	private String dateP;//计划时间
	private String workshop;//车间
	
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	public String getDateP() {
		return dateP;
	}
	public void setDateP(String dateP) {
		this.dateP = dateP;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
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
	public String getMdEqpCategory() {
		return mdEqpCategory;
	}
	public void setMdEqpCategory(String mdEqpCategory) {
		this.mdEqpCategory = mdEqpCategory;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDicID() {
		return dicID;
	}
	public void setDicID(String dicID) {
		this.dicID = dicID;
	}
	
	
}
