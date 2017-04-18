package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * MdWorkshop entity. @author MyEclipse Persistence Tools
 */

public class MdWorkshop  implements java.io.Serializable {


    // Fields    

     private String id;
     private String code;
     private String name;
     private Long seq;
     private Long enable;
     private String del;
     private Set mdShifts = new HashSet(0);
     private Set schCalendars = new HashSet(0);


    // Constructors

    /** default constructor */
    public MdWorkshop() {
    }

    
    /** full constructor */
    public MdWorkshop(String code, String name, Long seq, Long enable, String del, Set mdShifts, Set schCalendars) {
        this.code = code;
        this.name = name;
        this.seq = seq;
        this.enable = enable;
        this.del = del;
        this.mdShifts = mdShifts;
        this.schCalendars = schCalendars;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
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

    public Long getEnable() {
        return this.enable;
    }
    
    public void setEnable(Long enable) {
        this.enable = enable;
    }

    public String getDel() {
        return this.del;
    }
    
    public void setDel(String del) {
        this.del = del;
    }

    public Set getMdShifts() {
        return this.mdShifts;
    }
    
    public void setMdShifts(Set mdShifts) {
        this.mdShifts = mdShifts;
    }

    public Set getSchCalendars() {
        return this.schCalendars;
    }
    
    public void setSchCalendars(Set schCalendars) {
        this.schCalendars = schCalendars;
    }
   








}