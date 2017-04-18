package com.shlanbao.tzsc.base.mapping;

import java.util.Date;


public class EqmTroubleInfo {

	private String id;
	private String code;//代码，用作标识故障信息
	private String description;//故障描述
	private String type;//标题，用于树状结构区分多级标题
	private Date create_date;//故障创建时间
	private String parent_id;//父节点id
	public EqmTroubleInfo() {
		super();
	}
	public EqmTroubleInfo(String id, String code, String description,
			String type, Date create_date, String parent_id) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
		this.type = type;
		this.create_date = create_date;
		this.parent_id = parent_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	
	
}