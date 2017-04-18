package com.shlanbao.tzsc.pms.sys.user.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 系统用户实体类
 * <li>@author Leejean
 * <li>@create 2014-7-3下午03:51:19
 */
public class UserBean{
	private String id;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createDatetime;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String modifyDatetime;
	private String name;
	private String pwd;
	private String oldpwd;//修改密码时用来保存原密码
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
	

	private boolean isAssigned;//标志字段，用来区分用户是否被分配到某一个机构或某一个角色
	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	public UserBean(String name) {
		super();
		this.name = name;
	}

	public UserBean(String id, String createDatetime, String modifyDatetime,
			String name, String pwd) {
		super();
		this.id = id;
		this.createDatetime = createDatetime;
		this.modifyDatetime = modifyDatetime;
		this.name = name;
		this.pwd = pwd;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getModifyDatetime() {
		return modifyDatetime;
	}
	public void setModifyDatetime(String modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEno() {
		return eno;
	}
	public void setEno(String eno) {
		this.eno = eno;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getEnable() {
		return enable;
	}
	public void setEnable(Long enable) {
		this.enable = enable;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
	}
	public Long getSecurityLevel() {
		return securityLevel;
	}
	
	public void setSecurityLevel(Long securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getOldpwd() {
		return oldpwd;
	}

	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	
	public boolean getIsAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public UserBean(String id, String pwd) {
		super();
		this.id = id;
		this.pwd = pwd;
	}

	public UserBean(String id, String createDatetime, String modifyDatetime,
			String name, String pwd, String oldpwd, String eno,
			String loginName, String gender, String phone, String tel,
			String fax, String email, Long enable, Long del) {
		super();
		this.id = id;
		this.createDatetime = createDatetime;
		this.modifyDatetime = modifyDatetime;
		this.name = name;
		this.pwd = pwd;
		this.oldpwd = oldpwd;
		this.eno = eno;
		this.loginName = loginName;
		this.gender = gender;
		this.phone = phone;
		this.tel = tel;
		this.fax = fax;
		this.email = email;
		this.enable = enable;
		this.del = del;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", createDatetime=" + createDatetime
				+ ", modifyDatetime=" + modifyDatetime + ", name=" + name
				+ ", pwd=" + pwd + ", oldpwd=" + oldpwd + ", eno=" + eno
				+ ", loginName=" + loginName + ", gender=" + gender
				+ ", phone=" + phone + ", tel=" + tel + ", fax=" + fax
				+ ", email=" + email + ", enable=" + enable + ", del=" + del
				+ "]";
	}
	
}
