package com.shlanbao.tzsc.wct.eqm.checkplan.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：设备点检管理-查询实体
 * 
 * @author wchuang
 * @time 2015年7月16日14:48:48
 * 
 * */
public class EqpCheckRecordBean implements Serializable {
	
	private String id;
	private String eqp_name; //设备类型名称
	private String ch_type_name; //点检类型 
	private String ch_location; //点检关键部位
	private String ch_standard; //点检标准
	private String ch_method; //点检方法 
	private String cid; //
	private String eqp_id;//设备ID
	private String eqp_type_id;//设备类型ID
	private String ch_type_id;//点检类型ID
	private Integer ch_status;//点检状态
	private String set_id; //二级数据字典ID
	private String shift_id;//班组ID
	private String team_id;//ID
	private String stime;
	private String etime;
	
	private String datep;//点检表中日期
	private String userID;//登录人ID
	private String roles;//角色
	private String check_type;//角色种类
	private String check_position;//点检位置
	private String check_standard;//点检标准
	private String check_method;//点检方法
	private String status;//状态  0-未完成 1-完成 2-有故障
	private String shift_name;//班次
	private String team_name;//班组
	private String equipmentId;//机台ID
	private String delf;//判断维修主管状态

	
	public String getDelf() {
		return delf;
	}
	public void setDelf(String delf) {
		this.delf = delf;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getShift_name() {
		return shift_name;
	}
	public void setShift_name(String shift_name) {
		this.shift_name = shift_name;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getCheck_type() {
		return check_type;
	}
	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}
	public String getCheck_position() {
		return check_position;
	}
	public void setCheck_position(String check_position) {
		this.check_position = check_position;
	}
	public String getCheck_standard() {
		return check_standard;
	}
	public void setCheck_standard(String check_standard) {
		this.check_standard = check_standard;
	}
	public String getCheck_method() {
		return check_method;
	}
	public void setCheck_method(String check_method) {
		this.check_method = check_method;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roleID) {
		this.roles = roleID;
	}
	public String getDatep() {
		return datep;
	}
	public void setDatep(String datep) {
		this.datep = datep;
	}
	public String getEqp_type_id() {
		return eqp_type_id;
	}
	public void setEqp_type_id(String eqp_type_id) {
		this.eqp_type_id = eqp_type_id;
	}
	public String getShift_id() {
		return shift_id;
	}
	public void setShift_id(String shift_id) {
		this.shift_id = shift_id;
	}
	public String getSet_id() {
		return set_id;
	}
	public void setSet_id(String set_id) {
		this.set_id = set_id;
	}
	public Integer getCh_status() {
		return ch_status;
	}
	public void setCh_status(Integer ch_status) {
		this.ch_status = ch_status;
	}
	public String getEqp_id() {
		return eqp_id;
	}
	public void setEqp_id(String eqp_id) {
		this.eqp_id = eqp_id;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqp_name() {
		return eqp_name;
	}
	public void setEqp_name(String eqp_name) {
		this.eqp_name = eqp_name;
	}
	public String getCh_type_name() {
		return ch_type_name;
	}
	public void setCh_type_name(String ch_type_name) {
		this.ch_type_name = ch_type_name;
	}
	public String getCh_location() {
		return ch_location;
	}
	public void setCh_location(String ch_location) {
		this.ch_location = ch_location;
	}
	public String getCh_standard() {
		return ch_standard;
	}
	public void setCh_standard(String ch_standard) {
		this.ch_standard = ch_standard;
	}
	public String getCh_method() {
		return ch_method;
	}
	public void setCh_method(String ch_method) {
		this.ch_method = ch_method;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCh_type_id() {
		return ch_type_id;
	}
	public void setCh_type_id(String ch_type_id) {
		this.ch_type_id = ch_type_id;
	}
	
	
	
	
	
}
