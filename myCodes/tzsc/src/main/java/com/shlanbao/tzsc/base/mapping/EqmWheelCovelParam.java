package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * 
* @ClassName: EqmWheelCovelParam 
* @Description: 保养内容执行详细信息 
* @author luo
* @date 2015年3月13日 下午2:21:32 
*
 */
public class EqmWheelCovelParam{
	private String id;//id
	private EqmWheelCovelPlan pid;//保养计划
	private SysUser user;//完成的用户
	private String enable;//是否完成
	//@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String planTime;//计划开始时间
	//@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String actualTime;//实际完成时间
	
	private String setId;//数据字典 维护项目主键ID
	private String remarks;//备注
	
	private SysUser czgUserId;//操作工ID
	private SysUser lbgUserId;//轮保工ID
	private SysUser shgUserId;//审核工ID
	private String 	czgDate;//操作工操作时间
	private String 	lbgDate;//轮保工操作时间
	private String 	shgDate;//审核工操作时间
	private int  fixType;//维修类型
	private int  status;//状态
	
	
	
	public int getFixType() {
		return fixType;
	}
	public void setFixType(int fixType) {
		this.fixType = fixType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public EqmWheelCovelPlan getPid() {
		return pid;
	}
	public void setPid(EqmWheelCovelPlan pid) {
		this.pid = pid;
	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
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
	public SysUser getShgUserId() {
		return shgUserId;
	}
	public void setShgUserId(SysUser shgUserId) {
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
	

}