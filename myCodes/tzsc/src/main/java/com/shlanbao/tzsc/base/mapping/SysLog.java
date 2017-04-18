package com.shlanbao.tzsc.base.mapping;

import java.util.Date;


/**
 * SysLog entity. @author MyEclipse Persistence Tools
 */

public class SysLog  implements java.io.Serializable {


    // Fields    

     private String id;
     private String name;
     private String ip;
     private String sys;
     private String optname;
     private String success;
     private String params;
     private Date date;
     private Long del;


    // Constructors

    /** default constructor */
    public SysLog() {
    }

    
    /** full constructor */
    public SysLog(String name, String ip, String sys, String optname, String success, String params, Date date, Long del) {
        this.name = name;
        this.ip = ip;
        this.sys = sys;
        this.optname = optname;
        this.success = success;
        this.params = params;
        this.date = date;
        this.del = del;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSys() {
        return this.sys;
    }
    
    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getOptname() {
        return this.optname;
    }
    
    public void setOptname(String optname) {
        this.optname = optname;
    }

    public String getSuccess() {
        return this.success;
    }
    
    public void setSuccess(String success) {
        this.success = success;
    }

    public String getParams() {
        return this.params;
    }
    
    public void setParams(String params) {
        this.params = params;
    }

    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }
   








}