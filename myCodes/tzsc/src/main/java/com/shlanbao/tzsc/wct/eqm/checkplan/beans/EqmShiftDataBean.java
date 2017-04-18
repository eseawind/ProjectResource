package com.shlanbao.tzsc.wct.eqm.checkplan.beans;

import java.io.Serializable;

/**
 * 交接班实体bean
 * @author wanchanghuang
 * @createTime 2015年11月2日15:33:20
 * **/
public class EqmShiftDataBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Integer shift;
	private String eqp_id;
	private String nowTime;
	private String eqp_code;
	private String shop_code;
	
	
	
	
	
	public String getShop_code() {
		return shop_code;
	}
	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}
	public String getEqp_code() {
		return eqp_code;
	}
	public void setEqp_code(String eqp_code) {
		this.eqp_code = eqp_code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getShift() {
		return shift;
	}
	public void setShift(Integer shift) {
		this.shift = shift;
	}
	public String getEqp_id() {
		return eqp_id;
	}
	public void setEqp_id(String eqp_id) {
		this.eqp_id = eqp_id;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	
}
