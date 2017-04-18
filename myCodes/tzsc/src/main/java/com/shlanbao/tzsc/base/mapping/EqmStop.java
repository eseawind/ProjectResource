package com.shlanbao.tzsc.base.mapping;

/**
 * EqmStop entity. @author MyEclipse Persistence Tools
 */

public class EqmStop implements java.io.Serializable {

	// Fields

	private String stopId;
	private EqmResume eqmResume;
	private SysUser sysUserByAddUserid;
	private SysUser sysUserByModifyUserid;
	private String stopTime;
	private String stopType;
	private String stopNumber;
	private String stopReason;
	private String enableTime;
	private String enableType;
	private String enableNumber;
	private String enableReason;
	private String addTime;
	private String modifyTime;
	private String del;

	// Constructors

	/** default constructor */
	public EqmStop() {
	}

	/** full constructor */
	public EqmStop(EqmResume eqmResume, SysUser sysUserByAddUserid,
			SysUser sysUserByModifyUserid, String stopTime, String stopType,
			String stopNumber, String stopReason, String enableTime,
			String enableType, String enableNumber, String enableReason,
			String addTime, String modifyTime, String del) {
		this.eqmResume = eqmResume;
		this.sysUserByAddUserid = sysUserByAddUserid;
		this.sysUserByModifyUserid = sysUserByModifyUserid;
		this.stopTime = stopTime;
		this.stopType = stopType;
		this.stopNumber = stopNumber;
		this.stopReason = stopReason;
		this.enableTime = enableTime;
		this.enableType = enableType;
		this.enableNumber = enableNumber;
		this.enableReason = enableReason;
		this.addTime = addTime;
		this.modifyTime = modifyTime;
		this.del = del;
	}

	// Property accessors

	public String getStopId() {
		return this.stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
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

	public String getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getStopType() {
		return this.stopType;
	}

	public void setStopType(String stopType) {
		this.stopType = stopType;
	}

	public String getStopNumber() {
		return this.stopNumber;
	}

	public void setStopNumber(String stopNumber) {
		this.stopNumber = stopNumber;
	}

	public String getStopReason() {
		return this.stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getEnableTime() {
		return this.enableTime;
	}

	public void setEnableTime(String enableTime) {
		this.enableTime = enableTime;
	}

	public String getEnableType() {
		return this.enableType;
	}

	public void setEnableType(String enableType) {
		this.enableType = enableType;
	}

	public String getEnableNumber() {
		return this.enableNumber;
	}

	public void setEnableNumber(String enableNumber) {
		this.enableNumber = enableNumber;
	}

	public String getEnableReason() {
		return this.enableReason;
	}

	public void setEnableReason(String enableReason) {
		this.enableReason = enableReason;
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