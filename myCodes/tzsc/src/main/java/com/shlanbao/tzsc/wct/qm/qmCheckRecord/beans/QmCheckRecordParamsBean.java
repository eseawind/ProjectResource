package com.shlanbao.tzsc.wct.qm.qmCheckRecord.beans;

/**
 * wct质检信息bean
 * @author shisihai
 *
 */
public class QmCheckRecordParamsBean  {
	
	private String id;
	private String qId;
	private String paType;//检测项所属类型（箱/条/盒/支）
	private String paTypeCode;//检测项类型（A/B/C/XC/其他物理指标）
	private String pgDes;//检测标题(对应pg)
	private String paDes;//检测项（对应pa）
	private String valueF;//参数结果值（对应value_f）
	private Double scDeduct;//扣分（对应sc_deduct）
	private String unit;//单位(对应unit)
	private String cMean;//均值项（对应cell中的value）
	private String cSd;//标偏项
	private String cMin;//最小值项
	private String cMax;//最大值相
	private String cCv;//变异系数项
	private String cResult;//不合格支数项
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getqId() {
		return qId;
	}
	public void setqId(String qId) {
		this.qId = qId;
	}
	public String getPaType() {
		return paType;
	}
	public void setPaType(String paType) {
		this.paType = paType;
	}
	public String getPaTypeCode() {
		return paTypeCode;
	}
	public void setPaTypeCode(String paTypeCode) {
		this.paTypeCode = paTypeCode;
	}
	public String getPgDes() {
		return pgDes;
	}
	public void setPgDes(String pgDes) {
		this.pgDes = pgDes;
	}
	public String getPaDes() {
		return paDes;
	}
	public void setPaDes(String paDes) {
		this.paDes = paDes;
	}
	public String getValueF() {
		return valueF;
	}
	public void setValueF(String valueF) {
		this.valueF = valueF;
	}
	public Double getScDeduct() {
		return scDeduct;
	}
	public void setScDeduct(Double scDeduct) {
		this.scDeduct = scDeduct;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getcMean() {
		return cMean;
	}
	public void setcMean(String cMean) {
		this.cMean = cMean;
	}
	public String getcSd() {
		return cSd;
	}
	public void setcSd(String cSd) {
		this.cSd = cSd;
	}
	public String getcMin() {
		return cMin;
	}
	public void setcMin(String cMin) {
		this.cMin = cMin;
	}
	public String getcMax() {
		return cMax;
	}
	public void setcMax(String cMax) {
		this.cMax = cMax;
	}
	public String getcCv() {
		return cCv;
	}
	public void setcCv(String cCv) {
		this.cCv = cCv;
	}
	public String getcResult() {
		return cResult;
	}
	public void setcResult(String cResult) {
		this.cResult = cResult;
	}
	
	

}