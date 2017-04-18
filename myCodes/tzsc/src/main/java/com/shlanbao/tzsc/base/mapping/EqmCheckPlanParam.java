package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

public class EqmCheckPlanParam {
private String id;
private EqmCheckPlan pid;//计划id
private Date stime;//实际完成时间
private String enable;//是否启用
private String status;//状态   0-下发(未完成)，1-通过(完成)，2-有故障  暂时写死
private String eqpid;//设备名
private String checkType;//点检类型
private String checkPosition;//点检位置
private String checkStandard;//点检标准
private String checkMethod;//点检方式
private String dicid;//数据字典二级级id

public String getEqpid() {
	return eqpid;
}
public void setEqpid(String eqpid) {
	this.eqpid = eqpid;
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
public EqmCheckPlan getPid() {
	return pid;
}
public void setPid(EqmCheckPlan pid) {
	this.pid = pid;
}
public Date getStime() {
	return stime;
}
public void setStime(Date stime) {
	this.stime = stime;
}
public String getEnable() {
	return enable;
}
public void setEnable(String enable) {
	this.enable = enable;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
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
