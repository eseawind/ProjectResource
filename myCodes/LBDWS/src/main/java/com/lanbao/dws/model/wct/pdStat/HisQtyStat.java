package com.lanbao.dws.model.wct.pdStat;

import javax.persistence.Entity;

/**
 * 历史产量
 * @author shisihai
 *
 */
@Entity(name="SCH_WORKORDER")
public class HisQtyStat {
	private String eqpCode;
	private String eqpName;
	private String shiftName;
	private String teamName;
	private Double pqty;//计划产量
	private String pUnit;//计划产量单位
	private Double realQty;//实际产量
	private String realUnit;//产量单位
	private Double realBadQty;//实际提出产量
	private String mat;//牌号
	private String pDate;//计划日期
	private String pEDate;//计划日期(用于查询)
	private String orderByQty;//产量排序
	private String orderByTime;//时间排序
	private String orderBadQty;//剔除量排序
	
	private String[] xText;//x轴文本
	private Double[] yPData;//计划产量
	private Double[] yRData;//实际产量
	private Double[] yBData;//剔除产量
	
	
	public Double[] getyBData() {
		return yBData;
	}
	public void setyBData(Double[] yBData) {
		this.yBData = yBData;
	}
	public String getOrderBadQty() {
		return orderBadQty;
	}
	public void setOrderBadQty(String orderBadQty) {
		this.orderBadQty = orderBadQty;
	}
	public Double getRealBadQty() {
		return realBadQty;
	}
	public void setRealBadQty(Double realBadQty) {
		this.realBadQty = realBadQty;
	}
	public String getOrderByQty() {
		return orderByQty;
	}
	public void setOrderByQty(String orderByQty) {
		this.orderByQty = orderByQty;
	}
	public String getOrderByTime() {
		return orderByTime;
	}
	public void setOrderByTime(String orderByTime) {
		this.orderByTime = orderByTime;
	}
	public String[] getxText() {
		return xText;
	}
	public void setxText(String[] xText) {
		this.xText = xText;
	}
	public Double[] getyPData() {
		return yPData;
	}
	public void setyPData(Double[] yPData) {
		this.yPData = yPData;
	}
	public Double[] getyRData() {
		return yRData;
	}
	public void setyRData(Double[] yRData) {
		this.yRData = yRData;
	}
	public String getEqpCode() {
		return eqpCode;
	}
	public void setEqpCode(String eqpCode) {
		this.eqpCode = eqpCode;
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
	public String getpEDate() {
		return pEDate;
	}
	public void setpEDate(String pEDate) {
		this.pEDate = pEDate;
	}
}
