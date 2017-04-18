package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

/**
 * MsgQmWarn entity. @author MyEclipse Persistence Tools
 */

public class MsgQmWarn implements java.io.Serializable {

	// Fields

	private String id;
	private SysUser sysUser;
	private SchWorkorder schWorkorder;
	private Double qi;
	private Double val;
	private String item;
	private Date time;
	private Long sts;
	private String content;
	private Long del;

	// Constructors

	/** default constructor */
	public MsgQmWarn() {
	}

	/** full constructor */
	public MsgQmWarn(SysUser sysUser, SchWorkorder schWorkorder, Double qi,
			Double val, String item, Date time, Long sts, String content,
			Long del) {
		this.sysUser = sysUser;
		this.schWorkorder = schWorkorder;
		this.qi = qi;
		this.val = val;
		this.item = item;
		this.time = time;
		this.sts = sts;
		this.content = content;
		this.del = del;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public SchWorkorder getSchWorkorder() {
		return this.schWorkorder;
	}

	public void setSchWorkorder(SchWorkorder schWorkorder) {
		this.schWorkorder = schWorkorder;
	}

	public Double getQi() {
		return this.qi;
	}

	public void setQi(Double qi) {
		this.qi = qi;
	}

	public Double getVal() {
		return this.val;
	}

	public void setVal(Double val) {
		this.val = val;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getSts() {
		return this.sts;
	}

	public void setSts(Long sts) {
		this.sts = sts;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getDel() {
		return this.del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

}