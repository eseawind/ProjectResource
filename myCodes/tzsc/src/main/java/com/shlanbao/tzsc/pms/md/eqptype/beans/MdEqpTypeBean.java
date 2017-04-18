package com.shlanbao.tzsc.pms.md.eqptype.beans;



public class MdEqpTypeBean {

	private String id;
	private String code;
	private String name;
	private String des;
	private Long enable;
	private String del;
	private String categoryId;
	private String categoryName;
	//private String enableStr;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Long getEnable() {
		return enable;
	}
	public void setEnable(Long enable) {
		this.enable = enable;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	@Override
	public String toString() {
		return "MdEqpTypeBean [id=" + id + ", code=" + code + ", name=" + name
				+ ", des=" + des + ", enable=" + enable + ", del=" + del + "]";
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	/*public String getEnableStr() {
		return enableStr;
	}
	public void setEnableStr(String enableStr) {
		this.enableStr = enableStr;
	}*/
}
