package com.shlanbao.tzsc.pms.equ.effective.beans;
/**
 * 
* @ClassName: EffectiveOperaBean 
* @Description: 设备有效作业率 
* @author luo
* @date 2015年3月9日 下午3:14:24 
*
 */
public class EffectiveOperaBean {
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
	private double rbTime;//日保时间
	private String timeUnit;//时间单位
	private double qty;//产量
	private String qtyUnit;//产量单位
	private double eqty;//台时产量
	private String eqtyUnit;//台时产量单位
	private double effectiveOpera;//有效作业率
	private String worNum; //设备有效作业率
	private Double stopTime; //剔除时间
	private Double allTime;//实际生产总时间
	private String dwUnit;
	
	
	
	
	public double getRbTime() {
		return rbTime;
	}
	public void setRbTime(double rbTime) {
		this.rbTime = rbTime;
	}
	public String getDwUnit() {
		return dwUnit;
	}
	public void setDwUnit(String dwUnit) {
		this.dwUnit = dwUnit;
	}
	public String getWorNum() {
		return worNum;
	}
	public void setWorNum(String worNum) {
		this.worNum = worNum;
	}
	public Double getStopTime() {
		return stopTime;
	}
	public void setStopTime(Double stopTime) {
		this.stopTime = stopTime;
	}
	public Double getAllTime() {
		return allTime;
	}
	public void setAllTime(Double allTime) {
		this.allTime = allTime;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
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
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public String getQtyUnit() {
		return qtyUnit;
	}
	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}
	public double getEqty() {
		return eqty;
	}
	public void setEqty(double eqty) {
		this.eqty = eqty;
	}
	public String getEqtyUnit() {
		return eqtyUnit;
	}
	public void setEqtyUnit(String eqtyUnit) {
		this.eqtyUnit = eqtyUnit;
	}
	public double getEffectiveOpera() {
		return effectiveOpera;
	}
	public void setEffectiveOpera(double effectiveOpera) {
		this.effectiveOpera = effectiveOpera;
	}
	
	
}
