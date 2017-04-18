package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * QmOutward entity. @author MyEclipse Persistence Tools
 */

public class QmOutward  implements java.io.Serializable {


    // Fields    

     private String id;
     private SysUser sysUser;
     private SchWorkorder schWorkorder;
     private Date time;
     private String batchNo;
     private Set qmOutwardDets = new HashSet(0);


    // Constructors

    /** default constructor */
    public QmOutward() {
    }

    
    /** full constructor */
    public QmOutward(SysUser sysUser, SchWorkorder schWorkorder, Date time, String batchNo, Set qmOutwardDets) {
        this.sysUser = sysUser;
        this.schWorkorder = schWorkorder;
        this.time = time;
        this.batchNo = batchNo;
        this.qmOutwardDets = qmOutwardDets;
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

    public SchWorkorder getSchWorkorder() {
        return this.schWorkorder;
    }
    
    public void setSchWorkorder(SchWorkorder schWorkorder) {
        this.schWorkorder = schWorkorder;
    }

    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }

    public String getBatchNo() {
        return this.batchNo;
    }
    
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Set getQmOutwardDets() {
        return this.qmOutwardDets;
    }
    
    public void setQmOutwardDets(Set qmOutwardDets) {
        this.qmOutwardDets = qmOutwardDets;
    }
   








}