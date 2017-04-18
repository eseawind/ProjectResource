package com.shlanbao.tzsc.pms.cos.disabled.bean;

public class CosIncStandardBean {
	private String id; //code
	private String modelType;//机型 
	private String shift; //班次 
	private float storage_smoke;//储烟量
	private String modelTypeName;//机型名称
	private String shiftName;//班次名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public float getStorage_smoke() {
		return storage_smoke;
	}
	public void setStorage_smoke(float storage_smoke) {
		this.storage_smoke = storage_smoke;
	}
	public String getModelTypeName() {
		return modelTypeName;
	}
	public void setModelTypeName(String modelTypeName) {
		this.modelTypeName = modelTypeName;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	
	
	
}
