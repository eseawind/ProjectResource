package com.lanbao.dws.model.wct.eqpManager;
/**
 * [功能说明]：设备保养内登录实体类
 * 
 * */
public class EqmByLoginBean {
	
	private String userId; //用户ID
	private String userName; //用户姓名
	private String loginName;//用户登录名
	private String roleId; //角色ID
	private String roleName; //角色名称
	private String shiftId; //班次ID
	private String shiftName;//班次名称
	private String eqpId; //设备ID
	private String eqpName;//设备名称
	private String jtUserCode;//卷包机台控制屏系统登录名称
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getJtUserCode() {
		return jtUserCode;
	}
	public void setJtUserCode(String jtUserCode) {
		this.jtUserCode = jtUserCode;
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
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	
	
	 
}
