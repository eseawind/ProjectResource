package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

/**
 * 
 *@ClassName: EqmSpotchRecode 
* @Description: 点修记录
* @author shisihai
* @date 2015-7-17 上午8:09
 *
 */
public class EqmSpotchRecode {
private String id;
private MdEquipment eqmId;//设备id
private String eqpName;//设备名称
private MdShift shiftId;//班次id
private String shiftName;//班次名字
private String teamId;//班组id
private String teamName;//班组名称
private String chTypeId;//点检类型0--普工2--维修工3--维修组管
private String chTypeName;//点检类型名称
private String chLocation;//点检部位
private String chStandard;//点检标准
private String chMethod;//点检方式 听 闻 看
private Date chDate;//date 型 点检日期
private String createUserId;//点检人id
private String createUserName;
private Date createTime;//dataeTime型 记录时间
private int chStatus; //点检状态      0-下发(未完成)，1-通过(完成)，2-有故障  暂时写死
private String breLocation;//故障部位
private String remark;//备注
private String breId;//故障id
private String updateUserId;//更新着id
private String updateUserName;
private Date updateTime;//dateTime型  更新时间
private int del;//是否删除 1删除  0未删除（需要显示的数据）
private String setId;//数据字典id

public String getSetId() {
	return setId;
}
public void setSetId(String setId) {
	this.setId = setId;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public MdEquipment getEqmId() {
	return eqmId;
}
public void setEqmId(MdEquipment eqmId) {
	this.eqmId = eqmId;
}
public String getEqpName() {
	return eqpName;
}
public void setEqpName(String eqpName) {
	this.eqpName = eqpName;
}
public MdShift getShiftId() {
	return shiftId;
}
public void setShiftId(MdShift shiftId) {
	this.shiftId = shiftId;
}
public String getShiftName() {
	return shiftName;
}
public void setShiftName(String shiftName) {
	this.shiftName = shiftName;
}
public String getTeamId() {
	return teamId;
}
public void setTeamId(String teamId) {
	this.teamId = teamId;
}
public String getTeamName() {
	return teamName;
}
public void setTeamName(String TeamName) {
	this.teamName = TeamName;
}
public String getChTypeId() {
	return chTypeId;
}
public void setChTypeId(String chTypeId) {
	this.chTypeId = chTypeId;
}
public String getChTypeName() {
	return chTypeName;
}
public void setChTypeName(String chTypeName) {
	this.chTypeName = chTypeName;
}
public String getChLocation() {
	return chLocation;
}
public void setChLocation(String chLocation) {
	this.chLocation = chLocation;
}
public String getChStandard() {
	return chStandard;
}
public void setChStandard(String chStandard) {
	this.chStandard = chStandard;
}
public String getChMethod() {
	return chMethod;
}
public void setChMethod(String chMethod) {
	this.chMethod = chMethod;
}
public Date getChDate() {
	return chDate;
}
public void setChDate(Date chDate) {
	this.chDate = chDate;
}
public String getCreateUserId() {
	return createUserId;
}
public void setCreateUserId(String createUserId) {
	this.createUserId = createUserId;
}
public String getCreateUserName() {
	return createUserName;
}
public void setCreateUserName(String createUserName) {
	this.createUserName = createUserName;
}
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}
public int getChStatus() {
	return chStatus;
}
public void setChStatus(int chStatus) {
	this.chStatus = chStatus;
}
public String getBreLocation() {
	return breLocation;
}
public void setBreLocation(String breLocation) {
	this.breLocation = breLocation;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public String getBreId() {
	return breId;
}
public void setBreId(String breId) {
	this.breId = breId;
}
public String getUpdateUserId() {
	return updateUserId;
}
public void setUpdateUserId(String updateUserId) {
	this.updateUserId = updateUserId;
}
public String getUpdateUserName() {
	return updateUserName;
}
public void setUpdateUserName(String updateUserName) {
	this.updateUserName = updateUserName;
}
public Date getUpdateTime() {
	return updateTime;
}
public void setUpdateTime(Date updateTime) {
	this.updateTime = updateTime;
}
public int getDel() {
	return del;
}
public void setDel(int del) {
	this.del = del;
}

}
