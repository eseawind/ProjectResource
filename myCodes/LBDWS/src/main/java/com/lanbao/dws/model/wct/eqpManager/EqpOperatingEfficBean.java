package com.lanbao.dws.model.wct.eqpManager;

import java.util.Date;

import javax.persistence.Entity;

/**
 * 设备运行效率/有效作业率
 * @author shisihai
 */
@Entity(name="SCH_WORKORDER")
public class EqpOperatingEfficBean {
	private Date pStime;//计划开始时间
	private Date pEtime;//计划结束时间
	private float runTime;//实际运行时长
	private float pRuntime;//班次时长
	private float rStoptime;//停机时长
	private float pMaintenanceTime;//维保时长
	private float kickOutTime;//剔除时长
	private double rQty;//实际产量
	private double yie;//额定产能
	private String shiftName;//班次
	private String shiftId;
	private String teamName;//班组
	private String teamId;
	private String eqpName;//设备名称
	private String eqpCode;//设备名称
	private String eqpId;
	private String eqpCaregoryName;//设备类型
	private String eqpCaregoryId;
	private Date pDate;//计划日期
	private String pStrDate;//计划日期
	private String sDate;//计划开始日期
	private String eDate;//计划结束日期
	
	private String orderByEffic;//按利用率高低排序
	
	
	public float getrStoptime() {
		return rStoptime;
	}
	public void setrStoptime(float rStoptime) {
		this.rStoptime = rStoptime;
	}
	public float getpMaintenanceTime() {
		return pMaintenanceTime;
	}
	public void setpMaintenanceTime(float pMaintenanceTime) {
		this.pMaintenanceTime = pMaintenanceTime;
	}
	public float getKickOutTime() {
		return kickOutTime;
	}
	public void setKickOutTime(float kickOutTime) {
		this.kickOutTime = kickOutTime;
	}
	public double getrQty() {
		return rQty;
	}
	public void setrQty(double rQty) {
		this.rQty = rQty;
	}
	public double getYie() {
		return yie;
	}
	public void setYie(double yie) {
		this.yie = yie;
	}
	public String getEqpCode() {
		return eqpCode;
	}
	public void setEqpCode(String eqpCode) {
		this.eqpCode = eqpCode;
	}
	public String getpStrDate() {
		return pStrDate;
	}
	public void setpStrDate(String pStrDate) {
		this.pStrDate = pStrDate;
	}
	public float getpRuntime() {
		return pRuntime;
	}
	public void setpRuntime(float pRuntime) {
		this.pRuntime = pRuntime;
	}
	public String getOrderByEffic() {
		return orderByEffic;
	}
	public void setOrderByEffic(String orderByEffic) {
		this.orderByEffic = orderByEffic;
	}
	private String[] xText=new String[]{"","","","",""};
	private Float[] yVal=new Float[]{0F,0F,0F,0F,0F};
	public Date getpStime() {
		return pStime;
	}
	public void setpStime(Date pStime) {
		this.pStime = pStime;
	}
	public Date getpEtime() {
		return pEtime;
	}
	public void setpEtime(Date pEtime) {
		this.pEtime = pEtime;
	}
	public float getRunTime() {
		return runTime;
	}
	public void setRunTime(float runTime) {
		this.runTime = runTime;
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
	public String getEqpCaregoryName() {
		return eqpCaregoryName;
	}
	public void setEqpCaregoryName(String eqpCaregoryName) {
		this.eqpCaregoryName = eqpCaregoryName;
	}
	public String getEqpCaregoryId() {
		return eqpCaregoryId;
	}
	public void setEqpCaregoryId(String eqpCaregoryId) {
		this.eqpCaregoryId = eqpCaregoryId;
	}
	public Date getpDate() {
		return pDate;
	}
	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String[] getxText() {
		return xText;
	}
	public void setxText(String[] xText) {
		this.xText = xText;
	}
	public Float[] getyVal() {
		return yVal;
	}
	public void setyVal(Float[] yVal) {
		this.yVal = yVal;
	}
	
}
