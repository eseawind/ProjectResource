package com.shlanbao.tzsc.pms.sys.datadict.beans;


public class SysEqpCategoryBean {

	private String id;
	private String code;
	private String name;
	private String des;
	private Long enable;
	private String del;
	
	private String isUploadFile;//是否上传成功
	private String securityLevel;//安全级别
	private String imagesVersion;//版本号
	
	private String uplaodId;
	private String uploadUrl;
	private String uploadName;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Long getEnable() {
		return enable;
	}
	public void setEnable(Long enable) {
		this.enable = enable;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getIsUploadFile() {
		return isUploadFile;
	}
	public void setIsUploadFile(String isUploadFile) {
		this.isUploadFile = isUploadFile;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public String getImagesVersion() {
		return imagesVersion;
	}
	public void setImagesVersion(String imagesVersion) {
		this.imagesVersion = imagesVersion;
	}
	public String getUplaodId() {
		return uplaodId;
	}
	public void setUplaodId(String uplaodId) {
		this.uplaodId = uplaodId;
	}
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	public String getUploadName() {
		return uploadName;
	}
	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}
}
