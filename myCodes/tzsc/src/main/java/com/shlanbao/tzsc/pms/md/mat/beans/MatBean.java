package com.shlanbao.tzsc.pms.md.mat.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 物料
 * <li>@author Leejean
 * <li>@create 2014-7-5下午10:04:20
 */
public class MatBean {
	private String id;
	private String matType;
	private String unit;
	private String unitId;	
	private String code;
	private String name;
	private String simpleName;
	private String des;
	@DateFmtAnnotation(fmtPattern = "yyyy-MM-dd HH:mm:ss")
	private String lastUpdateTime;
	private Long enable;
	private String del;
	private Double standardVal;
	
	
	public Double getStandardVal() {
		return standardVal;
	}
	public void setStandardVal(Double standardVal) {
		this.standardVal = standardVal;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MatBean() {
		// TODO Auto-generated constructor stub
	}
	public MatBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getMatType() {
		return matType;
	}
	public void setMatType(String matType) {
		this.matType = matType;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Long getEnable() {
		return enable;
	}
	public void setEnable(Long enable) {
		this.enable = enable;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}
