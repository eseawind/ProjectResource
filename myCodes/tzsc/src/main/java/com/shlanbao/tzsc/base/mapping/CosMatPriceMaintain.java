package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * CosMatPriceMaintain entity. @author MyEclipse Persistence Tools
 */

public class CosMatPriceMaintain implements java.io.Serializable {

	/** 
	* @Fields serialVersionUID  
	*/ 
	private static final long serialVersionUID = 1L;
	private String id;
	private MdShift mdShift;
	private MdMat mdMat;
	private MdUnit mdUnit;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date takeeffectDate;
	private Double matPrice;
	private Long del;
	private Long enabled;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;

	// Constructors

	/** default constructor */
	public CosMatPriceMaintain() {
	}

	/** full constructor */
	public CosMatPriceMaintain(MdShift mdShift, MdMat mdMat,MdUnit mdUnit,
			Date takeeffectDate, Double matPrice, Long del, Long enabled,
			String attr1, String attr2, String attr3, String attr4) {
		this.mdShift = mdShift;
		this.mdMat = mdMat;
		this.mdUnit = mdUnit;
		this.takeeffectDate = takeeffectDate;
		this.matPrice = matPrice;
		this.del = del;
		this.enabled = enabled;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.attr4 = attr4;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdShift getMdShift() {
		return this.mdShift;
	}

	public void setMdShift(MdShift mdShift) {
		this.mdShift = mdShift;
	}

	public MdMat getMdMat() {
		return this.mdMat;
	}

	public void setMdMat(MdMat mdMat) {
		this.mdMat = mdMat;
	}
	
	public MdUnit getMdUnit() {
		return mdUnit;
	}

	public void setMdUnit(MdUnit mdUnit) {
		this.mdUnit = mdUnit;
	}

	public Date getTakeeffectDate() {
		return this.takeeffectDate;
	}

	public void setTakeeffectDate(Date takeeffectDate) {
		this.takeeffectDate = takeeffectDate;
	}

	public Double getMatPrice() {
		return this.matPrice;
	}

	public void setMatPrice(Double matPrice) {
		this.matPrice = matPrice;
	}

	public Long getDel() {
		return this.del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

	public Long getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Long enabled) {
		this.enabled = enabled;
	}

	public String getAttr1() {
		return this.attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return this.attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return this.attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getAttr4() {
		return this.attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

}