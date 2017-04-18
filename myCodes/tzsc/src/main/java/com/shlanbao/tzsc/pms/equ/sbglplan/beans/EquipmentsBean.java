package com.shlanbao.tzsc.pms.equ.sbglplan.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


public class EquipmentsBean {
	private String id;//设备主数据ID
	private String pid;//设备主数据ID
	private String equipmentCode;//设备主数据Code
	private String equipmentName;
	
	private String mdEqpCategoryId;//设备类型
	private String mdEqpCategoryCode;//设备类型
	private String mdEqpCategoryName;//设备类型
	
	private String eqpTypeId;	//设备型号
	private String eqpTypeName;
	
	private String queryEqpTypeId;	//设备型号
	private String queryType;//查询类型
	
	
	private String name;//用于保存设备名称，方便combobox  Model 转换
	private String equipmentDesc;
	private String workShopId;
	private String workShopName;
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
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createTime;
	private String updateUser;
	private String updateTime;
	private Long vouchProduction;
	private String del;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqpTypeId() {
		return eqpTypeId;
	}
	public void setEqpTypeId(String eqpTypeId) {
		this.eqpTypeId = eqpTypeId;
	}
	public String getEqpTypeName() {
		return eqpTypeName;
	}
	public void setEqpTypeName(String eqpTypeName) {
		this.eqpTypeName = eqpTypeName;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getEquipmentDesc() {
		return equipmentDesc;
	}
	public void setEquipmentDesc(String equipmentDesc) {
		this.equipmentDesc = equipmentDesc;
	}
	public String getWorkShopId() {
		return workShopId;
	}
	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getEquipmentPosition() {
		return equipmentPosition;
	}
	public void setEquipmentPosition(String equipmentPosition) {
		this.equipmentPosition = equipmentPosition;
	}
	public Double getRatedSpeed() {
		return ratedSpeed;
	}
	public void setRatedSpeed(Double ratedSpeed) {
		this.ratedSpeed = ratedSpeed;
	}
	public String getRateSpeedUnit() {
		return rateSpeedUnit;
	}
	public void setRateSpeedUnit(String rateSpeedUnit) {
		this.rateSpeedUnit = rateSpeedUnit;
	}

	public Double getYieId() {
		return yieId;
	}
	public void setYieId(Double yieId) {
		this.yieId = yieId;
	}
	public String getYieldUnit() {
		return yieldUnit;
	}
	public void setYieldUnit(String yieldUnit) {
		this.yieldUnit = yieldUnit;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getFixedAssetNum() {
		return fixedAssetNum;
	}
	public void setFixedAssetNum(String fixedAssetNum) {
		this.fixedAssetNum = fixedAssetNum;
	}
	public String getManufacturingNum() {
		return manufacturingNum;
	}
	public void setManufacturingNum(String manufacturingNum) {
		this.manufacturingNum = manufacturingNum;
	}
	public String getApprovalNum() {
		return approvalNum;
	}
	public void setApprovalNum(String approvalNum) {
		this.approvalNum = approvalNum;
	}
	public String getNavicertNum() {
		return navicertNum;
	}
	public void setNavicertNum(String navicertNum) {
		this.navicertNum = navicertNum;
	}
	public String getFixedAssetFlag() {
		return fixedAssetFlag;
	}
	public void setFixedAssetFlag(String fixedAssetFlag) {
		this.fixedAssetFlag = fixedAssetFlag;
	}
	public String getUsingDepartment() {
		return usingDepartment;
	}
	public void setUsingDepartment(String usingDepartment) {
		this.usingDepartment = usingDepartment;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getDel() {
		return del;
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
	public String getMdEqpCategoryId() {
		return mdEqpCategoryId;
	}
	public void setMdEqpCategoryId(String mdEqpCategoryId) {
		this.mdEqpCategoryId = mdEqpCategoryId;
	}
	public String getMdEqpCategoryCode() {
		return mdEqpCategoryCode;
	}
	public void setMdEqpCategoryCode(String mdEqpCategoryCode) {
		this.mdEqpCategoryCode = mdEqpCategoryCode;
	}
	public String getMdEqpCategoryName() {
		return mdEqpCategoryName;
	}
	public void setMdEqpCategoryName(String mdEqpCategoryName) {
		this.mdEqpCategoryName = mdEqpCategoryName;
	}
	public String getQueryEqpTypeId() {
		return queryEqpTypeId;
	}
	public void setQueryEqpTypeId(String queryEqpTypeId) {
		this.queryEqpTypeId = queryEqpTypeId;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
}
