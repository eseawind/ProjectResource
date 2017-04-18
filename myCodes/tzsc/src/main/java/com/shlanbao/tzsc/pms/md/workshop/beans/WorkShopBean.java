package com.shlanbao.tzsc.pms.md.workshop.beans;
/**
 *  车间  ： 卷包车间 成型车间
 * <li>@author Leejean
 * <li>@create 2014-7-5下午10:04:20
 */
public class WorkShopBean {
	private String id;
    private String code;
    private String name;
    private Long seq;
    private Long enable;
    private String del;
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
	public WorkShopBean() {
		// TODO Auto-generated constructor stub
	}
	public WorkShopBean(String id, String name) {
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
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	
}
