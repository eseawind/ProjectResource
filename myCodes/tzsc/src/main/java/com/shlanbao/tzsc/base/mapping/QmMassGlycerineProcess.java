package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 甘油过程自检
 * @author TRAVLER
 *
 */
public class QmMassGlycerineProcess {
	private String qmProcessId;
	private QmMassCheck qmMassCheck;
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
	
	public String getQmProcessId() {
		return qmProcessId;
	}
	public void setQmProcessId(String qmProcessId) {
		this.qmProcessId = qmProcessId;
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
