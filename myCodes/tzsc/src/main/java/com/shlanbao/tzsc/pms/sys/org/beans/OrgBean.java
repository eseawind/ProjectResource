package com.shlanbao.tzsc.pms.sys.org.beans;

/**
 * 组织机构
 * @author Leejean
 * @create 2015年1月22日下午3:45:40
 */
public class OrgBean {
	private String id;
	private String name;
	private Long seq;
	private String remark;
	private String iconCls;
	private Long enable;
	private String pid;
	private String pname;
	private Long del;
	private boolean checked;//是否选中
	
	public OrgBean() {
		// TODO Auto-generated constructor stub
	}
	
	public OrgBean(Long enable, Long del) {
		super();
		this.enable = enable;
		this.del = del;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Long getEnable() {
		return enable;
	}
	public void setEnable(Long enable) {
		this.enable = enable;
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
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
	}
}
