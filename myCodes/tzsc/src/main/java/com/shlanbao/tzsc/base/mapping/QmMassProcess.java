package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * QmMassProcess entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class QmMassProcess implements java.io.Serializable {

	// Fields

	private String qmProcessId;
	private QmMassCheck qmMassCheck;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String addUserId;
	private String isDelete;
	private String prodPart;//产品段
	private String runWeight;//重量重量
	private String runCondition;//外观质量情况
	private String isError;//判断
	private String isAgain;//复检
	private String runStep;//处理措施
	private String runRemark;//备注
	private String processType;//自检角色类型id
	private String shortTime;//显示的时间（没有日期）
	private String badNum;//质量问题数量
	private String numUnit;//问题数量单位
	
	private String wjzjNum;//包装机挡车工过程自检
	
	// Constructors

	public String getWjzjNum() {
		return wjzjNum;
	}

	public void setWjzjNum(String wjzjNum) {
		this.wjzjNum = wjzjNum;
	}

	public String getBadNum() {
		return badNum;
	}

	public void setBadNum(String badNum) {
		this.badNum = badNum;
	}

	public String getNumUnit() {
		return numUnit;
	}

	public void setNumUnit(String numUnit) {
		this.numUnit = numUnit;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getShortTime() {
		return shortTime;
	}

	public void setShortTime(String shortTime) {
		this.shortTime = shortTime;
	}

	/** default constructor */
	public QmMassProcess() {
	}

	/** full constructor */
	public QmMassProcess(QmMassCheck qmMassCheck, Date addUserTime,
			String modifyUserId, Date modifyTime, String addUserId,
			String isDelete, String prodPart, String runWeight,
			String runCondition, String isError, String isAgain,
			String runStep, String runRemark) {
		this.qmMassCheck = qmMassCheck;
		this.addUserTime = addUserTime;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
		this.addUserId = addUserId;
		this.isDelete = isDelete;
		this.prodPart = prodPart;
		this.runWeight = runWeight;
		this.runCondition = runCondition;
		this.isError = isError;
		this.isAgain = isAgain;
		this.runStep = runStep;
		this.runRemark = runRemark;
	}

	// Property accessors

	public String getQmProcessId() {
		return this.qmProcessId;
	}

	public void setQmProcessId(String qmProcessId) {
		this.qmProcessId = qmProcessId;
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

	public String getProdPart() {
		return this.prodPart;
	}

	public void setProdPart(String prodPart) {
		this.prodPart = prodPart;
	}

	public String getRunWeight() {
		return this.runWeight;
	}

	public void setRunWeight(String runWeight) {
		this.runWeight = runWeight;
	}

	public String getRunCondition() {
		return this.runCondition;
	}

	public void setRunCondition(String runCondition) {
		this.runCondition = runCondition;
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

	public String getRunStep() {
		return this.runStep;
	}

	public void setRunStep(String runStep) {
		this.runStep = runStep;
	}

	public String getRunRemark() {
		return this.runRemark;
	}

	public void setRunRemark(String runRemark) {
		this.runRemark = runRemark;
	}

}