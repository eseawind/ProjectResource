package com.shlanbao.tzsc.base.mapping;


import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * QmMassExcipient entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class QmMassExcipient implements java.io.Serializable {
	private String qmExcipientId;
	private QmMassCheck qmMassCheck;
	private String addUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String isDelete;
	private String checkItem;
	private String checkRate;
	private String isError;
	private String subStandard;
	private Integer orderNumber;//排序号
	private String checkTime;//检查时间

	// Constructors

	/** default constructor */
	public QmMassExcipient() {
	}

	/** full constructor */
	public QmMassExcipient(QmMassCheck qmMassCheck, String addUserId,
			Date addUserTime, String modifyUserId, Date modifyTime,
			String isDelete, String checkItem, String checkRate,
			String isError, String subStandard,Integer orderNumber,String checkTime) {
		this.qmMassCheck = qmMassCheck;
		this.addUserId = addUserId;
		this.addUserTime = addUserTime;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
		this.isDelete = isDelete;
		this.checkItem = checkItem;
		this.checkRate = checkRate;
		this.isError = isError;
		this.subStandard = subStandard;
		this.orderNumber = orderNumber;
		this.checkTime = checkTime;
	}

	// Property accessors

	
	
	public String getQmExcipientId() {
		return this.qmExcipientId;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public void setQmExcipientId(String qmExcipientId) {
		this.qmExcipientId = qmExcipientId;
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

	public String getCheckItem() {
		return this.checkItem;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}

	public String getCheckRate() {
		return this.checkRate;
	}

	public void setCheckRate(String checkRate) {
		this.checkRate = checkRate;
	}

	public String getIsError() {
		return this.isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getSubStandard() {
		return this.subStandard;
	}

	public void setSubStandard(String subStandard) {
		this.subStandard = subStandard;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}