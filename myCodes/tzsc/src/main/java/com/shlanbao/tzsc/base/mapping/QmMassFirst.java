package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * QmMassFirst entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class QmMassFirst implements java.io.Serializable {
	private String qmFirstId;
	private QmMassCheck qmMassCheck;
	private String addUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String isDelete;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date deleteUserTime;//删除时间
	private String deleteUserId;//删除人ID
	private String checkTime;//检查时间
	private String checkMatter;//首检原因
	private String checkWeight;//重量
	private String checkCondition;//外观质量情况
	private String checkStep;//处理措施
	private String addUserName;//添加人名
	private String checkType;//检查类型1：操作工，2：质检员，3：工段长
	private int failureNum;//不合格数量  
	private String failureUom;//不合格单位
	
	public int getFailureNum() {
		return failureNum;
	}
	public void setFailureNum(int failureNum) {
		this.failureNum = failureNum;
	}
	public String getFailureUom() {
		return failureUom;
	}
	public void setFailureUom(String failureUom) {
		this.failureUom = failureUom;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	public QmMassFirst() {}
	public QmMassFirst(String qmFirstId, QmMassCheck qmMassCheck,
			String addUserId, Date addUserTime, String modifyUserId,
			Date modifyTime, String isDelete, Date deleteUserTime,
			String deleteUserId, String checkTime, String checkMatter,
			String checkWeight, String checkCondition, String checkStep) {
		super();
		this.qmFirstId = qmFirstId;
		this.qmMassCheck = qmMassCheck;
		this.addUserId = addUserId;
		this.addUserTime = addUserTime;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
		this.isDelete = isDelete;
		this.deleteUserTime = deleteUserTime;
		this.deleteUserId = deleteUserId;
		this.checkTime = checkTime;
		this.checkMatter = checkMatter;
		this.checkWeight = checkWeight;
		this.checkCondition = checkCondition;
		this.checkStep = checkStep;
	}
	public String getQmFirstId() {
		return qmFirstId;
	}
	public void setQmFirstId(String qmFirstId) {
		this.qmFirstId = qmFirstId;
	}
	public QmMassCheck getQmMassCheck() {
		return qmMassCheck;
	}
	public void setQmMassCheck(QmMassCheck qmMassCheck) {
		this.qmMassCheck = qmMassCheck;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public Date getAddUserTime() {
		return addUserTime;
	}
	public void setAddUserTime(Date addUserTime) {
		this.addUserTime = addUserTime;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public Date getDeleteUserTime() {
		return deleteUserTime;
	}
	public void setDeleteUserTime(Date deleteUserTime) {
		this.deleteUserTime = deleteUserTime;
	}
	public String getDeleteUserId() {
		return deleteUserId;
	}
	public void setDeleteUserId(String deleteUserId) {
		this.deleteUserId = deleteUserId;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckMatter() {
		return checkMatter;
	}
	public void setCheckMatter(String checkMatter) {
		this.checkMatter = checkMatter;
	}
	public String getCheckWeight() {
		return checkWeight;
	}
	public void setCheckWeight(String checkWeight) {
		this.checkWeight = checkWeight;
	}
	public String getCheckCondition() {
		return checkCondition;
	}
	public void setCheckCondition(String checkCondition) {
		this.checkCondition = checkCondition;
	}
	public String getCheckStep() {
		return checkStep;
	}
	public void setCheckStep(String checkStep) {
		this.checkStep = checkStep;
	}

	@Override
	public String toString() {
		return "QmMassFirst [qmFirstId=" + qmFirstId + ", qmMassCheck="
				+ qmMassCheck + ", addUserId=" + addUserId + ", addUserTime="
				+ addUserTime + ", modifyUserId=" + modifyUserId
				+ ", modifyTime=" + modifyTime + ", isDelete=" + isDelete
				+ ", deleteUserTime=" + deleteUserTime + ", deleteUserId="
				+ deleteUserId + ", checkTime=" + checkTime + ", checkMatter="
				+ checkMatter + ", checkWeight=" + checkWeight
				+ ", checkCondition=" + checkCondition + ", checkStep="
				+ checkStep + "]";
	}

}