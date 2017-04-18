package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * EqmResume entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class EqmResume implements java.io.Serializable {
	// Fields
	private String id;
	private MdEquipment mdEquipment;
	private SysUser sysUser;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date manufactureDate;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date purchaseDate;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date maintenanceDate;
	private String maintenanceContent;
	private String maintenanceType;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date maintainDate;
	private String maintainContent;
	private String maintainType;
	private String maintainPerson;
	private String maintenancePerson;
	private String del;
	private Date createDate;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	
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
	private SysUser modifySysUser;//修改人
	private String modifyTime;//修改时间
	
	private Set eqmAuxils = new HashSet(0);
	private Set eqmScrapcels = new HashSet(0);
	private Set eqmChanges = new HashSet(0);
	private Set eqmRepairs = new HashSet(0);
	private Set eqmResumes = new HashSet(0);
	private Set eqmStops = new HashSet(0);
	private Set eqmSurveies = new HashSet(0);
	private Set eqmInsides = new HashSet(0);
	
	
	// Constructors

	/** default constructor */
	public EqmResume() {
	}

	/** full constructor */
	public EqmResume(MdEquipment mdEquipment, SysUser sysUser,
			Date manufactureDate, Date purchaseDate,
			Date maintenanceDate, String maintenanceContent,
			String maintenanceType, Date maintainDate,
			String maintainContent, String maintainType, String maintainPerson,
			String maintenancePerson, String del, Date createDate,
			String attr1, String attr2, String attr3, String attr4) {
		this.mdEquipment = mdEquipment;
		this.sysUser = sysUser;
		this.manufactureDate = manufactureDate;
		this.purchaseDate = purchaseDate;
		this.maintenanceDate = maintenanceDate;
		this.maintenanceContent = maintenanceContent;
		this.maintenanceType = maintenanceType;
		this.maintainDate = maintainDate;
		this.maintainContent = maintainContent;
		this.maintainType = maintainType;
		this.maintainPerson = maintainPerson;
		this.maintenancePerson = maintenancePerson;
		this.del = del;
		this.createDate = createDate;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.attr4 = attr4;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdEquipment getMdEquipment() {
		return this.mdEquipment;
	}

	public void setMdEquipment(MdEquipment mdEquipment) {
		this.mdEquipment = mdEquipment;
	}

	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Date getManufactureDate() {
		return this.manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getMaintenanceDate() {
		return this.maintenanceDate;
	}

	public void setMaintenanceDate(Date maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public String getMaintenanceContent() {
		return this.maintenanceContent;
	}

	public void setMaintenanceContent(String maintenanceContent) {
		this.maintenanceContent = maintenanceContent;
	}

	public String getMaintenanceType() {
		return this.maintenanceType;
	}

	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public Date getMaintainDate() {
		return this.maintainDate;
	}

	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}

	public String getMaintainContent() {
		return this.maintainContent;
	}

	public void setMaintainContent(String maintainContent) {
		this.maintainContent = maintainContent;
	}

	public String getMaintainType() {
		return this.maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}

	public String getMaintainPerson() {
		return this.maintainPerson;
	}

	public void setMaintainPerson(String maintainPerson) {
		this.maintainPerson = maintainPerson;
	}

	public String getMaintenancePerson() {
		return this.maintenancePerson;
	}

	public void setMaintenancePerson(String maintenancePerson) {
		this.maintenancePerson = maintenancePerson;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public SysUser getModifySysUser() {
		return modifySysUser;
	}

	public void setModifySysUser(SysUser modifySysUser) {
		this.modifySysUser = modifySysUser;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Set getEqmAuxils() {
		return eqmAuxils;
	}

	public void setEqmAuxils(Set eqmAuxils) {
		this.eqmAuxils = eqmAuxils;
	}

	public Set getEqmScrapcels() {
		return eqmScrapcels;
	}

	public void setEqmScrapcels(Set eqmScrapcels) {
		this.eqmScrapcels = eqmScrapcels;
	}

	public Set getEqmChanges() {
		return eqmChanges;
	}

	public void setEqmChanges(Set eqmChanges) {
		this.eqmChanges = eqmChanges;
	}

	public Set getEqmRepairs() {
		return eqmRepairs;
	}

	public void setEqmRepairs(Set eqmRepairs) {
		this.eqmRepairs = eqmRepairs;
	}

	public Set getEqmResumes() {
		return eqmResumes;
	}

	public void setEqmResumes(Set eqmResumes) {
		this.eqmResumes = eqmResumes;
	}

	public Set getEqmStops() {
		return eqmStops;
	}

	public void setEqmStops(Set eqmStops) {
		this.eqmStops = eqmStops;
	}

	public Set getEqmSurveies() {
		return eqmSurveies;
	}

	public void setEqmSurveies(Set eqmSurveies) {
		this.eqmSurveies = eqmSurveies;
	}

	public Set getEqmInsides() {
		return eqmInsides;
	}

	public void setEqmInsides(Set eqmInsides) {
		this.eqmInsides = eqmInsides;
	}

}