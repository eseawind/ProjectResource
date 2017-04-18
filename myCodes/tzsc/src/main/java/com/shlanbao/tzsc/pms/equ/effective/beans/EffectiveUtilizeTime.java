package com.shlanbao.tzsc.pms.equ.effective.beans;
/**
 * 
* @ClassName: EffectiveRunTime 
* @Description: 设备综合利用率 
* @author luo
* @date 2015年3月11日 下午5:02:38 
*
 */
public class EffectiveUtilizeTime {
	private String startTime;//查询条件开始时间
	private String endTime;//查询条件结束时间
	private String eqpId;//设备ID
	private String area;//车间
	private String eqpName;//设备名称
	private String eqpType;//设备类型
	private String date;//生产日期
	private String shift;//班次
	private String team;//班组
	private double runSumTime;//运行时间
	private double planTime;//计算运行时间
	private String timeUnit;//时间单位
	private double effectiveUtilize;//运行效率
	
	public double getPlanTime() {
		return planTime;
	}
	public void setPlanTime(double planTime) {
		this.planTime = planTime;
	}
	public double getEffectiveUtilize() {
		return effectiveUtilize;
	}
	public void setEffectiveUtilize(double effectiveUtilize) {
		this.effectiveUtilize = effectiveUtilize;
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
	
	public double getRunSumTime() {
		return runSumTime;
	}
	public void setRunSumTime(double runSumTime) {
		this.runSumTime = runSumTime;
	}
	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
}
