package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class EqmPaulDay {
	private String id;//ID	varchar(50)	Unchecked
	private String name;//日保养名称
	private MdShift shift;//SHIFT	varchar(50)	Checked
	private MdTeam team;//TEAM	varchar(50)	Checked
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private Date date_p;//执行日期
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date stim;//开始日期
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date etim;//结束日期
	private MdEqpCategory eqp_type;//设备类型
	private String paul_id;//日保Id
	private int del;//是否删除 1：删除
	private int status;//状态
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MdShift getShift() {
		return shift;
	}
	public void setShift(MdShift shift) {
		this.shift = shift;
	}
	public MdTeam getTeam() {
		return team;
	}
	public void setTeam(MdTeam team) {
		this.team = team;
	}
	public Date getDate_p() {
		return date_p;
	}
	public void setDate_p(Date date_p) {
		this.date_p = date_p;
	}
	public Date getStim() {
		return stim;
	}
	public void setStim(Date stim) {
		this.stim = stim;
	}
	public Date getEtim() {
		return etim;
	}
	public void setEtim(Date etim) {
		this.etim = etim;
	}
	public MdEqpCategory getEqp_type() {
		return eqp_type;
	}
	public void setEqp_type(MdEqpCategory eqp_type) {
		this.eqp_type = eqp_type;
	}
	public String getPaul_id() {
		return paul_id;
	}
	public void setPaul_id(String paul_id) {
		this.paul_id = paul_id;
	}

}
