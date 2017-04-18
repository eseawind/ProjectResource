package com.shlanbao.tzsc.wct.sch.stat.beans;


/**
 * 工单实绩消耗数据
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class InputBean {
	private String id;//ID
    private String unit;//单位
    private String mat;//物料
    private Double orignalData;//原始数据
    private Double qty;//消耗数量
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public Double getOrignalData() {
		return orignalData;
	}
	public void setOrignalData(Double orignalData) {
		this.orignalData = orignalData;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
    
}
