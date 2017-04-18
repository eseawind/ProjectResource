package com.shlanbao.tzsc.pms.sch.statjbcj.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class ShiftCheckBean {
	
	private String id;//班内考核编号
	
	private String shiftId;//班次编号
	
	private String shiftname;//班次名称
	
	private String teamId;//班组编号
	
	private String teamname;//班组名称
	
	private String eqptype;
	
	private String date;
	
	private String date2;
	
	private String eqpname;//设备名称
	
	private String lotid;//批次号
	
	private String entryid;//工单编号
	
	private String outmaterial;//品牌
	
	private Double planqty;//计划产量
	
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createtime;//创建时间
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getShiftname() {
		return shiftname;
	}

	public void setShiftname(String shiftname) {
		this.shiftname = shiftname;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getEqptype() {
		return eqptype;
	}

	public void setEqptype(String eqptype) {
		this.eqptype = eqptype;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getEqpname() {
		return eqpname;
	}

	public void setEqpname(String eqpname) {
		this.eqpname = eqpname;
	}

	public String getLotid() {
		return lotid;
	}

	public void setLotid(String lotid) {
		this.lotid = lotid;
	}

	public String getEntryid() {
		return entryid;
	}

	public void setEntryid(String entryid) {
		this.entryid = entryid;
	}

	public String getOutmaterial() {
		return outmaterial;
	}

	public void setOutmaterial(String outmaterial) {
		this.outmaterial = outmaterial;
	}

	public Double getPlanqty() {
		return planqty;
	}

	public void setPlanqty(Double planqty) {
		this.planqty = planqty;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public ShiftCheckBean() {}

	public ShiftCheckBean(String id, String shiftId, String shiftname, String teamId, String teamname,
		    String eqptype, String date, String date2, String eqpname, String lotid, String entryid,
			String outmaterial, Double planqty, String createtime) {
		this.id = id;
		this.shiftId = shiftId;
		this.shiftname = shiftname;
		this.teamId = teamId;
		this.teamname = teamname;
		this.eqptype = eqptype;
		this.date = date;
		this.date2 = date2;
		this.eqpname = eqpname;
		this.lotid = lotid;
		this.entryid = entryid;
		this.outmaterial = outmaterial;
		this.planqty = planqty;
		this.createtime = createtime;
	}
	
}
