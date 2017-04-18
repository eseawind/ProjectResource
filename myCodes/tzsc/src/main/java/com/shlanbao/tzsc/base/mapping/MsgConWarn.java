package com.shlanbao.tzsc.base.mapping;

import java.util.Date;


/**
 * MsgConWarn entity. @author MyEclipse Persistence Tools
 */

public class MsgConWarn  implements java.io.Serializable {


    // Fields    

     private String id;
     private SchWorkorder schWorkorder;
     private Double std;
     private Double val;
     private String item;
     private Date time;
     private Long sts;
     private String content;
     private Long del;


    // Constructors

    /** default constructor */
    public MsgConWarn() {
    }

    
    /** full constructor */
    public MsgConWarn(SchWorkorder schWorkorder, Double std, Double val, String item, Date time, Long sts, String content, Long del) {
        this.schWorkorder = schWorkorder;
        this.std = std;
        this.val = val;
        this.item = item;
        this.time = time;
        this.sts = sts;
        this.content = content;
        this.del = del;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public SchWorkorder getSchWorkorder() {
        return this.schWorkorder;
    }
    
    public void setSchWorkorder(SchWorkorder schWorkorder) {
        this.schWorkorder = schWorkorder;
    }

    public Double getStd() {
        return this.std;
    }
    
    public void setStd(Double std) {
        this.std = std;
    }

    public Double getVal() {
        return this.val;
    }
    
    public void setVal(Double val) {
        this.val = val;
    }

    public String getItem() {
        return this.item;
    }
    
    public void setItem(String item) {
        this.item = item;
    }

    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }

    public Long getSts() {
        return this.sts;
    }
    
    public void setSts(Long sts) {
        this.sts = sts;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }
   








}