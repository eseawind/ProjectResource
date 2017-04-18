package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

public class EqmTrouble {

	private String id;
	private String equ_id;//设备ID
	private String shift_id;//班次ID
	private String description;//故障描述
	private String source_id;//故障来源ID，存放点检   检修    检修的ID
	//private String spare_id;//存放备品备件ID
	private String create_user_id;//创建故障信息的用户ID
	//private String create_user_name;//创建故障信息的用户名称
	private Date create_date;//创建故障信息时间
	private String trouble_name;//故障名称
	private String source;//1-点检   2-维修呼叫   3-轮保   4-备品备件  5、检修   对应SOURCE_ID
	public EqmTrouble() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EqmTrouble(String id, String equ_id, String shift_id,
			String description, String source_id, 
			String create_user_id,  Date create_date,
			String trouble_name, String source) {
		super();
		this.id = id;
		this.equ_id = equ_id;
		this.shift_id = shift_id;
		this.description = description;
		this.source_id = source_id;
		this.create_user_id = create_user_id;
		this.create_date = create_date;
		this.trouble_name = trouble_name;
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
	public String getShift_id() {
		return shift_id;
	}
	public void setShift_id(String shift_id) {
		this.shift_id = shift_id;
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
	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getTrouble_name() {
		return trouble_name;
	}
	public void setTrouble_name(String trouble_name) {
		this.trouble_name = trouble_name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}