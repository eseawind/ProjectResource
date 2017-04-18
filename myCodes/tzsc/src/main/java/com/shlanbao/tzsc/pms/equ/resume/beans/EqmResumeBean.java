package com.shlanbao.tzsc.pms.equ.resume.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class EqmResumeBean {

	private String id;
	private String mdEquId;
	private String mdEquName;
	private String mdEquCode;
	private String mdEquType;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String manufactureDate;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String purchaseDate;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String maintenanceDate;
	private String maintenanceContent;
	private String maintenanceType;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String maintainDate;
	private String maintainContent;
	private String maintainType;
	private String maintainPerson;
	private String maintenancePerson;
	
	private String factoryName;//制造厂名	
	private String company;//承建单位
	private String buildDate;//建造年份	
	private String checkDate;//验收日期		
	private String usingDate;//开始使用日期	
	private String voucherCode;//交接凭证编号	
	private String callSource;//调入来源	
	private String moneySource;//资金来源	
	private String hasUsingYear;//调入时已使用年限	
	private String hasDepr;//调入时已提折旧
	private String resumeType;//类     别	
	private String resumeName;//名     称	
	private String resumeModel;//牌号、型号、规格或结构、层数建筑面积	
	private String propertyCode;//财产编号		
	private String skillDataCode;//技术资料编号
	private String costPrice;//原价	
	private String installPrice;//其中：安装成本	
	private String predUsingYear;//预计使用年限	
	private String predResidual;//预计残值	
	private String predClearMoney;//预计清理费用	
	private String yearDeprRate;//年折旧率	
	private String yearFixRate;//年大修理提存率	
	private String monthDeprMoney;//月折旧额	
	private String monthFixMoney;//月大修理提存额	
	private String modifyUserId;//修改人
	private String modifyUserName;//修改人
	private String modifyTime;//修改时间
	private String addUserId;
	private String addUserName;
	
	
	public String getMdEquCode() {
		return mdEquCode;
	}
	public void setMdEquCode(String mdEquCode) {
		this.mdEquCode = mdEquCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMdEquName() {
		return mdEquName;
	}
	public void setMdEquName(String mdEquName) {
		this.mdEquName = mdEquName;
	}
	public String getMdEquType() {
		return mdEquType;
	}
	public void setMdEquType(String mdEquType) {
		this.mdEquType = mdEquType;
	}
	public String getMdEquId() {
		return mdEquId;
	}
	public void setMdEquId(String mdEquId) {
		this.mdEquId = mdEquId;
	}
	public String getMaintenanceContent() {
		return maintenanceContent;
	}
	public void setMaintenanceContent(String maintenanceContent) {
		this.maintenanceContent = maintenanceContent;
	}
	public String getMaintenanceType() {
		return maintenanceType;
	}
	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}
	public String getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getMaintenanceDate() {
		return maintenanceDate;
	}
	public void setMaintenanceDate(String maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}
	public String getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(String maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getMaintainContent() {
		return maintainContent;
	}
	public void setMaintainContent(String maintainContent) {
		this.maintainContent = maintainContent;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public String getMaintainPerson() {
		return maintainPerson;
	}
	public void setMaintainPerson(String maintainPerson) {
		this.maintainPerson = maintainPerson;
	}
	public String getMaintenancePerson() {
		return maintenancePerson;
	}
	public void setMaintenancePerson(String maintenancePerson) {
		this.maintenancePerson = maintenancePerson;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getUsingDate() {
		return usingDate;
	}
	public void setUsingDate(String usingDate) {
		this.usingDate = usingDate;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public String getCallSource() {
		return callSource;
	}
	public void setCallSource(String callSource) {
		this.callSource = callSource;
	}
	public String getMoneySource() {
		return moneySource;
	}
	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}
	public String getHasUsingYear() {
		return hasUsingYear;
	}
	public void setHasUsingYear(String hasUsingYear) {
		this.hasUsingYear = hasUsingYear;
	}
	public String getHasDepr() {
		return hasDepr;
	}
	public void setHasDepr(String hasDepr) {
		this.hasDepr = hasDepr;
	}
	public String getResumeType() {
		return resumeType;
	}
	public void setResumeType(String resumeType) {
		this.resumeType = resumeType;
	}
	public String getResumeName() {
		return resumeName;
	}
	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}
	public String getResumeModel() {
		return resumeModel;
	}
	public void setResumeModel(String resumeModel) {
		this.resumeModel = resumeModel;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	public String getSkillDataCode() {
		return skillDataCode;
	}
	public void setSkillDataCode(String skillDataCode) {
		this.skillDataCode = skillDataCode;
	}
	public String getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}
	public String getInstallPrice() {
		return installPrice;
	}
	public void setInstallPrice(String installPrice) {
		this.installPrice = installPrice;
	}
	public String getPredUsingYear() {
		return predUsingYear;
	}
	public void setPredUsingYear(String predUsingYear) {
		this.predUsingYear = predUsingYear;
	}
	public String getPredResidual() {
		return predResidual;
	}
	public void setPredResidual(String predResidual) {
		this.predResidual = predResidual;
	}
	public String getPredClearMoney() {
		return predClearMoney;
	}
	public void setPredClearMoney(String predClearMoney) {
		this.predClearMoney = predClearMoney;
	}
	public String getYearDeprRate() {
		return yearDeprRate;
	}
	public void setYearDeprRate(String yearDeprRate) {
		this.yearDeprRate = yearDeprRate;
	}
	public String getYearFixRate() {
		return yearFixRate;
	}
	public void setYearFixRate(String yearFixRate) {
		this.yearFixRate = yearFixRate;
	}
	public String getMonthDeprMoney() {
		return monthDeprMoney;
	}
	public void setMonthDeprMoney(String monthDeprMoney) {
		this.monthDeprMoney = monthDeprMoney;
	}
	public String getMonthFixMoney() {
		return monthFixMoney;
	}
	public void setMonthFixMoney(String monthFixMoney) {
		this.monthFixMoney = monthFixMoney;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	
}
