package com.shlanbao.tzsc.wct.eqm.fix.beans;

import java.util.Date;

import com.shlanbao.tzsc.base.mapping.MdEquipment;
import com.shlanbao.tzsc.base.mapping.MdShift;

public class WctEqmFixInfoBean {
	private String id;
	private MdEquipment eqpId;//设备id
	private MdShift shiftId;//班次id
	private String maintaiinId;//维修人id
	private String maintaiinName;//维修人姓名
	private Date maintaiinTime;//维修时间
	private int maintaiinType;//维修类型
	private String sparePartsId;//备品备件表id
	private int sparePartsNum;//备品备件使用量
	private int status;//维修状态
	private String source;//维修来源
	private String remark;//备注
	private String name;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MdEquipment getEqpId() {
		return eqpId;
	}
	public void setEqpId(MdEquipment eqpId) {
		this.eqpId = eqpId;
	}
	
	public MdShift getShiftId() {
		return shiftId;
	}
	public void setShiftId(MdShift shiftId) {
		this.shiftId = shiftId;
	}
	public String getMaintaiinId() {
		return maintaiinId;
	}
	public void setMaintaiinId(String maintaiinId) {
		this.maintaiinId = maintaiinId;
	}
	public String getMaintaiinName() {
		return maintaiinName;
	}
	public void setMaintaiinName(String maintaiinName) {
		this.maintaiinName = maintaiinName;
	}
	public Date getMaintaiinTime() {
		return maintaiinTime;
	}
	public void setMaintaiinTime(Date maintaiinTime) {
		this.maintaiinTime = maintaiinTime;
	}
	public int getMaintaiinType() {
		return maintaiinType;
	}
	public void setMaintaiinType(int maintaiinType) {
		this.maintaiinType = maintaiinType;
	}
	public String getSparePartsId() {
		return sparePartsId;
	}
	public void setSparePartsId(String sparePartsId) {
		this.sparePartsId = sparePartsId;
	}
	public int getSparePartsNum() {
		return sparePartsNum;
	}
	public void setSparePartsNum(int sparePartsNum) {
		this.sparePartsNum = sparePartsNum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
