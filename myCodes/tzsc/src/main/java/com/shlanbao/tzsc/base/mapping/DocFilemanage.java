package com.shlanbao.tzsc.base.mapping;

import java.util.Date;


/**
 * DocFilemanage entity. @author MyEclipse Persistence Tools
 */

public class DocFilemanage  implements java.io.Serializable {


    // Fields    

     private String id;
     private String fileName;
     private String fileType;
     private String fileId;
     private Date createTime;
     private String createPersonId;
     private String createName;
     private String isDeleted;
     private Long securityLevel;
     private String attr1;
     private String attr2;
     private String attr3;
     private String attr4;

     private String tableZjId;
    // Constructors

    /** default constructor */
    public DocFilemanage() {
    }

    
    /** full constructor */
    public DocFilemanage(String fileName, String fileType, String fileId, Date createTime, String createPersonId, String createName, String isDeleted, Long securityLevel, String attr1, String attr2, String attr3, String attr4) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileId = fileId;
        this.createTime = createTime;
        this.createPersonId = createPersonId;
        this.createName = createName;
        this.isDeleted = isDeleted;
        this.securityLevel = securityLevel;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.attr4 = attr4;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return this.fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileId() {
        return this.fileId;
    }
    
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatePersonId() {
        return this.createPersonId;
    }
    
    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreateName() {
        return this.createName;
    }
    
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getIsDeleted() {
        return this.isDeleted;
    }
    
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getSecurityLevel() {
        return this.securityLevel;
    }
    
    public void setSecurityLevel(Long securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getAttr1() {
        return this.attr1;
    }
    
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return this.attr2;
    }
    
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return this.attr3;
    }
    
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return this.attr4;
    }
    
    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }


	public String getTableZjId() {
		return tableZjId;
	}


	public void setTableZjId(String tableZjId) {
		this.tableZjId = tableZjId;
	}
   

}