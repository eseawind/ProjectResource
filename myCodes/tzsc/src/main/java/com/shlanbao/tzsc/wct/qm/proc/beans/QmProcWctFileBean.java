package com.shlanbao.tzsc.wct.qm.proc.beans;


import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 系统用户实体类
 * <li>@author luther.zhang
 * <li>@create 2015-02-14
 */
public class QmProcWctFileBean{
	// Fields
	private String id;
	private String modUserName;
	private String addUserName;
	private String fileName;
	private String fileType;
	private String fileId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createTime;
	private String procManageId;
	private String isDeleted;
	private Long securityLevel;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String modifyTime;
	private String procVersion;//版本编号
	
	private String procSection;//工段
	private String procType;//机型(适用机型)
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String reviewDatetime;//批准时间
	private String procStop;//是否停用
	private String procContent;//指导书内容
	private String procStatus;//审核状态
	
	private String procId;//品名(牌号) 
	private String mdMatName;//品名(牌号) 
	
	private String uploadId;//上传ID
	private String addUserId;//新增人ID
	private String modifyUserId;//修改人ID
	
	private String reviewUserId;//批准人ID
	private String reviewUserName;//批准人
	
	private String uploadUserId;//上传人ID
	private String uploadUserName;//上传人
	private String uploadUrl;//下载地址
	
	private String sendUserId;//发送人
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String sendDatetime;//发送时间
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
	public String getProcManageId() {
		return procManageId;
	}
	public void setProcManageId(String procManageId) {
		this.procManageId = procManageId;
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
	public String getProcVersion() {
		return procVersion;
	}
	public void setProcVersion(String procVersion) {
		this.procVersion = procVersion;
	}
	public String getProcSection() {
		return procSection;
	}
	public void setProcSection(String procSection) {
		this.procSection = procSection;
	}
	public String getProcType() {
		return procType;
	}
	public void setProcType(String procType) {
		this.procType = procType;
	}
	public String getReviewDatetime() {
		return reviewDatetime;
	}
	public void setReviewDatetime(String reviewDatetime) {
		this.reviewDatetime = reviewDatetime;
	}
	public String getProcStop() {
		return procStop;
	}
	public void setProcStop(String procStop) {
		this.procStop = procStop;
	}
	public String getProcContent() {
		return procContent;
	}
	public void setProcContent(String procContent) {
		this.procContent = procContent;
	}
	public String getProcStatus() {
		return procStatus;
	}
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getMdMatName() {
		return mdMatName;
	}
	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}
	public String getUploadId() {
		return uploadId;
	}
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public String getReviewUserId() {
		return reviewUserId;
	}
	public void setReviewUserId(String reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	public String getReviewUserName() {
		return reviewUserName;
	}
	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}
	public String getUploadUserId() {
		return uploadUserId;
	}
	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId;
	}
	public String getUploadUserName() {
		return uploadUserName;
	}
	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getSendDatetime() {
		return sendDatetime;
	}
	public void setSendDatetime(String sendDatetime) {
		this.sendDatetime = sendDatetime;
	}
	
	
}
