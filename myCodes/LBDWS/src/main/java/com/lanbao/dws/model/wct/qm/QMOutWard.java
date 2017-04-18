package com.lanbao.dws.model.wct.qm;

import java.util.Date;

import javax.persistence.Entity;

/**
 * 外观巡检
 * @author shisihai
 *
 */
@Entity(name="QM_OUTWARD ")
public class QMOutWard {
	private String id;
	private String oid;//工单id
	private String uid_;//用户id
	private String uName;//用户名
	private String order;//工单code
	private String checkTime;//检测时间
	private Date time;//时间
	private String BATCH_NO;//批次号
	
	private String eqpCode;//设备编号
	
	private String eqpName;//机台名称
	
	
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getEqpCode() {
		return eqpCode;
	}
	public void setEqpCode(String eqpCode) {
		this.eqpCode = eqpCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getUid_() {
		return uid_;
	}
	public void setUid_(String uid_) {
		this.uid_ = uid_;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getBATCH_NO() {
		return BATCH_NO;
	}
	public void setBATCH_NO(String bATCH_NO) {
		BATCH_NO = bATCH_NO;
	}
}
