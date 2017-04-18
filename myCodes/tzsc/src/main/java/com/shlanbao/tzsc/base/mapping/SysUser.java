package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */

public class SysUser implements java.io.Serializable {

	// Fields

	private String id;
	private Date createDatetime;
	private Date modifyDatetime;
	private String name;
	private String pwd;
	private String eno;
	private String loginName;
	private String gender;
	private String phone;
	private String tel;
	private String fax;
	private String email;
	private Long enable;
	private Long del;
	private Long securityLevel;
	private Set schShiftExchgsForToUser = new HashSet(0);
	private Set sysUserRoles = new HashSet(0);
	private Set msgQmWarns = new HashSet(0);
	private Set schShiftExchgsForHoUser = new HashSet(0);
	private Set qmOutwards = new HashSet(0);
	private Set eqmLubricantMaintains = new HashSet(0);
	private Set qmSelfCheckFilters = new HashSet(0);
	private Set sysUserOrganizations = new HashSet(0);
	private Set qmSelfCheckCigarettes = new HashSet(0);
	private Set qmSelfCheckPackets = new HashSet(0);
	private Set eqmTroubles = new HashSet(0);
	private Set qmSelfCheckStrips = new HashSet(0);
	private Set qmSelfCheckCartonses = new HashSet(0);
	private Set sysFavorites = new HashSet(0);

	private Set msgInfosForApproval = new HashSet(0);
	private Set msgInfosForInitiator = new HashSet(0);
	private Set msgInfosForIssuer = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysUser() {
	}

	public SysUser(String id) {
		super();
		this.id = id;
	}

