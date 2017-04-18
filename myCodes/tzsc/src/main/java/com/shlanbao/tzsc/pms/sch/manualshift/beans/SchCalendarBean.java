package com.shlanbao.tzsc.pms.sch.manualshift.beans;

@SuppressWarnings("serial")
public class SchCalendarBean implements java.io.Serializable {
	private String id;
	private String mdShiftId;//班次 早 中 晚  ...
	private String mdShiftName;
	private String mdShiftCode;
	
	private String mdTeamId;//班组 甲乙丙丁
	private String mdTeamName;
	private String mdTeamCode;
	
	private String mdWorkshopId;//车间
	private String mdWorkshopName;
	private String mdWorkshopCode;
	
	private String date;
	private String stim;
	private String etim;
	private String del;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMdTeamId() {
		return mdTeamId;
	}
	public void setMdTeamId(String mdTeamId) {
		this.mdTeamId = mdTeamId;
	}
	public String getMdTeamName() {
		return mdTeamName;
	}
	public void setMdTeamName(String mdTeamName) {
		this.mdTeamName = mdTeamName;
	}
	public String getMdTeamCode() {
		return mdTeamCode;
	}
	public void setMdTeamCode(String mdTeamCode) {
		this.mdTeamCode = mdTeamCode;
	}
	public String getMdShiftId() {
		return mdShiftId;
	}
	public void setMdShiftId(String mdShiftId) {
		this.mdShiftId = mdShiftId;
	}
	public String getMdShiftName() {
		return mdShiftName;
	}
	public void setMdShiftName(String mdShiftName) {
		this.mdShiftName = mdShiftName;
	}
	public String getMdShiftCode() {
		return mdShiftCode;
	}
	public void setMdShiftCode(String mdShiftCode) {
		this.mdShiftCode = mdShiftCode;
	}
	public String getMdWorkshopId() {
		return mdWorkshopId;
	}
	public void setMdWorkshopId(String mdWorkshopId) {
		this.mdWorkshopId = mdWorkshopId;
	}
	public String getMdWorkshopName() {
		return mdWorkshopName;
	}
	public void setMdWorkshopName(String mdWorkshopName) {
		this.mdWorkshopName = mdWorkshopName;
	}
	public String getMdWorkshopCode() {
		return mdWorkshopCode;
	}
	public void setMdWorkshopCode(String mdWorkshopCode) {
		this.mdWorkshopCode = mdWorkshopCode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	
}