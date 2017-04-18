package com.shlanbao.tzsc.pms.sch.statjbcj.beans;

import com.shlanbao.tzsc.base.mapping.SchWorkorder;
import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

public class JuanBaoCJBean {

	private String id;
	private String mdUnitId;// 产量单位
	private String mdUnitName;
	private String mdTeamId;// 班组在SchWorkorder下
	private String mdTeamName;
	private String mdEquipmentId;// 设备信息
	private String equipmentName;
	private String equipmentNameCode;
	private String equipmentType;

	private String mdShiftId;// 班次信息
	private String mdShiftName;
	private String mdMatId;// 牌号信息
	private String mdMatName;
	private String date;
	private String mdWorkshopId;// 车间ID
	private Double qty=0.0;// 实际产量
	private Double badQty=0.0;// 剔除量
	@DateFmtAnnotation(fmtPattern = "yyyy-MM-dd HH:mm:ss")
	private String stim;// 实际开始时间
	@DateFmtAnnotation(fmtPattern = "yyyy-MM-dd HH:mm:ss")
	private String etim;// 实际结束时间
	private Double runTime=0.0;// 运行时长(min)
	private Double stopTime=0.0;// 停机时长(min)
	private Long stopTimes=0L;// 停机次数
	private String timeUnitId;// 时间单位
	private String timeUnitName;
	@DateFmtAnnotation(fmtPattern = "yyyy-MM-dd HH:mm:ss")
	private String lastRecvTime;// 数据最后接收时间
	@DateFmtAnnotation(fmtPattern = "yyyy-MM-dd HH:mm:ss")
	private String lastUpdateTime;// 数据最后编辑时间
	private String isFeedback01;// 是否反馈(0,未反馈1,已反馈)
	@DateFmtAnnotation(fmtPattern = "yyyy-MM-dd HH:mm:ss")
	private String feedbackTime;// 反馈时间
	private String feedbackUser;// 反馈人
	private Long del;

	private String mdUnitName1;// 滤棒 //小盒
	private Double danhao1=0.0;
	private Double qty1=0.0;

	private String mdUnitName2;// 滤棒 盘纸 //小透明纸
	private Double danhao2=0.0;
	private Double qty2=0.0;

	private String mdUnitName3;// 水松纸 //内衬纸
	private Double danhao3=0.0;
	private Double qty3=0.0;

	private String mdUnitName4;// 条盒
	private Double danhao4=0.0;
	private Double qty4=0.0;

	private String mdUnitName5;// 大透明纸

	private Double danhao5=0.0;
	private Double qty5=0.0;
	
	

	public String getMdUnitName4() {
		return mdUnitName4;
	}

	public void setMdUnitName4(String mdUnitName4) {
		this.mdUnitName4 = mdUnitName4;
	}

	public Double getDanhao4() {
		return danhao4;
	}

	public void setDanhao4(Double danhao4) {
		this.danhao4 = danhao4;
	}

	public Double getQty4() {
		return qty4;
	}

	public void setQty4(Double qty4) {
		this.qty4 = qty4;
	}

	public String getMdUnitName5() {
		return mdUnitName5;
	}

	public void setMdUnitName5(String mdUnitName5) {
		this.mdUnitName5 = mdUnitName5;
	}

	public Double getDanhao5() {
		return danhao5;
	}

	public void setDanhao5(Double danhao5) {
		this.danhao5 = danhao5;
	}

	public Double getQty5() {
		return qty5;
	}

	public void setQty5(Double qty5) {
		this.qty5 = qty5;
	}

	public String getMdUnitName1() {
		return mdUnitName1;
	}

	public void setMdUnitName1(String mdUnitName1) {
		this.mdUnitName1 = mdUnitName1;
	}

	public Double getDanhao1() {
		return danhao1;
	}

	public void setDanhao1(Double danhao1) {
		this.danhao1 = danhao1;
	}

	public Double getQty1() {
		return qty1;
	}

	public void setQty1(Double qty1) {
		this.qty1 = qty1;
	}

