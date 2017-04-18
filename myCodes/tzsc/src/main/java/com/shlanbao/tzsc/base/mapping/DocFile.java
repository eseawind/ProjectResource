package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * DocFile entity. @author MyEclipse Persistence Tools
 */

public class DocFile  implements java.io.Serializable {


    // Fields    

     private String id;
     private SysUser sysUser;
     private DocFile docFile;
     private String type;
     private String filename;
     private String fileType;
     private Date createTime;
     private String url;
     private String attr;
     private Set docFiles = new HashSet(0);


    // Constructors

    /** default constructor */
    public DocFile() {
    }

	/** minimal constructor */
    public DocFile(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public DocFile(String id, SysUser sysUser, DocFile docFile, String type, String filename, String fileType, Date createTime, String url, String attr, Set docFiles) {
        this.id = id;
        this.sysUser = sysUser;
        this.docFile = docFile;
        this.type = type;
        this.filename = filename;
        this.fileType = fileType;
        this.createTime = createTime;
        this.url = url;
        this.attr = attr;
        this.docFiles = docFiles;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public SysUser getSysUser() {
        return this.sysUser;
    }
    
    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public DocFile getDocFile() {
        return this.docFile;
    }
    
    public void setDocFile(DocFile docFile) {
        this.docFile = docFile;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return this.filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return this.fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

   

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public String getAttr() {
        return this.attr;
    }
    
    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Set getDocFiles() {
        return this.docFiles;
    }
    
    public void setDocFiles(Set docFiles) {
        this.docFiles = docFiles;
    }
   








}