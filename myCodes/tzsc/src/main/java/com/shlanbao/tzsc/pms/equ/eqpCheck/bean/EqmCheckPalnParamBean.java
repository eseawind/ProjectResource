package com.shlanbao.tzsc.pms.equ.eqpCheck.bean;


public class EqmCheckPalnParamBean {
	private String id;
	private String pid;//计划id
	private String stime;//实际完成时间
	private String enable;//是否启用
	private String CZUserID;//操作工id
	private String WXUserID;//维修工id
	private String ZGUserID;//维修主管id
	private String status;//状态
	private String eqpId;//设备名
	private String checkType;//点检类型
	private String checkPosition;//点检位置
	private String checkStandard;//点检标准
	private String checkMethod;//点检方式
	private String dicid;
	private String eqpName;//设备机台名称
	
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getDicid() {
		return dicid;
	}
	public void setDicid(String dicid) {
		this.dicid = dicid;
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
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getCZUserID() {
		return CZUserID;
	}
	public void setCZUserID(String cZUserID) {
		CZUserID = cZUserID;
	}
	public String getWXUserID() {
		return WXUserID;
	}
	public void setWXUserID(String wXUserID) {
		WXUserID = wXUserID;
	}
	public String getZGUserID() {
		return ZGUserID;
	}
	public void setZGUserID(String zGUserID) {
		ZGUserID = zGUserID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCheckPosition() {
		return checkPosition;
	}
	public void setCheckPosition(String checkPosition) {
		this.checkPosition = checkPosition;
	}
	public String getCheckStandard() {
		return checkStandard;
	}
	public void setCheckStandard(String checkStandard) {
		this.checkStandard = checkStandard;
	}
	public String getCheckMethod() {
		return checkMethod;
	}
	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}
	
	
}
