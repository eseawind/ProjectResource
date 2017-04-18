package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * QmOutwardDefectItem entity. @author MyEclipse Persistence Tools
 */

public class QmOutwardDefectItem  implements java.io.Serializable {


    // Fields    

     private String id;
     private String code;
     private String name;
     private String type;
     private String pos;
     private String des;
     private String lvl;
     private Long del;
     private Set qmOutwardDets = new HashSet(0);


    // Constructors

    /** default constructor */
    public QmOutwardDefectItem() {
    }

    
    /** full constructor */
    public QmOutwardDefectItem(String code, String name, String type, String pos, String des, String lvl, Long del, Set qmOutwardDets) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.pos = pos;
        this.des = des;
        this.lvl = lvl;
        this.del = del;
        this.qmOutwardDets = qmOutwardDets;
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

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getPos() {
        return this.pos;
    }
    
    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }

    public String getLvl() {
        return this.lvl;
    }
    
    public void setLvl(String lvl) {
        this.lvl = lvl;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }

    public Set getQmOutwardDets() {
        return this.qmOutwardDets;
    }
    
    public void setQmOutwardDets(Set qmOutwardDets) {
        this.qmOutwardDets = qmOutwardDets;
    }
   








}