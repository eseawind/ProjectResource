package com.lanbao.dws.model.wct.eqpManager;
/**
 * 备品备件表
 * COS_SPARE_PARTS
 * */
public class CosSparePartsBean {
	
	private String id; //
    private String unitName; //单位名称
    private String name; //备件名称
    private double num; //备件数量
    private String type; //备件型号
    private String sourceId;//来源id，根据来源查询班次、机台相关信息
    
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	

}
