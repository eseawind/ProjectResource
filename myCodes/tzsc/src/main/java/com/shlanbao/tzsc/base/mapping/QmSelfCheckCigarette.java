package com.shlanbao.tzsc.base.mapping;

import java.util.Date;


/**
 * QmSelfCheckCigarette entity. @author MyEclipse Persistence Tools
 */

public class QmSelfCheckCigarette  implements java.io.Serializable {


    // Fields    

     private String id;
     private SysUser sysUser;
     private SchWorkorder schWorkorder;
     private String checkFlag;
     private Date time;
     private String zl;
     private String lq;
     private String bk;
     private String jm;
     private String wzhb;
     private String bmzw;
     private String lz;
     private String jzz;
     private String qb;
     private String qk;
     private String gy;
     private String dt;
     private String cd;
     private Long del;


    // Constructors

    /** default constructor */
    public QmSelfCheckCigarette() {
    }

    
    /** full constructor */
    public QmSelfCheckCigarette(SysUser sysUser, SchWorkorder schWorkorder, String checkFlag, Date time, String zl, String lq, String bk, String jm, String wzhb, String bmzw, String lz, String jzz, String qb, String qk, String gy, String dt, String cd, Long del) {
        this.sysUser = sysUser;
        this.schWorkorder = schWorkorder;
        this.checkFlag = checkFlag;
        this.time = time;
        this.zl = zl;
        this.lq = lq;
        this.bk = bk;
        this.jm = jm;
        this.wzhb = wzhb;
        this.bmzw = bmzw;
        this.lz = lz;
        this.jzz = jzz;
        this.qb = qb;
        this.qk = qk;
        this.gy = gy;
        this.dt = dt;
        this.cd = cd;
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

    public String getZl() {
        return this.zl;
    }
    
    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getLq() {
        return this.lq;
    }
    
    public void setLq(String lq) {
        this.lq = lq;
    }

    public String getBk() {
        return this.bk;
    }
    
    public void setBk(String bk) {
        this.bk = bk;
    }

    public String getJm() {
        return this.jm;
    }
    
    public void setJm(String jm) {
        this.jm = jm;
    }

    public String getWzhb() {
        return this.wzhb;
    }
    
    public void setWzhb(String wzhb) {
        this.wzhb = wzhb;
    }

    public String getBmzw() {
        return this.bmzw;
    }
    
    public void setBmzw(String bmzw) {
        this.bmzw = bmzw;
    }

    public String getLz() {
        return this.lz;
    }
    
    public void setLz(String lz) {
        this.lz = lz;
    }

    public String getJzz() {
        return this.jzz;
    }
    
    public void setJzz(String jzz) {
        this.jzz = jzz;
    }

    public String getQb() {
        return this.qb;
    }
    
    public void setQb(String qb) {
        this.qb = qb;
    }

    public String getQk() {
        return this.qk;
    }
    
    public void setQk(String qk) {
        this.qk = qk;
    }

    public String getGy() {
        return this.gy;
    }
    
    public void setGy(String gy) {
        this.gy = gy;
    }

    public String getDt() {
        return this.dt;
    }
    
    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getCd() {
        return this.cd;
    }
    
    public void setCd(String cd) {
        this.cd = cd;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }
   








}