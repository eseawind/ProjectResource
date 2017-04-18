package com.shlanbao.tzsc.pms.qm.prod.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * QmProcFile entity. 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class QmProdFileBean implements java.io.Serializable {
	private String id;
	private String modUserName;
	private String addUserName;
	private String fileName;
	private String fileType;
	private String fileId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createTime;
	private String prodManageId;
	private String isDeleted;
	private Long securityLevel;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String modifyTime;
	private String prodVersion;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModUserName() {
		return modUserName;
	}
	public void setModUserName(String modUserName) {
		this.modUserName = modUserName;
	}
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getProdManageId() {
		return prodManageId;
	}
	public void setProdManageId(String prodManageId) {
		this.prodManageId = prodManageId;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Long getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(Long securityLevel) {
		this.securityLevel = securityLevel;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getProdVersion() {
		return prodVersion;
	}
	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
	}
	

}