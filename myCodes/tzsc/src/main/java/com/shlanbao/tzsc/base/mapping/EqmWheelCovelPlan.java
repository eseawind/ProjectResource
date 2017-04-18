package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * EqmWheelCovelPlan entity. @author MyEclipse Persistence Tools
 */

public class EqmWheelCovelPlan{

	// Fields

	private String id;
	private SysUser sysUserByDutyPeopleId;
	private SysUser sysUserByCreateId;//创建人
	private MdEquipment mdEquipment;//设备
	private MdShift mdShift;//班次
	private EqmWheelCovelPlan eqmWheelCovelPlan;
	private String planNo;//计划编号
	private String planName;//计划名称
	private String scheduleStrDate;//计划开始时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private Date scheduleDate;//计划开始时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private Date scheduleEndDate;//计划结束时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;//实际开始时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;//实际结束时间
	private String maintenanceLength;//计划时长
	private String maintenanceContent;//计划内容
	private String planner;//制单人
	private String maintenanceType;//计划类型,轮保类别
	private String locationCode;//车间代码
	private String wheelCoverType;//工单类型
	private String wheelParts;//轮保部件
	private String period;//周期
	private String remindCycle;//提醒周期
	private String dutyPeopleName;//责任人id
	private Date endMaintainDate;//最后维修日期
	private String del;//是否删除
	private Date createDate;//创建日期
	private String mdMatId;//设备ID
	private String auditUserId;//审核人ID
	private String auditTime;//审核时间
	private String ratifyUserId;//批准人ID
	private String ratifyTime;//批准时间
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	
	private SysUser czgUserId;//操作工ID
	private SysUser lbgUserId;//轮保工ID
	private SysUser shgUserId;//审核工ID
	private SysUser endUserId;//最后完成人ID
	private String 	czgDate;//操作工操作时间
	private String 	lbgDate;//轮保工操作时间
	private String 	shgDate;//审核工操作时间
	private String 	isCzgFinsh;//操作工是否完成
	private String 	isLbgFinsh;//轮保工是否完成
	private String 	isShgFinsh;//审核工是否完成
	private String modifyDate;//修改时间
	private String zrId;//主任ID
	private String glId;//车间管理ID
	private String zrName;//主任角色
	private String glName;//车间管理角色

	// Constructors

	/** default constructor */
	public EqmWheelCovelPlan() {
	}
	
	public EqmWheelCovelPlan(EqmWheelCovelPlan eqmWheelCovelPlan){
		this.eqmWheelCovelPlan = eqmWheelCovelPlan;
	}
	
