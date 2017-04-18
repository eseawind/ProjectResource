package com.lanbao.dws.model.combobox;

import java.util.Date;

import javax.persistence.Entity;

@Entity(name="MD_FIX_CODE")
public class MdFixCode implements java.io.Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private String id;
	private String name; // 名称
	private String mesCode; // MES编码
	private String type; // 节点类型
	private String del; // 删除 0-默认 1-删除
	private String code; // 编号
	private String upcode; // 上节点（父节点）
	private String des; // 备注
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String bak;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMesCode() {
		return mesCode;
	}

	public void setMesCode(String mesCode) {
		this.mesCode = mesCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUpcode() {
		return upcode;
	}

	public void setUpcode(String upcode) {
		this.upcode = upcode;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getBak() {
		return bak;
	}

	public void setBak(String bak) {
		this.bak = bak;
	}

}
