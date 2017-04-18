package com.shlanbao.tzsc.pms.qm.packagezx.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 卷包车间在线物理检验记录
 * <li>@author luther.zhang
 * <li>@create 2015-02-27
 */
public class PackageZxBean{
	private String id;
    private String checkFlag;//检验程序
    @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
    private String time;//自检开始时间
    private String endTime;//结束时间
    
    private String cb;//错包
    private String sb;//少包
    private String ps;//破损
    private String zt;//粘贴
    private String wz;//污渍
    private String dz;//倒装
    private String tmz;//透明纸
    private String lx;//拉线 
    private String oId;//工单号
    private String oName;//工单名称
    
    private String uId;//自检用户ID
    private String uName;//自检用户
    
    private String mdUnitId;	//单位ID
    private String mdUnitCode;	//单位CODE
    private String mdUnitName;  //单位名称
    
    private String mdTeamId;//班组ID
    private String mdTeamCode;//班组CODE
    private String mdTeamName;//班组名称
    
    private String mdShiftId;//班次ID
    private String mdShiftCode;//班次CODE
    private String mdShiftName;//班次名称
    
    private String mdEquipmentId;//设备ID
    private String mdEquipmentCode;//设备CODE
    private String mdEquipmentName;//设备名称
    
    private String mdMatId;//牌号ID
    private String mdMatCode;//牌号CODE
    private String mdMatName;//牌号名称
    
    private String mdWorkshopId;//车间ID
    private String mdWorkshopName;//车间名称
    
    private Long del;

    
	public String getMdWorkshopId() {
		return mdWorkshopId;
	}

	public void setMdWorkshopId(String mdWorkshopId) {
		this.mdWorkshopId = mdWorkshopId;
	}

	public String getMdWorkshopName() {
		return mdWorkshopName;
	}

	public void setMdWorkshopName(String mdWorkshopName) {
		this.mdWorkshopName = mdWorkshopName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCb() {
		return cb;
	}

	public void setCb(String cb) {
		this.cb = cb;
	}

	public String getSb() {
		return sb;
	}

	public void setSb(String sb) {
		this.sb = sb;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getWz() {
		return wz;
	}

	public void setWz(String wz) {
		this.wz = wz;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public String getTmz() {
		return tmz;
	}

	public void setTmz(String tmz) {
		this.tmz = tmz;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getoId() {
		return oId;
	}

	public void setoId(String oId) {
		this.oId = oId;
	}

	public String getoName() {
		return oName;
	}

	public void setoName(String oName) {
		this.oName = oName;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getMdUnitId() {
		return mdUnitId;
	}

	public void setMdUnitId(String mdUnitId) {
		this.mdUnitId = mdUnitId;
	}

	public String getMdUnitCode() {
		return mdUnitCode;
	}

	public void setMdUnitCode(String mdUnitCode) {
		this.mdUnitCode = mdUnitCode;
	}

	public String getMdUnitName() {
		return mdUnitName;
	}

	public void setMdUnitName(String mdUnitName) {
		this.mdUnitName = mdUnitName;
	}

	public String getMdTeamId() {
		return mdTeamId;
	}

	public void setMdTeamId(String mdTeamId) {
		this.mdTeamId = mdTeamId;
	}

	public String getMdTeamCode() {
		return mdTeamCode;
	}

	public void setMdTeamCode(String mdTeamCode) {
		this.mdTeamCode = mdTeamCode;
	}

	public String getMdTeamName() {
		return mdTeamName;
	}

	public void setMdTeamName(String mdTeamName) {
		this.mdTeamName = mdTeamName;
	}

	public String getMdShiftId() {
		return mdShiftId;
	}

	public void setMdShiftId(String mdShiftId) {
		this.mdShiftId = mdShiftId;
	}

	public String getMdShiftCode() {
		return mdShiftCode;
	}

	public void setMdShiftCode(String mdShiftCode) {
		this.mdShiftCode = mdShiftCode;
	}

	public String getMdShiftName() {
		return mdShiftName;
	}

	public void setMdShiftName(String mdShiftName) {
		this.mdShiftName = mdShiftName;
	}

	public String getMdEquipmentId() {
		return mdEquipmentId;
	}

	public void setMdEquipmentId(String mdEquipmentId) {
		this.mdEquipmentId = mdEquipmentId;
	}

	public String getMdEquipmentCode() {
		return mdEquipmentCode;
	}

	public void setMdEquipmentCode(String mdEquipmentCode) {
		this.mdEquipmentCode = mdEquipmentCode;
	}

	public String getMdEquipmentName() {
		return mdEquipmentName;
	}

	public void setMdEquipmentName(String mdEquipmentName) {
		this.mdEquipmentName = mdEquipmentName;
	}

	public String getMdMatId() {
		return mdMatId;
	}

	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}

	public String getMdMatCode() {
		return mdMatCode;
	}

	public void setMdMatCode(String mdMatCode) {
		this.mdMatCode = mdMatCode;
	}

	public String getMdMatName() {
		return mdMatName;
	}

	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}

	public Long getDel() {
		return del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
