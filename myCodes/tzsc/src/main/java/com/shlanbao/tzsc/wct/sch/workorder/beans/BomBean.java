package com.shlanbao.tzsc.wct.sch.workorder.beans;
/**
 * Bom实体
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class BomBean {
	private String id;//ID
	private String unit;//单位
	private String mat;//物料
	private Double qty;//投料
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
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public BomBean(String unit, String mat, Double qty) {
		super();
		this.unit = unit;
		this.mat = mat;
		this.qty = qty;
	}
	public BomBean() {
		// TODO Auto-generated constructor stub
	}
	
}
