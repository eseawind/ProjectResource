package com.shlanbao.tzsc.base.model;

import java.util.List;
import java.util.Map;

import com.shlanbao.tzsc.pms.sys.role.beans.RoleBean;
import com.shlanbao.tzsc.pms.sys.user.beans.UserBean;

/**
 * session信息模型
 * <li>@author Leejean
 * <li>@create 2014-6-24 下午04:19:23
 */
public class SessionInfo {
	private UserBean user;
	private String ip;// 用户IP
	private boolean islock=false;
	private List<RoleBean> roles;// 用户角色
	private String organizations;// 用户组织机构
	private String resources;// 用户可以访问的资源地址列表
	private Map<String,String> resourcesMap;
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isIslock() {
		return islock;
	}
	public void setIslock(boolean islock) {
		this.islock = islock;
	}
	public List<RoleBean> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleBean> roles) {
		this.roles = roles;
	}
	public String getOrganizations() {
		return organizations;
	}
	public void setOrganizations(String organizations) {
		this.organizations = organizations;
	}
	public String getResources() {
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public Map<String, String> getResourcesMap() {
		return resourcesMap;
	}
	public void setResourcesMap(Map<String, String> resourcesMap) {
		this.resourcesMap = resourcesMap;
	}
	
	
	
}
