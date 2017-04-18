package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

/**
 * QmSelfCheckFilter entity. @author MyEclipse Persistence Tools
 */

public class QmSelfCheckFilter implements java.io.Serializable {

	// Fields

	private String id;
	private SysUser sysUser;
	private SchWorkorder schWorkorder;
	private String checkFlag;
	private Date time;
	private String zl;
	private String cd;
	private String yd;
	private String bk;
	private String st;
	private String qk;
	private String zjx;
	private String dk;
	private String qt;
	private Double xzPjz;
	private Double xzBzpc;
	private Double xzCbs;
	private Double yzPzj;
	private Double yzBzpc;
	private Double yzCbs;
	private Double cdPjz;
	private Double cdBzpc;
	private Double cdCbs;
	private Double ydPjz;
	private Double ydBzpc;
	private Double ydCbs;
	private Long del;

	// Constructors

	/** default constructor */
	public QmSelfCheckFilter() {
	}

	/** full constructor */
	public QmSelfCheckFilter(SysUser sysUser, SchWorkorder schWorkorder,
			String checkFlag, Date time, String zl, String cd, String yd,
			String bk, String st, String qk, String zjx, String dk, String qt,
			Double xzPjz, Double xzBzpc, Double xzCbs, Double yzPzj,
			Double yzBzpc, Double yzCbs, Double cdPjz, Double cdBzpc,
			Double cdCbs, Double ydPjz, Double ydBzpc, Double ydCbs, Long del) {
		this.sysUser = sysUser;
		this.schWorkorder = schWorkorder;
		this.checkFlag = checkFlag;
		this.time = time;
		this.zl = zl;
		this.cd = cd;
		this.yd = yd;
		this.bk = bk;
		this.st = st;
		this.qk = qk;
		this.zjx = zjx;
		this.dk = dk;
		this.qt = qt;
		this.xzPjz = xzPjz;
		this.xzBzpc = xzBzpc;
		this.xzCbs = xzCbs;
		this.yzPzj = yzPzj;
		this.yzBzpc = yzBzpc;
		this.yzCbs = yzCbs;
		this.cdPjz = cdPjz;
		this.cdBzpc = cdBzpc;
		this.cdCbs = cdCbs;
		this.ydPjz = ydPjz;
		this.ydBzpc = ydBzpc;
		this.ydCbs = ydCbs;
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

	public String getZl() {
		return this.zl;
	}

	public void setZl(String zl) {
		this.zl = zl;
	}

	public String getCd() {
		return this.cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getYd() {
		return this.yd;
	}

	public void setYd(String yd) {
		this.yd = yd;
	}

	public String getBk() {
		return this.bk;
	}

	public void setBk(String bk) {
		this.bk = bk;
	}

	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getQk() {
		return this.qk;
	}

	public void setQk(String qk) {
		this.qk = qk;
	}

	public String getZjx() {
		return this.zjx;
	}

	public void setZjx(String zjx) {
		this.zjx = zjx;
	}

	public String getDk() {
		return this.dk;
	}

	public void setDk(String dk) {
		this.dk = dk;
	}

	public String getQt() {
		return this.qt;
	}

	public void setQt(String qt) {
		this.qt = qt;
	}

	public Double getXzPjz() {
		return this.xzPjz;
	}

	public void setXzPjz(Double xzPjz) {
		this.xzPjz = xzPjz;
	}

	public Double getXzBzpc() {
		return this.xzBzpc;
	}

	public void setXzBzpc(Double xzBzpc) {
		this.xzBzpc = xzBzpc;
	}

	public Double getXzCbs() {
		return this.xzCbs;
	}

	public void setXzCbs(Double xzCbs) {
		this.xzCbs = xzCbs;
	}

	public Double getYzPzj() {
		return this.yzPzj;
	}

	public void setYzPzj(Double yzPzj) {
		this.yzPzj = yzPzj;
	}

	public Double getYzBzpc() {
		return this.yzBzpc;
	}

	public void setYzBzpc(Double yzBzpc) {
		this.yzBzpc = yzBzpc;
	}

	public Double getYzCbs() {
		return this.yzCbs;
	}

	public void setYzCbs(Double yzCbs) {
		this.yzCbs = yzCbs;
	}

	public Double getCdPjz() {
		return this.cdPjz;
	}

	public void setCdPjz(Double cdPjz) {
		this.cdPjz = cdPjz;
	}

	public Double getCdBzpc() {
		return this.cdBzpc;
	}

	public void setCdBzpc(Double cdBzpc) {
		this.cdBzpc = cdBzpc;
	}

	public Double getCdCbs() {
		return this.cdCbs;
	}

	public void setCdCbs(Double cdCbs) {
		this.cdCbs = cdCbs;
	}

	public Double getYdPjz() {
		return this.ydPjz;
	}

	public void setYdPjz(Double ydPjz) {
		this.ydPjz = ydPjz;
	}

	public Double getYdBzpc() {
		return this.ydBzpc;
	}

	public void setYdBzpc(Double ydBzpc) {
		this.ydBzpc = ydBzpc;
	}

	public Double getYdCbs() {
		return this.ydCbs;
	}

	public void setYdCbs(Double ydCbs) {
		this.ydCbs = ydCbs;
	}

	public Long getDel() {
		return this.del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

}