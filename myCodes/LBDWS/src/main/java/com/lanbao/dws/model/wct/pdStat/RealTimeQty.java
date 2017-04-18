package com.lanbao.dws.model.wct.pdStat;

import javax.persistence.Entity;

import com.lanbao.dws.common.tools.MathUtil;
import com.lanbao.dws.common.tools.StringUtil;

/**
 * 实时产量
 * @author shisihai
 */
@Entity(name="SCH_WORKORDER")
public class RealTimeQty {
	private String orderType;//工单类型
	private String eqpCode;//设备编号
	private String eqpName;//设备名称
	private String mat;//牌号
	private String shiftName;//班次
	private String teamName;//班组
	private Double pqty;//计划产量
	private String pUnit;//计划产量单位
	private Double realQty;//实际产量
	private Double badQty;//剔除产量
	private String badProcessVal;//剔除产量进度条（用于显示剔除产量进度条长度）
	private String processVal;//进度条百分比（用于显示实时产量进度条长度）
	private String realUnit;//实际产量单位
	private Double runTime;//运行时长
	private String runUnit;//时间单位
	private String orderDesc;//产量降序
	private String orderAsc;//产量升序
	//剔除/计划实际产量
	public String getBadProcessVal() {
		if(StringUtil.isDouble(getBadQty()) && StringUtil.isDouble(getRealQty()) && getRealQty()>0){
			badProcessVal=MathUtil.roundHalfUp(getBadQty()/getRealQty(), 2)*100+"%";
		}else{
			badProcessVal="0%";
		}
		return badProcessVal;
	}
	public void setBadProcessVal(String badProcessVal) {
		this.badProcessVal = badProcessVal;
	}
	public Double getBadQty() {
		return badQty;
	}
	public void setBadQty(Double badQty) {
		this.badQty = badQty;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getOrderAsc() {
		return orderAsc;
	}
	public void setOrderAsc(String orderAsc) {
		this.orderAsc = orderAsc;
	}
	public String getEqpName() {
		return eqpName;
	}
	public String getMat() {
		return mat;
	}

	public String getProcessVal() {
		if(StringUtil.isDouble(getRealQty()) && StringUtil.isDouble(getPqty()) && getRealQty()>0){
			processVal=MathUtil.roundHalfUp(getRealQty()/getPqty(), 2)*100+"%";
		}else{
			processVal="0%";
		}
		return processVal;
	}
	public void setProcessVal(String processVal) {
		this.processVal = processVal;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}

	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getEqpCode() {
		return eqpCode;
	}
	public void setEqpCode(String eqpCode) {
		this.eqpCode = eqpCode;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
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
	public Double getPqty() {
		return pqty;
	}
	public void setPqty(Double pqty) {
		this.pqty = pqty;
	}
	public String getpUnit() {
		return pUnit;
	}
	public void setpUnit(String pUnit) {
		this.pUnit = pUnit;
	}
	public Double getRealQty() {
		return realQty;
	}
	public void setRealQty(Double realQty) {
		this.realQty = realQty;
	}
	public String getRealUnit() {
		return realUnit;
	}
	public void setRealUnit(String realUnit) {
		this.realUnit = realUnit;
	}
	public Double getRunTime() {
		return runTime;
	}
	public void setRunTime(Double runTime) {
		this.runTime = runTime;
	}
	public String getRunUnit() {
		return runUnit;
	}
	public void setRunUnit(String runUnit) {
		this.runUnit = runUnit;
	}
	
	
	
}
