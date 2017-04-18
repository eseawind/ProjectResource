package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * QmMassCheck entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class QmMassCheck implements java.io.Serializable {
	private String qmCheckId;//质量自检ID
	private String addUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String isDelete;
	private String mdShiftId;//班次ID
	private String mdShiftName;
	private String mdMatId;//牌号ID
	private String mdMatName;
	private String mdEqmentId;//机台号(设备)ID
	private String mdEqmentName;
	private String mdDcgId;
	private String mdDcgName;//挡车工姓名
	private String proWorkId;//工单主键ID
	private String smallBox;//小盒钢印
	private String bigBox;//条盒钢印
	@SuppressWarnings("rawtypes")
	private Set qmMassFirsts = new HashSet(0);
	@SuppressWarnings("rawtypes")
	private Set qmMassExcipients = new HashSet(0);
	@SuppressWarnings("rawtypes")
	private Set qmMassStems = new HashSet(0);
	@SuppressWarnings("rawtypes")
	private Set qmMassProcesses = new HashSet(0);
	@SuppressWarnings("rawtypes")
	private Set qmMassRemarkses = new HashSet(0);
	@SuppressWarnings("rawtypes")
	private Set qmMassOnlines = new HashSet(0);
	
	public String getSmallBox() {
		return smallBox;
	}

	public void setSmallBox(String smallBox) {
		this.smallBox = smallBox;
	}

	public String getBigBox() {
		return bigBox;
	}

	public void setBigBox(String bigBox) {
		this.bigBox = bigBox;
	}
	//查询条件
	private String time;
	private String team;
	
	// Constructors

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/** default constructor */
	public QmMassCheck() {
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public QmMassCheck(String addUserId, Date addTime,
			String modifyUserId, Date modifyTime, String isDelete,
			String mdShiftId, String mdShiftName, String mdMatId,
			String mdMatName, String mdEqmentId, String mdEqmentName,
			String mdDcgId, String mdDcgName, String proWorkId, Set qmMassFirsts,
			Set qmMassExcipients, Set qmMassStems, Set qmMassProcesses,
			Set qmMassRemarkses, Set qmMassOnlines) {
		this.addUserId = addUserId;
		this.addTime = addTime;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
		this.isDelete = isDelete;
		this.mdShiftId = mdShiftId;
		this.mdShiftName = mdShiftName;
		this.mdMatId = mdMatId;
		this.mdMatName = mdMatName;
		this.mdEqmentId = mdEqmentId;
		this.mdEqmentName = mdEqmentName;
		this.mdDcgId = mdDcgId;
		this.mdDcgName = mdDcgName;
		this.proWorkId = proWorkId;
		this.qmMassFirsts = qmMassFirsts;
		this.qmMassExcipients = qmMassExcipients;
		this.qmMassStems = qmMassStems;
		this.qmMassProcesses = qmMassProcesses;
		this.qmMassRemarkses = qmMassRemarkses;
		this.qmMassOnlines = qmMassOnlines;
	}

	// Property accessors

	public String getQmCheckId() {
		return this.qmCheckId;
	}

	public void setQmCheckId(String qmCheckId) {
		this.qmCheckId = qmCheckId;
	}

	public String getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getModifyUserId() {
		return this.modifyUserId;
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
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getMdShiftId() {
		return this.mdShiftId;
	}

	public void setMdShiftId(String mdShiftId) {
		this.mdShiftId = mdShiftId;
	}

	public String getMdShiftName() {
		return this.mdShiftName;
	}

	public void setMdShiftName(String mdShiftName) {
		this.mdShiftName = mdShiftName;
	}

	public String getMdMatId() {
		return this.mdMatId;
	}

	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}

	public String getMdMatName() {
		return this.mdMatName;
	}

	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}

	public String getMdEqmentId() {
		return this.mdEqmentId;
	}

	public void setMdEqmentId(String mdEqmentId) {
		this.mdEqmentId = mdEqmentId;
	}

	public String getMdEqmentName() {
		return this.mdEqmentName;
	}

	public void setMdEqmentName(String mdEqmentName) {
		this.mdEqmentName = mdEqmentName;
	}

	public String getMdDcgId() {
		return this.mdDcgId;
	}

	public void setMdDcgId(String mdDcgId) {
		this.mdDcgId = mdDcgId;
	}

	public String getMdDcgName() {
		return this.mdDcgName;
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

	@SuppressWarnings("rawtypes")
	public Set getQmMassFirsts() {
		return this.qmMassFirsts;
	}
	@SuppressWarnings("rawtypes")
	public void setQmMassFirsts(Set qmMassFirsts) {
		this.qmMassFirsts = qmMassFirsts;
	}
	@SuppressWarnings("rawtypes")
	public Set getQmMassExcipients() {
		return this.qmMassExcipients;
	}
	@SuppressWarnings("rawtypes")
	public void setQmMassExcipients(Set qmMassExcipients) {
		this.qmMassExcipients = qmMassExcipients;
	}
	@SuppressWarnings("rawtypes")
	public Set getQmMassStems() {
		return this.qmMassStems;
	}
	@SuppressWarnings("rawtypes")
	public void setQmMassStems(Set qmMassStems) {
		this.qmMassStems = qmMassStems;
	}
	@SuppressWarnings("rawtypes")
	public Set getQmMassProcesses() {
		return this.qmMassProcesses;
	}
	@SuppressWarnings("rawtypes")
	public void setQmMassProcesses(Set qmMassProcesses) {
		this.qmMassProcesses = qmMassProcesses;
	}
	@SuppressWarnings("rawtypes")
	public Set getQmMassRemarkses() {
		return this.qmMassRemarkses;
	}
	@SuppressWarnings("rawtypes")
	public void setQmMassRemarkses(Set qmMassRemarkses) {
		this.qmMassRemarkses = qmMassRemarkses;
	}
	@SuppressWarnings("rawtypes")
	public Set getQmMassOnlines() {
		return this.qmMassOnlines;
	}
	@SuppressWarnings("rawtypes")
	public void setQmMassOnlines(Set qmMassOnlines) {
		this.qmMassOnlines = qmMassOnlines;
	}

}