package com.shlanbao.tzsc.base.mapping;

/**
 * EqmAuxil entity. @author MyEclipse Persistence Tools
 */

public class EqmAuxil implements java.io.Serializable {

	// Fields

	private String auxilId;
	private EqmResume eqmResume;
	private SysUser sysUserByAddUserid;
	private SysUser sysUserByModifyUserid;
	private String auxilName;
	private String auxilNorms;
	private String auxilUnit;
	private String auxilPrice;
	private String auxilNumber;
	private String auxilMoney;
	private String remarks;
	private String addTime;
	private String modifyTime;
	private String del;

	// Constructors

	/** default constructor */
	public EqmAuxil() {
	}

	/** full constructor */
	public EqmAuxil(EqmResume eqmResume, SysUser sysUserByAddUserid,
			SysUser sysUserByModifyUserid, String auxilName, String auxilNorms,
			String auxilUnit, String auxilPrice, String auxilNumber,
			String auxilMoney, String remarks, String addTime,
			String modifyTime, String del) {
		this.eqmResume = eqmResume;
		this.sysUserByAddUserid = sysUserByAddUserid;
		this.sysUserByModifyUserid = sysUserByModifyUserid;
		this.auxilName = auxilName;
		this.auxilNorms = auxilNorms;
		this.auxilUnit = auxilUnit;
		this.auxilPrice = auxilPrice;
		this.auxilNumber = auxilNumber;
		this.auxilMoney = auxilMoney;
		this.remarks = remarks;
		this.addTime = addTime;
		this.modifyTime = modifyTime;
		this.del = del;
	}

	// Property accessors

	public String getAuxilId() {
		return this.auxilId;
	}

	public void setAuxilId(String auxilId) {
		this.auxilId = auxilId;
	}

	public EqmResume getEqmResume() {
		return this.eqmResume;
	}

	public void setEqmResume(EqmResume eqmResume) {
		this.eqmResume = eqmResume;
	}

	public SysUser getSysUserByAddUserid() {
		return this.sysUserByAddUserid;
	}

	public void setSysUserByAddUserid(SysUser sysUserByAddUserid) {
		this.sysUserByAddUserid = sysUserByAddUserid;
	}

	public SysUser getSysUserByModifyUserid() {
		return this.sysUserByModifyUserid;
	}

	public void setSysUserByModifyUserid(SysUser sysUserByModifyUserid) {
		this.sysUserByModifyUserid = sysUserByModifyUserid;
	}

	public String getAuxilName() {
		return this.auxilName;
	}

	public void setAuxilName(String auxilName) {
		this.auxilName = auxilName;
	}

	public String getAuxilNorms() {
		return this.auxilNorms;
	}

	public void setAuxilNorms(String auxilNorms) {
		this.auxilNorms = auxilNorms;
	}

	public String getAuxilUnit() {
		return this.auxilUnit;
	}

	public void setAuxilUnit(String auxilUnit) {
		this.auxilUnit = auxilUnit;
	}

	public String getAuxilPrice() {
		return this.auxilPrice;
	}

	public void setAuxilPrice(String auxilPrice) {
		this.auxilPrice = auxilPrice;
	}

	public String getAuxilNumber() {
		return this.auxilNumber;
	}

	public void setAuxilNumber(String auxilNumber) {
		this.auxilNumber = auxilNumber;
	}

	public String getAuxilMoney() {
		return this.auxilMoney;
	}

	public void setAuxilMoney(String auxilMoney) {
		this.auxilMoney = auxilMoney;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAddTime() {
		return this.addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

}