package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * QmProcManage entity. @author MyEclipse Persistence Tools
 */

public class QmProcManage implements java.io.Serializable {
	private String id;	//主键
	private String procSection;//工段
	private String procType;//机型(适用机型)
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date createDatetime;//新增时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyDatetime;//修改时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date reviewDatetime;//批准时间
	private String procStop;//是否停用
	private String procContent;//指导书内容
	private String procStatus;//审核状态
	private MdMat mdMat;	//品名(牌号)
	private SysUser sysUserByReviewUserid;//批准人ID
	private SysUser sysUserByUploadUserid;//上传人ID
	private SysUser sysUserByAddUserid;	//新增人ID
	private SysUser sysUserByModifyUserid;//修改人ID
	private SysUser sysUserBySendUserid;//发送人
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date sendDatetime;//发送时间
	private String procVersion;//版本号
	
	// Constructors

	/** default constructor */
	public QmProcManage() {
	}

	/** minimal constructor */
	public QmProcManage(String id) {
		this.id = id;
	}

	/** full constructor */
	public QmProcManage(String id, MdMat mdMat, SysUser sysUserByReviewUserid,
			SysUser sysUserByUploadUserid, SysUser sysUserByAddUserid,
			SysUser sysUserByModifyUserid, SysUser sysUserBySendUserid,
			String procSection, String procType, Date createDatetime,
			Date modifyDatetime, Date sendDatetime, String procStop,
			Date reviewDatetime, String procContent, String procStatus,
			String procVersion) {
		this.id = id;
		this.mdMat = mdMat;
		this.sysUserByReviewUserid = sysUserByReviewUserid;
		this.sysUserByUploadUserid = sysUserByUploadUserid;
		this.sysUserByAddUserid = sysUserByAddUserid;
		this.sysUserByModifyUserid = sysUserByModifyUserid;
		this.sysUserBySendUserid = sysUserBySendUserid;
		this.procSection = procSection;
		this.procType = procType;
		this.createDatetime = createDatetime;
		this.modifyDatetime = modifyDatetime;
		this.sendDatetime = sendDatetime;
		this.procStop = procStop;
		this.reviewDatetime = reviewDatetime;
		this.procContent = procContent;
		this.procStatus = procStatus;
		this.procVersion = procVersion;
	}

	// Property accessors


	public MdMat getMdMat() {
		return this.mdMat;
	}

	public SysUser getSysUserBySendUserid() {
		return sysUserBySendUserid;
	}

	public void setSysUserBySendUserid(SysUser sysUserBySendUserid) {
		this.sysUserBySendUserid = sysUserBySendUserid;
	}

	public Date getSendDatetime() {
		return sendDatetime;
	}

	public void setSendDatetime(Date sendDatetime) {
		this.sendDatetime = sendDatetime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcVersion() {
		return procVersion;
	}

	public void setProcVersion(String procVersion) {
		this.procVersion = procVersion;
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

	public SysUser getSysUserByModifyUserid() {
		return this.sysUserByModifyUserid;
	}

	public void setSysUserByModifyUserid(SysUser sysUserByModifyUserid) {
		this.sysUserByModifyUserid = sysUserByModifyUserid;
	}

	public String getProcSection() {
		return this.procSection;
	}

	public void setProcSection(String procSection) {
		this.procSection = procSection;
	}

	public String getProcType() {
		return this.procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}

	public String getProcStop() {
		return this.procStop;
	}

	public void setProcStop(String procStop) {
		this.procStop = procStop;
	}

	public String getProcContent() {
		return this.procContent;
	}

	public void setProcContent(String procContent) {
		this.procContent = procContent;
	}

	public String getProcStatus() {
		return this.procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
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
	

}