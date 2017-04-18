package com.lanbao.dws.model.wct.pddisplay;

import javax.persistence.Entity;
/**
 * 工作实际辅料
 * @author shisihai
 *
 */
@Entity(name="SCH_STAT_OUTPUT")
public class FLOfOrderResult {
	private Double flQty;
	private String flName;
	private String flUnitName;
	
	private Double outQty;
	private String outQtyUnit;
	private String rstim;
	private String retim;
	
	public Double getOutQty() {
		return outQty;
	}
	public void setOutQty(Double outQty) {
		this.outQty = outQty;
	}
	public String getOutQtyUnit() {
		return outQtyUnit;
	}
	public void setOutQtyUnit(String outQtyUnit) {
		this.outQtyUnit = outQtyUnit;
	}
	public String getRstim() {
		return rstim;
	}
	public void setRstim(String rstim) {
		this.rstim = rstim;
	}
	public String getRetim() {
		return retim;
	}
	public void setRetim(String retim) {
		this.retim = retim;
	}
	public Double getFlQty() {
		return flQty;
	}
	public void setFlQty(Double flQty) {
		this.flQty = flQty;
	}
	public String getFlName() {
		return flName;
	}
	public void setFlName(String flName) {
		this.flName = flName;
	}
	public String getFlUnitName() {
		return flUnitName;
	}
	public void setFlUnitName(String flUnitName) {
		this.flUnitName = flUnitName;
	}
	
}
