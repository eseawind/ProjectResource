package com.shlanbao.tzsc.pms.msg.info.beans;


import java.sql.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


public class MsgInfoBean {
	private String id;//ID
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String time;//通知时间
	private String content;//通知内容
	private String eid;//机台ID
	private String equipName;//机台名称
	private Long flag;//状态标记
	private Long del;//删除标记
	private String title;//标题
	private String initiator;//发起人
	private String initiatorName;//发起人名称
	private String approval;//审批人Id
	private String approvalName;//审批人名称
	private String noticeObject;//通知对象ID列表
	private String noticeObjectList;//通知对象名称列表
	private String approveContent;//审批内容
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String approveTime;//审批时间
	private String pid;
	private String pName;
	private String issuer;//签发人
	private String issuerName;//签发人名称
	
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getEquipName() {
		return equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getInitiatorName() {
		return initiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getApprovalName() {
		return approvalName;
	}
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}
	public String getNoticeObject() {
		return noticeObject;
	}
	public void setNoticeObject(String noticeObject) {
		this.noticeObject = noticeObject;
	}
	public String getNoticeObjectList() {
		return noticeObjectList;
	}
	public void setNoticeObjectList(String noticeObjectList) {
		this.noticeObjectList = noticeObjectList;
	}
	public String getApproveContent() {
		return approveContent;
	}
	public void setApproveContent(String approveContent) {
		this.approveContent = approveContent;
	}
	public String getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
}
