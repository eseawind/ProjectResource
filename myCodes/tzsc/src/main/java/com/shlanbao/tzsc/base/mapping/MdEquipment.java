package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * MdEquipment entity. @author MyEclipse Persistence Tools
 */

public class MdEquipment implements java.io.Serializable {

	// Fields

	private String id;
	private String mesEqpCode;//mes系统设备code
	private MdEqpType mdEqpType;
	private MdWorkshop mdWorkshop;
	private String equipmentCode;
	private String equipmentName;
	private String equipmentDesc;
	private String workCenter;
	private String equipmentPosition;
	private Double ratedSpeed;
	private String rateSpeedUnit;
	private Double yieId;
	private String yieldUnit;
	private String enabled;
	private String fixedAssetNum;
	private String manufacturingNum;
	private String approvalNum;
	private String navicertNum;
	private String fixedAssetFlag;
	private String usingDepartment;
	private String createUserId;
	private String createUserName;
	private Date createTime;
	private Long vouchProduction;
	private String updateUser;
	private Date updateTime;
	private String del;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	private Set eqmMaintains = new HashSet(0);
	private Set eqmTroubles = new HashSet(0);
	private Set eqmWheelCovelPlans = new HashSet(0);
	private Set eqmResumes = new HashSet(0);

	// Constructors

	/** default constructor */
	public MdEquipment() {
	}
	
	public MdEquipment(String id) {
		this.id = id;
	}

	/** minimal constructor */
	public MdEquipment(MdEqpType mdEqpType) {
		this.mdEqpType = mdEqpType;
	}

	public String getMesEqpCode() {
		return mesEqpCode;
	}

	public void setMesEqpCode(String mesEqpCode) {
		this.mesEqpCode = mesEqpCode;
	}

	/** full constructor */
	public MdEquipment(MdEqpType mdEqpType, MdWorkshop mdWorkshop,String equipmentCode,
			String equipmentName, String equipmentDesc, String workChop,
			String workCenter, String equipmentPosition, Double ratedSpeed,
			String rateSpeedUnit, Double yieId, String yieldUnit,
			String enabled, String fixedAssetNum, String manufacturingNum,
			String approvalNum, String navicertNum, String fixedAssetFlag,
			String usingDepartment, String createUserId, String createUserName,
			Date createTime, String updateUser, Date updateTime,
			String del, String attr1, String attr2, String attr3, String attr4,
			Set eqmMaintains,  Set eqmTroubles,
			Set eqmWheelCovelPlans, Set eqmResumes) {
		this.mdEqpType = mdEqpType;
		this.mdWorkshop = mdWorkshop;
		this.equipmentCode = equipmentCode;
		this.equipmentName = equipmentName;
		this.equipmentDesc = equipmentDesc;
		this.workCenter = workCenter;
		this.equipmentPosition = equipmentPosition;
		this.ratedSpeed = ratedSpeed;
		this.rateSpeedUnit = rateSpeedUnit;
		this.yieId = yieId;
		this.yieldUnit = yieldUnit;
		this.enabled = enabled;
		this.fixedAssetNum = fixedAssetNum;
		this.manufacturingNum = manufacturingNum;
		this.approvalNum = approvalNum;
		this.navicertNum = navicertNum;
		this.fixedAssetFlag = fixedAssetFlag;
		this.usingDepartment = usingDepartment;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
		this.del = del;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.attr4 = attr4;
		this.eqmMaintains = eqmMaintains;
		this.eqmTroubles = eqmTroubles;
		this.eqmWheelCovelPlans = eqmWheelCovelPlans;
		this.eqmResumes = eqmResumes;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdEqpType getMdEqpType() {
		return this.mdEqpType;
	}

	public void setMdEqpType(MdEqpType mdEqpType) {
		this.mdEqpType = mdEqpType;
	}

	public MdWorkshop getMdWorkshop() {
		return mdWorkshop;
	}

	public void setMdWorkshop(MdWorkshop mdWorkshop) {
		this.mdWorkshop = mdWorkshop;
	}

	public String getEquipmentCode() {
		return this.equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getEquipmentName() {
		return this.equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentDesc() {
		return this.equipmentDesc;
	}

	public void setEquipmentDesc(String equipmentDesc) {
		this.equipmentDesc = equipmentDesc;
	}

	public String getWorkCenter() {
		return this.workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getEquipmentPosition() {
		return this.equipmentPosition;
	}

	public void setEquipmentPosition(String equipmentPosition) {
		this.equipmentPosition = equipmentPosition;
	}

	public Double getRatedSpeed() {
		return this.ratedSpeed;
	}

	public void setRatedSpeed(Double ratedSpeed) {
		this.ratedSpeed = ratedSpeed;
	}

	public String getRateSpeedUnit() {
		return this.rateSpeedUnit;
	}

	public void setRateSpeedUnit(String rateSpeedUnit) {
		this.rateSpeedUnit = rateSpeedUnit;
	}

	public Double getYieId() {
		return this.yieId;
	}

	public void setYieId(Double yieId) {
		this.yieId = yieId;
	}

	public String getYieldUnit() {
		return this.yieldUnit;
	}

	public void setYieldUnit(String yieldUnit) {
		this.yieldUnit = yieldUnit;
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getFixedAssetNum() {
		return this.fixedAssetNum;
	}

	public void setFixedAssetNum(String fixedAssetNum) {
		this.fixedAssetNum = fixedAssetNum;
	}

	public String getManufacturingNum() {
		return this.manufacturingNum;
	}

	public void setManufacturingNum(String manufacturingNum) {
		this.manufacturingNum = manufacturingNum;
	}

	public String getApprovalNum() {
		return this.approvalNum;
	}

	public void setApprovalNum(String approvalNum) {
		this.approvalNum = approvalNum;
	}

	public String getNavicertNum() {
		return this.navicertNum;
	}

	public void setNavicertNum(String navicertNum) {
		this.navicertNum = navicertNum;
	}

	public String getFixedAssetFlag() {
		return this.fixedAssetFlag;
	}

	public void setFixedAssetFlag(String fixedAssetFlag) {
		this.fixedAssetFlag = fixedAssetFlag;
	}

	public String getUsingDepartment() {
		return this.usingDepartment;
	}

	public void setUsingDepartment(String usingDepartment) {
		this.usingDepartment = usingDepartment;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public Long getVouchProduction() {
		return vouchProduction;
	}

	public void setVouchProduction(Long vouchProduction) {
		this.vouchProduction = vouchProduction;
	}

	public String getAttr1() {
		return this.attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return this.attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return this.attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getAttr4() {
		return this.attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	public Set getEqmMaintains() {
		return this.eqmMaintains;
	}

	public void setEqmMaintains(Set eqmMaintains) {
		this.eqmMaintains = eqmMaintains;
	}


	public Set getEqmTroubles() {
		return this.eqmTroubles;
	}

	public void setEqmTroubles(Set eqmTroubles) {
		this.eqmTroubles = eqmTroubles;
	}

	public Set getEqmWheelCovelPlans() {
		return this.eqmWheelCovelPlans;
	}

	public void setEqmWheelCovelPlans(Set eqmWheelCovelPlans) {
		this.eqmWheelCovelPlans = eqmWheelCovelPlans;
	}

	public Set getEqmResumes() {
		return this.eqmResumes;
	}

	public void setEqmResumes(Set eqmResumes) {
		this.eqmResumes = eqmResumes;
	}

}