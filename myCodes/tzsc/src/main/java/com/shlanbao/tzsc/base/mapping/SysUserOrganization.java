package com.shlanbao.tzsc.base.mapping;



/**
 * SysUserOrganization entity. @author MyEclipse Persistence Tools
 */

public class SysUserOrganization  implements java.io.Serializable {


    // Fields    

     private String id;
     private SysUser sysUser;
     private SysOrganization sysOrganization;


    // Constructors

    /** default constructor */
    public SysUserOrganization() {
    }

    
    /** full constructor */
    public SysUserOrganization(SysUser sysUser, SysOrganization sysOrganization) {
        this.sysUser = sysUser;
        this.sysOrganization = sysOrganization;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public SysUser getSysUser() {
        return this.sysUser;
    }
    
    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public SysOrganization getSysOrganization() {
        return this.sysOrganization;
    }
    
    public void setSysOrganization(SysOrganization sysOrganization) {
        this.sysOrganization = sysOrganization;
    }
   








}