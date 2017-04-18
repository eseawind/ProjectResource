package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;

/**
 * MdTeam entity. @author MyEclipse Persistence Tools
 */

public class MdTeam implements java.io.Serializable {

	// Fields

	private String id;
	private String code;
	private String name;
	private Long seq;
	private Long enable;
	private String del;
	private Set schCalendars = new HashSet(0);
	private Set schShiftExchgs = new HashSet(0);
	private Set schWorkorders = new HashSet(0);
	private Set schShiftExchgs_1 = new HashSet(0);

	// Constructors

	/** default constructor */
	public MdTeam() {
	}

	
	
	public MdTeam(String id) {
		super();
		this.id = id;
	}



	/** full constructor */
	public MdTeam(String code, String name, Long seq, Long enable, String del,
			Set schCalendars, Set schShiftExchgs, Set schWorkorders,
			Set schShiftExchgs_1) {
		this.code = code;
		this.name = name;
		this.seq = seq;
		this.enable = enable;
		this.del = del;
		this.schCalendars = schCalendars;
		this.schShiftExchgs = schShiftExchgs;
		this.schWorkorders = schWorkorders;
		this.schShiftExchgs_1 = schShiftExchgs_1;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSeq() {
		return this.seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getEnable() {
		return this.enable;
	}

	public void setEnable(Long enable) {
		this.enable = enable;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public Set getSchCalendars() {
		return this.schCalendars;
	}

	public void setSchCalendars(Set schCalendars) {
		this.schCalendars = schCalendars;
	}

	public Set getSchShiftExchgs() {
		return this.schShiftExchgs;
	}

	public void setSchShiftExchgs(Set schShiftExchgs) {
		this.schShiftExchgs = schShiftExchgs;
	}

	public Set getSchWorkorders() {
		return this.schWorkorders;
	}

	public void setSchWorkorders(Set schWorkorders) {
		this.schWorkorders = schWorkorders;
	}

	public Set getSchShiftExchgs_1() {
		return this.schShiftExchgs_1;
	}

	public void setSchShiftExchgs_1(Set schShiftExchgs_1) {
		this.schShiftExchgs_1 = schShiftExchgs_1;
	}

}