package com.shlanbao.tzsc.base.model;
/**
 * Easyui combobox组件model
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:16:05
 */
public class Combobox implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
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
	public Combobox() {
		// TODO Auto-generated constructor stub
	}
	public Combobox(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	
}
