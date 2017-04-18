package com.lanbao.dws.model.wct.dac;

/**
 * 卷烟机实时数据
 * @author shisihai
 *
 */
public class RollerData {
	private String equipmentCode;//设备型号
	private String equipmentType;//设备型号
	private String equipmentEdcs;//设备名
	private String workorderCode;//工单号
	private String orderId;//工单id
	private String shiftName;//班次
	private String teamName;//班组
	private String matName;//牌号
	private Double planQty;//计划产量
	private String unit;
	private String planStim;//计划开始时间
	private String planEtim;//计划结束时间
	private String bthCode;//批次号
	private String stim;//开始时间
	private Double lvbangBzdh;//滤棒标准单耗
	private Double juanyanzhiBzdh;//卷烟纸标准单耗
	private Double shuisongzhiBzdh;//水松纸标准单耗
	private Double lvbangEuval;//滤棒报警上限
	private Double juanyanzhiEuval;//卷烟纸报警上限
	private Double shuisongzhiEuval;//水松纸报警上限
	
	//实时数据部分
	private Integer online;//网络状态  0异常，1正常
	private Double qty;//产量
	private Double badQty;//剔除产量
	private Double lvbangVal;//滤棒消耗
	private Double juanyanzhiVal;//卷烟纸消耗
	private Double shuisongzhiVal;//水松纸消耗
	private Double runTime;//运行时间
	private Double stopTime;//停机时间
	private Integer stopTimes;//停机次数
	private Integer speed;//车速
	private Integer runStatus;//运行状态 -1 断网 0停止 1低速运行 2正常运行
	private Double cy;//储烟设备储量
	private Integer cy_cs;//储烟设备车速(1-包装机    6-卷烟机   )
	private String cy_online; //(22-卷烟机运行状态    23-包装机运行状态 )
	
	
	
	public Integer getCy_cs() {
		return cy_cs;
	}
	public void setCy_cs(Integer cy_cs) {
		this.cy_cs = cy_cs;
	}

	public String getCy_online() {
		return cy_online;
	}

	public void setCy_online(String cy_online) {
		this.cy_online = cy_online;
	}



	public Double getCy() {
		return cy;
	}



	public void setCy(Double cy) {
		this.cy = cy;
	}



	public RollerData() {
	}
	
	

	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public RollerData(Double qty, double badQty, Double lvbangVal, Double juanyanzhiVal, Double shuisongzhiVal,
			Double runTime, Double stopTime, Integer stopTimes, Integer speed, Integer online,Double cy,Integer cy_cs,String cy_online) {
		this.qty=qty;
		this.badQty=badQty;
		this.lvbangVal=lvbangVal;
		this.juanyanzhiVal=juanyanzhiVal;
		this.shuisongzhiVal=shuisongzhiVal;
		this.runTime=runTime;
		this.stopTime=stopTime;
		this.stopTimes=stopTimes;
		this.speed=speed;
		this.online=online;
		this.cy=cy;
		this.cy_cs=cy_cs;
		this.cy_online=cy_online;
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
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
	public Double getLvbangBzdh() {
		return lvbangBzdh;
	}
	public void setLvbangBzdh(Double lvbangBzdh) {
		this.lvbangBzdh = lvbangBzdh;
	}
	public Double getJuanyanzhiBzdh() {
		return juanyanzhiBzdh;
	}
	public void setJuanyanzhiBzdh(Double juanyanzhiBzdh) {
		this.juanyanzhiBzdh = juanyanzhiBzdh;
	}
	public Double getShuisongzhiBzdh() {
		return shuisongzhiBzdh;
	}
	public void setShuisongzhiBzdh(Double shuisongzhiBzdh) {
		this.shuisongzhiBzdh = shuisongzhiBzdh;
	}
	public Double getLvbangEuval() {
		return lvbangEuval;
	}
	public void setLvbangEuval(Double lvbangEuval) {
		this.lvbangEuval = lvbangEuval;
	}
	public Double getJuanyanzhiEuval() {
		return juanyanzhiEuval;
	}
	public void setJuanyanzhiEuval(Double juanyanzhiEuval) {
		this.juanyanzhiEuval = juanyanzhiEuval;
	}
	public Double getShuisongzhiEuval() {
		return shuisongzhiEuval;
	}
	public void setShuisongzhiEuval(Double shuisongzhiEuval) {
		this.shuisongzhiEuval = shuisongzhiEuval;
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
	public Double getLvbangVal() {
		return lvbangVal;
	}
	public void setLvbangVal(Double lvbangVal) {
		this.lvbangVal = lvbangVal;
	}
	public Double getJuanyanzhiVal() {
		return juanyanzhiVal;
	}
	public void setJuanyanzhiVal(Double juanyanzhiVal) {
		this.juanyanzhiVal = juanyanzhiVal;
	}
	public Double getShuisongzhiVal() {
		return shuisongzhiVal;
	}
	public void setShuisongzhiVal(Double shuisongzhiVal) {
		this.shuisongzhiVal = shuisongzhiVal;
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
	public Integer getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}
	
}
