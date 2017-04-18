package com.shlanbao.tzsc.pms.sch.shiftexchg.beans;
/**
 * 换班换牌明细
 * @author Leejean
 * @create 2014年12月8日下午2:47:17
 */
public class ShiftExchgDetBean {
	private String id;
    private String exchg;//FK
    private String mat;//结存物料
    private Double qty;//数量
    private String unit;//单位
    private Long del;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExchg() {
		return exchg;
	}
	public void setExchg(String exchg) {
		this.exchg = exchg;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
	}
    
}
