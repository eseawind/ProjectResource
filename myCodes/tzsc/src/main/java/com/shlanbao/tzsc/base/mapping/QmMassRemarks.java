package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * QmMassRemarks entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class QmMassRemarks implements java.io.Serializable {
	private String qmRemarksId;
	private QmMassCheck qmMassCheck;
	private String addUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String isDelete;
	private String remarks;

	// Constructors

	/** default constructor */
	public QmMassRemarks() {
	}

	/** full constructor */
	public QmMassRemarks(QmMassCheck qmMassCheck, String addUserId,
			Date addUserTime, String modifyUserId, Date modifyTime,
			String isDelete, String remarks) {
		this.qmMassCheck = qmMassCheck;
		this.addUserId = addUserId;
		this.addUserTime = addUserTime;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
		this.isDelete = isDelete;
		this.remarks = remarks;
	}

	// Property accessors

	public String getQmRemarksId() {
		return this.qmRemarksId;
	}

	public void setQmRemarksId(String qmRemarksId) {
		this.qmRemarksId = qmRemarksId;
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

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}