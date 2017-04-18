package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * MdEqpCategory entity. @author MyEclipse Persistence Tools
 */

public class MdEqpCategory  implements java.io.Serializable {


    // Fields    

     private String id;
     private String code;
     private String name;
     private String des;
     private Long enable;
     private String del;
     private Set mdEqpTypes = new HashSet(0);


    // Constructors

    /** default constructor */
    public MdEqpCategory() {
    }

    public MdEqpCategory(String id){
    	this.id = id;
    }
    
    /** full constructor */
    public MdEqpCategory(String code, String name, String des, Long enable, String del, Set mdEqpTypes) {
        this.code = code;
        this.name = name;
        this.des = des;
        this.enable = enable;
        this.del = del;
        this.mdEqpTypes = mdEqpTypes;
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

    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
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

    public Set getMdEqpTypes() {
        return this.mdEqpTypes;
    }
    
    public void setMdEqpTypes(Set mdEqpTypes) {
        this.mdEqpTypes = mdEqpTypes;
    }
}