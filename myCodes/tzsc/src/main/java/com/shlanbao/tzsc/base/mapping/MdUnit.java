package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * MdUnit entity. @author MyEclipse Persistence Tools
 */

public class MdUnit  implements java.io.Serializable {


    // Fields    

     private String id;
     private MdUnit mdUnit;
     private String code;
     private String name;
     private String type;
     private Long enable;
     private String del;
     private Set mdUnits = new HashSet(0);
     private Set schStatInputs = new HashSet(0);
     private Set schWorkorders = new HashSet(0);
     private Set mdMats = new HashSet(0);
     private Set schWorkorderBoms = new HashSet(0);
     private Set schStatOutputs = new HashSet(0);

     private String mesCode;
     
    // Constructors

    public String getMesCode() {
		return mesCode;
	}


	public void setMesCode(String mesCode) {
		this.mesCode = mesCode;
	}


	/** default constructor */
    public MdUnit() {
    }
    
    
    public MdUnit(String id) {
		super();
		this.id = id;
	}


	/** full constructor */
    public MdUnit(MdUnit mdUnit, String code, String name, String type, Long enable, String del, Set mdUnits, Set schStatInputs, Set schWorkorders, Set mdMats, Set schWorkorderBoms, Set schStatOutputs) {
        this.mdUnit = mdUnit;
        this.code = code;
        this.name = name;
        this.type = type;
        this.enable = enable;
        this.del = del;
        this.mdUnits = mdUnits;
        this.schStatInputs = schStatInputs;
        this.schWorkorders = schWorkorders;
        this.mdMats = mdMats;
        this.schWorkorderBoms = schWorkorderBoms;
        this.schStatOutputs = schStatOutputs;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public MdUnit getMdUnit() {
        return this.mdUnit;
    }
    
    public void setMdUnit(MdUnit mdUnit) {
        this.mdUnit = mdUnit;
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

    public Set getMdUnits() {
        return this.mdUnits;
    }
    
    public void setMdUnits(Set mdUnits) {
        this.mdUnits = mdUnits;
    }

    public Set getSchStatInputs() {
        return this.schStatInputs;
    }
    
    public void setSchStatInputs(Set schStatInputs) {
        this.schStatInputs = schStatInputs;
    }

    public Set getSchWorkorders() {
        return this.schWorkorders;
    }
    
    public void setSchWorkorders(Set schWorkorders) {
        this.schWorkorders = schWorkorders;
    }

    public Set getMdMats() {
        return this.mdMats;
    }
    
    public void setMdMats(Set mdMats) {
        this.mdMats = mdMats;
    }

    public Set getSchWorkorderBoms() {
        return this.schWorkorderBoms;
    }
    
    public void setSchWorkorderBoms(Set schWorkorderBoms) {
        this.schWorkorderBoms = schWorkorderBoms;
    }

    public Set getSchStatOutputs() {
        return this.schStatOutputs;
    }
    
    public void setSchStatOutputs(Set schStatOutputs) {
        this.schStatOutputs = schStatOutputs;
    }
   








}