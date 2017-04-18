package com.shlanbao.tzsc.wct.eqm.covelplan.beans;

import java.util.Date;
/**
 * 
* @ClassName: EqmWheelCovelParam 
* @Description: 保养内容执行详细信息 
* @author luo
* @date 2015年3月13日 下午2:21:32 
*
 */
public class EqmWheelCovelParamBean{
	private String id;//id
	private String pid;//保养计划Id
	
	private String userName;//用户名称
	private String enable;//是否完成
	private String planTimeStr;//计划开始时间字符串
	//@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String planTime;//计划开始时间
	private String actualTimeStr;//实际完成时间字符串
	//@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String actualTime;//实际完成时间
	private String shiftName;//计划班次
	private String jobName;//计划名称
	private String jobType;//保养类型
	private String jobContext;//保养内容
	private String eqpName;//设备名称

	//----查询条件-------
	private Date startTime;
	private Date endTime;
	private String userId;
	private String eqpId;
	private String eqpCode;
	
	private String queryStartTime;//查询时间
	private String queryEndTime;
	private String queryUserRole;//用户角色
	private String queryPlanId;//计划ID
	private String queryWheelParts;//设备大类ID
	private String queryMetcId;//大类ID,可以根据该ID 查询出此类下的 所有小类
	
	
	private String sbCode;//设备编号 
	private String sbName;//设备名称
	private String sbDes;//设备备注
	private String lxType;//设备类型
	private String sbPid;//设备PID 就是 计划ID
	private String setId;//数据字典 维护项目主键ID
	private String remarks;//备注
	private String planName;//保养计划名称
	private String planId;//保养计划名称
	private String roleType;//角色类型
	private String actualStrTime;//实际完成时间
	private String isCheck;//是否选中
	private String czgUserId;//操作工ID
	private String lbgUserId;//轮保工ID
	private String shgUserId;//审核工ID
	private String 	czgDate;//操作工操作时间
	private String 	lbgDate;//轮保工操作时间
	private String 	shgDate;//审核工操作时间
	private String eqmFixRecId;//维修主键ID
	private String fixType;//维修类型
	private int status;//状态
	
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFixType() {
		return fixType;
	}
	public void setFixType(String fixType) {
		this.fixType = fixType;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	

	public void setActualTimeStr(String actualTimeStr) {
		this.actualTimeStr = actualTimeStr;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobContext() {
		return jobContext;
	}
	public void setJobContext(String jobContext) {
		this.jobContext = jobContext;
	}
	public String getQueryStartTime() {
		return queryStartTime;
	}
	public void setQueryStartTime(String queryStartTime) {
		this.queryStartTime = queryStartTime;
	}
	public String getQueryEndTime() {
		return queryEndTime;
	}
	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}
	public String getEqpCode() {
		return eqpCode;
	}
	public void setEqpCode(String eqpCode) {
		this.eqpCode = eqpCode;
	}
	public String getSetId() {
		return setId;
	}
	public void setSetId(String setId) {
		this.setId = setId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getActualStrTime() {
		return actualStrTime;
	}
	public void setActualStrTime(String actualStrTime) {
		this.actualStrTime = actualStrTime;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getSbCode() {
		return sbCode;
	}
	public void setSbCode(String sbCode) {
		this.sbCode = sbCode;
	}
	public String getSbName() {
		return sbName;
	}
	public void setSbName(String sbName) {
		this.sbName = sbName;
	}
	public String getSbDes() {
		return sbDes;
	}
	public void setSbDes(String sbDes) {
		this.sbDes = sbDes;
	}
	public String getLxType() {
		return lxType;
	}
	public void setLxType(String lxType) {
		this.lxType = lxType;
	}
	public String getQueryUserRole() {
		return queryUserRole;
	}
	public void setQueryUserRole(String queryUserRole) {
		this.queryUserRole = queryUserRole;
	}
	public String getQueryPlanId() {
		return queryPlanId;
	}
	public void setQueryPlanId(String queryPlanId) {
		this.queryPlanId = queryPlanId;
	}
	public String getQueryMetcId() {
		return queryMetcId;
	}
	public void setQueryMetcId(String queryMetcId) {
		this.queryMetcId = queryMetcId;
	}
	public String getQueryWheelParts() {
		return queryWheelParts;
	}
	public void setQueryWheelParts(String queryWheelParts) {
		this.queryWheelParts = queryWheelParts;
	}
	public String getPlanTimeStr() {
		return planTimeStr;
	}
	public void setPlanTimeStr(String planTimeStr) {
		this.planTimeStr = planTimeStr;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getActualTime() {
		return actualTime;
	}
	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}
	public String getActualTimeStr() {
		return actualTimeStr;
	}
	public String getSbPid() {
		return sbPid;
	}
	public void setSbPid(String sbPid) {
		this.sbPid = sbPid;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public String getCzgUserId() {
		return czgUserId;
	}
	public void setCzgUserId(String czgUserId) {
		this.czgUserId = czgUserId;
	}
	public String getLbgUserId() {
		return lbgUserId;
	}
	public void setLbgUserId(String lbgUserId) {
		this.lbgUserId = lbgUserId;
	}
	public String getShgUserId() {
		return shgUserId;
	}
	public void setShgUserId(String shgUserId) {
		this.shgUserId = shgUserId;
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
	public String getShgDate() {
		return shgDate;
	}
	public void setShgDate(String shgDate) {
		this.shgDate = shgDate;
	}
	public String getEqmFixRecId() {
		return eqmFixRecId;
	}
	public void setEqmFixRecId(String eqmFixRecId) {
		this.eqmFixRecId = eqmFixRecId;
	}
	
	
}