package com.shlanbao.tzsc.base.mapping;



/**
 * SysOrganizationResource entity. @author MyEclipse Persistence Tools
 */

public class SysOrganizationResource  implements java.io.Serializable {


    // Fields    

     private String id;
     private SysResource sysResource;
     private SysOrganization sysOrganization;


    // Constructors

    /** default constructor */
    public SysOrganizationResource() {
    }

    
    /** full constructor */
    public SysOrganizationResource(SysResource sysResource, SysOrganization sysOrganization) {
        this.sysResource = sysResource;
        this.sysOrganization = sysOrganization;
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

    public SysOrganization getSysOrganization() {
        return this.sysOrganization;
    }
    
    public void setSysOrganization(SysOrganization sysOrganization) {
        this.sysOrganization = sysOrganization;
    }
   








}