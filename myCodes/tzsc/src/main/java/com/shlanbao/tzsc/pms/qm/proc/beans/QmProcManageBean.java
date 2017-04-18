package com.shlanbao.tzsc.pms.qm.proc.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 系统用户实体类
 * <li>@author luther.zhang
 * <li>@create 2014-12-22
 */
public class QmProcManageBean{
	private String id;	//主键
	private String procSection;//工段
	private String procType;//机型(适用机型)
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createDatetime;//新增时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String modifyDatetime;//修改时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String reviewDatetime;//批准时间
	private String procStop;//是否停用
	private String procContent;//指导书内容
	private String procStatus;//审核状态
	
	private String procId;//品名(牌号) 
	private String mdMatName;//品名(牌号) 
	
	private String uploadId;//上传ID
	private String fileName;//上传名称
	
	private String addUserId;//新增人ID
	private String addUserName;//新增人姓名
	
	private String modifyUserId;//修改人ID
	private String modifyUserName;//修改人
	
	private String reviewUserId;//批准人ID
	private String reviewUserName;//批准人
	
	private String uploadUserId;//上传人ID
	private String uploadUserName;//上传人
	private String uploadUrl;//下载地址
	
	private String sendUserId;//发送人
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String sendDatetime;//发送时间
	
	private String procVersion;//版本号
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getModifyDatetime() {
		return modifyDatetime;
	}
	public void setModifyDatetime(String modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
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
	public String getProcVersion() {
		return procVersion;
	}
	public void setProcVersion(String procVersion) {
		this.procVersion = procVersion;
	}
	
	
}
