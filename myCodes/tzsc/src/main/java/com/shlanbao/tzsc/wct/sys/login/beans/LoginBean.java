package com.shlanbao.tzsc.wct.sys.login.beans;

import java.util.List;

import com.shlanbao.tzsc.pms.sys.role.beans.RoleBean;

/**
 * 登录实体
 * @author Leejean
 * @create 2014年12月11日上午11:07:25
 */
public class LoginBean {
	private String loginName;//账号
	private String passWord;//密码
	private String name;//用户名
	private String eno;//打卡登录-卡号
	
	private String team;//班组
	private String shift;//班次
	
	private String equipmentName;//设备名称,机台号
	private String equipmentCode;//设备代码
	private String equipmentType;//设备型号
	private String equipmentIp;//终端IP
	private String workshop;//车间
	
	//add by luther.zhang 20150312
	private String shiftId;//班次
	private String teamId;//班组
	private String equipmentId;//设备主键ID
	private String mdMatId;//牌号ID
	private String mdMatName;//牌号名称
	private String prodId;//工单号
	private String isSuccess;//是否成功
	private String userId;//用户主键ID
	private String teamCode;//班组
	private String shiftCode;//班次
	private String workshopCode;//车间
	private String roles;//角色
	private String rolesNames;//角色
	private String dlStatus;//登录状态
	private String equipmentTypeId;//设备类型ID
	
	
	
	
	
	public String getEno() {
		return eno;
	}

	public void setEno(String eno) {
		this.eno = eno;
	}

	public String getEquipmentTypeId() {
		return equipmentTypeId;
	}

	public void setEquipmentTypeId(String equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}

	public String getMdMatId() {
		return mdMatId;
	}
	
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}
	public String getMdMatName() {
		return mdMatName;
	}
	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
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
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getEquipmentIp() {
		return equipmentIp;
	}
	public void setEquipmentIp(String equipmentIp) {
		this.equipmentIp = equipmentIp;
	}
	
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LoginBean() {
		// TODO Auto-generated constructor stub
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "LoginBean [loginName=" + loginName + ", passWord=" + passWord
				+ ", team=" + team + ", shift=" + shift + ", equipmentName="
				+ equipmentName + ", equipmentCode=" + equipmentCode
				+ ", equipmentType=" + equipmentType + ", equipmentIp="
				+ equipmentIp + ", workshop=" + workshop + "]";
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public String getWorkshopCode() {
		return workshopCode;
	}

	public void setWorkshopCode(String workshopCode) {
		this.workshopCode = workshopCode;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getRolesNames() {
		return rolesNames;
	}

	public void setRolesNames(String rolesNames) {
		this.rolesNames = rolesNames;
	}

	public String getDlStatus() {
		return dlStatus;
	}

	public void setDlStatus(String dlStatus) {
		this.dlStatus = dlStatus;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
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
}
