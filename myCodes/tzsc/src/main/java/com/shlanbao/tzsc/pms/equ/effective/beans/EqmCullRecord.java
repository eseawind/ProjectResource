package com.shlanbao.tzsc.pms.equ.effective.beans;

import java.io.Serializable;
import java.util.Date;

public class EqmCullRecord implements Serializable{
	
	private String id;
	private String eqp_name; //设备名称
	private String eqp_id;  //设备ID
	private String shift_id; //班次ID
	private String shift_name; //班次名称
	private String cid;  //外键关联ID
	private String st_date;  //开始时间
	private String ed_date;  //结束时间
	private String remark;  //备注
	private Date create_time;  //创建时间
	private String create_user_id; //创建人ID
	private String create_user_name; //创建人姓名
	
	private Date update_time; //最后修改时间
	private String update_user_id; //最后修改人ID
	private String update_user_name; //最后修改人姓名
	
	private Integer del;  //是否显示   1-显示 0-删除
	private String type_id; //剔除类型ID   1-固定停机  2-计划提前完成停机   3-不可控停机
	private String type_name; //剔除类型名称  1(就餐，试车，保养，换牌)   2(由手动添加)   3(停电，停水)
	
	private String stop_time; //剔除总时间 
	
	private String sdate;
	private String edate;
	private String stime;
	private String etime;
	
	
	
	
	public String getStop_time() {
		return stop_time;
	}
	public void setStop_time(String stop_time) {
		this.stop_time = stop_time;
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
	public String getEqp_id() {
		return eqp_id;
	}
	public void setEqp_id(String eqp_id) {
		this.eqp_id = eqp_id;
	}
	public String getShift_id() {
		return shift_id;
	}
	public void setShift_id(String shift_id) {
		this.shift_id = shift_id;
	}
	public String getShift_name() {
		return shift_name;
	}
	public void setShift_name(String shift_name) {
		this.shift_name = shift_name;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public String getSt_date() {
		return st_date;
	}
	public void setSt_date(String st_date) {
		this.st_date = st_date;
	}
	public String getEd_date() {
		return ed_date;
	}
	public void setEd_date(String ed_date) {
		this.ed_date = ed_date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
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
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
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
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
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
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
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


}
