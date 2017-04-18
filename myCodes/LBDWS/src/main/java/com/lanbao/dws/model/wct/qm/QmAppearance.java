package com.lanbao.dws.model.wct.qm;

/**
 * 外观巡检封装的数据bean
 * @author shisihai
 *
 */
public class QmAppearance { 
	private String id;
	private String order;//工单code
	private String oid;//工单id
	private String uid;//用户id
	private String batchID;//批次号
	private String uName;//用户名
	private String checkTime;//检测时间
	private String checkItem;//检测项目  code
	private Integer checkVal;//检测值
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckItem() {
		return checkItem;
	}
	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	public Integer getCheckVal() {
		return checkVal;
	}
	public void setCheckVal(Integer checkVal) {
		this.checkVal = checkVal;
	}
}
