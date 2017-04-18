package com.shlanbao.tzsc.wct.eqm.fix.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

//设备维修服务明细实体对象
public class SysServiceInfo {
	
	private String id;
	private String shift_id;
	private String team_id;
	private String eqp_id;
	private String eqp_name;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date create_user_time;
	private String create_user_id;
	private String create_user_name;
	private String designated_person_id;
	private String designated_person_name;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date designated_person_time;
	private String update_user_id;
	private String update_user_name;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date update_user_time;

	private String type_name;
	private String place;
	private String remark;
	private String status;
	private String workorder_id;
	private String del;
	private String shift_name;

	private String stime; //开始时间
	private String etime; //结束时间
	
	private String roletype;//存放角色类型
	private String spare_parts_num;//使用的备品备件数量
	private String spare_parts_id;//使用的备品备件id
	private String spare_prats_name;//使用的备品备件名称
	private String trouble_name;//故障名称
	private String description;//故障描述
	
	
	public String getSpare_parts_num() {
		return spare_parts_num;
	}
	public void setSpare_parts_num(String spare_parts_num) {
		this.spare_parts_num = spare_parts_num;
	}
	public String getSpare_parts_id() {
		return spare_parts_id;
	}
	public void setSpare_parts_id(String spare_parts_id) {
		this.spare_parts_id = spare_parts_id;
	}
	public String getSpare_prats_name() {
		return spare_prats_name;
	}
	public void setSpare_prats_name(String spare_prats_name) {
		this.spare_prats_name = spare_prats_name;
	}
	public String getTrouble_name() {
		return trouble_name;
	}
	public void setTrouble_name(String trouble_name) {
		this.trouble_name = trouble_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRoletype() {
		return roletype;
	}
	public void setRoletype(String roletype) {
		this.roletype = roletype;
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
	public String getShift_name() {
		return shift_name;
	}
	public void setShift_name(String shift_name) {
		this.shift_name = shift_name;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
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
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getEqp_id() {
		return eqp_id;
	}
	public void setEqp_id(String eqp_id) {
		this.eqp_id = eqp_id;
	}
	public String getEqp_name() {
		return eqp_name;
	}
	public void setEqp_name(String eqp_name) {
		this.eqp_name = eqp_name;
	}
	public Date getCreate_user_time() {
		return create_user_time;
	}
	public void setCreate_user_time(Date create_user_time) {
		this.create_user_time = create_user_time;
	}
	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public String getDesignated_person_id() {
		return designated_person_id;
	}
	public void setDesignated_person_id(String designated_person_id) {
		this.designated_person_id = designated_person_id;
	}
	public String getDesignated_person_name() {
		return designated_person_name;
	}
	public void setDesignated_person_name(String designated_person_name) {
		this.designated_person_name = designated_person_name;
	}
	public Date getDesignated_person_time() {
		return designated_person_time;
	}
	public void setDesignated_person_time(Date designated_person_time) {
		this.designated_person_time = designated_person_time;
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
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
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
	public String getWorkorder_id() {
		return workorder_id;
	}
	public void setWorkorder_id(String workorder_id) {
		this.workorder_id = workorder_id;
	}
	

	
	
	
	
	
	
	
	
	
}
