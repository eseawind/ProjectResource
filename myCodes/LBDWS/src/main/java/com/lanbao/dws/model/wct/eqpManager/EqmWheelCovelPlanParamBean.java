package com.lanbao.dws.model.wct.eqpManager;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * [功能说明]：设备- 检修，轮保，润滑( 具体保养项)
 * @author wanchanghuang
 * */
public class EqmWheelCovelPlanParamBean {
	
	private String id;
	private String eqp_id; //设备编号
	private String eqp_code;//设备code
	private String eqm_wcp_id; //主表ID
	private String content; //保养内容  -保养项目
	private String content_datail; //保养内容明细规则  -保养方法
	private String job_standard; //保养效果标准
	private String role_id; //角色ID
	private String role_name;//角色名称
	private String actual_user_id; //实际保养人ID
	private String actual_user_name; //实际保养人姓名
	private Date update_time; //保养完成时间
	private String des; //备注 --检查情况
	private Integer status; //状态  0-默认  1-通过  3-后期处理    -完成情况
	private String last_user_id; //后期处理人
	private Date last_time; //后期计划处理时间
	private Integer del; //0-默认  1-删除
	private String sys_ec_id; //SYS_EQP_CATEGORY 表ID 
	private String attr1; //备用字段 1,2,3
	private String attr2;
	private String attr3;
	
	
	public String getEqp_id() {
		return eqp_id;
	}
	public void setEqp_id(String eqp_id) {
		this.eqp_id = eqp_id;
	}
	public String getEqp_code() {
		return eqp_code;
	}
	public void setEqp_code(String eqp_code) {
		this.eqp_code = eqp_code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqm_wcp_id() {
		return eqm_wcp_id;
	}
	public void setEqm_wcp_id(String eqm_wcp_id) {
		this.eqm_wcp_id = eqm_wcp_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent_datail() {
		return content_datail;
	}
	public void setContent_datail(String content_datail) {
		this.content_datail = content_datail;
	}
	public String getJob_standard() {
		return job_standard;
	}
	public void setJob_standard(String job_standard) {
		this.job_standard = job_standard;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getActual_user_id() {
		return actual_user_id;
	}
	public void setActual_user_id(String actual_user_id) {
		this.actual_user_id = actual_user_id;
	}
	public String getActual_user_name() {
		return actual_user_name;
	}
	public void setActual_user_name(String actual_user_name) {
		this.actual_user_name = actual_user_name;
	}
	
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLast_user_id() {
		return last_user_id;
	}
	public void setLast_user_id(String last_user_id) {
		this.last_user_id = last_user_id;
	}
	
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Date getLast_time() {
		return last_time;
	}
	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public String getSys_ec_id() {
		return sys_ec_id;
	}
	public void setSys_ec_id(String sys_ec_id) {
		this.sys_ec_id = sys_ec_id;
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
	
	
	

}
