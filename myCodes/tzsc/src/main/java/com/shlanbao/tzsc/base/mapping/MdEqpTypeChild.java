package com.shlanbao.tzsc.base.mapping;

/**
 * MdEqpTypeChild entity. @author MyEclipse Persistence Tools
 */

public class MdEqpTypeChild implements java.io.Serializable {

	// Fields

	private String id;
	private SysEqpCategory sysEqpCategory;
	private MdEqpType mdEqpType;
	private String del;
	private String type;

	// Constructors

	/** default constructor */
	public MdEqpTypeChild() {
	}

	/** full constructor */
	public MdEqpTypeChild(SysEqpCategory sysEqpCategory, MdEqpType mdEqpType,
			String del, String type) {
		this.sysEqpCategory = sysEqpCategory;
		this.mdEqpType = mdEqpType;
		this.del = del;
		this.type = type;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysEqpCategory getSysEqpCategory() {
		return this.sysEqpCategory;
	}

	public void setSysEqpCategory(SysEqpCategory sysEqpCategory) {
		this.sysEqpCategory = sysEqpCategory;
	}

	public MdEqpType getMdEqpType() {
		return this.mdEqpType;
	}

	public void setMdEqpType(MdEqpType mdEqpType) {
		this.mdEqpType = mdEqpType;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}