package com.lanbao.dws.model.wct.qm;
/**
 * 外观检测登陆
 * @author shisihai
 *
 */
public class QMUser {
	private String id;
	private String token;//登陆名
	private String uName;//用户名
	private String rName;//角色
	private boolean flag;//是否成功
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
}
