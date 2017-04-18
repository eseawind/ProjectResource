package com.shlanbao.tzsc.base.mapping;


/**
 * [功能说明]：换班后，查询上一班次保存数据实体bean
 * 
 * @createTime 2015年10月26日08:27:22
 * @author wanchanghuang
 */

public class ChangeShiftDatas implements java.io.Serializable {


	private String id;
	private Integer shift;
	private String date;
	private String sts;
	private Double qty;
	private String equipment_code;
	private String equipment_name;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getShift() {
		return shift;
	}
	public void setShift(Integer shift) {
		this.shift = shift;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	
	
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getEquipment_code() {
		return equipment_code;
	}
	public void setEquipment_code(String equipment_code) {
		this.equipment_code = equipment_code;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	

}