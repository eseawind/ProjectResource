package com.shlanbao.tzsc.pms.sys.loadjsp.beans;


import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class IndexInfoBean {
	private String id;
	private String content;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String time;
	private String title;
    private String noticeObject;
    private String approveContent;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
}
