package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * SysOrganization entity. @author MyEclipse Persistence Tools
 */

public class SysOrganization  implements java.io.Serializable {


    // Fields    

     private String id;
     private SysOrganization sysOrganization;
     private String name;
     private Long seq;
     private String remark;
     private String iconCls;
     private Long enable;
     private Long del;
     private Set sysOrganizationResources = new HashSet(0);
     private Set sysOrganizations = new HashSet(0);
     private Set sysUserOrganizations = new HashSet(0);


    // Constructors

    /** default constructor */
    public SysOrganization() {
    	
    }

    
    
    public SysOrganization(String id) {
		super();
		this.id = id;
	}



	/** full constructor */
    public SysOrganization(SysOrganization sysOrganization, String name, Long seq, String remark, String iconCls, Long enable, Long del, Set sysOrganizationResources, Set sysOrganizations, Set sysUserOrganizations) {
        this.sysOrganization = sysOrganization;
        this.name = name;
        this.seq = seq;
        this.remark = remark;
        this.iconCls = iconCls;
        this.enable = enable;
        this.del = del;
        this.sysOrganizationResources = sysOrganizationResources;
        this.sysOrganizations = sysOrganizations;
        this.sysUserOrganizations = sysUserOrganizations;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public SysOrganization getSysOrganization() {
        return this.sysOrganization;
    }
    
    public void setSysOrganization(SysOrganization sysOrganization) {
        this.sysOrganization = sysOrganization;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Long getSeq() {
        return this.seq;
    }
    
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIconCls() {
        return this.iconCls;
    }
    
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
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

    public Set getSysOrganizationResources() {
        return this.sysOrganizationResources;
    }
    
    public void setSysOrganizationResources(Set sysOrganizationResources) {
        this.sysOrganizationResources = sysOrganizationResources;
    }

    public Set getSysOrganizations() {
        return this.sysOrganizations;
    }
    
    public void setSysOrganizations(Set sysOrganizations) {
        this.sysOrganizations = sysOrganizations;
    }

    public Set getSysUserOrganizations() {
        return this.sysUserOrganizations;
    }
    
    public void setSysUserOrganizations(Set sysUserOrganizations) {
        this.sysUserOrganizations = sysUserOrganizations;
    }
   








}