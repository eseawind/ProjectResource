package com.lanbao.dws.model.wct.pddisplay;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "sch_workorder")
public class PdDisplayBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String shiftName;
	private String teamName;
	private String eqmName;
	private double qty;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String date_;
	private String stim;
	private String etim;
	private int sts;
	private String unitName;
	private String matName;
	private String type;
	private int del;
	private String eqp_id; //设备ID
	
	

	public String getEqp_id() {
		return eqp_id;
	}
	public void setEqp_id(String eqp_id) {
		this.eqp_id = eqp_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getEqmName() {
		return eqmName;
	}
	public void setEqmName(String eqmName) {
		this.eqmName = eqmName;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getDate_() {
		return date_;
	}
	public void setDate_(String date_) {
		this.date_ = date_;
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
	public int getSts() {
		return sts;
	}
	public void setSts(int sts) {
		this.sts = sts;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	
	

}
