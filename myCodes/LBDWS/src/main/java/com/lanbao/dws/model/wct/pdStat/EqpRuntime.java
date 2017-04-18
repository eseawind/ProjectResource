package com.lanbao.dws.model.wct.pdStat;
/**
 * @author shisihai
 *实时产量数据
 */
public class EqpRuntime {
	private String eqpCod;//设备编号
	private Double curOut;//当前产量
	private Double badQty;//当前剔除
	public EqpRuntime(String eqpCod, Double curOut, Double badQty) {
		super();
		this.eqpCod = eqpCod;
		this.curOut = curOut;
		this.badQty = badQty;
	}
	public String getEqpCod() {
		return eqpCod;
	}
	public void setEqpCod(String eqpCod) {
		this.eqpCod = eqpCod;
	}
	public Double getCurOut() {
		return curOut;
	}
	public void setCurOut(Double curOut) {
		this.curOut = curOut;
	}
	public Double getBadQty() {
		return badQty;
	}
	public void setBadQty(Double badQty) {
		this.badQty = badQty;
	}
}
