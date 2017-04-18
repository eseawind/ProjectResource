package com.shlanbao.tzsc.pms.equ.paul.beans;


public class EqmPaulDayBean {
	private String id;//ID	varchar(50)	Unchecked
	private String name;//日保养名称
	private String shiftId;
	private String shiftName;
	private String teamId;
	private String teamName;	
	private String date_p;//执行日期
	private String stim;//开始日期
	private String etim;//结束日期
	private String eqp_type_name;
	private String eqp_type_id;
	private String paul_id;//日保Id
	private int del;//是否删除 1：删除
	private int status;//状态
	//查询条件
	private String runtime;
	private String endtime;
	
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getDate_p() {
		return date_p;
	}
	public void setDate_p(String date_p) {
		this.date_p = date_p;
	}
	public String getStim() {
		return stim;
	}
	public void setStim(String stim) {
		this.stim = stim;
	}
	public String getEtim() {
		return etim;
	}
	public void setEtim(String etim) {
		this.etim = etim;
	}
	public String getEqp_type_name() {
		return eqp_type_name;
	}
	public void setEqp_type_name(String eqp_type_name) {
		this.eqp_type_name = eqp_type_name;
	}
	public String getEqp_type_id() {
		return eqp_type_id;
	}
	public void setEqp_type_id(String eqp_type_id) {
		this.eqp_type_id = eqp_type_id;
	}
	public String getPaul_id() {
		return paul_id;
	}
	public void setPaul_id(String paul_id) {
		this.paul_id = paul_id;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	
}
