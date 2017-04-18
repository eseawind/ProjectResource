package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

/**
 * @ClassName: EqmWorkOrderTime 
 * @Description: 设备有效利用率视图 
 * @author luo
 * @date 2015年3月12日 10:23:38
 */
public class EqmWorkOrderTime {
	private String workOrderId;//工单号
	private String workShopName;//车间名称
	private String matName;//牌号名称
	private String eqpName;//设备名称
	private String eqpTypeName;//设备类型名称
	private Date qDate;//工单生产日期
	private String teamName;//班组名称
	private String shiftName;//班次名称
	private double actualTime;//计划生产时长
	private double planTime;//实际生产时长=运行-停机
	private String eqpTypeId;//设备类型Id
	private String eqpId;//设备Id
	private String teamId;//班组Id
	private String workShopId;//车间Id
	
	public String getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	public String getWorkShopId() {
		return workShopId;
	}
	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getEqpTypeName() {
		return eqpTypeName;
	}
	public void setEqpTypeName(String eqpTypeName) {
		this.eqpTypeName = eqpTypeName;
	}
	public Date getqDate() {
		return qDate;
	}
	public void setqDate(Date qDate) {
		this.qDate = qDate;
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
	public double getActualTime() {
		return actualTime;
	}
	public void setActualTime(double actualTime) {
		this.actualTime = actualTime;
	}
	public double getPlanTime() {
		return planTime;
	}
	public void setPlanTime(double planTime) {
		this.planTime = planTime;
	}
	public String getEqpTypeId() {
		return eqpTypeId;
	}
	public void setEqpTypeId(String eqpTypeId) {
		this.eqpTypeId = eqpTypeId;
	}
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
}
