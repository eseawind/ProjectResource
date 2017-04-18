package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * QmProdManage entity. @author MyEclipse Persistence Tools
 */

public class QmProdManage implements java.io.Serializable {

	// Fields

	private String id;
	private MdMat mdMat;
	private SysUser sysUserByReviewUserid;
	private SysUser sysUserByUploadUserid;
	private SysUser sysUserByAddUserid;
	private SysUser sysUserBySendUserid;
	private SysUser sysUserByModifyUserid;
	private String prodNumber;
	private String prodName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date createDatetime;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyDatetime;
	private String prodStop;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date reviewDatetime;
	private String prodContent;
	private String prodStatus;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date sendDatetime;
	private String prodVersion;

	// Constructors

	/** default constructor */
	public QmProdManage() {
	}

	/** full constructor */
	public QmProdManage(MdMat mdMat, SysUser sysUserByReviewUserid,
			SysUser sysUserByUploadUserid, SysUser sysUserByAddUserid,
			SysUser sysUserBySendUserid, SysUser sysUserByModifyUserid,
			String prodNumber, String prodName, Date createDatetime,
			Date modifyDatetime, String prodStop,
			Date reviewDatetime, String prodContent, String prodStatus,
			Date sendDatetime, String prodVersion) {
		this.mdMat = mdMat;
		this.sysUserByReviewUserid = sysUserByReviewUserid;
		this.sysUserByUploadUserid = sysUserByUploadUserid;
		this.sysUserByAddUserid = sysUserByAddUserid;
		this.sysUserBySendUserid = sysUserBySendUserid;
		this.sysUserByModifyUserid = sysUserByModifyUserid;
		this.prodNumber = prodNumber;
		this.prodName = prodName;
		this.createDatetime = createDatetime;
		this.modifyDatetime = modifyDatetime;
		this.prodStop = prodStop;
		this.reviewDatetime = reviewDatetime;
		this.prodContent = prodContent;
		this.prodStatus = prodStatus;
		this.sendDatetime = sendDatetime;
		this.prodVersion = prodVersion;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdMat getMdMat() {
		return this.mdMat;
	}

	public void setMdMat(MdMat mdMat) {
		this.mdMat = mdMat;
	}

	public SysUser getSysUserByReviewUserid() {
		return this.sysUserByReviewUserid;
	}

	public void setSysUserByReviewUserid(SysUser sysUserByReviewUserid) {
		this.sysUserByReviewUserid = sysUserByReviewUserid;
	}

	public SysUser getSysUserByUploadUserid() {
		return this.sysUserByUploadUserid;
	}

	public void setSysUserByUploadUserid(SysUser sysUserByUploadUserid) {
		this.sysUserByUploadUserid = sysUserByUploadUserid;
	}

	public SysUser getSysUserByAddUserid() {
		return this.sysUserByAddUserid;
	}

	public void setSysUserByAddUserid(SysUser sysUserByAddUserid) {
		this.sysUserByAddUserid = sysUserByAddUserid;
	}

	public SysUser getSysUserBySendUserid() {
		return this.sysUserBySendUserid;
	}

	public void setSysUserBySendUserid(SysUser sysUserBySendUserid) {
		this.sysUserBySendUserid = sysUserBySendUserid;
	}

	public SysUser getSysUserByModifyUserid() {
		return this.sysUserByModifyUserid;
	}

	public void setSysUserByModifyUserid(SysUser sysUserByModifyUserid) {
		this.sysUserByModifyUserid = sysUserByModifyUserid;
	}


	public String getProdNumber() {
		return prodNumber;
	}

	public void setProdNumber(String prodNumber) {
		this.prodNumber = prodNumber;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdStop() {
		return this.prodStop;
	}

	public void setProdStop(String prodStop) {
		this.prodStop = prodStop;
	}

	public String getProdContent() {
		return this.prodContent;
	}

	public void setProdContent(String prodContent) {
		this.prodContent = prodContent;
	}

	public String getProdStatus() {
		return this.prodStatus;
	}

	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}


	public String getProdVersion() {
		return this.prodVersion;
	}

	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
	}

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Date getModifyDatetime() {
		return modifyDatetime;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public Date getReviewDatetime() {
		return reviewDatetime;
	}

	public void setReviewDatetime(Date reviewDatetime) {
		this.reviewDatetime = reviewDatetime;
	}

	public Date getSendDatetime() {
		return sendDatetime;
	}

	public void setSendDatetime(Date sendDatetime) {
		this.sendDatetime = sendDatetime;
	}


}