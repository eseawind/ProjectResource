package com.shlanbao.tzsc.pms.equ.lubricate.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class EquLubricantMaintainBean {
	private String id;
	private String lubricantCode;
	private String lubricantName;
	private String standard;
	private String del;
	private String createId;
	private String createName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createDate;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLubricantCode() {
		return lubricantCode;
	}
	public void setLubricantCode(String lubricantCode) {
		this.lubricantCode = lubricantCode;
	}
	public String getLubricantName() {
		return lubricantName;
	}
	public void setLubricantName(String lubricantName) {
		this.lubricantName = lubricantName;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public String getAttr3() {
		return attr3;
	}
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	public String getAttr4() {
		return attr4;
	}
	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}
	
}
