package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

public class EqmCheckPlan {
private String id;
private MdShift shift;//班次
private MdTeam team;//班组
private Date dateP;//点检计划开始日期
private MdEqpCategory mdEqpCategory;//设备类型
private String del;//删除标识
private String name;//一级名
private String status;//完成状态    0-下发(未完成)，1-通过(完成)，2-有故障  暂时写死
private String dicid;//数据字典一级id

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
public MdShift getShift() {
	return shift;
}
public void setShift(MdShift shift) {
	this.shift = shift;
}
public MdTeam getTeam() {
	return team;
}
public void setTeam(MdTeam team) {
	this.team = team;
}
public Date getDateP() {
	return dateP;
}
public void setDateP(Date dateP) {
	this.dateP = dateP;
}
public MdEqpCategory getMdEqpCategory() {
	return mdEqpCategory;
}
public void setMdEqpCategory(MdEqpCategory mdEqpCategory) {
	this.mdEqpCategory = mdEqpCategory;
}
public String getDel() {
	return del;
}
public void setDel(String del) {
	this.del = del;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
}
