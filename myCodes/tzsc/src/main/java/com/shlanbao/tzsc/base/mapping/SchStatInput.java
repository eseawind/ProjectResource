package com.shlanbao.tzsc.base.mapping;



/**
 * SchStatInput entity. @author MyEclipse Persistence Tools
 */

public class SchStatInput  implements java.io.Serializable {


    // Fields    

     private String id;
     private MdUnit mdUnit;
     private SchStatOutput schStatOutput;
     private MdMat mdMat;
     private Double orignalData;
     private Double qty;


    // Constructors

    /** default constructor */
    public SchStatInput() {
    }

    
    /** full constructor */
    public SchStatInput(MdUnit mdUnit, SchStatOutput schStatOutput, MdMat mdMat, Double orignalData, Double qty) {
        this.mdUnit = mdUnit;
        this.schStatOutput = schStatOutput;
        this.mdMat = mdMat;
        this.orignalData = orignalData;
        this.qty = qty;
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

    public SchStatOutput getSchStatOutput() {
        return this.schStatOutput;
    }
    
    public void setSchStatOutput(SchStatOutput schStatOutput) {
        this.schStatOutput = schStatOutput;
    }

    public MdMat getMdMat() {
        return this.mdMat;
    }
    
    public void setMdMat(MdMat mdMat) {
        this.mdMat = mdMat;
    }

    public Double getOrignalData() {
        return this.orignalData;
    }
    
    public void setOrignalData(Double orignalData) {
        this.orignalData = orignalData;
    }

    public Double getQty() {
        return this.qty;
    }
    
    public void setQty(Double qty) {
        this.qty = qty;
    }
   








}