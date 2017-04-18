package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * QmProcFile entity. @author MyEclipse Persistence Tools
 */

public class QmProcFile implements java.io.Serializable {
	private String id;
	private QmProcManage qmProcManage;
	private SysUser sysUserByModifyUserId;
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
	private String procVersion;//版本号
	// Constructors

	/** default constructor */
	public QmProcFile() {
	}

	/** minimal constructor */
	public QmProcFile(String id) {
		this.id = id;
	}

	/** full constructor */
	public QmProcFile(String id, QmProcManage qmProcManage,
			SysUser sysUserByModifyUserId, SysUser sysUserByCreateUserId,
			String fileName, String fileType, String fileId, Date createTime,
			String isDeleted, Long securityLevel, Date modifyTime,String procVersion) {
		this.id = id;
		this.qmProcManage = qmProcManage;
		this.sysUserByModifyUserId = sysUserByModifyUserId;
		this.sysUserByCreateUserId = sysUserByCreateUserId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileId = fileId;
		this.createTime = createTime;
		this.isDeleted = isDeleted;
		this.securityLevel = securityLevel;
		this.modifyTime = modifyTime;
		this.procVersion = procVersion;
	}

	// Property accessors

	public String getId() {
		return this.id;
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

	public void setId(String id) {
		this.id = id;
	}

	public SysUser getSysUserByModifyUserId() {
		return this.sysUserByModifyUserId;
	}

	public void setSysUserByModifyUserId(SysUser sysUserByModifyUserId) {
		this.sysUserByModifyUserId = sysUserByModifyUserId;
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


	public QmProcManage getQmProcManage() {
		return qmProcManage;
	}

	public void setQmProcManage(QmProcManage qmProcManage) {
		this.qmProcManage = qmProcManage;
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

	public String getProcVersion() {
		return procVersion;
	}

	public void setProcVersion(String procVersion) {
		this.procVersion = procVersion;
	}

}