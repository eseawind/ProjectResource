package com.lanbao.dws.model.wct.pddisplay;

import java.util.Date;

import javax.persistence.Entity;

/**
 * 工单表实体类
 * wanchanghuang
 * */
@Entity(name="sch_workOrder")
public class WorkOrderBean {

	private String id;
	private String code;
	private String bth;
	private Integer isNew;
	private Integer type;
	private Integer prodType;
	private String shift;
	private String team;
	private String eqp;
	private String mat;
	private Integer qty;
	private String unit;
	private String date_;
	private String stime;
	private String etime;
	private String bomVersion;
	private Integer sts;
	private Integer isCheck;
	private String checkTime;
	private String realStim;
	private String realEtim;
	private String erpSeq;
	private Integer runSeq;
	private String recvTime;
	private Integer enable;
	private Integer del;
	private String isAuto;
	private String workShop;//车间
	private String orderType;//工单类型  1卷烟机  2包装机  3封箱机 4成型机  工单
	private String nowTime;//当前服务器时间
	
	
	private Integer eqpcode; //设备code   
	private String eqptype; //设备型号 zj17
	private String matTypeCode;//辅料类型code（7-小盒纸 8-条盒纸 5小盒膜6条盒膜9内衬纸）
	private Double flxsVal; //辅料系数值
	private Double axlePz;//盘纸滚轴系数
	private Double axleSsz;//水松纸滚轴系数
	private Date fdate;
	private Integer eshift;//当前班次的上一班次（   如：当前中班，上一班次是：早班）     
	private String edate;//
	
	private String outId;//产出表id
	
	
	
	
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getWorkShop() {
		return workShop;
	}
	public void setWorkShop(String workShop) {
		this.workShop = workShop;
	}
	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	
	public Integer getEshift() {
		return eshift;
	}
	public void setEshift(Integer eshift) {
		this.eshift = eshift;
	}
	public Double getAxlePz() {
		return axlePz;
	}
	public void setAxlePz(Double axlePz) {
		this.axlePz = axlePz;
	}
	public Double getAxleSsz() {
		return axleSsz;
	}
	public void setAxleSsz(Double axleSsz) {
		this.axleSsz = axleSsz;
	}
	public Double getFlxsVal() {
		return flxsVal;
	}
	public void setFlxsVal(Double flxsVal) {
		this.flxsVal = flxsVal;
	}
	public String getMatTypeCode() {
		return matTypeCode;
	}
	public void setMatTypeCode(String matTypeCode) {
		this.matTypeCode = matTypeCode;
	}
	public Date getFdate() {
		return fdate;
	}
	public void setFdate(Date fdate) {
		this.fdate = fdate;
	}
	public Integer getEqpcode() {
		return eqpcode;
	}
	public void setEqpcode(Integer eqpcode) {
		this.eqpcode = eqpcode;
	}
	public String getEqptype() {
		return eqptype;
	}
	public void setEqptype(String eqptype) {
		this.eqptype = eqptype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBth() {
		return bth;
	}
	public void setBth(String bth) {
		this.bth = bth;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getProdType() {
		return prodType;
	}
	public void setProdType(Integer prodType) {
		this.prodType = prodType;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getEqp() {
		return eqp;
	}
	public void setEqp(String eqp) {
		this.eqp = eqp;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDate_() {
		return date_;
	}
	public void setDate_(String date_) {
		this.date_ = date_;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getBomVersion() {
		return bomVersion;
	}
	public void setBomVersion(String bomVersion) {
		this.bomVersion = bomVersion;
	}
	public Integer getSts() {
		return sts;
	}
	public void setSts(Integer sts) {
		this.sts = sts;
	}
	public Integer getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getRealStim() {
		return realStim;
	}
	public void setRealStim(String realStim) {
		this.realStim = realStim;
	}
	public String getRealEtim() {
		return realEtim;
	}
	public void setRealEtim(String realEtim) {
		this.realEtim = realEtim;
	}
	public String getErpSeq() {
		return erpSeq;
	}
	public void setErpSeq(String erpSeq) {
		this.erpSeq = erpSeq;
	}
	public Integer getRunSeq() {
		return runSeq;
	}
	public void setRunSeq(Integer runSeq) {
		this.runSeq = runSeq;
	}
	public String getRecvTime() {
		return recvTime;
	}
	public void setRecvTime(String recvTime) {
		this.recvTime = recvTime;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public String getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}
	
}
