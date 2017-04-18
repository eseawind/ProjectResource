package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SchShiftExchg entity. @author MyEclipse Persistence Tools
 */

public class SchShiftExchg implements java.io.Serializable {

	// Fields

	private String id;
	private SysUser sysUserByHoUser;
	private MdTeam mdTeamByToTeam;
	private MdShift mdShiftByToShift;
	private MdMat mdMatByToMat;
	private MdShift mdShiftByHoShift;
	private MdMat mdMatByHoMat;
	private SysUser sysUserByToUser;
	private Date date;
	private MdTeam mdTeamByHoTeam;
	private Date hoTime;
	private Date toTime;
	private SchWorkorder hoOrder;
	private SchWorkorder toOrder;
	private Long del;
	private Long type;
	private Set schShiftExchgDets = new HashSet(0);

	// Constructors

	/** default constructor */
	public SchShiftExchg() {
	}

	/** full constructor */


	// Property accessors

	public String getId() {
		return this.id;
	}

	public SchShiftExchg(String id, SysUser sysUserByHoUser,
			MdTeam mdTeamByToTeam, MdShift mdShiftByToShift,
			MdMat mdMatByToMat, MdShift mdShiftByHoShift, MdMat mdMatByHoMat,
			SysUser sysUserByToUser, Date date, MdTeam mdTeamByHoTeam,
			Date hoTime, Date toTime, SchWorkorder hoOrder,
			SchWorkorder toOrder, Long del, Long type, Set schShiftExchgDets) {
		super();
		this.id = id;
		this.sysUserByHoUser = sysUserByHoUser;
		this.mdTeamByToTeam = mdTeamByToTeam;
		this.mdShiftByToShift = mdShiftByToShift;
		this.mdMatByToMat = mdMatByToMat;
		this.mdShiftByHoShift = mdShiftByHoShift;
		this.mdMatByHoMat = mdMatByHoMat;
		this.sysUserByToUser = sysUserByToUser;
		this.date = date;
		this.mdTeamByHoTeam = mdTeamByHoTeam;
		this.hoTime = hoTime;
		this.toTime = toTime;
		this.hoOrder = hoOrder;
		this.toOrder = toOrder;
		this.del = del;
		this.type = type;
		this.schShiftExchgDets = schShiftExchgDets;
	}

	public SchWorkorder getHoOrder() {
		return hoOrder;
	}

	public void setHoOrder(SchWorkorder hoOrder) {
		this.hoOrder = hoOrder;
	}

	public SchWorkorder getToOrder() {
		return toOrder;
	}

	public void setToOrder(SchWorkorder toOrder) {
		this.toOrder = toOrder;
	}

	public SchShiftExchg(String id) {
		super();
		this.id = id;
	}

	

	public void setId(String id) {
		this.id = id;
	}

	public SysUser getSysUserByHoUser() {
		return this.sysUserByHoUser;
	}

	public void setSysUserByHoUser(SysUser sysUserByHoUser) {
		this.sysUserByHoUser = sysUserByHoUser;
	}

	

	public MdShift getMdShiftByToShift() {
		return this.mdShiftByToShift;
	}

	public void setMdShiftByToShift(MdShift mdShiftByToShift) {
		this.mdShiftByToShift = mdShiftByToShift;
	}

	public MdMat getMdMatByToMat() {
		return this.mdMatByToMat;
	}

	public void setMdMatByToMat(MdMat mdMatByToMat) {
		this.mdMatByToMat = mdMatByToMat;
	}

	public MdShift getMdShiftByHoShift() {
		return this.mdShiftByHoShift;
	}

	public void setMdShiftByHoShift(MdShift mdShiftByHoShift) {
		this.mdShiftByHoShift = mdShiftByHoShift;
	}

	public MdMat getMdMatByHoMat() {
		return this.mdMatByHoMat;
	}

	public void setMdMatByHoMat(MdMat mdMatByHoMat) {
		this.mdMatByHoMat = mdMatByHoMat;
	}

	public SysUser getSysUserByToUser() {
		return this.sysUserByToUser;
	}

	public void setSysUserByToUser(SysUser sysUserByToUser) {
		this.sysUserByToUser = sysUserByToUser;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

	public Date getHoTime() {
		return this.hoTime;
	}

	public void setHoTime(Date hoTime) {
		this.hoTime = hoTime;
	}

	public Date getToTime() {
		return this.toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	

	

	public Long getDel() {
		return this.del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	
	
	public MdTeam getMdTeamByToTeam() {
		return mdTeamByToTeam;
	}

	public void setMdTeamByToTeam(MdTeam mdTeamByToTeam) {
		this.mdTeamByToTeam = mdTeamByToTeam;
	}

	public MdTeam getMdTeamByHoTeam() {
		return mdTeamByHoTeam;
	}

	public void setMdTeamByHoTeam(MdTeam mdTeamByHoTeam) {
		this.mdTeamByHoTeam = mdTeamByHoTeam;
	}

	public Set getSchShiftExchgDets() {
		return this.schShiftExchgDets;
	}

	public void setSchShiftExchgDets(Set schShiftExchgDets) {
		this.schShiftExchgDets = schShiftExchgDets;
	}

}