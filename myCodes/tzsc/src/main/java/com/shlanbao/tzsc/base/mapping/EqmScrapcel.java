package com.shlanbao.tzsc.base.mapping;

/**
 * EqmScrapcel entity. @author MyEclipse Persistence Tools
 */

public class EqmScrapcel implements java.io.Serializable {

	// Fields

	private String clearId;
	private EqmResume eqmResume;
	private SysUser sysUserByAddUserid;
	private SysUser sysUserByModifyUserid;
	private String clearDate;
	private String surveyCode;
	private String clearReason;
	private String costPrice;
	private String deprMoney;
	private String changeRevenue;
	private String clearCost;
	private String cardDate;
	private String logoutDate;
	private String cardUser;
	private String addTime;
	private String modifyTime;
	private String del;

	// Constructors

	/** default constructor */
	public EqmScrapcel() {
	}

	/** full constructor */
	public EqmScrapcel(EqmResume eqmResume, SysUser sysUserByAddUserid,
			SysUser sysUserByModifyUserid, String clearDate, String surveyCode,
			String clearReason, String costPrice, String deprMoney,
			String changeRevenue, String clearCost, String cardDate,
			String logoutDate, String cardUser, String addTime,
			String modifyTime, String del) {
		this.eqmResume = eqmResume;
		this.sysUserByAddUserid = sysUserByAddUserid;
		this.sysUserByModifyUserid = sysUserByModifyUserid;
		this.clearDate = clearDate;
		this.surveyCode = surveyCode;
		this.clearReason = clearReason;
		this.costPrice = costPrice;
		this.deprMoney = deprMoney;
		this.changeRevenue = changeRevenue;
		this.clearCost = clearCost;
		this.cardDate = cardDate;
		this.logoutDate = logoutDate;
		this.cardUser = cardUser;
		this.addTime = addTime;
		this.modifyTime = modifyTime;
		this.del = del;
	}

	// Property accessors

	public String getClearId() {
		return this.clearId;
	}

	public void setClearId(String clearId) {
		this.clearId = clearId;
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

	public String getClearDate() {
		return this.clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	public String getSurveyCode() {
		return this.surveyCode;
	}

	public void setSurveyCode(String surveyCode) {
		this.surveyCode = surveyCode;
	}

	public String getClearReason() {
		return this.clearReason;
	}

	public void setClearReason(String clearReason) {
		this.clearReason = clearReason;
	}

	public String getCostPrice() {
		return this.costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getDeprMoney() {
		return this.deprMoney;
	}

	public void setDeprMoney(String deprMoney) {
		this.deprMoney = deprMoney;
	}

	public String getChangeRevenue() {
		return this.changeRevenue;
	}

	public void setChangeRevenue(String changeRevenue) {
		this.changeRevenue = changeRevenue;
	}

	public String getClearCost() {
		return this.clearCost;
	}

	public void setClearCost(String clearCost) {
		this.clearCost = clearCost;
	}

	public String getCardDate() {
		return this.cardDate;
	}

	public void setCardDate(String cardDate) {
		this.cardDate = cardDate;
	}

	public String getLogoutDate() {
		return this.logoutDate;
	}

	public void setLogoutDate(String logoutDate) {
		this.logoutDate = logoutDate;
	}

	public String getCardUser() {
		return this.cardUser;
	}

	public void setCardUser(String cardUser) {
		this.cardUser = cardUser;
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