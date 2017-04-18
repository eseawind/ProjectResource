package com.lanbao.dws.model.combobox;

import javax.persistence.Entity;

/**
 * MdMatGrp entity. @author MyEclipse Persistence Tools
 */

@Entity(name="MD_MAT_GRP")
public class MdMatGrp  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
     private String code;
     private String name;
     private String des;
     private Long enable;
     private String del;


    // Constructors

    /** default constructor */
    public MdMatGrp() {
    }

    
    public MdMatGrp(String id) {
		super();
		this.id = id;
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
}