package com.shlanbao.tzsc.base.mapping;

import java.io.Serializable;
import java.util.Date;


public class EqmProtectRecordBean implements Serializable{
	private String id;//ID	 
	private String equ_name;//设备名称
	private String ep_shift; //班次
	private String ep_team; //班组
	private String ep_stime; //计划开始时间
	private String ep_etime; //计划结束时间
	private String ep_name;	 //保养名称
	private String epr_time;//实际保养日期
	private String epr_username;//保养人
	
	private String stime; //开始时间
	private String etime; //结束时间
	
	private String equ_id; // 设备ID
	private String status; // 保养状态
	private String eqpTypeName; // sys_eqp_type中name字段
	private String eqpTypeDES; // sys_eqp_type中des字段
	
	
	public String getEqpTypeName() {
		return eqpTypeName;
	}
	public void setEqpTypeName(String eqpTypeName) {
		this.eqpTypeName = eqpTypeName;
	}
	public String getEqpTypeDES() {
		return eqpTypeDES;
	}
	public void setEqpTypeDES(String eqpTypeDES) {
		this.eqpTypeDES = eqpTypeDES;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEqu_id() {
		return equ_id;
	}
	public void setEqu_id(String equ_id) {
		this.equ_id = equ_id;
	}
	public String getEp_etime() {
		return ep_etime;
	}
	public void setEp_etime(String ep_etime) {
		this.ep_etime = ep_etime;
	}
	
	
	
	public String getEp_stime() {
		return ep_stime;
	}
	public void setEp_stime(String ep_stime) {
		this.ep_stime = ep_stime;
	}
	public String getEpr_time() {
		return epr_time;
	}
	public void setEpr_time(String epr_time) {
		this.epr_time = epr_time;
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
	public String getEqu_name() {
		return equ_name;
	}
	public void setEqu_name(String equ_name) {
		this.equ_name = equ_name;
	}
	public String getEp_shift() {
		return ep_shift;
	}
	public void setEp_shift(String ep_shift) {
		this.ep_shift = ep_shift;
	}
	public String getEp_team() {
		return ep_team;
	}
	public void setEp_team(String ep_team) {
		this.ep_team = ep_team;
	}
	
	public String getEp_name() {
		return ep_name;
	}
	public void setEp_name(String ep_name) {
		this.ep_name = ep_name;
	}
	
	public String getEpr_username() {
		return epr_username;
	}
	public void setEpr_username(String epr_username) {
		this.epr_username = epr_username;
	}
	
	
	
}
