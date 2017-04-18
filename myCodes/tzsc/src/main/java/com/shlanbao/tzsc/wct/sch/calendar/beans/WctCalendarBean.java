package com.shlanbao.tzsc.wct.sch.calendar.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 *  工单实体
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class WctCalendarBean {
	private String id;
	private String team;
	private String shift;
	private String workshop;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String date;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String stim;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String etim;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
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
	
}