	public String getMdUnitName2() {
		return mdUnitName2;
	}

	public void setMdUnitName2(String mdUnitName2) {
		this.mdUnitName2 = mdUnitName2;
	}

	public Double getDanhao2() {
		return danhao2;
	}

	public void setDanhao2(Double danhao2) {
		this.danhao2 = danhao2;
	}

	public Double getQty2() {
		return qty2;
	}

	public void setQty2(Double qty2) {
		this.qty2 = qty2;
	}

	public String getMdUnitName3() {
		return mdUnitName3;
	}

	public void setMdUnitName3(String mdUnitName3) {
		this.mdUnitName3 = mdUnitName3;
	}

	public Double getDanhao3() {
		return danhao3;
	}

	public void setDanhao3(Double danhao3) {
		this.danhao3 = danhao3;
	}

	public Double getQty3() {
		return qty3;
	}

	public void setQty3(Double qty3) {
		this.qty3 = qty3;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMdUnitId() {
		return mdUnitId;
	}

	public void setMdUnitId(String mdUnitId) {
		this.mdUnitId = mdUnitId;
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

	public String getMdTeamName() {
		return mdTeamName;
	}

	public void setMdTeamName(String mdTeamName) {
		this.mdTeamName = mdTeamName;
	}

	public String getMdEquipmentId() {
		return mdEquipmentId;
	}

	public void setMdEquipmentId(String mdEquipmentId) {
		this.mdEquipmentId = mdEquipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getMdShiftId() {
		return mdShiftId;
	}

	public void setMdShiftId(String mdShiftId) {
		this.mdShiftId = mdShiftId;
	}

	public String getMdShiftName() {
		return mdShiftName;
	}

	public void setMdShiftName(String mdShiftName) {
		this.mdShiftName = mdShiftName;
	}

	public String getMdMatId() {
		return mdMatId;
	}

	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}

	public String getMdMatName() {
		return mdMatName;
	}

	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}

	public String getMdWorkshopId() {
		return mdWorkshopId;
	}

	public void setMdWorkshopId(String mdWorkshopId) {
		this.mdWorkshopId = mdWorkshopId;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getBadQty() {
		return badQty;
	}

	public void setBadQty(Double badQty) {
		this.badQty = badQty;
	}

	public String getStim() {
		return stim;
	}

	public void setStim(String stim) {
		this.stim = stim;
	}

	public String getEtim() {
		return etim;
	}

	public void setEtim(String etim) {
		this.etim = etim;
	}

	public Double getRunTime() {
		return runTime;
	}

	public void setRunTime(Double runTime) {
		this.runTime = runTime;
	}

	public Double getStopTime() {
		return stopTime;
	}

	public void setStopTime(Double stopTime) {
		this.stopTime = stopTime;
	}

	public Long getStopTimes() {
		return stopTimes;
	}

	public void setStopTimes(Long stopTimes) {
		this.stopTimes = stopTimes;
	}

	public String getTimeUnitId() {
		return timeUnitId;
	}

	public void setTimeUnitId(String timeUnitId) {
		this.timeUnitId = timeUnitId;
	}

	public String getTimeUnitName() {
		return timeUnitName;
	}

	public void setTimeUnitName(String timeUnitName) {
		this.timeUnitName = timeUnitName;
	}

	public String getLastRecvTime() {
		return lastRecvTime;
	}

	public void setLastRecvTime(String lastRecvTime) {
		this.lastRecvTime = lastRecvTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getIsFeedback01() {
		return isFeedback01;
	}

	public void setIsFeedback01(String isFeedback01) {
		this.isFeedback01 = isFeedback01;
	}

	public String getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getFeedbackUser() {
		return feedbackUser;
	}

	public void setFeedbackUser(String feedbackUser) {
		this.feedbackUser = feedbackUser;
	}

	public Long getDel() {
		return del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

	public String getEquipmentNameCode() {
		return equipmentNameCode;
	}

	public void setEquipmentNameCode(String equipmentNameCode) {
		this.equipmentNameCode = equipmentNameCode;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

}
