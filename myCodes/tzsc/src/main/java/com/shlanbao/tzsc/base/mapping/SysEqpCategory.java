package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;


/**
 * SysEqpCategory entity. @author MyEclipse Persistence Tools
 */

public class SysEqpCategory  implements java.io.Serializable {


    // Fields    

     private String id;
     private String code;
     private String name;
     private String des;
     private Long enable;
     private String del;
     //private DocFilemanage uploadBean;
     //private Set sysEqpTypes = new HashSet(0);


    // Constructors

    /** default constructor */
    public SysEqpCategory() {
    }

    public SysEqpCategory(String id){
    	this.id = id;
    }
    
    /** full constructor */
    public SysEqpCategory(String code, String name, String des, Long enable, String del, Set sysEqpTypes) {
        this.code = code;
        this.name = name;
        this.des = des;
        this.enable = enable;
        this.del = del;
        //this.sysEqpTypes = sysEqpTypes;
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

	/*public Set getSysEqpTypes() {
		return sysEqpTypes;
	}

	public void setSysEqpTypes(Set sysEqpTypes) {
		this.sysEqpTypes = sysEqpTypes;
	}*/

	/*public DocFilemanage getUploadBean() {
		return uploadBean;
	}

	public void setUploadBean(DocFilemanage uploadBean) {
		this.uploadBean = uploadBean;
	}*/
}