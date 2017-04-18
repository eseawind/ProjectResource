package com.shlanbao.tzsc.pms.equ.runtime.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 
 * @ClassName: EqpRunTimeBean 
 * @Description: 设备运行统计 
 * @author luo
 * @date 2015年3月5日 下午3:16:14 
 *
 */
public class EqpRunTimeBean {
	private String time;
	private String eqpName;
	private String teamName;
	private String shiftName;
	private double runTime;
	private String eqpType;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String runDate;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String runDate2;
	
	public String getEqpType() {
		return eqpType;
	}
	public void setEqpType(String eqpType) {
		this.eqpType = eqpType;
	}
	public String getRunDate() {
		return runDate;
	}
	public void setRunDate(String runDate) {
		this.runDate = runDate;
	}
	public String getRunDate2() {
		return runDate2;
	}
	public void setRunDate2(String runDate2) {
		this.runDate2 = runDate2;
	}
	@Override
	public String toString() {
		return "EqpRunTimeBean [eqpName=" + eqpName + ", runTime=" + runTime
				+ ", shiftName=" + shiftName + ", teamName=" + teamName
				+ ", time=" + time + "]";
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
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
	public double getRunTime() {
		return runTime;
	}
	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}
	
}
