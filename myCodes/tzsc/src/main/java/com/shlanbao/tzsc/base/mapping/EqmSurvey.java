package com.shlanbao.tzsc.base.mapping;

/**
 * EqmSurvey entity. @author MyEclipse Persistence Tools
 */

public class EqmSurvey implements java.io.Serializable {

	// Fields

	private String surveyId;
	private EqmResume eqmResume;
	private SysUser sysUserByAddUserid;
	private SysUser sysUserByModifyUserid;
	private String surveyDate;
	private String surveyCode;
	private String surveyWay;
	private String surveyCompany;
	private String costPrice;
	private String deprMoney;
	private String allotMoney;
	private String allotRevenue;
	private String remarks;
	private String addTime;
	private String modifyTime;
	private String del;

	// Constructors

	/** default constructor */
	public EqmSurvey() {
	}

	/** full constructor */
	public EqmSurvey(EqmResume eqmResume, SysUser sysUserByAddUserid,
			SysUser sysUserByModifyUserid, String surveyDate,
			String surveyCode, String surveyWay, String surveyCompany,
			String costPrice, String deprMoney, String allotMoney,
			String allotRevenue, String remarks, String addTime,
			String modifyTime, String del) {
		this.eqmResume = eqmResume;
		this.sysUserByAddUserid = sysUserByAddUserid;
		this.sysUserByModifyUserid = sysUserByModifyUserid;
		this.surveyDate = surveyDate;
		this.surveyCode = surveyCode;
		this.surveyWay = surveyWay;
		this.surveyCompany = surveyCompany;
		this.costPrice = costPrice;
		this.deprMoney = deprMoney;
		this.allotMoney = allotMoney;
		this.allotRevenue = allotRevenue;
		this.remarks = remarks;
		this.addTime = addTime;
		this.modifyTime = modifyTime;
		this.del = del;
	}

	// Property accessors

	public String getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
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

	public String getSurveyDate() {
		return this.surveyDate;
	}

	public void setSurveyDate(String surveyDate) {
		this.surveyDate = surveyDate;
	}

	public String getSurveyCode() {
		return this.surveyCode;
	}

	public void setSurveyCode(String surveyCode) {
		this.surveyCode = surveyCode;
	}

	public String getSurveyWay() {
		return this.surveyWay;
	}

	public void setSurveyWay(String surveyWay) {
		this.surveyWay = surveyWay;
	}

	public String getSurveyCompany() {
		return this.surveyCompany;
	}

	public void setSurveyCompany(String surveyCompany) {
		this.surveyCompany = surveyCompany;
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

	public String getAllotMoney() {
		return this.allotMoney;
	}

	public void setAllotMoney(String allotMoney) {
		this.allotMoney = allotMoney;
	}

	public String getAllotRevenue() {
		return this.allotRevenue;
	}

	public void setAllotRevenue(String allotRevenue) {
		this.allotRevenue = allotRevenue;
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