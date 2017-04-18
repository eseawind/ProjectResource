package com.shlanbao.tzsc.pms.md.eqpparam.beans;
/**
 * 滚轴系数
 * @author Leejean
 * @create 2014年12月1日下午3:46:28
 */
public class EquipmentParamBean {
	private String id;
	private String equipment;
	private String equipmentName;
	private Double axlePz;
	private Double axleSsz;
	private String des;
	private String workshop;
	 
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public Double getAxlePz() {
		return axlePz;
	}
	public void setAxlePz(Double axlePz) {
		this.axlePz = axlePz;
	}
	public Double getAxleSsz() {
		return axleSsz;
	}
	public void setAxleSsz(Double axleSsz) {
		this.axleSsz = axleSsz;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}	
	
}
