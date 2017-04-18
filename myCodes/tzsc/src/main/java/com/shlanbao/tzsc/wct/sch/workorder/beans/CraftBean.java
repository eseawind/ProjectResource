package com.shlanbao.tzsc.wct.sch.workorder.beans;


/**
 * 工艺参数实体
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class CraftBean {

	private String id;
	private String paramId;//参数ID
	private Long pcp;
	private Long qc;
	//字段含义对照数据库设计文档
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public Long getPcp() {
		return pcp;
	}
	public void setPcp(Long pcp) {
		this.pcp = pcp;
	}
	public Long getQc() {
		return qc;
	}
	public void setQc(Long qc) {
		this.qc = qc;
	}
	public Double getPval() {
		return pval;
	}
	public void setPval(Double pval) {
		this.pval = pval;
	}
	public Double getZval() {
		return zval;
	}
	public void setZval(Double zval) {
		this.zval = zval;
	}
	public Double getCpk() {
		return cpk;
	}
	public void setCpk(Double cpk) {
		this.cpk = cpk;
	}
	public Double getCp() {
		return cp;
	}
	public void setCp(Double cp) {
		this.cp = cp;
	}
	public Double getPp() {
		return pp;
	}
	public void setPp(Double pp) {
		this.pp = pp;
	}
	public Double getPpk() {
		return ppk;
	}
	public void setPpk(Double ppk) {
		this.ppk = ppk;
	}
	public Double getPpm() {
		return ppm;
	}
	public void setPpm(Double ppm) {
		this.ppm = ppm;
	}
	public Double getStd() {
		return std;
	}
	public void setStd(Double std) {
		this.std = std;
	}
	public Double getUval() {
		return uval;
	}
	public void setUval(Double uval) {
		this.uval = uval;
	}
	public Double getLval() {
		return lval;
	}
	public void setLval(Double lval) {
		this.lval = lval;
	}
	public Double getCuval() {
		return cuval;
	}
	public void setCuval(Double cuval) {
		this.cuval = cuval;
	}
	public Double getClval() {
		return clval;
	}
	public void setClval(Double clval) {
		this.clval = clval;
	}
	public Double getWuval() {
		return wuval;
	}
	public void setWuval(Double wuval) {
		this.wuval = wuval;
	}
	public Double getWlval() {
		return wlval;
	}
	public void setWlval(Double wlval) {
		this.wlval = wlval;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Long getDtm() {
		return dtm;
	}
	public void setDtm(Long dtm) {
		this.dtm = dtm;
	}
	public Double getDtmuval() {
		return dtmuval;
	}
	public void setDtmuval(Double dtmuval) {
		this.dtmuval = dtmuval;
	}
	public Double getDtmlval() {
		return dtmlval;
	}
	public void setDtmlval(Double dtmlval) {
		this.dtmlval = dtmlval;
	}
	public Long getParamType() {
		return paramType;
	}
	public void setParamType(Long paramType) {
		this.paramType = paramType;
	}
}
