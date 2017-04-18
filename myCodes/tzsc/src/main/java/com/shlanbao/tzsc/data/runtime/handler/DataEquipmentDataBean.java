package com.shlanbao.tzsc.data.runtime.handler;

import com.shlanbao.tzsc.data.runtime.bean.EquipmentData;

public class DataEquipmentDataBean {
	//卷烟机参数 begin
	public EquipmentData plcEqpData =null;//PLC上一条记录
	public int hbPyMinCount= 0;//自动换班偏移 阀值 次数 3次
	public int hbCount = 0;//自动换班次数,目前最多2次
	private int hbMinFaZhi = 50;//自动换班阀值
	public  String plcBeforCl="";//PLC上一次产量
	/**
	public  String plcBeforPzCd="";//盘纸长度
	public  String plcBeforSszCd="";//水松纸长度
	public  String plcBeforZbCd="";//当前消耗的嘴棒 数量
	 */	
	DataEquipmentDataBean(){
		this.plcBeforCl="";//PLC上一次产量
		this.plcEqpData =null;//PLC上一条记录
		this.hbPyMinCount= 0;//自动换班偏移 阀值 次数 3次
		this.hbCount = 0;//自动换班次数,目前最多2次
		this.hbMinFaZhi = 50;//自动换班阀值
	}
	//end

	
	
	public String getPlcBeforCl() {
		return plcBeforCl;
	}
	public void setPlcBeforCl(String plcBeforCl) {
		this.plcBeforCl = plcBeforCl;
	}
	public EquipmentData getPlcEqpData() {
		return plcEqpData;
	}
	public void setPlcEqpData(EquipmentData plcEqpData) {
		this.plcEqpData = plcEqpData;
	}
	public int getHbPyMinCount() {
		return hbPyMinCount;
	}
	public void setHbPyMinCount(int hbPyMinCount) {
		this.hbPyMinCount = hbPyMinCount;
	}
	public int getHbCount() {
		return hbCount;
	}
	public void setHbCount(int hbCount) {
		this.hbCount = hbCount;
	}
	public int getHbMinFaZhi() {
		return hbMinFaZhi;
	}
	public void setHbMinFaZhi(int hbMinFaZhi) {
		this.hbMinFaZhi = hbMinFaZhi;
	}
}
