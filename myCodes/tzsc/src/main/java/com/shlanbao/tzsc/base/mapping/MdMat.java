package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * MdMat entity. @author MyEclipse Persistence Tools
 */

public class MdMat implements java.io.Serializable {

	// Fields

	private String id;
	private MdMatType mdMatType;
	private MdUnit mdUnit;
	private String code;
	private String name;
	private String simpleName;
	private String des;
	@DateFmtAnnotation(fmtPattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;
	private Long enable;
	private String del;
	private Set schWorkorderBoms = new HashSet(0);
	private Set schWorkorders = new HashSet(0);
	private Set schConStdsForProd = new HashSet(0);
	private Set schConStdsForMat = new HashSet(0);
	private Set schShiftExchgsForToMat = new HashSet(0);
	private Set schShiftExchgsForHoMat = new HashSet(0);
    private Double standardVal; //物料消耗标注值
	// Constructors

    
    
	/** default constructor */
	public MdMat() {
	}

	public Double getStandardVal() {
		return standardVal;
	}

	public void setStandardVal(Double standardVal) {
		this.standardVal = standardVal;
	}

	public MdMat(String id) {
		super();
		this.id = id;
	}

	/** full constructor */
	public MdMat(MdMatType mdMatType, MdUnit mdUnit, String code, String name,
			String simpleName, String des, Date lastUpdateTime,
			Long enable, String del, Set schWorkorderBoms, Set schWorkorders,
			Set schConStdsForProd, Set schConStdsForMat,
			Set schShiftExchgsForToMat, Set schShiftExchgsForHoMat) {
		this.mdMatType = mdMatType;
		this.mdUnit = mdUnit;
		this.code = code;
		this.name = name;
		this.simpleName = simpleName;
		this.des = des;
		this.lastUpdateTime = lastUpdateTime;
		this.enable = enable;
		this.del = del;
		this.schWorkorderBoms = schWorkorderBoms;
		this.schWorkorders = schWorkorders;
		this.schConStdsForProd = schConStdsForProd;
		this.schConStdsForMat = schConStdsForMat;
		this.schShiftExchgsForToMat = schShiftExchgsForToMat;
		this.schShiftExchgsForHoMat = schShiftExchgsForHoMat;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdMatType getMdMatType() {
		return this.mdMatType;
	}

	public void setMdMatType(MdMatType mdMatType) {
		this.mdMatType = mdMatType;
	}

	public MdUnit getMdUnit() {
		return this.mdUnit;
	}

	public void setMdUnit(MdUnit mdUnit) {
		this.mdUnit = mdUnit;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return this.simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getEnable() {
		return this.enable;
	}

	public void setEnable(Long enable) {
		this.enable = enable;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public Set getSchWorkorderBoms() {
		return this.schWorkorderBoms;
	}

	public void setSchWorkorderBoms(Set schWorkorderBoms) {
		this.schWorkorderBoms = schWorkorderBoms;
	}

	public Set getSchWorkorders() {
		return this.schWorkorders;
	}

	public void setSchWorkorders(Set schWorkorders) {
		this.schWorkorders = schWorkorders;
	}

	public Set getSchConStdsForProd() {
		return this.schConStdsForProd;
	}

	public void setSchConStdsForProd(Set schConStdsForProd) {
		this.schConStdsForProd = schConStdsForProd;
	}

	public Set getSchConStdsForMat() {
		return this.schConStdsForMat;
	}

	public void setSchConStdsForMat(Set schConStdsForMat) {
		this.schConStdsForMat = schConStdsForMat;
	}

	public Set getSchShiftExchgsForToMat() {
		return this.schShiftExchgsForToMat;
	}

	public void setSchShiftExchgsForToMat(Set schShiftExchgsForToMat) {
		this.schShiftExchgsForToMat = schShiftExchgsForToMat;
	}

	public Set getSchShiftExchgsForHoMat() {
		return this.schShiftExchgsForHoMat;
	}

	public void setSchShiftExchgsForHoMat(Set schShiftExchgsForHoMat) {
		this.schShiftExchgsForHoMat = schShiftExchgsForHoMat;
	}

}