package com.shlanbao.tzsc.pms.sch.stat.beans;


/**
 * 工单实绩消耗数据
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class InputBean {
	private String id;
	private String unitCode;//单位code
    private String unit;
    private String matCode;//辅料code
    private String mat;
    private Double orignalData;//原始数据
    private Double qty;
    private String bth;//物料批次
    private String outputId;
    private String matType;//物料类型
    
    public String getMatType() {
		return matType;
	}

	public void setMatType(String matType) {
		this.matType = matType;
	}

	public InputBean() {
		// TODO Auto-generated constructor stub
	}
    
	public InputBean(String id, Double orignalData, Double qty) {
		super();
		this.id = id;
		this.orignalData = orignalData;
		this.qty = qty;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getMatCode() {
		return matCode;
	}

	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}

	public String getBth() {
		return bth;
	}

	public void setBth(String bth) {
		this.bth = bth;
	}

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
	public String getOutputId() {
		return outputId;
	}
	public void setOutputId(String outputId) {
		this.outputId = outputId;
	}
    
}