   public EqmWheelCovelPlan(String id) {
		super();
		this.id = id;
	}
	/** full constructor */
	public EqmWheelCovelPlan(SysUser sysUserByDutyPeopleId,
			SysUser sysUserByCreateId, MdEquipment mdEquipment,
			MdShift mdShift, String planNo, String planName,
			Date scheduleDate,Date scheduleEndDate, Date startTime, Date endTime,
			String maintenanceLength, String maintenanceContent,
			String planner, String maintenanceType, String locationCode,
			String wheelCoverType, String wheelParts, String period,
			String remindCycle, String dutyPeopleName,
			Date endMaintainDate, String del, Date createDate,
			String attr1, String attr2, String attr3, String attr4,String zrId,String glId,String zrName,String glName) {
		this.sysUserByDutyPeopleId = sysUserByDutyPeopleId;
		this.sysUserByCreateId = sysUserByCreateId;
		this.mdEquipment = mdEquipment;
		this.mdShift = mdShift;
		this.planNo = planNo;
		this.planName = planName;
		this.scheduleDate = scheduleDate;
		this.scheduleEndDate = scheduleEndDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.maintenanceLength = maintenanceLength;
		this.maintenanceContent = maintenanceContent;
		this.planner = planner;
		this.maintenanceType = maintenanceType;
		this.locationCode = locationCode;
		this.wheelCoverType = wheelCoverType;
		this.wheelParts = wheelParts;
		this.period = period;
		this.remindCycle = remindCycle;
		this.dutyPeopleName = dutyPeopleName;
		this.endMaintainDate = endMaintainDate;
		this.del = del;
		this.createDate = createDate;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.attr4 = attr4;
		this.zrId = zrId;
		this.glId = glId;
		this.zrName = zrName;
		this.glName = glName;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public String getzrName() {
		return zrName;
	}

	public void setzrName(String zrName) {
		this.zrName = zrName;
	}

	public String getglName() {
		return glName;
	}

	public void setglName(String glName) {
		this.glName = glName;
	}

	public String getZrId() {
		return zrId;
	}

	public void setZrId(String zrId) {
		this.zrId = zrId;
	}

	public String getGlId() {
		return glId;
	}

	public void setGlId(String glId) {
		this.glId = glId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysUser getSysUserByDutyPeopleId() {
		return this.sysUserByDutyPeopleId;
	}

	public void setSysUserByDutyPeopleId(SysUser sysUserByDutyPeopleId) {
		this.sysUserByDutyPeopleId = sysUserByDutyPeopleId;
	}

	public SysUser getSysUserByCreateId() {
		return this.sysUserByCreateId;
	}

	public void setSysUserByCreateId(SysUser sysUserByCreateId) {
		this.sysUserByCreateId = sysUserByCreateId;
	}

	public MdEquipment getMdEquipment() {
		return this.mdEquipment;
	}

	public void setMdEquipment(MdEquipment mdEquipment) {
		this.mdEquipment = mdEquipment;
	}

	public MdShift getMdShift() {
		return this.mdShift;
	}

	public void setMdShift(MdShift mdShift) {
		this.mdShift = mdShift;
	}

	public EqmWheelCovelPlan getEqmWheelCovelPlan() {
		return eqmWheelCovelPlan;
	}

	public void setEqmWheelCovelPlan(EqmWheelCovelPlan eqmWheelCovelPlan) {
		this.eqmWheelCovelPlan = eqmWheelCovelPlan;
	}

	public String getPlanNo() {
		return this.planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getPlanName() {
		return this.planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Date getScheduleDate() {
		return this.scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Date getScheduleEndDate() {
		return scheduleEndDate;
	}

	public void setScheduleEndDate(Date scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMaintenanceLength() {
		return this.maintenanceLength;
	}

	public void setMaintenanceLength(String maintenanceLength) {
		this.maintenanceLength = maintenanceLength;
	}

	public String getMaintenanceContent() {
		return this.maintenanceContent;
	}

	public void setMaintenanceContent(String maintenanceContent) {
		this.maintenanceContent = maintenanceContent;
	}

	public String getPlanner() {
		return this.planner;
	}

	public void setPlanner(String planner) {
		this.planner = planner;
	}

	public String getMaintenanceType() {
		return this.maintenanceType;
	}

	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getWheelCoverType() {
		return this.wheelCoverType;
	}

	public void setWheelCoverType(String wheelCoverType) {
		this.wheelCoverType = wheelCoverType;
	}

	public String getWheelParts() {
		return this.wheelParts;
	}

	public void setWheelParts(String wheelParts) {
		this.wheelParts = wheelParts;
	}

	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getRemindCycle() {
		return this.remindCycle;
	}

	public void setRemindCycle(String remindCycle) {
		this.remindCycle = remindCycle;
	}

	public String getDutyPeopleName() {
		return this.dutyPeopleName;
	}

	public void setDutyPeopleName(String dutyPeopleName) {
		this.dutyPeopleName = dutyPeopleName;
	}

	public Date getEndMaintainDate() {
		return this.endMaintainDate;
	}

	public void setEndMaintainDate(Date endMaintainDate) {
		this.endMaintainDate = endMaintainDate;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAttr1() {
		return this.attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return this.attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return this.attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getAttr4() {
		return this.attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	public String getMdMatId() {
		return mdMatId;
	}

	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}

	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getRatifyUserId() {
		return ratifyUserId;
	}

	public void setRatifyUserId(String ratifyUserId) {
		this.ratifyUserId = ratifyUserId;
	}

	public String getRatifyTime() {
		return ratifyTime;
	}

	public void setRatifyTime(String ratifyTime) {
		this.ratifyTime = ratifyTime;
	}

	public String getCzgDate() {
		return czgDate;
	}

	public void setCzgDate(String czgDate) {
		this.czgDate = czgDate;
	}

	public String getLbgDate() {
		return lbgDate;
	}

	public void setLbgDate(String lbgDate) {
		this.lbgDate = lbgDate;
	}

	public SysUser getCzgUserId() {
		return czgUserId;
	}

	public void setCzgUserId(SysUser czgUserId) {
		this.czgUserId = czgUserId;
	}

	public SysUser getLbgUserId() {
		return lbgUserId;
	}

	public void setLbgUserId(SysUser lbgUserId) {
		this.lbgUserId = lbgUserId;
	}

	public String getScheduleStrDate() {
		return scheduleStrDate;
	}

	public void setScheduleStrDate(String scheduleStrDate) {
		this.scheduleStrDate = scheduleStrDate;
	}

	public String getIsCzgFinsh() {
		return isCzgFinsh;
	}

	public void setIsCzgFinsh(String isCzgFinsh) {
		this.isCzgFinsh = isCzgFinsh;
	}

	public String getIsLbgFinsh() {
		return isLbgFinsh;
	}

	public void setIsLbgFinsh(String isLbgFinsh) {
		this.isLbgFinsh = isLbgFinsh;
	}

	public String getIsShgFinsh() {
		return isShgFinsh;
	}

	public void setIsShgFinsh(String isShgFinsh) {
		this.isShgFinsh = isShgFinsh;
	}

	public SysUser getShgUserId() {
		return shgUserId;
	}

	public void setShgUserId(SysUser shgUserId) {
		this.shgUserId = shgUserId;
	}

	public String getShgDate() {
		return shgDate;
	}

	public void setShgDate(String shgDate) {
		this.shgDate = shgDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public SysUser getEndUserId() {
		return endUserId;
	}

	public void setEndUserId(SysUser endUserId) {
		this.endUserId = endUserId;
	}
	
	
}