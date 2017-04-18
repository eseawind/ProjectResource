package com.shlanbao.tzsc.base.mapping;



/**
 * SchConStd entity. @author MyEclipse Persistence Tools
 */

public class SchConStd  implements java.io.Serializable {


    // Fields    

     private String id;
     private MdMat mdMatByProd;
     private MdMat mdMatByMat;
     private Double val;
     private Double uval;
     private Double lval;
     private Double euval;
     private Double elval;
     private String des;
     private Long del;
     private String bomLotId;
	 private String oid;
	 private String materialClass;
     
    // Constructors

    public String getOid() {
		return oid;
	}


	public String getMaterialClass() {
		return materialClass;
	}


	public void setMaterialClass(String materialClass) {
		this.materialClass = materialClass;
	}


	public void setOid(String oid) {
		this.oid = oid;
	}


	public String getBomLotId() {
		return bomLotId;
	}


	public void setBomLotId(String bomLotId) {
		this.bomLotId = bomLotId;
	}

	/** default constructor */
    public SchConStd() {
    }

    
    /** full constructor */
    public SchConStd(MdMat mdMatByProd, MdMat mdMatByMat, Double val, Double uval, Double lval, Double euval, Double elval, Long del) {
        this.mdMatByProd = mdMatByProd;
        this.mdMatByMat = mdMatByMat;
        this.val = val;
        this.uval = uval;
        this.lval = lval;
        this.euval = euval;
        this.elval = elval;
        this.del = del;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public MdMat getMdMatByProd() {
        return this.mdMatByProd;
    }
    
    public void setMdMatByProd(MdMat mdMatByProd) {
        this.mdMatByProd = mdMatByProd;
    }

    public MdMat getMdMatByMat() {
        return this.mdMatByMat;
    }
    
    public void setMdMatByMat(MdMat mdMatByMat) {
        this.mdMatByMat = mdMatByMat;
    }

    public Double getVal() {
        return this.val;
    }
    
    public void setVal(Double val) {
        this.val = val;
    }

    public Double getUval() {
        return this.uval;
    }
    
    public void setUval(Double uval) {
        this.uval = uval;
    }

    public Double getLval() {
        return this.lval;
    }
    
    public void setLval(Double lval) {
        this.lval = lval;
    }

    public Double getEuval() {
        return this.euval;
    }
    
    public void setEuval(Double euval) {
        this.euval = euval;
    }

    public Double getElval() {
        return this.elval;
    }
    
    public void setElval(Double elval) {
        this.elval = elval;
    }

    public Long getDel() {
        return this.del;
    }
    
    public void setDel(Long del) {
        this.del = del;
    }


	public String getDes() {
		return des;
	}


	public void setDes(String des) {
		this.des = des;
	}
   








}