package com.shlanbao.tzsc.pms.msg.cons.beans;

import java.util.Date;

import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class MsgConWarnBean {
	private String id;
    private String workId; //工单ID
	private String workCode;//工单编码
	private String workShopId;//车间ID
	private String workShopName;//车间名称
	private String equipId;//机台ID
	private String equipName;//机台名称
    private Double std;
    private Double val;
    private String item;
    @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
    private String time;
    private Long sts;
    private String content;
    private Long del;
    private String time2;
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	public String getWorkShopId() {
		return workShopId;
	}
	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	public String getEquipId() {
		return equipId;
	}
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	public String getEquipName() {
		return equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getSts() {
		return sts;
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