	/** full constructor */
	public SysUser(Date createDatetime, Date modifyDatetime, String name,
			String pwd, String eno, String loginName, String gender,
			String phone, String tel, String fax, String email, Long enable,
			Long del, Long securityLevel, Set msgInfosForApproval,
			Set msgInfosForInitiator, Set msgInfosForIssuer,
			Set schShiftExchgsForToUser, Set sysUserRoles, Set msgQmWarns,
			Set schShiftExchgsForHoUser, Set qmOutwards,
			Set eqmLubricantMaintains, Set qmSelfCheckFilters,
			Set sysUserOrganizations, Set qmSelfCheckCigarettes,
			Set qmSelfCheckPackets, Set eqmTroubles, Set qmSelfCheckStrips,
			Set qmSelfCheckCartonses, Set sysFavorites) {
		this.msgInfosForApproval = msgInfosForApproval;
		this.msgInfosForInitiator = msgInfosForInitiator;
		this.msgInfosForIssuer = msgInfosForIssuer;
		this.createDatetime = createDatetime;
		this.modifyDatetime = modifyDatetime;
		this.name = name;
		this.pwd = pwd;
		this.eno = eno;
		this.loginName = loginName;
		this.gender = gender;
		this.phone = phone;
		this.tel = tel;
		this.fax = fax;
		this.email = email;
		this.enable = enable;
		this.del = del;
		this.securityLevel = securityLevel;
		this.schShiftExchgsForToUser = schShiftExchgsForToUser;
		this.sysUserRoles = sysUserRoles;
		this.msgQmWarns = msgQmWarns;
		this.schShiftExchgsForHoUser = schShiftExchgsForHoUser;
		this.qmOutwards = qmOutwards;
		this.eqmLubricantMaintains = eqmLubricantMaintains;
		this.qmSelfCheckFilters = qmSelfCheckFilters;
		this.sysUserOrganizations = sysUserOrganizations;
		this.qmSelfCheckCigarettes = qmSelfCheckCigarettes;
		this.qmSelfCheckPackets = qmSelfCheckPackets;
		this.eqmTroubles = eqmTroubles;
		this.qmSelfCheckStrips = qmSelfCheckStrips;
		this.qmSelfCheckCartonses = qmSelfCheckCartonses;
		this.sysFavorites = sysFavorites;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEno() {
		return this.eno;
	}

	public void setEno(String eno) {
		this.eno = eno;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getEnable() {
		return this.enable;
	}

	public void setEnable(Long enable) {
		this.enable = enable;
	}

	public Long getDel() {
		return this.del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

	public Long getSecurityLevel() {
		return this.securityLevel;
	}

	public void setSecurityLevel(Long securityLevel) {
		this.securityLevel = securityLevel;
	}

	public Set getSchShiftExchgsForToUser() {
		return this.schShiftExchgsForToUser;
	}

	public void setSchShiftExchgsForToUser(Set schShiftExchgsForToUser) {
		this.schShiftExchgsForToUser = schShiftExchgsForToUser;
	}

	public Set getSysUserRoles() {
		return this.sysUserRoles;
	}

	public void setSysUserRoles(Set sysUserRoles) {
		this.sysUserRoles = sysUserRoles;
	}

	public Set getMsgQmWarns() {
		return this.msgQmWarns;
	}

	public void setMsgQmWarns(Set msgQmWarns) {
		this.msgQmWarns = msgQmWarns;
	}

	public Set getSchShiftExchgsForHoUser() {
		return this.schShiftExchgsForHoUser;
	}

	public void setSchShiftExchgsForHoUser(Set schShiftExchgsForHoUser) {
		this.schShiftExchgsForHoUser = schShiftExchgsForHoUser;
	}

	public Set getQmOutwards() {
		return this.qmOutwards;
	}

	public void setQmOutwards(Set qmOutwards) {
		this.qmOutwards = qmOutwards;
	}

	public Set getEqmLubricantMaintains() {
		return this.eqmLubricantMaintains;
	}

	public void setEqmLubricantMaintains(Set eqmLubricantMaintains) {
		this.eqmLubricantMaintains = eqmLubricantMaintains;
	}

	public Set getQmSelfCheckFilters() {
		return this.qmSelfCheckFilters;
	}

	public void setQmSelfCheckFilters(Set qmSelfCheckFilters) {
		this.qmSelfCheckFilters = qmSelfCheckFilters;
	}

	public Set getSysUserOrganizations() {
		return this.sysUserOrganizations;
	}

	public void setSysUserOrganizations(Set sysUserOrganizations) {
		this.sysUserOrganizations = sysUserOrganizations;
	}

	public Set getQmSelfCheckCigarettes() {
		return this.qmSelfCheckCigarettes;
	}

	public void setQmSelfCheckCigarettes(Set qmSelfCheckCigarettes) {
		this.qmSelfCheckCigarettes = qmSelfCheckCigarettes;
	}

	public Set getQmSelfCheckPackets() {
		return this.qmSelfCheckPackets;
	}

	public void setQmSelfCheckPackets(Set qmSelfCheckPackets) {
		this.qmSelfCheckPackets = qmSelfCheckPackets;
	}

	public Set getEqmTroubles() {
		return this.eqmTroubles;
	}

	public void setEqmTroubles(Set eqmTroubles) {
		this.eqmTroubles = eqmTroubles;
	}

	public Set getQmSelfCheckStrips() {
		return this.qmSelfCheckStrips;
	}

	public void setQmSelfCheckStrips(Set qmSelfCheckStrips) {
		this.qmSelfCheckStrips = qmSelfCheckStrips;
	}

	public Set getQmSelfCheckCartonses() {
		return this.qmSelfCheckCartonses;
	}

	public void setQmSelfCheckCartonses(Set qmSelfCheckCartonses) {
		this.qmSelfCheckCartonses = qmSelfCheckCartonses;
	}

	public Set getSysFavorites() {
		return this.sysFavorites;
	}

	public void setSysFavorites(Set sysFavorites) {
		this.sysFavorites = sysFavorites;
	}

	public Set getMsgInfosForApproval() {
		return this.msgInfosForApproval;
	}

	public void setMsgInfosForApproval(Set msgInfosForApproval) {
		this.msgInfosForApproval = msgInfosForApproval;
	}

	public Set getMsgInfosForInitiator() {
		return this.msgInfosForInitiator;
	}

	public void setMsgInfosForInitiator(Set msgInfosForInitiator) {
		this.msgInfosForInitiator = msgInfosForInitiator;
	}

	public Set getMsgInfosForIssuer() {
		return this.msgInfosForIssuer;
	}

	public void setMsgInfosForIssuer(Set msgInfosForIssuer) {
		this.msgInfosForIssuer = msgInfosForIssuer;
	}
}