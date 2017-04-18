package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * QmSelfCheckStrip entity. @author MyEclipse Persistence Tools
 */

public class QmSelfCheckStrip  implements java.io.Serializable {


    // Fields    

     private String id;
     private SysUser sysUser;
     private SchWorkorder schWorkorder;
     private String checkFlag;
     @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
     private Date time;
     private String cb;
     private String sb;
     private String ps;
     private String zt;
     private String wz;
     private String dz;
     private String tmz;
     private String lx;
     private Long del;


    // Constructors

    /** default constructor */
    public QmSelfCheckStrip() {
    }

    
    /** full constructor */
    public QmSelfCheckStrip(SysUser sysUser, SchWorkorder schWorkorder, String checkFlag, Date time, String cb, String sb, String ps, String zt, String wz, String dz, String tmz, String lx, Long del) {
        this.sysUser = sysUser;
        this.schWorkorder = schWorkorder;
        this.checkFlag = checkFlag;
        this.time = time;
        this.cb = cb;
        this.sb = sb;
        this.ps = ps;
        this.zt = zt;
        this.wz = wz;
        this.dz = dz;
        this.tmz = tmz;
        this.lx = lx;
        this.del = del;
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

    public String getCheckFlag() {
        return this.checkFlag;
    }
    
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }

    public String getCb() {
        return this.cb;
    }
    
    public void setCb(String cb) {
        this.cb = cb;
    }

    public String getSb() {
        return this.sb;
    }
    
    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getPs() {
        return this.ps;
    }
    
    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getZt() {
        return this.zt;
    }
    
    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getWz() {
        return this.wz;
    }
    
    public void setWz(String wz) {
        this.wz = wz;
    }

    public String getDz() {
        return this.dz;
    }
    
    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getTmz() {
        return this.tmz;
    }
    
    public void setTmz(String tmz) {
        this.tmz = tmz;
    }

    public String getLx() {
        return this.lx;
    }
    
    public void setLx(String lx) {
        this.lx = lx;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }
   








}