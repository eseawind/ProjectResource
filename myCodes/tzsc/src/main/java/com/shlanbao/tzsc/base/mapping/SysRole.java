package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */

public class SysRole  implements java.io.Serializable {


    // Fields    

     private String id;
     private SysRole sysRole;
     private String name;
     private String remark;
     private Long seq;
     private Long enable;
     private Long del;
     private String iconCls;
     private Set sysUserRoles = new HashSet(0);
     private Set sysRoles = new HashSet(0);
     private Set sysRoleResources = new HashSet(0);


    // Constructors

    /** default constructor */
    public SysRole() {
    }

    
    
    public SysRole(String id) {
		super();
		this.id = id;
	}



	/** full constructor */
    public SysRole(SysRole sysRole, String name, String remark, Long seq, Long enable, Long del, String iconCls, Set sysUserRoles, Set sysRoles, Set sysRoleResources) {
        this.sysRole = sysRole;
        this.name = name;
        this.remark = remark;
        this.seq = seq;
        this.enable = enable;
        this.del = del;
        this.iconCls = iconCls;
        this.sysUserRoles = sysUserRoles;
        this.sysRoles = sysRoles;
        this.sysRoleResources = sysRoleResources;
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

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSeq() {
        return this.seq;
    }
    
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getEnable() {
        return this.enable;
    }
    
    public void setEnable(Long enable) {
        this.enable = enable;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }

    public String getIconCls() {
        return this.iconCls;
    }
    
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Set getSysUserRoles() {
        return this.sysUserRoles;
    }
    
    public void setSysUserRoles(Set sysUserRoles) {
        this.sysUserRoles = sysUserRoles;
    }

    public Set getSysRoles() {
        return this.sysRoles;
    }
    
    public void setSysRoles(Set sysRoles) {
        this.sysRoles = sysRoles;
    }

    public Set getSysRoleResources() {
        return this.sysRoleResources;
    }
    
    public void setSysRoleResources(Set sysRoleResources) {
        this.sysRoleResources = sysRoleResources;
    }
   








}