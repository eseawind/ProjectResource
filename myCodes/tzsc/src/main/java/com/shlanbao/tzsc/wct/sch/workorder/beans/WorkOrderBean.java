package com.shlanbao.tzsc.wct.sch.workorder.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 工单实体
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class WorkOrderBean {
	private String id;//id
	private String code;//工单号
	private String mat;//牌号
	private String equipment;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String shift;//班次
	private String team;//班组
	private Double qty;//计划产量
    private String unit;//单位
    @DateFmtAnnotation(fmtPattern="MM-dd HH:mm")
    private String stim;//计划开始时间
    @DateFmtAnnotation(fmtPattern="HH:mm")
    private String etim;//计划结束时间
    private Long sts;//状态
    private Long tyle;//工单类型(例如1：卷烟机工单2：包装机工单3:封箱机工单4:成型机工)
    
    private String shiftCode;//班次
	private String teamCode;//班组
	private String isAuto;//是否自动运行 add by luther.zhang 20150417
	private String eqp;//设备ID
    public WorkOrderBean() {
		// TODO Auto-generated constructor stub
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
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
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
	public Long getSts() {
		return sts;
	}
	public void setSts(Long sts) {
		this.sts = sts;
	}
	public Long getTyle() {
		return tyle;
	}
	public void setTyle(Long tyle) {
		this.tyle = tyle;
	}
	public String getShiftCode() {
		return shiftCode;
	}
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}
	public String getEqp() {
		return eqp;
	}
	public void setEqp(String eqp) {
		this.eqp = eqp;
	}
}
