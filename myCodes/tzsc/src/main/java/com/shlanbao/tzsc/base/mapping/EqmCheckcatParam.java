package com.shlanbao.tzsc.base.mapping;


/**
 * 
 * @author Administrator
 *
 */
public class EqmCheckcatParam{
	private String id;//id
	private EqmCheckcatPlan checkcatPid;//保养计划
	private SysUser user;//完成的用户
	private String enable;//是否完成
	private String planTime;//计划开始时间
	private String actualTime;//实际完成时间
	private String setId;//数据字典 维护项目主键ID
	private String remarks;//备注
	private SysUser czgUserId;//操作工ID
	private SysUser lbgUserId;//轮保工ID
	private SysUser shgUserId;//审核工ID
	private String 	czgDate;//操作工操作时间
	private String 	lbgDate;//轮保工操作时间
	private String 	shgDate;//审核工操作时间
	
	private String 	number;//润滑点
	private String 	fillQuantity;//计划量
	private String 	oilId;//润滑油(脂)
	private String 	fillUnit;//单位
	private String 	fashion;//润滑方式
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getSetId() {
		return setId;
	}
	public void setSetId(String setId) {
		this.setId = setId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getActualTime() {
		return actualTime;
	}
	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}
	public SysUser getCzgUserId() {
		return czgUserId;
	}
	public void setCzgUserId(SysUser czgUserId) {
		this.czgUserId = czgUserId;
	}
	public SysUser getLbgUserId() {
		return lbgUserId;
	}
	public void setLbgUserId(SysUser lbgUserId) {
		this.lbgUserId = lbgUserId;
	}
	public SysUser getShgUserId() {
		return shgUserId;
	}
	public void setShgUserId(SysUser shgUserId) {
		this.shgUserId = shgUserId;
	}
	public String getCzgDate() {
		return czgDate;
	}
	public void setCzgDate(String czgDate) {
		this.czgDate = czgDate;
	}
	public String getLbgDate() {
		return lbgDate;
	}
	public void setLbgDate(String lbgDate) {
		this.lbgDate = lbgDate;
	}
	public String getShgDate() {
		return shgDate;
	}
	public void setShgDate(String shgDate) {
		this.shgDate = shgDate;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getFillQuantity() {
		return fillQuantity;
	}
	public void setFillQuantity(String fillQuantity) {
		this.fillQuantity = fillQuantity;
	}
	public String getOilId() {
		return oilId;
	}
	public void setOilId(String oilId) {
		this.oilId = oilId;
	}
	public String getFillUnit() {
		return fillUnit;
	}
	public void setFillUnit(String fillUnit) {
		this.fillUnit = fillUnit;
	}
	public String getFashion() {
		return fashion;
	}
	public void setFashion(String fashion) {
		this.fashion = fashion;
	}
	public EqmCheckcatPlan getCheckcatPid() {
		return checkcatPid;
	}
	public void setCheckcatPid(EqmCheckcatPlan checkcatPid) {
		this.checkcatPid = checkcatPid;
	}
}