package com.shlanbao.tzsc.pms.isp.beans;
/**
 * 卷包机组实时监控数据
 * @author Leejean
 * @create 2015年1月16日上午8:59:46
 */
public class RollerPackerData {
	//data 
	private String code;
	private boolean online;
	private Integer speed;
	private Double qty;
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
	
}
