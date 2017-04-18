package com.shlanbao.tzsc.base.mapping;

/**
 * EqmChange entity. @author MyEclipse Persistence Tools
 */

public class EqmChange implements java.io.Serializable {

	// Fields

	private String changeId;
	private EqmResume eqmResume;
	private SysUser sysUserByAddUserid;
	private SysUser sysUserByModifyUserid;
	private String changeDate;
	private String voucherType;
	private String voucherNumber;
	private String raiseMoney;
	private String reduceMoney;
	private String changeCostprice;
	private String remarks;
	private String addTime;
	private String modifyTime;
	private String del;

	// Constructors

	/** default constructor */
	public EqmChange() {
	}

	/** full constructor */
	public EqmChange(EqmResume eqmResume, SysUser sysUserByAddUserid,
			SysUser sysUserByModifyUserid, String changeDate,
			String voucherType, String voucherNumber, String raiseMoney,
			String reduceMoney, String changeCostprice, String remarks,
			String addTime, String modifyTime, String del) {
		this.eqmResume = eqmResume;
		this.sysUserByAddUserid = sysUserByAddUserid;
		this.sysUserByModifyUserid = sysUserByModifyUserid;
		this.changeDate = changeDate;
		this.voucherType = voucherType;
		this.voucherNumber = voucherNumber;
		this.raiseMoney = raiseMoney;
		this.reduceMoney = reduceMoney;
		this.changeCostprice = changeCostprice;
		this.remarks = remarks;
		this.addTime = addTime;
		this.modifyTime = modifyTime;
		this.del = del;
	}

	// Property accessors

	public String getChangeId() {
		return this.changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
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

	public String getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
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

	public String getRaiseMoney() {
		return this.raiseMoney;
	}

	public void setRaiseMoney(String raiseMoney) {
		this.raiseMoney = raiseMoney;
	}

	public String getReduceMoney() {
		return this.reduceMoney;
	}

	public void setReduceMoney(String reduceMoney) {
		this.reduceMoney = reduceMoney;
	}

	public String getChangeCostprice() {
		return this.changeCostprice;
	}

	public void setChangeCostprice(String changeCostprice) {
		this.changeCostprice = changeCostprice;
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