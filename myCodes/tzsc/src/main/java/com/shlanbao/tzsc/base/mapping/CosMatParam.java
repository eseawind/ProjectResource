package com.shlanbao.tzsc.base.mapping;

/**
 * CosMatParam entity. @author MyEclipse Persistence Tools
 */

public class CosMatParam {

	// Fields

	private String id;
	private MdMat mdMat;
	private MdEqpType mdEqpType;
	private Double award;
	private Long del;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	private String status;//状态  ： 新建 、生效、归档
	private String version;//版本号

	// Constructors

	/** default constructor */
	public CosMatParam() {
	}

	/** full constructor */
	public CosMatParam(MdMat mdMat, MdEqpType mdEqpType, Double award,
			Long del, String attr1, String attr2, String attr3, String attr4,String status,String version) {
		this.mdMat = mdMat;
		this.mdEqpType = mdEqpType;
		this.award = award;
		this.del = del;
		this.status=status;
		this.version=version;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.attr4 = attr4;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdMat getMdMat() {
		return mdMat;
	}

	public void setMdMat(MdMat mdMat) {
		this.mdMat = mdMat;
	}

	public MdEqpType getMdEqpType() {
		return this.mdEqpType;
	}

	public void setMdEqpType(MdEqpType mdEqpType) {
		this.mdEqpType = mdEqpType;
	}

	public Double getAward() {
		return this.award;
	}

	public void setAward(Double award) {
		this.award = award;
	}

	public Long getDel() {
		return this.del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

	public String getAttr1() {
		return this.attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return this.attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return this.attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getAttr4() {
		return this.attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

}