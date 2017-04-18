package com.shlanbao.tzsc.base.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * SchStatOutput entity. @author MyEclipse Persistence Tools
 */

public class SchStatOutput implements java.io.Serializable {

	// Fields

	private String id;
	private MdUnit mdUnit;
	private SchWorkorder schWorkorder;
	private Double qty;
	private Double badQty;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date stim;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date etim;
	private Double runTime;
	private Double stopTime;
	private Long stopTimes;
	private MdUnit timeUnit;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date lastRecvTime;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;
	private Long isFeedback;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date feedbackTime;
	private String feedbackUser;
	private Long del;
	private Set schStatInputs = new HashSet(0);
	private Set schStatFaults = new HashSet(0);
	private Double DACqty;//数采站采集的真实数据
	// Constructors

	/** default constructor */
	public SchStatOutput() {
	}

	
	public SchStatOutput(String id) {
		super();
		this.id = id;
	}



	/** full constructor */
	public SchStatOutput(MdUnit mdUnit, SchWorkorder schWorkorder, Double qty,
			Double badQty, Date stim, Date etim, Double runTime,
			Double stopTime, Long stopTimes, MdUnit timeUnit,
			Date lastRecvTime, Date lastUpdateTime, Long isFeedback,
			Date feedbackTime, String feedbackUser, Long del,
			Set schStatInputs, Set schStatFaults) {
		this.mdUnit = mdUnit;
		this.schWorkorder = schWorkorder;
		this.qty = qty;
		this.badQty = badQty;
		this.stim = stim;
		this.etim = etim;
		this.runTime = runTime;
		this.stopTime = stopTime;
		this.stopTimes = stopTimes;
		this.timeUnit = timeUnit;
		this.lastRecvTime = lastRecvTime;
		this.lastUpdateTime = lastUpdateTime;
		this.isFeedback = isFeedback;
		this.feedbackTime = feedbackTime;
		this.feedbackUser = feedbackUser;
		this.del = del;
		this.schStatInputs = schStatInputs;
		this.schStatFaults = schStatFaults;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public Double getDACqty() {
		return DACqty;
	}


	public void setDACqty(Double dACqty) {
		DACqty = dACqty;
	}


	public void setId(String id) {
		this.id = id;
	}

	public MdUnit getMdUnit() {
		return this.mdUnit;
	}

	public void setMdUnit(MdUnit mdUnit) {
		this.mdUnit = mdUnit;
	}

	public SchWorkorder getSchWorkorder() {
		return this.schWorkorder;
	}

	public void setSchWorkorder(SchWorkorder schWorkorder) {
		this.schWorkorder = schWorkorder;
	}

	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getBadQty() {
		return this.badQty;
	}

	public void setBadQty(Double badQty) {
		this.badQty = badQty;
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

	public Double getRunTime() {
		return this.runTime;
	}

	public void setRunTime(Double runTime) {
		this.runTime = runTime;
	}

	public Double getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(Double stopTime) {
		this.stopTime = stopTime;
	}

	public Long getStopTimes() {
		return this.stopTimes;
	}

	public void setStopTimes(Long stopTimes) {
		this.stopTimes = stopTimes;
	}

	public MdUnit getTimeUnit() {
		return this.timeUnit;
	}

	public void setTimeUnit(MdUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Date getLastRecvTime() {
		return this.lastRecvTime;
	}

	public void setLastRecvTime(Date lastRecvTime) {
		this.lastRecvTime = lastRecvTime;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getIsFeedback() {
		return this.isFeedback;
	}

	public void setIsFeedback(Long isFeedback) {
		this.isFeedback = isFeedback;
	}

	public Date getFeedbackTime() {
		return this.feedbackTime;
	}

	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getFeedbackUser() {
		return this.feedbackUser;
	}

	public void setFeedbackUser(String feedbackUser) {
		this.feedbackUser = feedbackUser;
	}

	public Long getDel() {
		return this.del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

	public Set getSchStatInputs() {
		return this.schStatInputs;
	}

	public void setSchStatInputs(Set schStatInputs) {
		this.schStatInputs = schStatInputs;
	}

	public Set getSchStatFaults() {
		return this.schStatFaults;
	}

	public void setSchStatFaults(Set schStatFaults) {
		this.schStatFaults = schStatFaults;
	}

}