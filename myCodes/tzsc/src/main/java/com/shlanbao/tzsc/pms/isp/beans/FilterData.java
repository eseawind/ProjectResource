package com.shlanbao.tzsc.pms.isp.beans;
/**
 * 查询机组实时监控数据
 * @author Leejean
 * @create 2015年1月16日上午8:59:46
 */
public class FilterData {
	//data code
	private String code;
	/**
	 * 在线状态
	 */
	private boolean online;
	/**
	 * 速度
	 */
	private Integer speed;
	/**
	 * 产量
	 */
	private Double qty;
	/**
	 * 运行时间
	 */
	private Double runTime;
	/**
	 * 停机时间
	 */
	private Double stopTime;
	/**
	 * 停机次数
	 */
	private Integer stopTimes;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
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
	public Integer getStopTimes() {
		return stopTimes;
	}
	public void setStopTimes(Integer stopTimes) {
		this.stopTimes = stopTimes;
	}
	
}
