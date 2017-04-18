package com.shlanbao.tzsc.wct.qm.massexcipient.bean;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 辅料、自检自控装置确认记录
 * <li>@author luther.zhang
 * <li>@create 2015-03-27
 */
public class QmMassExcipientBean{
	private String qmExcipientId;//辅料自检ID
	private String addUserId;//新增者
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String addUserTime;//新增时间
	private String modifyUserId;//修改人
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String modifyTime;//修改时间
	private String isDelete;
	private String checkItem;//检测项目
	private String checkRate;//检查频率
	private String isError;//检查判断
	private String subStandard;//不合格情况
	private Integer orderNumber;//排序号
	
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
	private String checkTime;//检查时间
	
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getQmExcipientId() {
		return qmExcipientId;
	}
	public void setQmExcipientId(String qmExcipientId) {
		this.qmExcipientId = qmExcipientId;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
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
	public String getCheckRate() {
		return checkRate;
	}
	public void setCheckRate(String checkRate) {
		this.checkRate = checkRate;
	}
	public String getIsError() {
		return isError;
	}
	public void setIsError(String isError) {
		this.isError = isError;
	}
	public String getSubStandard() {
		return subStandard;
	}
	public void setSubStandard(String subStandard) {
		this.subStandard = subStandard;
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
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	
	
}
