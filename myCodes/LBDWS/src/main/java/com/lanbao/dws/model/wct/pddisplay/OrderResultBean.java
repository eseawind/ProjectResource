package com.lanbao.dws.model.wct.pddisplay;

import javax.persistence.Entity;

/**
 * 工作实际bean
 * @author shisihai
 *
 */
@Entity(name="SCH_WORKORDER")
public class OrderResultBean {
	private String id;//工单号
	private String eqpName;//设备名称
	private String eqpId;//设备id
	private String pdDate;//计划日期
	private String shiftName;//班次
	private String shiftId;
	private String teamName;//班组
	private String teamId;
	private String eqpCg;
	private String matName;//牌号
	private Double pQty;//计划产量
	private String unitName;//计划产量单位
	private Double rqty;//实际产量
	private String rQtyUnit;//实际产量单位
	private Double badQty;//剔除产量
	private Double runTime;//运行时长
	private Double stopTime;//停机时长
	private Double stopTimes;//停机次数
	private String timeUnit;//时间单位
	
	private String realStim;
	private String realEtim;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	public String getPdDate() {
		return pdDate;
	}
	public void setPdDate(String pdDate) {
		this.pdDate = pdDate;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getEqpCg() {
		return eqpCg;
	}
	public void setEqpCg(String eqpCg) {
		this.eqpCg = eqpCg;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public Double getpQty() {
		return pQty;
	}
	public void setpQty(Double pQty) {
		this.pQty = pQty;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Double getRqty() {
		return rqty;
	}
	public void setRqty(Double rqty) {
		this.rqty = rqty;
	}
	public String getrQtyUnit() {
		return rQtyUnit;
	}
	public void setrQtyUnit(String rQtyUnit) {
		this.rQtyUnit = rQtyUnit;
	}
	public Double getBadQty() {
		return badQty;
	}
	public void setBadQty(Double badQty) {
		this.badQty = badQty;
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
	public Double getStopTimes() {
		return stopTimes;
	}
	public void setStopTimes(Double stopTimes) {
		this.stopTimes = stopTimes;
	}
	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	public String getRealStim() {
		return realStim;
	}
	public void setRealStim(String realStim) {
		this.realStim = realStim;
	}
	public String getRealEtim() {
		return realEtim;
	}
	public void setRealEtim(String realEtim) {
		this.realEtim = realEtim;
	}
	
}
