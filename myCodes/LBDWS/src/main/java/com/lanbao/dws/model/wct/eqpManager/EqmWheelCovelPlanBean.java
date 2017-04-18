package com.lanbao.dws.model.wct.eqpManager;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * [功能说明]：设备- 检修，轮保，润滑( 具体保养计划)
 * @author wanchanghuang
 * */
@Entity(name="eqm_wheel_covel_plan")
public class EqmWheelCovelPlanBean {
	
	private String id; 
	private String eqp_id; //设备编号
	private String eqp_name; //设备名称
	private String eqp_code;//设备code
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date plan_date; //计划维护日期
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date plan_stime; //计划维护开始时间
	private String plan_times; //计划维护时长
	private String plan_name;//MES下发人名称
	private String shift_id; //班次id
	private String shift_name; // 班次名称
	private String type; //类型     1. 轮保 2 润滑 3 停产检修
	private String shop_code; //车间代码 -卷包，成型
	private String shop_name; //车间名称
	private int sh_status; //审核状态 0-默认  1-完成  3-后期处理  4-下发状态
	private String sh_user_id; //审核人ID
	private String sh_user_name; //审核人名称
	private String zx_user_id; //执行人ID
	private String zx_user_name;//执行人姓名
	private String des; //备注
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date last_stime; //实际开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date last_etime; //实际结束时间
	private int del; //是否删除   0默认  1删除
	private String attr1; //备用字段  1,2,3 --实际维护时长
	private String attr2;
	private String attr3;
	private String eqp_work_order_code;//MES设备保养工单号
	
	private String date1; //查询开始时间
	private String date2;//查询结束时间
	private String roleId;//角色ID
	
	
	
	
	public String getEqp_code() {
		return eqp_code;
	}
	public void setEqp_code(String eqp_code) {
		this.eqp_code = eqp_code;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Date getPlan_date() {
		return plan_date;
	}
	public void setPlan_date(Date plan_date) {
		this.plan_date = plan_date;
	}
	public Date getPlan_stime() {
		return plan_stime;
	}
	public void setPlan_stime(Date plan_stime) {
		this.plan_stime = plan_stime;
	}
	public String getPlan_times() {
		return plan_times;
	}
	public void setPlan_times(String plan_times) {
		this.plan_times = plan_times;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShop_code() {
		return shop_code;
	}
	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public int getSh_status() {
		return sh_status;
	}
	public void setSh_status(int sh_status) {
		this.sh_status = sh_status;
	}
	public String getSh_user_id() {
		return sh_user_id;
	}
	public void setSh_user_id(String sh_user_id) {
		this.sh_user_id = sh_user_id;
	}
	public String getSh_user_name() {
		return sh_user_name;
	}
	public void setSh_user_name(String sh_user_name) {
		this.sh_user_name = sh_user_name;
	}
	public String getZx_user_id() {
		return zx_user_id;
	}
	public void setZx_user_id(String zx_user_id) {
		this.zx_user_id = zx_user_id;
	}
	public String getZx_user_name() {
		return zx_user_name;
	}
	public void setZx_user_name(String zx_user_name) {
		this.zx_user_name = zx_user_name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Date getLast_stime() {
		return last_stime;
	}
	public void setLast_stime(Date last_stime) {
		this.last_stime = last_stime;
	}
	public Date getLast_etime() {
		return last_etime;
	}
	public void setLast_etime(Date last_etime) {
		this.last_etime = last_etime;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public String getAttr3() {
		return attr3;
	}
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	public String getEqp_work_order_code() {
		return eqp_work_order_code;
	}
	public void setEqp_work_order_code(String eqp_work_order_code) {
		this.eqp_work_order_code = eqp_work_order_code;
	}
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	

	
}
