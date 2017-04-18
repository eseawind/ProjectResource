package com.shlanbao.tzsc.pms.equ.trouble.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class EqmTroubleBean {
	private String id;
	private String equ_id;//设备ID
	private String equ_name;//设备名称
	private String shift_id;//班次ID
	private String shift_name;//班次名称
	private String description;//故障描述
	private String source_id;//故障来源ID，存放点检   检修    检修的ID
	private String spare_id;//存放备品备件ID
	private String create_user_id;//创建故障信息的用户ID
	private String create_user_name;//创建故障信息的用户名称
	private String create_date;//创建故障信息时间
	private String trouble_name;//故障名称
	private String source;//1-点检   2-维修呼叫   3-轮保   4-备品备件  5、检修   对应SOURCE_ID
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqu_id() {
		return equ_id;
	}
	public void setEqu_id(String equ_id) {
		this.equ_id = equ_id;
	}
	public String getEqu_name() {
		return equ_name;
	}
	public void setEqu_name(String equ_name) {
		this.equ_name = equ_name;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSource_id() {
		return source_id;
	}
	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}
	public String getSpare_id() {
		return spare_id;
	}
	public void setSpare_id(String spare_id) {
		this.spare_id = spare_id;
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
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getTrouble_name() {
		return trouble_name;
	}
	public void setTrouble_name(String trouble_name) {
		this.trouble_name = trouble_name;
	}
	
	
}
