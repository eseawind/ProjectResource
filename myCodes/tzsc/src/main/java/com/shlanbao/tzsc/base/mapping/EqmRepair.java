package com.shlanbao.tzsc.base.mapping;

/**
 * EqmRepair entity. @author MyEclipse Persistence Tools
 */

public class EqmRepair implements java.io.Serializable {

	// Fields

	private String repairId;
	private EqmResume eqmResume;
	private SysUser sysUserByAddUserid;
	private SysUser sysUserByModifyUserid;
	private String startTime;
	private String overTime;
	private String voucherType;
	private String voucherNumber;
	private String remarks;
	private String fixMoney;
	private String addTime;
	private String modifyTime;
	private String del;

	// Constructors

	/** default constructor */
	public EqmRepair() {
	}

	/** full constructor */
	public EqmRepair(EqmResume eqmResume, SysUser sysUserByAddUserid,
			SysUser sysUserByModifyUserid, String startTime, String overTime,
			String voucherType, String voucherNumber, String remarks,
			String fixMoney, String addTime, String modifyTime, String del) {
		this.eqmResume = eqmResume;
		this.sysUserByAddUserid = sysUserByAddUserid;
		this.sysUserByModifyUserid = sysUserByModifyUserid;
		this.startTime = startTime;
		this.overTime = overTime;
		this.voucherType = voucherType;
		this.voucherNumber = voucherNumber;
		this.remarks = remarks;
		this.fixMoney = fixMoney;
		this.addTime = addTime;
		this.modifyTime = modifyTime;
		this.del = del;
	}

	// Property accessors

	public String getRepairId() {
		return this.repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
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

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getOverTime() {
		return this.overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
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

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFixMoney() {
		return this.fixMoney;
	}

	public void setFixMoney(String fixMoney) {
		this.fixMoney = fixMoney;
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