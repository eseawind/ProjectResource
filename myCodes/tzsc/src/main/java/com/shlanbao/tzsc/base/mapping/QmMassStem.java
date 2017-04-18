package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * QmMassStem entity. @author MyEclipse Persistence Tools
 */

public class QmMassStem implements java.io.Serializable {

	// Fields

	private String qmStemId;
	private QmMassCheck qmMassCheck;
	private String addUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String isDelete;
	private String checkCondition;
	private String isError;
	private String stemStep;
	private String stemRemark;
	private String shortTime;
	
	// Constructors

	public String getShortTime() {
		return shortTime;
	}

	public void setShortTime(String shortTime) {
		this.shortTime = shortTime;
	}

	/** default constructor */
	public QmMassStem() {
	}

	/** full constructor */
	public QmMassStem(QmMassCheck qmMassCheck, String addUserId,
			Date addUserTime, String modifyUserId, Date modifyTime,
			String isDelete, String checkCondition, String isError,
			String stemStep, String stemRemark) {
		this.qmMassCheck = qmMassCheck;
		this.addUserId = addUserId;
		this.addUserTime = addUserTime;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
		this.isDelete = isDelete;
		this.checkCondition = checkCondition;
		this.isError = isError;
		this.stemStep = stemStep;
		this.stemRemark = stemRemark;
	}

	// Property accessors

	public String getQmStemId() {
		return this.qmStemId;
	}

	public void setQmStemId(String qmStemId) {
		this.qmStemId = qmStemId;
	}

	public QmMassCheck getQmMassCheck() {
		return this.qmMassCheck;
	}

	public void setQmMassCheck(QmMassCheck qmMassCheck) {
		this.qmMassCheck = qmMassCheck;
	}

	public String getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}


	public String getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public Date getAddUserTime() {
		return addUserTime;
	}

	public void setAddUserTime(Date addUserTime) {
		this.addUserTime = addUserTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getCheckCondition() {
		return this.checkCondition;
	}

	public void setCheckCondition(String checkCondition) {
		this.checkCondition = checkCondition;
	}

	public String getIsError() {
		return this.isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getStemStep() {
		return this.stemStep;
	}

	public void setStemStep(String stemStep) {
		this.stemStep = stemStep;
	}

	public String getStemRemark() {
		return this.stemRemark;
	}

	public void setStemRemark(String stemRemark) {
		this.stemRemark = stemRemark;
	}

}