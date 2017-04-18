package com.lanbao.dws.model.wct.pdStat;

import com.lanbao.dws.common.tools.MathUtil;
import com.lanbao.dws.common.tools.StringUtil;

public class RealTimeFLConsumeShowBean implements Comparable<RealTimeFLConsumeShowBean>{
	private String eqpCode;//设备编号
	private String eqpName;//设备名称
	private String shiftName;//班次
	private String teamName;//班组
	private String matType;//物料类型
	private Double qty;//当前产量
	private String pDate;//生产日期
	private String unitName;//产量单位
	private String matName;//牌号
	//卷烟机
	//滤棒
	private Double lbStd;//标准单耗
	private String lbRealVal;//实际单耗百分比
	private Double lbNowVal;//当前耗用
	private Double lbDH;//实际单耗
	//盘纸
	private Double pzStd;//标准单耗
	private String pzVal;//实际单耗百分比
	private Double pzNowVal;//当前耗用
	private Double pzDH;//实际单耗
	//水松纸
	private Double sszStd;//标准单耗
	private String sszVal;//实际单耗百分比
	private Double sszNowVal;//当前耗用
	private Double sszDH;//实际单耗
	
	//包装机
	
	//小盒纸
	private Double xhzStd;//标准单耗
	private String xhzVal;//实际单耗百分比
	private Double xhzNowVal;//当前耗用
	private Double xxzDH;//实际单耗
	//小盒膜
	private Double xhmStd;//标准单耗
	private String xhmVal;//实际单耗百分比
	private Double xhmNowVal;//当前耗用
	private Double xhmDH;//实际单耗
	//条盒纸
	private Double thzStd;//标准单耗
	private String thzVal;//实际单耗百分比
	private Double thzNowVal;//当前耗用
	private Double thzDH;//实际单耗
	//条盒膜
	private Double thmStd;//标准单耗
	private String thmVal;//实际单耗百分比
	private Double thmNowVal;//当前耗用
	private Double thmDH;//实际单耗
	//内衬纸
	private Double nczStd;//标准单耗
	private String nczVal;//实际单耗百分比
	private Double nczNowVal;//当前耗用
	private Double nczDH;//实际单耗
	
	
	
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Double getLbDH() {
		//滤棒单位为万支
		if(StringUtil.isDouble(getLbNowVal()) && StringUtil.isDouble(getQty()) && getQty()>0){
			lbDH=MathUtil.roundHalfUp(getLbNowVal()*10000/getQty(), 2);
		}
		return lbDH;
	}
	public void setLbDH(Double lbDH) {
		this.lbDH = lbDH;
	}
	public Double getPzDH() {
		if(StringUtil.isDouble(getPzNowVal()) && StringUtil.isDouble(getQty()) && getQty()>0){
			pzDH=MathUtil.roundHalfUp(getPzNowVal()/getQty(), 2);
		}
		return pzDH;
	}
	public void setPzDH(Double pzDH) {
		this.pzDH = pzDH;
	}
	public Double getSszDH() {
		if(StringUtil.isDouble(getSszNowVal()) && StringUtil.isDouble(getQty()) && getQty()>0){
			sszDH=MathUtil.roundHalfUp(getSszNowVal()/getQty(), 2);
		}
		return sszDH;
	}
	public void setSszDH(Double sszDH) {
		this.sszDH = sszDH;
	}
	public Double getXxzDH() {
		if(StringUtil.isDouble(getXhzNowVal()) && StringUtil.isDouble(getQty()) && getQty()>0){
			xxzDH=MathUtil.roundHalfUp(getXhzNowVal()/getQty(), 2);
		}
		return xxzDH;
	}
	public void setXxzDH(Double xxzDH) {
		this.xxzDH = xxzDH;
	}
	public Double getXhmDH() {
		if(StringUtil.isDouble(getXhmNowVal()) && StringUtil.isDouble(getQty()) && getQty()>0){
			xhmDH=MathUtil.roundHalfUp(getXhmNowVal()/getQty(), 2);
		}
		return xhmDH;
	}
	public void setXhmDH(Double xhmDH) {
		this.xhmDH = xhmDH;
	}
	public Double getThzDH() {
		if(StringUtil.isDouble(getThzNowVal()) && StringUtil.isDouble(getQty()) && getQty()>0){
			thzDH=MathUtil.roundHalfUp(getThzNowVal()/getQty(), 2);
		}
		return thzDH;
	}
	public void setThzDH(Double thzDH) {
		this.thzDH = thzDH;
	}
	public Double getThmDH() {
		if(StringUtil.isDouble(getThmNowVal()) && StringUtil.isDouble(getQty()) && getQty()>0){
			thmDH=MathUtil.roundHalfUp(getThmNowVal()/getQty(), 2);
		}
		return thmDH;
	}
	public void setThmDH(Double thmDH) {
		this.thmDH = thmDH;
	}
	public Double getNczDH() {
		if(StringUtil.isDouble(getNczNowVal()) && StringUtil.isDouble(getQty()) && getQty()>0){
			nczDH=MathUtil.roundHalfUp(getNczNowVal()/getQty(), 2);
		}
		return nczDH;
	}
	public void setNczDH(Double nczDH) {
		this.nczDH = nczDH;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getEqpCode() {
		return eqpCode;
	}
	public void setEqpCode(String eqpCode) {
		this.eqpCode = eqpCode;
	}
	public String getMatType() {
		return matType;
	}
	public void setMatType(String matType) {
		this.matType = matType;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Double getLbStd() {
		return lbStd;
	}
	public void setLbStd(Double lbStd) {
		this.lbStd = lbStd;
	}
	public String getLbRealVal() {
		return lbRealVal;
	}
	public void setLbRealVal(String lbRealVal) {
		this.lbRealVal = lbRealVal;
	}
	public Double getLbNowVal() {
		return lbNowVal;
	}
	public void setLbNowVal(Double lbNowVal) {
		this.lbNowVal = lbNowVal;
	}
	public Double getPzStd() {
		return pzStd;
	}
	public void setPzStd(Double pzStd) {
		this.pzStd = pzStd;
	}
	public String getPzVal() {
		return pzVal;
	}
	public void setPzVal(String pzVal) {
		this.pzVal = pzVal;
	}
	public Double getPzNowVal() {
		return pzNowVal;
	}
	public void setPzNowVal(Double pzNowVal) {
		this.pzNowVal = pzNowVal;
	}
	public Double getSszStd() {
		return sszStd;
	}
	public void setSszStd(Double sszStd) {
		this.sszStd = sszStd;
	}
	public String getSszVal() {
		return sszVal;
	}
	public void setSszVal(String sszVal) {
		this.sszVal = sszVal;
	}
	public Double getSszNowVal() {
		return sszNowVal;
	}
	public void setSszNowVal(Double sszNowVal) {
		this.sszNowVal = sszNowVal;
	}
	public Double getXhzStd() {
		return xhzStd;
	}
	public void setXhzStd(Double xhzStd) {
		this.xhzStd = xhzStd;
	}
	public String getXhzVal() {
		return xhzVal;
	}
	public void setXhzVal(String xhzVal) {
		this.xhzVal = xhzVal;
	}
	public Double getXhzNowVal() {
		return xhzNowVal;
	}
	public void setXhzNowVal(Double xhzNowVal) {
		this.xhzNowVal = xhzNowVal;
	}
	public Double getXhmStd() {
		return xhmStd;
	}
	public void setXhmStd(Double xhmStd) {
		this.xhmStd = xhmStd;
	}
	public String getXhmVal() {
		return xhmVal;
	}
	public void setXhmVal(String xhmVal) {
		this.xhmVal = xhmVal;
	}
	public Double getXhmNowVal() {
		return xhmNowVal;
	}
	public void setXhmNowVal(Double xhmNowVal) {
		this.xhmNowVal = xhmNowVal;
	}
	public Double getThzStd() {
		return thzStd;
	}
	public void setThzStd(Double thzStd) {
		this.thzStd = thzStd;
	}
	public String getThzVal() {
		return thzVal;
	}
	public void setThzVal(String thzVal) {
		this.thzVal = thzVal;
	}
	public Double getThzNowVal() {
		return thzNowVal;
	}
	public void setThzNowVal(Double thzNowVal) {
		this.thzNowVal = thzNowVal;
	}
	public Double getThmStd() {
		return thmStd;
	}
	public void setThmStd(Double thmStd) {
		this.thmStd = thmStd;
	}
	public String getThmVal() {
		return thmVal;
	}
	public void setThmVal(String thmVal) {
		this.thmVal = thmVal;
	}
	public Double getThmNowVal() {
		return thmNowVal;
	}
	public void setThmNowVal(Double thmNowVal) {
		this.thmNowVal = thmNowVal;
	}
	public Double getNczStd() {
		return nczStd;
	}
	public void setNczStd(Double nczStd) {
		this.nczStd = nczStd;
	}
	public String getNczVal() {
		return nczVal;
	}
	public void setNczVal(String nczVal) {
		this.nczVal = nczVal;
	}
	public Double getNczNowVal() {
		return nczNowVal;
	}
	public void setNczNowVal(Double nczNowVal) {
		this.nczNowVal = nczNowVal;
	}
	
	public String getpDate() {
		return pDate;
	}
	public void setpDate(String pDate) {
		this.pDate = pDate;
	}
	@Override
	public int compareTo(RealTimeFLConsumeShowBean o) {
		return eqpCode.compareTo(o.getEqpCode());
	}
	
}
