package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class EqmProtectRecord {
	
	private String id;
	private String eqpId;
	private Date createTime;
	private String createUserId;
	private String endUserId;
	private Date endTime;
	private String pauldayId;
	private Integer status;//1完成，0未完成
	private String endUserName;
	private String shift_id;
	private String paul_id;
	
	
	
	public String getShift_id() {
		return shift_id;
	}
	public void setShift_id(String shift_id) {
		this.shift_id = shift_id;
	}
	public String getPaul_id() {
		return paul_id;
	}
	public void setPaul_id(String paul_id) {
		this.paul_id = paul_id;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getEndUserName() {
		return endUserName;
	}
	public void setEndUserName(String endUserName) {
		this.endUserName = endUserName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEndUserId() {
		return endUserId;
	}
	public void setEndUserId(String endUserId) {
		this.endUserId = endUserId;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getPauldayId() {
		return pauldayId;
	}
	public void setPauldayId(String pauldayId) {
		this.pauldayId = pauldayId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
