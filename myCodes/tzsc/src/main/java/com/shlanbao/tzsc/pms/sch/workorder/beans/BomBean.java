package com.shlanbao.tzsc.pms.sch.workorder.beans;



/**
 * Bom实体
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class BomBean {
	private String id;
	private String unit;
	private String unitId;
	private String mat;
	private String matId;
	private Double qty;
	private String orderId;
	private String typeName;
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
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getMatId() {
		return matId;
	}
	public void setMatId(String matId) {
		this.matId = matId;
	}

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public BomBean(String unit, String mat, Double qty,String typeName) {
		super();
		this.unit = unit;
		this.mat = mat;
		this.qty = qty;
		this.typeName=typeName;
	}
	public BomBean() {
		// TODO Auto-generated constructor stub
	}
	
}
