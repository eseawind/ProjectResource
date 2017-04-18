package com.lanbao.dws.model.combobox;

import java.util.Date;

import javax.persistence.Entity;

/**
 * MdMat entity. @author MyEclipse Persistence Tools
 */
@Entity(name="MD_MAT")
public class MdMat implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String code;
	private String name;
	private String simpleName;
	private String des;
	private Date lastUpdateTime;
	private Long enable;
	private String del;

	// Constructors

	/** default constructor */
	public MdMat() {
	}

	public MdMat(String id) {
		super();
		this.id = id;
	}


	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return this.simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getEnable() {
		return this.enable;
	}

	public void setEnable(Long enable) {
		this.enable = enable;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}



}