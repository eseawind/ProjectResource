package com.shlanbao.tzsc.wct.sch.stat.beans;

public class EqpRuntime {
	/*
	private Integer eqpId;//设备ID
	private String eqpCod;
	private String eqpDes;
	private String date;//日期
	private String shift;//班次
	private String team;//班组
	private Double planOut;//计划产量
	private Double curOut;//当前产量
	private Double curRej;//当前剔除
	private String uom;//单位
	 */	
	private String orderNumber;
	private String eqpCod;
	private String eqpName;
	private String mat;
	private String matId;//产品id
	private String date;//日期
	private String shift;//班次
	private String shiftID;
	private String team;//班组
	private String teamID;//
	private Double planOut;//计划产量
	private Double curOut;//当前产量
	private Double badQty;//当前剔除
	private String unit;//单位
	// add by luther.zhang 20150312
	private String eqpId;//设备ID
	private String prodCode;//工单号
	private String schWorkorderId;//工单主键ID
	//end
	
	public String getEqpId() {
		return eqpId;
	}
	public String getMatId() {
		return matId;
	}
	public void setMatId(String matId) {
		this.matId = matId;
	}
	public String getShiftID() {
		return shiftID;
	}
	public void setShiftID(String shiftID) {
		this.shiftID = shiftID;
	}
	public String getTeamID() {
		return teamID;
	}
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getEqpCod() {
		return eqpCod;
	}
	public void setEqpCod(String eqpCod) {
		this.eqpCod = eqpCod;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public Double getPlanOut() {
		return planOut;
	}
	public void setPlanOut(Double planOut) {
		this.planOut = planOut;
	}
	public Double getCurOut() {
		return curOut;
	}
	public void setCurOut(Double curOut) {
		this.curOut = curOut;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public EqpRuntime() {
		// TODO Auto-generated constructor stub
	}
	
	public EqpRuntime(String eqpCod, String eqpName, String mat, String date,
			String shift, String team, Double planOut, Double curOut,
			Double badQty, String unit) {
		super();
		this.eqpCod = eqpCod;
		this.eqpName = eqpName;
		this.mat = mat;
		this.date = date;
		this.shift = shift;
		this.team = team;
		this.planOut = planOut;
		this.curOut = curOut;
		this.badQty = badQty;
		this.unit = unit;
	}
	
	public EqpRuntime(String eqpCod, String eqpName, String mat, String date,
			String shift, String team, Double planOut, String unit) {
		super();
		this.eqpCod = eqpCod;
		this.eqpName = eqpName;
		this.mat = mat;
		this.date = date;
		this.shift = shift;
		this.team = team;
		this.planOut = planOut;
		this.unit = unit;
	}

	public EqpRuntime(String eqpCod, String eqpName, String mat, String date,
			String shift, String team, Double planOut, String unit,
			String prodCode, String schWorkorderId) {
		super();
		this.eqpCod = eqpCod;
		this.eqpName = eqpName;
		this.mat = mat;
		this.date = date;
		this.shift = shift;
		this.team = team;
		this.planOut = planOut;
		this.unit = unit;
		this.prodCode = prodCode;
		this.schWorkorderId = schWorkorderId;
	}
	
	public EqpRuntime(String eqpCod, Double curOut) {
		super();
		this.eqpCod = eqpCod;
		this.curOut = curOut;
	}
	
	public EqpRuntime(String eqpCod, Double curOut, Double badQty) {
		super();
		this.eqpCod = eqpCod;
		this.curOut = curOut;
		this.badQty = badQty;
	}
	public Double getBadQty() {
		return badQty;
	}
	public void setBadQty(Double badQty) {
		this.badQty = badQty;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getSchWorkorderId() {
		return schWorkorderId;
	}
	public void setSchWorkorderId(String schWorkorderId) {
		this.schWorkorderId = schWorkorderId;
	}
	
}
