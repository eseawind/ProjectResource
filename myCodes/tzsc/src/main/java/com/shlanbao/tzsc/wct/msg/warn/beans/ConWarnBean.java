package com.shlanbao.tzsc.wct.msg.warn.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class ConWarnBean {
	private String id;
	private Double std;
	private Double val;
	private String item;
	@DateFmtAnnotation(fmtPattern = "yyyy-MM-dd HH:mm:ss")
	private String time;
	private Long sts;
	private String content;
	private Long del;
	private String etim;

	private String stim;
	private String workshopid;
	private String equipmentid;
	private String workordername;
	private String equipmentname;

	public String getEtim() {
		return etim;
	}

	public void setEtim(String etim) {
		this.etim = etim;
	}

	public String getStim() {
		return stim;
	}

	public void setStim(String stim) {
		this.stim = stim;
	}

	public String getWorkshopid() {
		return workshopid;
	}

	public void setWorkshopid(String workshopid) {
		this.workshopid = workshopid;
	}

	public String getEquipmentid() {
		return equipmentid;
	}

	public void setEquipmentid(String equipmentid) {
		this.equipmentid = equipmentid;
	}

	public String getWorkordername() {
		return workordername;
	}

	public void setWorkordername(String workordername) {
		this.workordername = workordername;
	}

	public String getEquipmentname() {
		return equipmentname;
	}

	public void setEquipmentname(String equipmentname) {
		this.equipmentname = equipmentname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getStd() {
		return std;
	}

	public void setStd(Double std) {
		this.std = std;
	}

	public Double getVal() {
		return val;
	}

	public void setVal(Double val) {
		this.val = val;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getSts() {
		return sts;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setSts(Long sts) {
		this.sts = sts;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getDel() {
		return del;
	}

	public void setDel(Long del) {
		this.del = del;
	}
}
