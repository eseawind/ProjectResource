package com.shlanbao.tzsc.pms.md.unit.beans;


/**
 *  计量单位
 * <li>@author Leejean
 * <li>@create 2014-7-5下午10:04:20
 */
public class UnitBean {
	 private String id;
     private String code;
     private String name;
     private String type;
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
	public UnitBean() {
		// TODO Auto-generated constructor stub
	}
	public UnitBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public UnitBean(String id,String name,String code){
		super();
		this.name = name;
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
