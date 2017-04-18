package com.shlanbao.tzsc.pms.equ.wcplan.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;
import com.shlanbao.tzsc.utils.tools.DateUtil;
import com.shlanbao.tzsc.utils.tools.StringUtil;

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
	
	//@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String planTime;//计划开始时间
	private String actualTimeStr;//实际完成时间字符串
	//@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String actualTime;//实际完成时间
	private String shiftName;//计划班次
	private String jobName;//计划名称
	private String jobType;//保养类型
	private String jobContext;//保养内容
	//----查询条件-------
	private Date startTime;
	private Date endTime;
	private String lxType;//角色类型
	
	private String sbCode;//设备编号 
	private String sbName;//设备名称
	private String sbDes;//设备备注
	
	private String planName;//保养计划名称
	private String isCheck;//是否选中
	private String sbZjId;//设备ID
	private String sbPid;
	private String actualStrTime;//实际完成时间,
	private String userId;//用户名称
	private String userName;//用户名称
	private String planTimeStr;//计划开始时间字符串
	private String enable;//是否完成
	private String remarks;//备注
	private String setId;//数据字典 维护项目主键ID
	
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
	public String getPlanTimeStr() {
		return planTimeStr;
	}
	public void setPlanTimeStr(String planTimeStr) {
		this.planTimeStr = planTimeStr;
	}
	public String getActualTimeStr() {
		return actualTimeStr;
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
	public String getLxType() {
		return lxType;
	}
	public void setLxType(String lxType) {
		this.lxType = lxType;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSbZjId() {
		return sbZjId;
	}
	public void setSbZjId(String sbZjId) {
		this.sbZjId = sbZjId;
	}
	public String getSbPid() {
		return sbPid;
	}
	public void setSbPid(String sbPid) {
		this.sbPid = sbPid;
	}
	
}