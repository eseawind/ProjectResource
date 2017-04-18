package com.shlanbao.tzsc.pms.equ.sbglplan.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


public class EquRhParamBean {
	
	private String planTime;//计划开始时间
	private String actualTimeStr;//实际完成时间字符串
	private String actualTime;//实际完成时间
	private String shiftName;//计划班次
	private String jobName;//计划名称
	private String jobType;//保养类型
	private String jobContext;//保养内容
	//----查询条件-------
	private Date startTime;
	private Date endTime;
	private String lxType;//角色类型
	
	private String sbCode;//设备编号 
	private String sbName;//设备名称
	private String sbDes;//设备备注
	
	private String planName;//保养计划名称
	private String isCheck;//是否选中
	private String sbZjId;//设备ID
	private String sbPid;
	private String actualStrTime;//实际完成时间,
	private String userId;//用户名称
	private String userName;//用户名称
	private String planTimeStr;//计划开始时间字符串
	private String enable;//是否完成
	private String remarks;//备注
	private String setId;//数据字典 维护项目主键ID
	
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
	
	
	private String number;//点数
	private String oilId;//润 滑 油（脂）ID
	private String oilName;//润 滑 油（脂）
	private String fillQuantity;//加注量
	private String fillUnit;//加注量单位
	private String fillUnitName;//加注量单位
	private String fashion;//方 式
	private String fashionName;//方 式
	
	
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
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getActualTimeStr() {
		return actualTimeStr;
	}
	public void setActualTimeStr(String actualTimeStr) {
		this.actualTimeStr = actualTimeStr;
	}
	public String getActualTime() {
		return actualTime;
	}
	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobContext() {
		return jobContext;
	}
	public void setJobContext(String jobContext) {
		this.jobContext = jobContext;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getLxType() {
		return lxType;
	}
	public void setLxType(String lxType) {
		this.lxType = lxType;
	}
	public String getSbCode() {
		return sbCode;
	}
	public void setSbCode(String sbCode) {
		this.sbCode = sbCode;
	}
	public String getSbName() {
		return sbName;
	}
	public void setSbName(String sbName) {
		this.sbName = sbName;
	}
	public String getSbDes() {
		return sbDes;
	}
	public void setSbDes(String sbDes) {
		this.sbDes = sbDes;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getSbZjId() {
		return sbZjId;
	}
	public void setSbZjId(String sbZjId) {
		this.sbZjId = sbZjId;
	}
	public String getSbPid() {
		return sbPid;
	}
	public void setSbPid(String sbPid) {
		this.sbPid = sbPid;
	}
	public String getActualStrTime() {
		return actualStrTime;
	}
	public void setActualStrTime(String actualStrTime) {
		this.actualStrTime = actualStrTime;
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
	public String getPlanTimeStr() {
		return planTimeStr;
	}
	public void setPlanTimeStr(String planTimeStr) {
		this.planTimeStr = planTimeStr;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSetId() {
		return setId;
	}
	public void setSetId(String setId) {
		this.setId = setId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getOilId() {
		return oilId;
	}
	public void setOilId(String oilId) {
		this.oilId = oilId;
	}
	public String getOilName() {
		return oilName;
	}
	public void setOilName(String oilName) {
		this.oilName = oilName;
	}
	public String getFillQuantity() {
		return fillQuantity;
	}
	public void setFillQuantity(String fillQuantity) {
		this.fillQuantity = fillQuantity;
	}
	public String getFillUnit() {
		return fillUnit;
	}
	public void setFillUnit(String fillUnit) {
		this.fillUnit = fillUnit;
	}
	public String getFillUnitName() {
		return fillUnitName;
	}
	public void setFillUnitName(String fillUnitName) {
		this.fillUnitName = fillUnitName;
	}
	public String getFashion() {
		return fashion;
	}
	public void setFashion(String fashion) {
		this.fashion = fashion;
	}
	public String getFashionName() {
		return fashionName;
	}
	public void setFashionName(String fashionName) {
		this.fashionName = fashionName;
	}
}
