package com.shlanbao.tzsc.pms.qm.check.beans;


import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 过程自检记录
 * <li>@author luther.zhang
 * <li>@create 2015-03-18
 */
public class QmMassProcessBean{
	
	private String qmProcessId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String modifyTime;
	private String addUserId;
	private String isDelete;
	private String prodPart;//产品段
	private String runWeight;//重量
	private String runCondition;//外观质量情况
	private String isError;//判断
	private String isAgain;//复检
	private String runStep;//处理措施
	private String runRemark;//备注
	
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
	
	private String shortTime;//显示的时间
	private String processType;//操作类型 D挡车工 C操作工 J卷烟机
	
	private String badNum;//质量问题数量
	private String numUnit;//问题数量单位
	
	private String wjzjNum;//包装机挡车工过程自检
	
	
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
	public String getQmProcessId() {
		return qmProcessId;
	}
	public void setQmProcessId(String qmProcessId) {
		this.qmProcessId = qmProcessId;
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
	public String getProdPart() {
		return prodPart;
	}
	public void setProdPart(String prodPart) {
		this.prodPart = prodPart;
	}
	public String getRunWeight() {
		return runWeight;
	}
	public void setRunWeight(String runWeight) {
		this.runWeight = runWeight;
	}
	public String getRunCondition() {
		return runCondition;
	}
	public void setRunCondition(String runCondition) {
		this.runCondition = runCondition;
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
	public String getRunStep() {
		return runStep;
	}
	public void setRunStep(String runStep) {
		this.runStep = runStep;
	}
	public String getRunRemark() {
		return runRemark;
	}
	public void setRunRemark(String runRemark) {
		this.runRemark = runRemark;
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
