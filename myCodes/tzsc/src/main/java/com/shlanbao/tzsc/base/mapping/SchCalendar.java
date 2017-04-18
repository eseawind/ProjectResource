package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

/**
 * SchCalendar entity. @author MyEclipse Persistence Tools
 */

public class SchCalendar implements java.io.Serializable {

	// Fields

	private String id;
	private MdTeam mdTeam;
	private MdShift mdShift;
	private MdWorkshop mdWorkshop;
	private Date date;
	private Date stim;
	private Date etim;
	private Long del;

	// Constructors

	/** default constructor */
	public SchCalendar() {
	}

	/** full constructor */
	public SchCalendar(MdTeam mdTeam, MdShift mdShift, MdWorkshop mdWorkshop,
			Date date, Date stim, Date etim, Long del) {
		this.mdTeam = mdTeam;
		this.mdShift = mdShift;
		this.mdWorkshop = mdWorkshop;
		this.date = date;
		this.stim = stim;
		this.etim = etim;
		this.del = del;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdTeam getMdTeam() {
		return this.mdTeam;
	}

	public void setMdTeam(MdTeam mdTeam) {
		this.mdTeam = mdTeam;
	}

	public MdShift getMdShift() {
		return this.mdShift;
	}

	public void setMdShift(MdShift mdShift) {
		this.mdShift = mdShift;
	}

	public MdWorkshop getMdWorkshop() {
		return this.mdWorkshop;
	}

	public void setMdWorkshop(MdWorkshop mdWorkshop) {
		this.mdWorkshop = mdWorkshop;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStim() {
		return this.stim;
	}

	public void setStim(Date stim) {
		this.stim = stim;
	}

	public Date getEtim() {
		return this.etim;
	}

	public void setEtim(Date etim) {
		this.etim = etim;
	}

	public Long getDel() {
		return this.del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

}