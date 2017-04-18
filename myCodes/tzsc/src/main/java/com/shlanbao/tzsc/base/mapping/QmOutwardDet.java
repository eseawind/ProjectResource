package com.shlanbao.tzsc.base.mapping;

/**
 * QmOutwardDet entity. @author MyEclipse Persistence Tools
 */

public class QmOutwardDet implements java.io.Serializable {

	// Fields

	private String id;
	private QmOutwardDefectItem qmOutwardDefectItem;
	private QmOutward qmOutward;
	private Long val;

	// Constructors

	/** default constructor */
	public QmOutwardDet() {
	}

	/** full constructor */
	public QmOutwardDet(QmOutwardDefectItem qmOutwardDefectItem,
			QmOutward qmOutward, Long val) {
		this.qmOutwardDefectItem = qmOutwardDefectItem;
		this.qmOutward = qmOutward;
		this.val = val;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public QmOutwardDefectItem getQmOutwardDefectItem() {
		return this.qmOutwardDefectItem;
	}

	public void setQmOutwardDefectItem(QmOutwardDefectItem qmOutwardDefectItem) {
		this.qmOutwardDefectItem = qmOutwardDefectItem;
	}

	public QmOutward getQmOutward() {
		return this.qmOutward;
	}

	public void setQmOutward(QmOutward qmOutward) {
		this.qmOutward = qmOutward;
	}

	public Long getVal() {
		return this.val;
	}

	public void setVal(Long val) {
		this.val = val;
	}

}