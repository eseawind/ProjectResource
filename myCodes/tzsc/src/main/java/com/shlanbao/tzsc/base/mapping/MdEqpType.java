package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * MdEqpType entity. @author MyEclipse Persistence Tools
 */

public class MdEqpType  implements java.io.Serializable {


    // Fields    

     private String id;
     private MdEqpCategory mdEqpCategory;
     private String code;
     private String name;
     private String des;
     private Long enable;
     private String del;
     private Set mdEquipments = new HashSet(0);


    // Constructors

    /** default constructor */
    public MdEqpType() {
    }
    
    public MdEqpType(String id){
    	super();
    	this.id = id;
    }
    
    /** full constructor */
    public MdEqpType(MdEqpCategory mdEqpCategory, String code, String name, String des, Long enable, String del, Set mdEquipments) {
        this.mdEqpCategory = mdEqpCategory;
        this.code = code;
        this.name = name;
        this.des = des;
        this.enable = enable;
        this.del = del;
        this.mdEquipments = mdEquipments;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public MdEqpCategory getMdEqpCategory() {
        return this.mdEqpCategory;
    }
    
    public void setMdEqpCategory(MdEqpCategory mdEqpCategory) {
        this.mdEqpCategory = mdEqpCategory;
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

    public Set getMdEquipments() {
        return this.mdEquipments;
    }
    
    public void setMdEquipments(Set mdEquipments) {
        this.mdEquipments = mdEquipments;
    }
   








}