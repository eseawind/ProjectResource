package com.shlanbao.tzsc.pms.md.matgrp.beans;
/**
 * 物料类型
 * <li>@author Leejean
 * <li>@create 2014-7-5下午10:04:20
 */
public class MatGrpBean {
	private String id;
    private String code;
    private String name;
    private String des;
    private Long enable;
    private String del;
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
	public MatGrpBean() {
		// TODO Auto-generated constructor stub
	}
	public MatGrpBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Long getEnable() {
		return enable;
	}
	public void setEnable(Long enable) {
		this.enable = enable;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	
}
