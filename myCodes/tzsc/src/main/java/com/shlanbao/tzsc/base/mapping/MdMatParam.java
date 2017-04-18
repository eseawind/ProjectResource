package com.shlanbao.tzsc.base.mapping;

/**
 * MdMatParam entity. @author MyEclipse Persistence Tools
 */

public class MdMatParam implements java.io.Serializable {

	// Fields

	private String id;
	private MdMat mdMat;
	private Double length;
	private Double width;
	private Double density;
	private Double val;
	private String des;
	private String eqp_type;
	

	// Constructors

	public String getEqp_type() {
		return eqp_type;
	}

	public void setEqp_type(String eqp_type) {
		this.eqp_type = eqp_type;
	}

	/** default constructor */
	public MdMatParam() {
	}

	/** minimal constructor */
	public MdMatParam(MdMat mdMat) {
		this.mdMat = mdMat;
	}

	/** full constructor */
	public MdMatParam(MdMat mdMat, Double length, Double width,
			Double density, Double val, String des) {
		this.mdMat = mdMat;
		this.length = length;
		this.width = width;
		this.density = density;
		this.val = val;
		this.des = des;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdMat getMdMat() {
		return this.mdMat;
	}

	public void setMdMat(MdMat mdMat) {
		this.mdMat = mdMat;
	}

	public Double getLength() {
		return this.length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return this.width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getDensity() {
		return this.density;
	}

	public void setDensity(Double density) {
		this.density = density;
	}

	public Double getVal() {
		return this.val;
	}

	public void setVal(Double val) {
		this.val = val;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}