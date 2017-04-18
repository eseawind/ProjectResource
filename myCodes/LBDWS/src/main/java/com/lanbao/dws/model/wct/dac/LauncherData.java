package com.lanbao.dws.model.wct.dac;
/**
 * 发射机
 * @author shisihai
 */
public class LauncherData {
	private String equipmentCode;//设备型号
	private String equipmentType;//设备型号
	private String equipmentEdcs;//设备名称
	
	
	//实时数据部分
	private Double xp1Qty0;//卸盘机（左侧）
	private Double xp1Qty1;//卸盘机（右侧）
	private Double qty0;//数量（1到10发射管）
	private Double qty1;
	private Double qty2;
	private Double qty3;
	private Double qty4;
	private Double qty5;
	private Double qty6;
	private Double qty7;
	private Double qty8;
	private Double qty9;
	private Double speed0;//速度（1到10发射管）
	private Double speed1;
	private Double speed2;
	private Double speed3;
	private Double speed4;
	private Double speed5;
	private Double speed6;
	private Double speed7;
	private Double speed8;
	private Double speed9;
	private Double qty;//发射总量（计算得到（1到10发射总计））
	
	public LauncherData(String equipmentCode,  Double xp1Qty0,
			Double xp1Qty1, Double qty0, Double qty1, Double qty2, Double qty3, Double qty4, Double qty5, Double qty6,
			Double qty7, Double qty8, Double qty9, Double speed0, Double speed1, Double speed2, Double speed3, Double speed4,
			Double speed5, Double speed6, Double speed7, Double speed8, Double speed9,Double qty
			) {
		super();
		this.equipmentCode = equipmentCode;
		this.xp1Qty0 = xp1Qty0;
		this.xp1Qty1 = xp1Qty1;
		this.qty0 = qty0;
		this.qty1 = qty1;
		this.qty2 = qty2;
		this.qty3 = qty3;
		this.qty4 = qty4;
		this.qty5 = qty5;
		this.qty6 = qty6;
		this.qty7 = qty7;
		this.qty8 = qty8;
		this.qty9 = qty9;
		this.speed0 = speed0;
		this.speed1 = speed1;
		this.speed2 = speed2;
		this.speed3 = speed3;
		this.speed4 = speed4;
		this.speed5 = speed5;
		this.speed6 = speed6;
		this.speed7 = speed7;
		this.speed8 = speed8;
		this.speed9 = speed9;
		this.qty = qty;
	}
	
	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public LauncherData() {
		super();
	}
	public Double getXp1Qty0() {
		return xp1Qty0;
	}
	public void setXp1Qty0(Double xp1Qty0) {
		this.xp1Qty0 = xp1Qty0;
	}
	public Double getXp1Qty1() {
		return xp1Qty1;
	}
	public void setXp1Qty1(Double xp1Qty1) {
		this.xp1Qty1 = xp1Qty1;
	}
	public Double getQty0() {
		return qty0;
	}
	public void setQty0(Double qty0) {
		this.qty0 = qty0;
	}
	public Double getQty1() {
		return qty1;
	}
	public void setQty1(Double qty1) {
		this.qty1 = qty1;
	}
	public Double getQty2() {
		return qty2;
	}
	public void setQty2(Double qty2) {
		this.qty2 = qty2;
	}
	public Double getQty3() {
		return qty3;
	}
	public void setQty3(Double qty3) {
		this.qty3 = qty3;
	}
	public Double getQty4() {
		return qty4;
	}
	public void setQty4(Double qty4) {
		this.qty4 = qty4;
	}
	public Double getQty5() {
		return qty5;
	}
	public void setQty5(Double qty5) {
		this.qty5 = qty5;
	}
	public Double getQty6() {
		return qty6;
	}
	public void setQty6(Double qty6) {
		this.qty6 = qty6;
	}
	public Double getQty7() {
		return qty7;
	}
	public void setQty7(Double qty7) {
		this.qty7 = qty7;
	}
	public Double getQty8() {
		return qty8;
	}
	public void setQty8(Double qty8) {
		this.qty8 = qty8;
	}
	public Double getQty9() {
		return qty9;
	}
	public void setQty9(Double qty9) {
		this.qty9 = qty9;
	}
	
	public Double getSpeed0() {
		return speed0;
	}
	public void setSpeed0(Double speed0) {
		this.speed0 = speed0;
	}
	public Double getSpeed1() {
		return speed1;
	}
	public void setSpeed1(Double speed1) {
		this.speed1 = speed1;
	}
	public Double getSpeed2() {
		return speed2;
	}
	public void setSpeed2(Double speed2) {
		this.speed2 = speed2;
	}
	public Double getSpeed3() {
		return speed3;
	}
	public void setSpeed3(Double speed3) {
		this.speed3 = speed3;
	}
	public Double getSpeed4() {
		return speed4;
	}
	public void setSpeed4(Double speed4) {
		this.speed4 = speed4;
	}
	public Double getSpeed5() {
		return speed5;
	}
	public void setSpeed5(Double speed5) {
		this.speed5 = speed5;
	}
	public Double getSpeed6() {
		return speed6;
	}
	public void setSpeed6(Double speed6) {
		this.speed6 = speed6;
	}
	public Double getSpeed7() {
		return speed7;
	}
	public void setSpeed7(Double speed7) {
		this.speed7 = speed7;
	}
	public Double getSpeed8() {
		return speed8;
	}
	public void setSpeed8(Double speed8) {
		this.speed8 = speed8;
	}
	public Double getSpeed9() {
		return speed9;
	}
	public void setSpeed9(Double speed9) {
		this.speed9 = speed9;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getEquipmentEdcs() {
		return equipmentEdcs;
	}
	public void setEquipmentEdcs(String equipmentEdcs) {
		this.equipmentEdcs = equipmentEdcs;
	}
	
}
