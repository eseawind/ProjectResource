package com.shlanbao.tzsc.wct.qm.massprocess.beans;

import java.util.Date;

import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class QmMassGlycerineProcessBean {
	private String qmProcessId;
	private String qmMassCheckId;
	private String addUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String isDelete;
	private double darWeight;//干棒检测  g/10支
	private double wetWeight;
	private double useWeight;//施加量  湿-干 /湿
	
	private String proWorkId;//工单主键ID
	private String mdShiftId;//班次ID
	private String mdShiftName;
	private String mdMatId;//牌号ID
	private String mdMatName;
	private String mdEqmentId;//机台号(设备)ID
	private String mdEqmentName;
	
	private String mdDcgId;
	private String mdDcgName;//挡车工姓名
	
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
	public String getQmProcessId() {
		return qmProcessId;
	}
	public void setQmProcessId(String qmProcessId) {
		this.qmProcessId = qmProcessId;
	}
	public String getQmMassCheckId() {
		return qmMassCheckId;
	}
	public void setQmMassCheck(String qmMassCheckId) {
		this.qmMassCheckId = qmMassCheckId;
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
	public double getDarWeight() {
		return darWeight;
	}
	public void setDarWeight(double darWeight) {
		this.darWeight = darWeight;
	}
	public double getWetWeight() {
		return wetWeight;
	}
	public void setWetWeight(double wetWeight) {
		this.wetWeight = wetWeight;
	}
	public double getUseWeight() {
		return useWeight;
	}
	public void setUseWeight(double useWeight) {
		this.useWeight = useWeight;
	}
	
}
