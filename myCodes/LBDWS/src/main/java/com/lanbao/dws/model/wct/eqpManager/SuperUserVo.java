package com.lanbao.dws.model.wct.eqpManager;

import java.io.Serializable;

import javax.persistence.Entity;
@Entity(name = "sys_user")
public class SuperUserVo implements Serializable{
	
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

}
