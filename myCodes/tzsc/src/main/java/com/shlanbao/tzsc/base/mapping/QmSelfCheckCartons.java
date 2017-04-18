package com.shlanbao.tzsc.base.mapping;

import java.util.Date;


/**
 * QmSelfCheckCartons entity. @author MyEclipse Persistence Tools
 */

public class QmSelfCheckCartons  implements java.io.Serializable {


    // Fields    

     private String checkFlag;
     private SysUser sysUser;
     private SchWorkorder schWorkorder;
     private String id;
     private Date time;
     private String ct;
     private String st;
     private String ps;
     private String ft;
     private String zt;
     private Long del;


    // Constructors

    /** default constructor */
    public QmSelfCheckCartons() {
    }

	/** minimal constructor */
    public QmSelfCheckCartons(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public QmSelfCheckCartons(SysUser sysUser, SchWorkorder schWorkorder, String id, Date time, String ct, String st, String ps, String ft, String zt, Long del) {
        this.sysUser = sysUser;
        this.schWorkorder = schWorkorder;
        this.id = id;
        this.time = time;
        this.ct = ct;
        this.st = st;
        this.ps = ps;
        this.ft = ft;
        this.zt = zt;
        this.del = del;
    }

   
    // Property accessors

    public String getCheckFlag() {
        return this.checkFlag;
    }
    
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
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

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }

    public String getCt() {
        return this.ct;
    }
    
    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getSt() {
        return this.st;
    }
    
    public void setSt(String st) {
        this.st = st;
    }

    public String getPs() {
        return this.ps;
    }
    
    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getFt() {
        return this.ft;
    }
    
    public void setFt(String ft) {
        this.ft = ft;
    }

    public String getZt() {
        return this.zt;
    }
    
    public void setZt(String zt) {
        this.zt = zt;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }
   








}