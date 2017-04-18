package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * SysResource entity. @author MyEclipse Persistence Tools
 */

public class SysResource  implements java.io.Serializable {


    // Fields    

     private String id;
     private SysResource sysResource;
     private Long seq;
     private String text;
     private String iconCls;
     private String url;
     private Long typ;
     private Long enable;
     private Long del;
     private Long securityLevel;
     private String remark;
     private Set sysFavorites = new HashSet(0);
     private Set sysRoleResources = new HashSet(0);
     private Set sysOrganizationResources = new HashSet(0);
     private Set sysResources = new HashSet(0);


    // Constructors

    /** default constructor */
    public SysResource() {
    }

    
    public SysResource(String id) {
		super();
		this.id = id;
	}


	/** full constructor */
    public SysResource(SysResource sysResource, Long seq, String text, String iconCls, String url, Long typ, Long enable, Long del, Long securityLevel, String remark, Set sysFavorites, Set sysRoleResources, Set sysOrganizationResources, Set sysResources) {
        this.sysResource = sysResource;
        this.seq = seq;
        this.text = text;
        this.iconCls = iconCls;
        this.url = url;
        this.typ = typ;
        this.enable = enable;
        this.del = del;
        this.securityLevel = securityLevel;
        this.remark = remark;
        this.sysFavorites = sysFavorites;
        this.sysRoleResources = sysRoleResources;
        this.sysOrganizationResources = sysOrganizationResources;
        this.sysResources = sysResources;
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

    public Long getSeq() {
        return this.seq;
    }
    
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getText() {
        return this.text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return this.iconCls;
    }
    
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTyp() {
        return this.typ;
    }
    
    public void setTyp(Long typ) {
        this.typ = typ;
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

    public Long getSecurityLevel() {
        return this.securityLevel;
    }
    
    public void setSecurityLevel(Long securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set getSysFavorites() {
        return this.sysFavorites;
    }
    
    public void setSysFavorites(Set sysFavorites) {
        this.sysFavorites = sysFavorites;
    }

    public Set getSysRoleResources() {
        return this.sysRoleResources;
    }
    
    public void setSysRoleResources(Set sysRoleResources) {
        this.sysRoleResources = sysRoleResources;
    }

    public Set getSysOrganizationResources() {
        return this.sysOrganizationResources;
    }
    
    public void setSysOrganizationResources(Set sysOrganizationResources) {
        this.sysOrganizationResources = sysOrganizationResources;
    }

    public Set getSysResources() {
        return this.sysResources;
    }
    
    public void setSysResources(Set sysResources) {
        this.sysResources = sysResources;
    }
   








}