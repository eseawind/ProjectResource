package com.lanbao.dws.model.wct.qm;
/**
 * 用于显示巡检详细项
 * @author shisihai
 *
 */
public class QMOutWandDelInfo {
	private String itemCode;//缺陷code
	private String pos;//缺陷类型
	private String des;//缺陷描述
	private String lvl;//缺陷等级
	private Integer val;//缺陷值
	
	
	private String batchNo;//批次号
	
	private Integer outWandIndex=1;//当前页数（巡检项） 
	
	
	
	
	public Integer getOutWandIndex() {
		return outWandIndex;
	}
	public void setOutWandIndex(Integer outWandIndex) {
		this.outWandIndex = outWandIndex;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public Integer getVal() {
		return val;
	}
	public void setVal(Integer val) {
		this.val = val;
	}
	
	
}
