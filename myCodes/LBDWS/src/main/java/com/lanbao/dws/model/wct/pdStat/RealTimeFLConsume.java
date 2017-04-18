package com.lanbao.dws.model.wct.pdStat;

import javax.persistence.Entity;

import com.lanbao.dws.common.tools.MathUtil;

/**
 * 生产统计辅料实时消耗
 * @author shisihai
 *
 */
@Entity(name="SCH_STAT_INPUT")
public class RealTimeFLConsume {
	private String eqpName;//设备名称
	private String shiftName;//班次
	private String teamName;//班组
	private String orderType;//工单类型
	private String eqpCode;//设备编号
	private String unit;//单位
	private String matName;//物料名
	private Double realQty;//当前消耗
	private Double stdQty;//标准消耗
	private String percent;//百分比   realQty/stdQty *100  %
	private String matType;//物料类型
	private Double nowQty;//产量
	private String unitName;//产量单位
	private String matTypeName;//物料类型名称
	private String pDate;//计划日期
	private String pEDate;//计划日期2（用于搜索）
	private String mat;//牌号
	private String eqpCategory;//设备类型
	
	
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getpEDate() {
		return pEDate;
	}
	public void setpEDate(String pEDate) {
		this.pEDate = pEDate;
	}
	public String getEqpCategory() {
		return eqpCategory;
	}
	public void setEqpCategory(String eqpCategory) {
		this.eqpCategory = eqpCategory;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public String getpDate() {
		return pDate;
	}
	public void setpDate(String pDate) {
		this.pDate = pDate;
	}
	public Double getNowQty() {
		return nowQty;
	}
	public void setNowQty(Double nowQty) {
		this.nowQty = nowQty;
	}
	public String getMatTypeName() {
		return matTypeName;
	}
	public void setMatTypeName(String matTypeName) {
		this.matTypeName = matTypeName;
	}
	public String getMatType() {
		return matType;
	}
	public void setMatType(String matType) {
		this.matType = matType;
	}
	public String getEqpName() {
		return eqpName;
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPercent() {
		Double p=0.0;
		if(getNowQty()!=null && getNowQty()>0 && getStdQty()!=null && getStdQty()>0){
			p=MathUtil.roundHalfUp(getRealQty()/getNowQty()/getStdQty(), 2)*0.8;
		}
		p=p>1?1:p;
		percent=p*100+"%";
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getEqpCode() {
		return eqpCode;
	}
	public void setEqpCode(String eqpCode) {
		this.eqpCode = eqpCode;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public Double getRealQty() {
		return realQty;
	}
	public void setRealQty(Double realQty) {
		this.realQty = realQty;
	}
	public Double getStdQty() {
		return stdQty;
	}
	public void setStdQty(Double stdQty) {
		this.stdQty = stdQty;
	}
	
	
}
