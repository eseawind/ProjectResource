package com.lanbao.dws.model.wct.eqpManager;
/**
 * 维修工
 * @author shisihai
 */
public class RepairUserBean {
	private String id;
	private String shiftId;
	private String teamId;
	private String userId;//维修工id
	private String userName;
	private String typeId;//维修工类型   1机械    2电气
	private String typeName;//
	private String imgPath;//图片名称
	private String eqpTypeName;//维修机器类型
	private String status;//当前维修工状态  0空闲   1忙碌
	private String eqm_wcp_id;// 保养计划EQM_WHEEL_COVEL_PLAN表ID   
	
	
	
	public String getEqm_wcp_id() {
		return eqm_wcp_id;
	}
	public void setEqm_wcp_id(String eqm_wcp_id) {
		this.eqm_wcp_id = eqm_wcp_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getEqpTypeName() {
		return eqpTypeName;
	}
	public void setEqpTypeName(String eqpTypeName) {
		this.eqpTypeName = eqpTypeName;
	}
}
