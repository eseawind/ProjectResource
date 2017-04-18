package com.shlanbao.tzsc.base.mapping;

/**
 * EqmInside entity. @author MyEclipse Persistence Tools
 */

public class EqmInside implements java.io.Serializable {

	// Fields

	private String insideId;
	private EqmResume eqmResume;
	private SysUser sysUserByAddUserid;
	private SysUser sysUserByModifyUserid;
	private String insideDate;
	private String voucherType;
	private String voucherNumber;
	private String receiveDept;
	private String depositPlace;
	private String remarks;
	private String addTime;
	private String modifyTime;
	private String del;
	private String resumeId;

	// Constructors

	/** default constructor */
	public EqmInside() {
	}

	/** full constructor */
	public EqmInside(EqmResume eqmResume, SysUser sysUserByAddUserid,
			SysUser sysUserByModifyUserid, String insideDate,
			String voucherType, String voucherNumber, String receiveDept,
			String depositPlace, String remarks, String addTime,
			String modifyTime, String del, String resumeId) {
		this.eqmResume = eqmResume;
		this.sysUserByAddUserid = sysUserByAddUserid;
		this.sysUserByModifyUserid = sysUserByModifyUserid;
		this.insideDate = insideDate;
		this.voucherType = voucherType;
		this.voucherNumber = voucherNumber;
		this.receiveDept = receiveDept;
		this.depositPlace = depositPlace;
		this.remarks = remarks;
		this.addTime = addTime;
		this.modifyTime = modifyTime;
		this.del = del;
		this.resumeId = resumeId;
	}

	// Property accessors

	public String getInsideId() {
		return this.insideId;
	}

	public void setInsideId(String insideId) {
		this.insideId = insideId;
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

	public String getInsideDate() {
		return this.insideDate;
	}

	public void setInsideDate(String insideDate) {
		this.insideDate = insideDate;
	}

	public String getVoucherType() {
		return this.voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public String getVoucherNumber() {
		return this.voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getReceiveDept() {
		return this.receiveDept;
	}

	public void setReceiveDept(String receiveDept) {
		this.receiveDept = receiveDept;
	}

	public String getDepositPlace() {
		return this.depositPlace;
	}

	public void setDepositPlace(String depositPlace) {
		this.depositPlace = depositPlace;
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

	public String getResumeId() {
		return this.resumeId;
	}

	public void setResumeId(String resumeId) {
		this.resumeId = resumeId;
	}

}