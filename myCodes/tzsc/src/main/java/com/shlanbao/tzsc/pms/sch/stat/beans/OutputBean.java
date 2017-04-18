package com.shlanbao.tzsc.pms.sch.stat.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * 工单产出数据 
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class OutputBean {
	private String id;
	private String eqpType;//设备类型
	private String date;//生产日期
	private String date2;//生产日期第二个时间
	private String shift;//班次
	private String team;//班组
	private String equipment;//设备
	private String eqpCode;//设备code
	private String workShop;//车间代码
	private String workorder;//工单ID
	private String workorderCode;//工单号
	private String bth;//批次号
	private String orderNum;//订单号
	private String matProd;//牌号
	private String matId;//牌号id
	private String orderStat;//工单状态 1-下发  2-开始  4-结束 (工单状态)
	private String feedbackType;//反馈类型 0-结束工单  1-换班
	private Double qty;//产量
	private Double changeQty;//调整产量数
	private Double badQty;//剔除量
	private String unit;//产量单位
	private String unitName;//产量单位
	private String pstim;//计划开始
	private String petim;//计划结束
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String stim;//实绩开始时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String etim;//实绩结束时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String supplytim;//下发时间
	private Double runTime;//运行时间
	private Double stopTime;//停机时间
	private Long stopTimes;//停机次数
	private String timeunit;//时间单位
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String lastRecvTime;//最后数据接收时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String lastUpdateTime;//数据最后编辑时间
	private Long isFeedback;//是否反馈
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String feedbackTime;//反馈时间
	private String feedbackUser;//反馈人
	private Long del;//删除
	private String changeFlag;//产量修改标识
	private String OrderType;
	private String result;//结果类型
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOrderType() {
		return OrderType;
	}
	public void setOrderType(String orderType) {
		OrderType = orderType;
	}
	private String sequenceNumber;//批次作业顺序号
	
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getChangeFlag() {
		return changeFlag;
	}
	public void setChangeFlag(String changeFlag) {
		this.changeFlag = changeFlag;
	}
	public OutputBean() {
		// TODO Auto-generated constructor stub
	}
	public OutputBean(String id, Double qty, Double badQty, Double runTime,
			Double stopTime, Long stopTimes, String lastRecvTime) {
		super();
		this.id = id;
		this.qty = qty;
		this.badQty = badQty;
		this.runTime = runTime;
		this.stopTime = stopTime;
		this.stopTimes = stopTimes;
		this.lastRecvTime = lastRecvTime;
	}
	
	public String getPstim() {
		return pstim;
	}
	public void setPstim(String pstim) {
		this.pstim = pstim;
	}
	public String getPetim() {
		return petim;
	}
	public void setPetim(String petim) {
		this.petim = petim;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEqpType() {
		return eqpType;
	}
	public void setEqpType(String eqpType) {
		this.eqpType = eqpType;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getWorkorder() {
		return workorder;
	}
	public void setWorkorder(String workorder) {
		this.workorder = workorder;
	}
	public String getMatProd() {
		return matProd;
	}
	public void setMatProd(String matProd) {
		this.matProd = matProd;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	
	public String getEqpCode() {
		return eqpCode;
	}
	public void setEqpCode(String eqpCode) {
		this.eqpCode = eqpCode;
	}
	public String getBth() {
		return bth;
	}
	public void setBth(String bth) {
		this.bth = bth;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getMatId() {
		return matId;
	}
	public void setMatId(String matId) {
		this.matId = matId;
	}
	public String getOrderStat() {
		return orderStat;
	}
	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}
	public String getFeedbackType() {
		return feedbackType;
	}
	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	public String getSupplytim() {
		return supplytim;
	}
	public void setSupplytim(String supplytim) {
		this.supplytim = supplytim;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
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
	public String getTimeunit() {
		return timeunit;
	}
	public void setTimeunit(String timeunit) {
		this.timeunit = timeunit;
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
	public Long getIsFeedback() {
		return isFeedback;
	}
	public void setIsFeedback(Long isFeedback) {
		this.isFeedback = isFeedback;
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
	
	public Double getChangeQty() {
		return changeQty;
	}
	public void setChangeQty(Double changeQty) {
		this.changeQty = changeQty;
	}
	public void setDel(Long del) {
		this.del = del;
	}
	public String getWorkShop() {
		return workShop;
	}
	public void setWorkShop(String workShop) {
		this.workShop = workShop;
	}
	public String getWorkorderCode() {
		return workorderCode;
	}
	public void setWorkorderCode(String workorderCode) {
		this.workorderCode = workorderCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	
}
