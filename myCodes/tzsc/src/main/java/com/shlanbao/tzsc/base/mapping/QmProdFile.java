package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * QmProdFile entity. @author MyEclipse Persistence Tools
 */

public class QmProdFile implements java.io.Serializable {

	// Fields

	private String id;
	private SysUser sysUserByModifyUserId;
	private QmProdManage qmProdManage;
	private SysUser sysUserByCreateUserId;
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

	// Constructors

	/** default constructor */
	public QmProdFile() {
	}

	/** full constructor */
	public QmProdFile(SysUser sysUserByModifyUserId, QmProdManage qmProdManage,
			SysUser sysUserByCreateUserId, String fileName, String fileType,
			String fileId, Date createTime, String isDeleted,
			Long securityLevel, Date modifyTime, String prodVersion) {
		this.sysUserByModifyUserId = sysUserByModifyUserId;
		this.qmProdManage = qmProdManage;
		this.sysUserByCreateUserId = sysUserByCreateUserId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileId = fileId;
		this.createTime = createTime;
		this.isDeleted = isDeleted;
		this.securityLevel = securityLevel;
		this.modifyTime = modifyTime;
		this.prodVersion = prodVersion;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysUser getSysUserByModifyUserId() {
		return this.sysUserByModifyUserId;
	}

	public void setSysUserByModifyUserId(SysUser sysUserByModifyUserId) {
		this.sysUserByModifyUserId = sysUserByModifyUserId;
	}

	public QmProdManage getQmProdManage() {
		return this.qmProdManage;
	}

	public void setQmProdManage(QmProdManage qmProdManage) {
		this.qmProdManage = qmProdManage;
	}

	public SysUser getSysUserByCreateUserId() {
		return this.sysUserByCreateUserId;
	}

	public void setSysUserByCreateUserId(SysUser sysUserByCreateUserId) {
		this.sysUserByCreateUserId = sysUserByCreateUserId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getProdVersion() {
		return this.prodVersion;
	}

	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
	}

}