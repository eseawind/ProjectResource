package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

/**
 * QmSelfCheckPacket entity. @author MyEclipse Persistence Tools
 */

public class QmSelfCheckPacket implements java.io.Serializable {

	// Fields

	private String id;
	private SysUser sysUser;
	private SchWorkorder schWorkorder;
	private String checkFlag;
	private Date time;
	private String cz;
	private String qz;
	private String yzqx;
	private String ncz;
	private String nkz;
	private String sbz;
	private String kb;
	private String cy;
	private String zj;
	private String nb;
	private String tmz;
	private String lx;
	private Long del;

	// Constructors

	/** default constructor */
	public QmSelfCheckPacket() {
	}

	/** full constructor */
	public QmSelfCheckPacket(SysUser sysUser, SchWorkorder schWorkorder,
			String checkFlag, Date time, String cz, String qz,
			String yzqx, String ncz, String nkz, String sbz, String kb,
			String cy, String zj, String nb, String tmz, String lx, Long del) {
		this.sysUser = sysUser;
		this.schWorkorder = schWorkorder;
		this.checkFlag = checkFlag;
		this.time = time;
		this.cz = cz;
		this.qz = qz;
		this.yzqx = yzqx;
		this.ncz = ncz;
		this.nkz = nkz;
		this.sbz = sbz;
		this.kb = kb;
		this.cy = cy;
		this.zj = zj;
		this.nb = nb;
		this.tmz = tmz;
		this.lx = lx;
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

	public String getCheckFlag() {
		return this.checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getCz() {
		return this.cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getQz() {
		return this.qz;
	}

	public void setQz(String qz) {
		this.qz = qz;
	}

	public String getYzqx() {
		return this.yzqx;
	}

	public void setYzqx(String yzqx) {
		this.yzqx = yzqx;
	}

	public String getNcz() {
		return this.ncz;
	}

	public void setNcz(String ncz) {
		this.ncz = ncz;
	}

	public String getNkz() {
		return this.nkz;
	}

	public void setNkz(String nkz) {
		this.nkz = nkz;
	}

	public String getSbz() {
		return this.sbz;
	}

	public void setSbz(String sbz) {
		this.sbz = sbz;
	}

	public String getKb() {
		return this.kb;
	}

	public void setKb(String kb) {
		this.kb = kb;
	}

	public String getCy() {
		return this.cy;
	}

	public void setCy(String cy) {
		this.cy = cy;
	}

	public String getZj() {
		return this.zj;
	}

	public void setZj(String zj) {
		this.zj = zj;
	}

	public String getNb() {
		return this.nb;
	}

	public void setNb(String nb) {
		this.nb = nb;
	}

	public String getTmz() {
		return this.tmz;
	}

	public void setTmz(String tmz) {
		this.tmz = tmz;
	}

	public String getLx() {
		return this.lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public Long getDel() {
		return this.del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

}