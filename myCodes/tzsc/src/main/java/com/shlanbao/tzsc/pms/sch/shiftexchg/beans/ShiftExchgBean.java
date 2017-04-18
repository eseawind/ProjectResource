package com.shlanbao.tzsc.pms.sch.shiftexchg.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * 换班换牌
 * @author Leejean
 * @create 2014年11月25日下午1:24:52
 */
public class ShiftExchgBean {
	private String id;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String date;
	//交班
	private String hoOrderId;
	private String hoUser;
	private String hoShift;
	private String hoTeam;
	private String hoMat;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm")
	private String hoTime;
	//接班
	private String toOrderId;
	private String toUser;
	private String toShift;
	private String toTeam;
	private String toMat;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm")
	private String toTime;
	private String workshop;
	private String equipment;
	private Long del;
	private Long type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHoUser() {
		return hoUser;
	}
	public void setHoUser(String hoUser) {
		this.hoUser = hoUser;
	}
	public String getHoShift() {
		return hoShift;
	}
	public void setHoShift(String hoShift) {
		this.hoShift = hoShift;
	}
	public String getHoTeam() {
		return hoTeam;
	}
	public void setHoTeam(String hoTeam) {
		this.hoTeam = hoTeam;
	}
	public String getHoMat() {
		return hoMat;
	}
	public void setHoMat(String hoMat) {
		this.hoMat = hoMat;
	}
	public String getHoTime() {
		return hoTime;
	}
	public void setHoTime(String hoTime) {
		this.hoTime = hoTime;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getToShift() {
		return toShift;
	}
	public void setToShift(String toShift) {
		this.toShift = toShift;
	}
	public String getToTeam() {
		return toTeam;
	}
	public void setToTeam(String toTeam) {
		this.toTeam = toTeam;
	}
	public String getToMat() {
		return toMat;
	}
	public void setToMat(String toMat) {
		this.toMat = toMat;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getHoOrderId() {
		return hoOrderId;
	}
	public void setHoOrderId(String hoOrderId) {
		this.hoOrderId = hoOrderId;
	}
	public String getToOrderId() {
		return toOrderId;
	}
	public void setToOrderId(String toOrderId) {
		this.toOrderId = toOrderId;
	}
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	
	
}
