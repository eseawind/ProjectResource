package com.shlanbao.tzsc.wct.eqm.fix.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

//设备维修，维修工人员维护实体对象
public class SysMaintenanceStaff {
	
	private String id;
	private String shift_id;
	private String user_id;
	private String user_name;
	private String remark;
	private String status;
	private String crete_user_id;
	private String create_user_name;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date create_user_time;
	private String update_user_id;
	private String update_user_name;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date update_user_time;
	private String type_id;
	private String type_name;
	private String path;
	
	private String eqp_type;
	private String team_id; //班组ID
	
	
	
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getEqp_type() {
		return eqp_type;
	}
	public void setEqp_type(String eqp_type) {
		this.eqp_type = eqp_type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShift_id() {
		return shift_id;
	}
	public void setShift_id(String shift_id) {
		this.shift_id = shift_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCrete_user_id() {
		return crete_user_id;
	}
	public void setCrete_user_id(String crete_user_id) {
		this.crete_user_id = crete_user_id;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public Date getCreate_user_time() {
		return create_user_time;
	}
	public void setCreate_user_time(Date create_user_time) {
		this.create_user_time = create_user_time;
	}
	public String getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
	public Date getUpdate_user_time() {
		return update_user_time;
	}
	public void setUpdate_user_time(Date update_user_time) {
		this.update_user_time = update_user_time;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
