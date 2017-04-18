package com.shlanbao.tzsc.base.mapping;



/**
 * SchShiftExchgDet entity. @author MyEclipse Persistence Tools
 */

public class SchShiftExchgDet  implements java.io.Serializable {


    // Fields    

     private String id;
     private SchShiftExchg schShiftExchg;
     private MdMat mdMat;
     private Double qty;
     private MdUnit mdUnit;
     private Long del;


    // Constructors

    /** default constructor */
    public SchShiftExchgDet() {
    }

    
    /** full constructor */
    
   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public SchShiftExchgDet(SchShiftExchg schShiftExchg, MdMat mdMat,
			Double qty, MdUnit mdUnit, Long del) {
		super();
		this.schShiftExchg = schShiftExchg;
		this.mdMat = mdMat;
		this.qty = qty;
		this.mdUnit = mdUnit;
		this.del = del;
	}


	public MdMat getMdMat() {
		return mdMat;
	}


	public void setMdMat(MdMat mdMat) {
		this.mdMat = mdMat;
	}


	public MdUnit getMdUnit() {
		return mdUnit;
	}


	public void setMdUnit(MdUnit mdUnit) {
		this.mdUnit = mdUnit;
	}


	public void setId(String id) {
        this.id = id;
    }

    public SchShiftExchg getSchShiftExchg() {
        return this.schShiftExchg;
    }
    
    public void setSchShiftExchg(SchShiftExchg schShiftExchg) {
        this.schShiftExchg = schShiftExchg;
    }

    public Double getQty() {
        return this.qty;
    }
    
    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }
   








}