package com.shlanbao.tzsc.pms.sys.resource.beans;

import java.util.List;


public class ResourceBean {

	private String id;
	private Long seq;
	private String text;
	private String iconCls;
	private String url;
	private Long typ;
	private Long enable;
	private Long del;
	private Long securityLevel;
	private String remark;
	private String pid;
	private String pname;
	private boolean checked;
	private List<ResourceBean> functions;
	public ResourceBean() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getTyp() {
		return typ;
	}
	public void setTyp(Long typ) {
		this.typ = typ;
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ResourceBean> getFunctions() {
		return functions;
	}

	public void setFunctions(List<ResourceBean> functions) {
		this.functions = functions;
	}
	

}
