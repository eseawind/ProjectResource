package com.shlanbao.tzsc.pms.cos.param.beans;

public class CosMatParamBean {
	private String id;
	private String mdMatId;
	private String mdMatName;
	private String mdEqpTypeId;
	private String mdEqpName;
	private Double award;//奖罚单价
	private Long del;
	private Double awardCount;
	private String status;//状态  ： 新建 、生效、归档
	private String version;//版本号
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMdMatId() {
		return mdMatId;
	}
	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}
	public String getMdMatName() {
		return mdMatName;
	}
	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}
	public String getMdEqpTypeId() {
		return mdEqpTypeId;
	}
	public void setMdEqpTypeId(String mdEqpTypeId) {
		this.mdEqpTypeId = mdEqpTypeId;
	}
	public String getMdEqpName() {
		return mdEqpName;
	}
	public void setMdEqpName(String mdEqpName) {
		this.mdEqpName = mdEqpName;
	}
	public Double getAward() {
		return award;
	}
	public void setAward(Double award) {
		this.award = award;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
	}
	public Double getAwardCount() {
		return awardCount;
	}
	public void setAwardCount(Double awardCount) {
		this.awardCount = awardCount;
	}
}
