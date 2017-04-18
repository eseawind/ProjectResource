package com.shlanbao.tzsc.base.mapping;

/**
 * SysFavorite entity. @author MyEclipse Persistence Tools
 */

public class SysFavorite implements java.io.Serializable {

	// Fields

	private String id;
	private SysResource sysResource;
	private SysUser sysUser;

	// Constructors

	/** default constructor */
	public SysFavorite() {
	}

	/** full constructor */
	public SysFavorite(SysResource sysResource, SysUser sysUser) {
		this.sysResource = sysResource;
		this.sysUser = sysUser;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysResource getSysResource() {
		return this.sysResource;
	}

	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}

	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

}