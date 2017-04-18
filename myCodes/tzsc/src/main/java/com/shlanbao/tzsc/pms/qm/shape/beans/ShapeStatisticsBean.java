package com.shlanbao.tzsc.pms.qm.shape.beans;

public class ShapeStatisticsBean {
	private String[] lables;//表中的中文值
	private Double[] values;//值,这里指的是数字
	private String type;// 类型
	
	public ShapeStatisticsBean(String[] lables, Double[] values, String type) {
		super();
		this.values = values;
		this.lables = lables;
		this.type = type;
	}
	public String[] getLables() {
		return lables;
	}
	public void setLables(String[] lables) {
		this.lables = lables;
	}
	public Double[] getValues() {
		return values;
	}
	public void setValues(Double[] values) {
		this.values = values;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
