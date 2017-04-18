package com.shlanbao.tzsc.pms.md.team.beans;
/**
 *  班组:甲乙丙丁
 * <li>@author Leejean
 * <li>@create 2014-7-5下午10:04:01
 */
public class TeamBean {
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
	public TeamBean() {
		// TODO Auto-generated constructor stub
	}
	public TeamBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public TeamBean(String id, String code, String name, Long seq, Long enable,
			String del) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.seq = seq;
		this.enable = enable;
		this.del = del;
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
