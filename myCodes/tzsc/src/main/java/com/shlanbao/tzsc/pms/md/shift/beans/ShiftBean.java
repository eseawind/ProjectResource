package com.shlanbao.tzsc.pms.md.shift.beans;


/**
 * 班次 ：早中晚班
 * <li>@author Leejean
 * <li>@create 2014-7-5下午10:03:12
 */
public class ShiftBean {
	private String id;
    private String code;
    private String name;
    private Long seq;
    private Long enable;
    private Long del;
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
	public ShiftBean() {
		// TODO Auto-generated constructor stub
	}
	public ShiftBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	
	
}
