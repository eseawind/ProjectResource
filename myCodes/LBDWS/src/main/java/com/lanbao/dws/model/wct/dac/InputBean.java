package com.lanbao.dws.model.wct.dac;

public class InputBean {
	private String inputId;
	private Double orignalData;
	private Double qty;
	
	
	
	public InputBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InputBean(String inputId, Double orignalData, Double qty) {
		super();
		this.inputId = inputId;
		this.orignalData = orignalData;
		this.qty = qty;
	}
	public String getInputId() {
		return inputId;
	}
	public void setInputId(String inputId) {
		this.inputId = inputId;
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
