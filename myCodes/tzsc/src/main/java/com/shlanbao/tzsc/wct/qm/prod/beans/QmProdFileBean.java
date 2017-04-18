package com.shlanbao.tzsc.wct.qm.prod.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * QmProcFile entity. 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class QmProdFileBean implements java.io.Serializable {
	
	private String id;
	private String fileName;
	private String fileType;
	private String fileId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private String isDeleted;
	private Long securityLevel;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String prodVersion;
	private String addUserName;
	private String prodManageId;
	private String prodManageName;
	private String mdMatName;
	private String prodNumber;
	private String prodStatus;
	private String uploadUrl;
	
	public String getProdStatus() {
		return prodStatus;
	}
	public String getMdMatName() {
		return mdMatName;
	}
	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}
	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getProdVersion() {
		return prodVersion;
	}
	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
	}
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	public String getProdManageId() {
		return prodManageId;
	}
	public void setProdManageId(String prodManageId) {
		this.prodManageId = prodManageId;
	}
	public String getProdManageName() {
		return prodManageName;
	}
	public void setProdManageName(String prodManageName) {
		this.prodManageName = prodManageName;
	}
	public String getProdNumber() {
		return prodNumber;
	}
	public void setProdNumber(String prodNumber) {
		this.prodNumber = prodNumber;
	}
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	
	
	

}