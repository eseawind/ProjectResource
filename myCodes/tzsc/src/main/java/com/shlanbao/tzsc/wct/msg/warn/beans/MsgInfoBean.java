package com.shlanbao.tzsc.wct.msg.warn.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;



public class MsgInfoBean {
	private String issuerName;
	private String issuerId;
    
	@DateFmtAnnotation(fmtPattern = "yyyy-MM-dd HH:mm:ss")
    private String time;
    private String eid;
    private Long flag;
    private Long del;
    private String noticeObject;
    private String approveContent;
    private String etim;
	private String stim;

	private String id;
	private String title;
	private String content;
	
	
	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	public Long getDel() {
		return del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

	public String getNoticeObject() {
		return noticeObject;
	}

	public void setNoticeObject(String noticeObject) {
		this.noticeObject = noticeObject;
	}

	public String getApproveContent() {
		return approveContent;
	}

	public void setApproveContent(String approveContent) {
		this.approveContent = approveContent;
	}

	public String getEtim() {
		return etim;
	}

	public void setEtim(String etim) {
		this.etim = etim;
	}

	public String getStim() {
		return stim;
	}

	public void setStim(String stim) {
		this.stim = stim;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
