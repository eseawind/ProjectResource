package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EqmLubricantMaintain entity. @author MyEclipse Persistence Tools
 */

public class EqmLubricantMaintain implements java.io.Serializable {

	// Fields

	private String id;
	private SysUser sysUser;
	private String lubricantCode;
	private String lubricantName;
	private String standard;
	private String del;
	private Date createDate;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	/*private Set eqmLubrimaints = new HashSet(0);*/

	// Constructors

	/** default constructor */
	public EqmLubricantMaintain() {
	}

	/** full constructor */
	public EqmLubricantMaintain(SysUser sysUser, String lubricantCode,
			String lubricantName, String standard, String del, Date createDate,
			String attr1, String attr2, String attr3, String attr4) {
		this.sysUser = sysUser;
		this.lubricantCode = lubricantCode;
		this.lubricantName = lubricantName;
		this.standard = standard;
		this.del = del;
		this.createDate = createDate;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.attr4 = attr4;
		//this.eqmLubrimaints = eqmLubrimaints;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public String getLubricantCode() {
		return this.lubricantCode;
	}

	public void setLubricantCode(String lubricantCode) {
		this.lubricantCode = lubricantCode;
	}

	public String getLubricantName() {
		return this.lubricantName;
	}

	public void setLubricantName(String lubricantName) {
		this.lubricantName = lubricantName;
	}

	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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