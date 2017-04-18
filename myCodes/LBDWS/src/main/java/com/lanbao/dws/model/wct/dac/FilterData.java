package com.lanbao.dws.model.wct.dac;

public class FilterData {
	private String equipmentCode;//设备编号
	private String equipmentType;//设备型号
	private String equipmentEdcs;//设备名称
	private String workorderCode;//工单code
	private String matName;//牌号
	private Double planQty;//计划产量
	private String unit;
	private String planStim;//计划开始时间
	private String planEtim;//计划结束时间
	private String bthCode;//批次号
	private String stim;//开始时间
	private String teamName;//班组
	private String shiftName;//班次
	private Double zpQty;//装盘机
	
	/*辅料*/
	private Double panzhiBzdh;//盘纸标准单耗
	private Double panzhiEuval;//盘纸报警
	
	//实时数据部分
	private Integer online;//网络状态  0异常，1正常
	private Double qty;//产量
	private Double badQty;//剔除产量
	
	private Double panzhiVal;//盘纸消耗
	
	
	private Double runTime;//运行时间
	private Double stopTime;//停机时间
	private Integer stopTimes;//停机次数
	private Integer speed;//车速
	
	
	
	public FilterData(String equipmentCode, Double zpQty, Integer online, Double qty, Double badQty, Double panzhiVal,
			 Double runTime, Double stopTime, Integer stopTimes, Integer speed) {
		super();
		this.equipmentCode = equipmentCode;
		this.zpQty = zpQty;
		this.online = online;
		this.qty = qty;
		this.badQty = badQty;
		this.panzhiVal = panzhiVal;
		this.runTime = runTime;
		this.stopTime = stopTime;
		this.stopTimes = stopTimes;
		this.speed = speed;
	}
	
	
	public FilterData() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getTeamName() {
		return teamName;
	}


	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public String getShiftName() {
		return shiftName;
	}


	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}


	public Double getZpQty() {
		return zpQty;
	}
	public void setZpQty(Double zpQty) {
		this.zpQty = zpQty;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getEquipmentEdcs() {
		return equipmentEdcs;
	}
	public void setEquipmentEdcs(String equipmentEdcs) {
		this.equipmentEdcs = equipmentEdcs;
	}
	public String getWorkorderCode() {
		return workorderCode;
	}
	public void setWorkorderCode(String workorderCode) {
		this.workorderCode = workorderCode;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public Double getPlanQty() {
		return planQty;
	}
	public void setPlanQty(Double planQty) {
		this.planQty = planQty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPlanStim() {
		return planStim;
	}
	public void setPlanStim(String planStim) {
		this.planStim = planStim;
	}
	public String getPlanEtim() {
		return planEtim;
	}
	public void setPlanEtim(String planEtim) {
		this.planEtim = planEtim;
	}
	public String getBthCode() {
		return bthCode;
	}
	public void setBthCode(String bthCode) {
		this.bthCode = bthCode;
	}
	public String getStim() {
		return stim;
	}
	public void setStim(String stim) {
		this.stim = stim;
	}
	public Double getPanzhiBzdh() {
		return panzhiBzdh;
	}
	public void setPanzhiBzdh(Double panzhiBzdh) {
		this.panzhiBzdh = panzhiBzdh;
	}
	public Double getPanzhiEuval() {
		return panzhiEuval;
	}
	public void setPanzhiEuval(Double panzhiEuval) {
		this.panzhiEuval = panzhiEuval;
	}
	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
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
	public Double getPanzhiVal() {
		return panzhiVal;
	}
	public void setPanzhiVal(Double panzhiVal) {
		this.panzhiVal = panzhiVal;
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
	public Integer getStopTimes() {
		return stopTimes;
	}
	public void setStopTimes(Integer stopTimes) {
		this.stopTimes = stopTimes;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
}
