package com.shlanbao.tzsc.pms.qm.prod.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 系统用户实体类
 * <li>@author luther.zhang
 * <li>@create 2014-12-31
 */
public class QmProdManageBean{
	private String id;
	private String prodNumber;
	private String prodName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createDatetime;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String modifyDatetime;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String reviewDatetime;
	private String prodStop;
	private String prodContent;
	private String prodStatus;
	
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
	
	private String prodVersion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getProdStop() {
		return prodStop;
	}

	public void setProdStop(String prodStop) {
		this.prodStop = prodStop;
	}

	public String getProdContent() {
		return prodContent;
	}

	public void setProdContent(String prodContent) {
		this.prodContent = prodContent;
	}

	public String getProdStatus() {
		return prodStatus;
	}

	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
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

	public String getProdVersion() {
		return prodVersion;
	}

	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
	}
	
	
	
}
