package com.shlanbao.tzsc.pms.equ.effective.beans;
/**
 * 
* @ClassName: EffectiveRunTime 
* @Description: 设备运行效率实体类 
* @author shi
* @date 2015年10月19日 下午2:02:38 
*
 */
public class EffectiveRunTime {
	private String startTime;//查询条件开始时间
	private String endTime;//查询条件结束时间
	private String eqpId;//设备ID
	private String area;//车间
	private String eqpName;//设备名称
	private String eqpType;//设备类型
	private String date;//生产日期
	private String shift;//班次
	private String team;//班组
	private double runTime;//运行时间
	private double downTime;//停机时间
	private double excludTime;//剔除时间
	private String timeUnit;//时间单位
	private double effectiveRunTime;//运行效率
	private double rbTime;//日保时间
	private double jcTime;//吃饭时间
	
	public double getRbTime() {
		return rbTime;
	}
	public void setRbTime(double rbTime) {
		this.rbTime = rbTime;
	}
	public double getJcTime() {
		return jcTime;
	}
	public void setJcTime(double jcTime) {
		this.jcTime = jcTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getEqpType() {
		return eqpType;
	}
	public void setEqpType(String eqpType) {
		this.eqpType = eqpType;
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
	public double getRunTime() {
		return runTime;
	}
	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}
	public double getDownTime() {
		return downTime;
	}
	public void setDownTime(double downTime) {
		this.downTime = downTime;
	}
	public double getExcludTime() {
		return excludTime;
	}
	public void setExcludTime(double excludTime) {
		this.excludTime = excludTime;
	}
	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	public double getEffectiveRunTime() {
		return effectiveRunTime;
	}
	public void setEffectiveRunTime(double effectiveRunTime) {
		this.effectiveRunTime = effectiveRunTime;
	}
	
	
	
}
