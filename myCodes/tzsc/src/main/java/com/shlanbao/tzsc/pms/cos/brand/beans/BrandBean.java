package com.shlanbao.tzsc.pms.cos.brand.beans;

import java.util.Hashtable;

public class BrandBean {
	private String eqdCode;//设备code 注：非ID
	private String eqdID;//设备ＩＤ
	private String eqdType;//类型 卷烟机，包装机
	private String workOrderId;//工单ID
	private String batch;//批次
	private String matName;//牌号
	private String teamName;//班组
	private double qty;//产量
	private String unit;//产量单位
	private String time;//生产日期
	private String team;//班组
	private String fmatName;//辅料名称
	private String fmatType;//辅料类型
	private String fmatTypeID;//辅料类型ID
	private double fqty;//辅料消耗量
	private String funit;//辅料单位
	private String shiftId;//班次
	private String shiftName;//班次名称
	private double excluding;//剔除量
	
	public void setExcluding(double excluding) {
		this.excluding = excluding;
	}
	public double getExcluding() {
		return excluding;
	}
	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getEqdCode() {
		return eqdCode;
	}

	public void setEqdCode(String eqdCode) {
		this.eqdCode = eqdCode;
	}
	public String getEqdID() {
		return eqdID;
	}

	public void setEqdID(String eqdID) {
		this.eqdID = eqdID;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFunit() {
		return funit;
	}

	public void setFunit(String funit) {
		this.funit = funit;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getFmatTypeID() {
		return fmatTypeID;
	}

	public void setFmatTypeID(String fmatTypeID) {
		this.fmatTypeID = fmatTypeID;
	}

	public String getFmatType() {
		return fmatType;
	}

	public void setFmatType(String fmatType) {
		this.fmatType = fmatType;
	}

	public String getFmatName() {
		return fmatName;
	}

	public void setFmatName(String fmatName) {
		this.fmatName = fmatName;
	}

	public double getFqty() {
		return fqty;
	}

	public void setFqty(double fqty) {
		this.fqty = fqty;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	private Hashtable<String,String[]> ht;//key 辅料类型+成品牌号，value 数组：0、辅料类型，1、消耗量，2、单位，3、单价，4、总成本，5、单箱成本

	public String getEqdType() {
		return eqdType;
	}

	public void setEqdType(String eqdType) {
		this.eqdType = eqdType;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public Hashtable<String, String[]> getHt() {
		return ht;
	}

	public void setHt(Hashtable<String, String[]> ht) {
		this.ht = ht;
	}
	
	
	
	
}
