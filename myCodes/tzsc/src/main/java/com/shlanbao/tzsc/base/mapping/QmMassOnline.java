package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * QmMassOnline entity. @author MyEclipse Persistence Tools
 */

public class QmMassOnline implements java.io.Serializable {
	private static final long serialVersionUID = -942801109857795558L;
	private String qmOnlineId;
	private QmMassCheck qmMassCheck;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String addUserId;
	private String isDelete;
	private String checkItem;	//检测项目
	private String upperNumber; //超上线只数
	private String lowerNumber; //超下线只数
	private String avgNumber;	//平均值
	private String standardDiff;//标偏
	private String isError;		//判断
	private String isAgain;		//复检
	private String onlineStep;	//处理措施
	private String onlineRemark;//备注
	private String shortTime;//显示的简短时间
	

	// Constructors

	public String getShortTime() {
		return shortTime;
	}

	public void setShortTime(String shortTime) {
		this.shortTime = shortTime;
	}

	/** default constructor */
	public QmMassOnline() {
	}

	/** full constructor */
	public QmMassOnline(QmMassCheck qmMassCheck, Date addUserTime,
			String modifyUserId, Date modifyTime, String addUserId,
			String isDelete, String checkItem, String upperNumber,
			String lowerNumber, String avgNumber, String standardDiff,
			String isError, String isAgain, String onlineStep,
			String onlineRemark) {
		this.qmMassCheck = qmMassCheck;
		this.addUserTime = addUserTime;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
		this.addUserId = addUserId;
		this.isDelete = isDelete;
		this.checkItem = checkItem;
		this.upperNumber = upperNumber;
		this.lowerNumber = lowerNumber;
		this.avgNumber = avgNumber;
		this.standardDiff = standardDiff;
		this.isError = isError;
		this.isAgain = isAgain;
		this.onlineStep = onlineStep;
		this.onlineRemark = onlineRemark;
	}

	// Property accessors

	public String getQmOnlineId() {
		return this.qmOnlineId;
	}

	public void setQmOnlineId(String qmOnlineId) {
		this.qmOnlineId = qmOnlineId;
	}

	public QmMassCheck getQmMassCheck() {
		return this.qmMassCheck;
	}

	public void setQmMassCheck(QmMassCheck qmMassCheck) {
		this.qmMassCheck = qmMassCheck;
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

	public String getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
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

	public String getUpperNumber() {
		return this.upperNumber;
	}

	public void setUpperNumber(String upperNumber) {
		this.upperNumber = upperNumber;
	}

	public String getLowerNumber() {
		return this.lowerNumber;
	}

	public void setLowerNumber(String lowerNumber) {
		this.lowerNumber = lowerNumber;
	}

	public String getAvgNumber() {
		return this.avgNumber;
	}

	public void setAvgNumber(String avgNumber) {
		this.avgNumber = avgNumber;
	}

	public String getStandardDiff() {
		return this.standardDiff;
	}

	public void setStandardDiff(String standardDiff) {
		this.standardDiff = standardDiff;
	}

	public String getIsError() {
		return this.isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getIsAgain() {
		return this.isAgain;
	}

	public void setIsAgain(String isAgain) {
		this.isAgain = isAgain;
	}

	public String getOnlineStep() {
		return this.onlineStep;
	}

	public void setOnlineStep(String onlineStep) {
		this.onlineStep = onlineStep;
	}

	public String getOnlineRemark() {
		return this.onlineRemark;
	}

	public void setOnlineRemark(String onlineRemark) {
		this.onlineRemark = onlineRemark;
	}

}