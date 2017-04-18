package com.shlanbao.tzsc.base.mapping;

/**
 * SysUserRole entity. @author MyEclipse Persistence Tools
 */

public class SysUserRole implements java.io.Serializable {

	// Fields

	private String id;
	private SysRole sysRole;
	private SysUser sysUser;

	// Constructors

	/** default constructor */
	public SysUserRole() {
	}

	/** full constructor */
	public SysUserRole(SysRole sysRole, SysUser sysUser) {
		this.sysRole = sysRole;
		this.sysUser = sysUser;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysRole getSysRole() {
		return this.sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

}