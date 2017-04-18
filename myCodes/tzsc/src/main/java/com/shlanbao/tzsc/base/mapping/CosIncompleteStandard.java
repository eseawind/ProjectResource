package com.shlanbao.tzsc.base.mapping;

/**
 * @description 残烟考核参数，不同机型、班次设置储烟量调整参数
 * @author luoliang
 * @version 
 */
public class CosIncompleteStandard {
	
	private String id; //code
	private String modelType;//机型 MODEL_TYPE	varchar(50)	Unchecked
	private String shift; //班次 SHIFT
	private float storage_smoke;//储烟量 STORAGE_SMOKE	float	Unchecked
	private MdEqpType mdEqupType;
	private MdShift mdShift;
	
	public MdEqpType getMdEqupType() {
		return mdEqupType;
	}
	public void setMdEqupType(MdEqpType mdEqupType) {
		this.mdEqupType = mdEqupType;
	}
	public MdShift getMdShift() {
		return mdShift;
	}
	public void setMdShift(MdShift mdShift) {
		this.mdShift = mdShift;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModelType() {
		if(mdEqupType!=null)
			return mdEqupType.getId();
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getShift() {
		if(mdShift!=null)
			return mdShift.getId();
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

	
}
