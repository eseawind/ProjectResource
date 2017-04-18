package com.shlanbao.tzsc.base.mapping;

/**
 * SchWorkorderCraft entity. @author MyEclipse Persistence Tools
 */

public class SchWorkorderCraft implements java.io.Serializable {

	// Fields

	private String id;
	private SchWorkorder schWorkorder;
	private String paramId;
	private Long pcp;
	private Long qc;
	private Double pval;
	private Double zval;
	private Double cpk;
	private Double cp;
	private Double pp;
	private Double ppk;
	private Double ppm;
	private Double std;
	private Double uval;
	private Double lval;
	private Double cuval;
	private Double clval;
	private Double wuval;
	private Double wlval;
	private String unit;
	private Long dtm;
	private Double dtmuval;
	private Double dtmlval;
	private Long paramType;

	// Constructors

	/** default constructor */
	public SchWorkorderCraft() {
	}

	/** full constructor */
	public SchWorkorderCraft(SchWorkorder schWorkorder, String paramId,
			Long pcp, Long qc, Double pval, Double zval, Double cpk, Double cp,
			Double pp, Double ppk, Double ppm, Double std, Double uval,
			Double lval, Double cuval, Double clval, Double wuval,
			Double wlval, String unit, Long dtm, Double dtmuval,
			Double dtmlval, Long paramType) {
		this.schWorkorder = schWorkorder;
		this.paramId = paramId;
		this.pcp = pcp;
		this.qc = qc;
		this.pval = pval;
		this.zval = zval;
		this.cpk = cpk;
		this.cp = cp;
		this.pp = pp;
		this.ppk = ppk;
		this.ppm = ppm;
		this.std = std;
		this.uval = uval;
		this.lval = lval;
		this.cuval = cuval;
		this.clval = clval;
		this.wuval = wuval;
		this.wlval = wlval;
		this.unit = unit;
		this.dtm = dtm;
		this.dtmuval = dtmuval;
		this.dtmlval = dtmlval;
		this.paramType = paramType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SchWorkorder getSchWorkorder() {
		return this.schWorkorder;
	}

	public void setSchWorkorder(SchWorkorder schWorkorder) {
		this.schWorkorder = schWorkorder;
	}

	public String getParamId() {
		return this.paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public Long getPcp() {
		return this.pcp;
	}

	public void setPcp(Long pcp) {
		this.pcp = pcp;
	}

	public Long getQc() {
		return this.qc;
	}

	public void setQc(Long qc) {
		this.qc = qc;
	}

	public Double getPval() {
		return this.pval;
	}

	public void setPval(Double pval) {
		this.pval = pval;
	}

	public Double getZval() {
		return this.zval;
	}

	public void setZval(Double zval) {
		this.zval = zval;
	}

	public Double getCpk() {
		return this.cpk;
	}

	public void setCpk(Double cpk) {
		this.cpk = cpk;
	}

	public Double getCp() {
		return this.cp;
	}

	public void setCp(Double cp) {
		this.cp = cp;
	}

	public Double getPp() {
		return this.pp;
	}

	public void setPp(Double pp) {
		this.pp = pp;
	}

	public Double getPpk() {
		return this.ppk;
	}

	public void setPpk(Double ppk) {
		this.ppk = ppk;
	}

	public Double getPpm() {
		return this.ppm;
	}

	public void setPpm(Double ppm) {
		this.ppm = ppm;
	}

	public Double getStd() {
		return this.std;
	}

	public void setStd(Double std) {
		this.std = std;
	}

	public Double getUval() {
		return this.uval;
	}

	public void setUval(Double uval) {
		this.uval = uval;
	}

	public Double getLval() {
		return this.lval;
	}

	public void setLval(Double lval) {
		this.lval = lval;
	}

	public Double getCuval() {
		return this.cuval;
	}

	public void setCuval(Double cuval) {
		this.cuval = cuval;
	}

	public Double getClval() {
		return this.clval;
	}

	public void setClval(Double clval) {
		this.clval = clval;
	}

	public Double getWuval() {
		return this.wuval;
	}

	public void setWuval(Double wuval) {
		this.wuval = wuval;
	}

	public Double getWlval() {
		return this.wlval;
	}

	public void setWlval(Double wlval) {
		this.wlval = wlval;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getDtm() {
		return this.dtm;
	}

	public void setDtm(Long dtm) {
		this.dtm = dtm;
	}

	public Double getDtmuval() {
		return this.dtmuval;
	}

	public void setDtmuval(Double dtmuval) {
		this.dtmuval = dtmuval;
	}

	public Double getDtmlval() {
		return this.dtmlval;
	}

	public void setDtmlval(Double dtmlval) {
		this.dtmlval = dtmlval;
	}

	public Long getParamType() {
		return this.paramType;
	}

	public void setParamType(Long paramType) {
		this.paramType = paramType;
	}

}