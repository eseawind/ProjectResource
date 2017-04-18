package com.shlanbao.tzsc.wct.qm.massonline.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 在线物理指标自检记录
 * <li>@author luther.zhang
 * <li>@create 2015-03-18
 */
public class QmMassOnlineBean{
	private String qmOnlineId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String modifyTime;
	private String addUserId;
	private String isDelete;
	private String checkItem;	//检测项目
	private String upperNumber;//超上限支数
	private String lowerNumber;//超下限支数
	private String avgNumber;//平均值
	private String standardDiff;//标准偏差
	private String isError;//判断
	private String isAgain;//复检
	private String onlineStep;//处理措施
	private String onlineRemark;//备注
	
	private String qmCheckId;//质量自检ID
	private String mdShiftId;//班次ID
	private String mdShiftName;
	private String mdMatId;//牌号ID
	private String mdMatName;
	private String mdEqmentId;//机台号(设备)ID
	private String mdEqmentName;
	private String mdDcgId;
	private String mdDcgName;//挡车工姓名
	private String proWorkId;//工单主键ID
	private String zjUserName;//质检员
	private String shortTime;//页面显示的简短时间
	
	
	public String getShortTime() {
		return shortTime;
	}
	public void setShortTime(String shortTime) {
		this.shortTime = shortTime;
	}
	public String getQmOnlineId() {
		return qmOnlineId;
	}
	public void setQmOnlineId(String qmOnlineId) {
		this.qmOnlineId = qmOnlineId;
	}
	public String getAddUserTime() {
		return addUserTime;
	}
	public void setAddUserTime(String addUserTime) {
		this.addUserTime = addUserTime;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getCheckItem() {
		return checkItem;
	}
	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	public String getUpperNumber() {
		return upperNumber;
	}
	public void setUpperNumber(String upperNumber) {
		this.upperNumber = upperNumber;
	}
	public String getLowerNumber() {
		return lowerNumber;
	}
	public void setLowerNumber(String lowerNumber) {
		this.lowerNumber = lowerNumber;
	}
	public String getAvgNumber() {
		return avgNumber;
	}
	public void setAvgNumber(String avgNumber) {
		this.avgNumber = avgNumber;
	}
	public String getStandardDiff() {
		return standardDiff;
	}
	public void setStandardDiff(String standardDiff) {
		this.standardDiff = standardDiff;
	}
	public String getIsError() {
		return isError;
	}
	public void setIsError(String isError) {
		this.isError = isError;
	}
	public String getIsAgain() {
		return isAgain;
	}
	public void setIsAgain(String isAgain) {
		this.isAgain = isAgain;
	}
	public String getOnlineStep() {
		return onlineStep;
	}
	public void setOnlineStep(String onlineStep) {
		this.onlineStep = onlineStep;
	}
	public String getOnlineRemark() {
		return onlineRemark;
	}
	public void setOnlineRemark(String onlineRemark) {
		this.onlineRemark = onlineRemark;
	}
	public String getQmCheckId() {
		return qmCheckId;
	}
	public void setQmCheckId(String qmCheckId) {
		this.qmCheckId = qmCheckId;
	}
	public String getMdShiftId() {
		return mdShiftId;
	}
	public void setMdShiftId(String mdShiftId) {
		this.mdShiftId = mdShiftId;
	}
	public String getMdShiftName() {
		return mdShiftName;
	}
	public void setMdShiftName(String mdShiftName) {
		this.mdShiftName = mdShiftName;
	}
	public String getMdMatId() {
		return mdMatId;
	}
	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}
	public String getMdMatName() {
		return mdMatName;
	}
	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}
	public String getMdEqmentId() {
		return mdEqmentId;
	}
	public void setMdEqmentId(String mdEqmentId) {
		this.mdEqmentId = mdEqmentId;
	}
	public String getMdEqmentName() {
		return mdEqmentName;
	}
	public void setMdEqmentName(String mdEqmentName) {
		this.mdEqmentName = mdEqmentName;
	}
	public String getMdDcgId() {
		return mdDcgId;
	}
	public void setMdDcgId(String mdDcgId) {
		this.mdDcgId = mdDcgId;
	}
	public String getMdDcgName() {
		return mdDcgName;
	}
	public void setMdDcgName(String mdDcgName) {
		this.mdDcgName = mdDcgName;
	}
	public String getProWorkId() {
		return proWorkId;
	}
	public void setProWorkId(String proWorkId) {
		this.proWorkId = proWorkId;
	}
	public String getZjUserName() {
		return zjUserName;
	}
	public void setZjUserName(String zjUserName) {
		this.zjUserName = zjUserName;
	}
	
}
