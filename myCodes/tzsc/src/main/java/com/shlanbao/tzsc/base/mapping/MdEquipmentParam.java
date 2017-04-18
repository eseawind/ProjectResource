package com.shlanbao.tzsc.base.mapping;

/**
 * MdEquipmentParam entity. @author MyEclipse Persistence Tools
 */

public class MdEquipmentParam implements java.io.Serializable {

	// Fields

	private String id;
	private MdEquipment mdEquipment;
	private Double axlePz;
	private Double axleSsz;
	private String des;

	// Constructors

	/** default constructor */
	public MdEquipmentParam() {
		
	}

	/** full constructor */
	public MdEquipmentParam(MdEquipment mdEquipment, Double axlePz,
			Double axleSsz, String des) {
		this.mdEquipment = mdEquipment;
		this.axlePz = axlePz;
		this.axleSsz = axleSsz;
		this.des = des;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdEquipment getMdEquipment() {
		return this.mdEquipment;
	}

	public void setMdEquipment(MdEquipment mdEquipment) {
		this.mdEquipment = mdEquipment;
	}

	public Double getAxlePz() {
		return this.axlePz;
	}

	public void setAxlePz(Double axlePz) {
		this.axlePz = axlePz;
	}

	public Double getAxleSsz() {
		return this.axleSsz;
	}

	public void setAxleSsz(Double axleSsz) {
		this.axleSsz = axleSsz;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}