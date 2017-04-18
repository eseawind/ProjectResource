package com.lanbao.dws.model.wct.dac;

/**
 * PMS实时监控页面数据（卷包机）
 * @author shisihai
 */
public class RollerPackerData {
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
