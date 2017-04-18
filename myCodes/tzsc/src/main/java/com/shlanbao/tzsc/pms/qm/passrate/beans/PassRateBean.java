package com.shlanbao.tzsc.pms.qm.passrate.beans;

public class PassRateBean {
	private String[] lables;//表中的中文值
	private Double[] values1;//值,这里指的是数字
	private Double[] values2;//值,这里指的是数字
	private String type;// 类型
	
	public PassRateBean(String[] lables, String type, Double[] values1, Double[] values2) {
		super();
		this.values1 = values1;
		this.values2 = values2;
		this.lables = lables;
		this.type = type;
	}
	public String[] getLables() {
		return lables;
	}
	public void setLables(String[] lables) {
		this.lables = lables;
	}


	public Double[] getValues1() {
		return values1;
	}
	public void setValues1(Double[] values1) {
		this.values1 = values1;
	}
	public Double[] getValues2() {
		return values2;
	}
	public void setValues2(Double[] values2) {
		this.values2 = values2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
