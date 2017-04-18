package com.shlanbao.tzsc.base.mapping;

/**
 * 说明：交接班（MES下发虚领虚退）工单基本信息
 * 日期时间：2016年9月8日10:21:29 
 * 编写者：wanchanghuang
 * */
public class QmChangeShift implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//编号
	private String deviceId;//DEVICE_ID 设备编号
	private String lotId;//LOT_ID 批次号
	private String entryId;//ENTRY_ID 卷包工单号=mes工单号+班次ID
	private String outMaterialId;//OUT_MATERIAL_ID 产出物料编码
	private String outMaterialNm;//OUT_MATERIAL_NM 产出物料描述
    private Float outMaterialQty;//OUT_MATERIAL_QTY 产量
	private String uom;//UOM 单位
	private String shiftId;//SHIFT_ID //班次ID
	private String shiftName;//SHIFT_NAME //班次名称
	private String teamId;//TEAM_ID //班组ID
	private String teamName;//TEAM_NAME //班组名称
	private String woState;//WO_STATE 工单状态
	private String planStartTime;//PLAN_START_TIME -计划开始时间
	private String planFinishedTime;//PLAN_FINISHED_TIME -计划结束时间
	private String actualStartTime;//ACTUAL_START_TIME -实际开始时间
	private String actualFinishedTime;//ACTUAL_START_TIME --实际结束时间
	private String supplyTime;//SUPPLY_TIME -反馈时间
	private String mesEntryId;//MES_ENTRY_ID --MES工单号
	private String createTime;//创建时间
	private String updateTime;//修改时间
	private String orderQty;// 数采实际产量

	
	
	public String getActualFinishedTime() {
		return actualFinishedTime;
	}
	public void setActualFinishedTime(String actualFinishedTime) {
		this.actualFinishedTime = actualFinishedTime;
	}
	public String getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getLotId() {
		return lotId;
	}
	public void setLotId(String lotId) {
		this.lotId = lotId;
	}
	public String getEntryId() {
		return entryId;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	public String getOutMaterialId() {
		return outMaterialId;
	}
	public void setOutMaterialId(String outMaterialId) {
		this.outMaterialId = outMaterialId;
	}
	public String getOutMaterialNm() {
		return outMaterialNm;
	}
	public void setOutMaterialNm(String outMaterialNm) {
		this.outMaterialNm = outMaterialNm;
	}
	public Float getOutMaterialQty() {
		return outMaterialQty;
	}
	public void setOutMaterialQty(Float outMaterialQty) {
		this.outMaterialQty = outMaterialQty;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
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
	public String getWoState() {
		return woState;
	}
	public void setWoState(String woState) {
		this.woState = woState;
	}
	public String getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}
	public String getPlanFinishedTime() {
		return planFinishedTime;
	}
	public void setPlanFinishedTime(String planFinishedTime) {
		this.planFinishedTime = planFinishedTime;
	}
	public String getActualStartTime() {
		return actualStartTime;
	}
	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	public String getSupplyTime() {
		return supplyTime;
	}
	public void setSupplyTime(String supplyTime) {
		this.supplyTime = supplyTime;
	}
	public String getMesEntryId() {
		return mesEntryId;
	}
	public void setMesEntryId(String mesEntryId) {
		this.mesEntryId = mesEntryId;
	}

	
	
	
}