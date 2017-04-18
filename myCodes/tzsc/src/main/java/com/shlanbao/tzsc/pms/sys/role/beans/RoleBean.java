package com.shlanbao.tzsc.pms.sys.role.beans;


/**
 * 角色bean
 * <li>@author Leejean
 * <li>@create 2014-7-11上午11:08:26
 */
public class RoleBean {
	private String id;
	private String pid;
	private String pname;
	private String name;
	private String remark;
	private Long seq;
	private Long enable;
	private Long del;
	private String iconCls;
	private boolean checked;
	public RoleBean() {
		// TODO Auto-generated constructor stub
	}
	public RoleBean(String pid, String name, String remark, Long seq,
			Long enable, Long del, String iconCls) {
		super();
		this.pid = pid;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.enable = enable;
		this.del = del;
		this.iconCls = iconCls;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
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
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	@Override
	public String toString() {
		return "RoleBean [id=" + id + ", pid=" + pid + ", pname=" + pname
				+ ", name=" + name + ", remark=" + remark + ", seq=" + seq
				+ ", enable=" + enable + ", del=" + del + ", iconCls="
				+ iconCls + ", checked=" + checked + "]";
	}
	
}
