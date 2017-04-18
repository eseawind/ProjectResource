package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * MdMatType entity. @author MyEclipse Persistence Tools
 */

public class MdMatType  implements java.io.Serializable {


    // Fields    

     private String id;
     private MdMatGrp mdMatGrp;
     private String code;
     private String name;
     private String des;
     private Long enable;
     private String del;
     private Set mdMats = new HashSet(0);
     private String mesCode;
     

    // Constructors

    public String getMesCode() {
		return mesCode;
	}


	public void setMesCode(String mesCode) {
		this.mesCode = mesCode;
	}


	/** default constructor */
    public MdMatType() {
    	
    }
    
    
    public MdMatType(String id) {
		super();
		this.id = id;
	}


	/** full constructor */
    public MdMatType(MdMatGrp mdMatGrp, String code, String name, Long enable, String del, Set mdMats) {
        this.mdMatGrp = mdMatGrp;
        this.code = code;
        this.name = name;
        this.enable = enable;
        this.del = del;
        this.mdMats = mdMats;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public MdMatGrp getMdMatGrp() {
        return this.mdMatGrp;
    }
    
    public void setMdMatGrp(MdMatGrp mdMatGrp) {
        this.mdMatGrp = mdMatGrp;
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

    public Set getMdMats() {
        return this.mdMats;
    }
    
    public void setMdMats(Set mdMats) {
        this.mdMats = mdMats;
    }


	public String getDes() {
		return des;
	}


	public void setDes(String des) {
		this.des = des;
	}

}