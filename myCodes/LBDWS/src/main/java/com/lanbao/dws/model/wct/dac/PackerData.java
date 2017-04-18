package com.lanbao.dws.model.wct.dac;

public class PackerData {

	private String equipmentCode;//设备型号
	private String equipmentType;//设备型号
	private String equipmentEdcs;//设备名
	private String workorderCode;//工单code
	private String orderId;//工单id
	private String matName;//牌号
	private Double planQty;//计划产量
	private String unit;
	private String planStim;//计划开始时间
	private String planEtim;//计划结束时间
	private String bthCode;//批次号
	private String stim;//开始时间
	
	private Double xiaohemoBzdh;//小盒膜标准单耗
	private Double tiaohemoBzdh;//条盒膜标准单耗
	private Double xiaohezhiBzdh;//小盒纸标准单耗
	private Double tiaohezhiBzdh;//条盒纸标准单耗
	private Double neichenzhiBzdh;//内衬纸标准单耗
	
	
	//调整页面物料系数时使用，这些事MD_Mat 表中的id
	private String xiaohemoMatId;//小盒膜
	private String tiaohemoMatId;//条盒膜
	private String xiaohezhMatId;//小盒纸
	private String tiaohezhMatId;//条盒纸
	private String neichenzMatId;//内衬纸
	
	private Double xiaohemoEuval;//小盒膜报警上限
	private Double tiaohemoEuval;//条盒膜报警上限
	private Double xiaohezhiEuval;//小盒纸报警上限
	private Double tiaohezhiEuval;//条盒纸报警上限
	private Double neichenzhiEuval;//内衬纸报警上限
	
	//实时数据部分
	private Integer online;//网络状态  0异常，1正常
	private Double qty;//产量
	private Double badQty;//剔除产量
	
	private Double xiaohemoVal;//小盒膜消耗
	private Double tiaohemoVal;//条盒膜消耗
	private Double xiaohezhiVal;//小盒纸消耗
	private Double tiaohezhiVal;//条盒纸消耗
	private Double neichenzhiVal;//内衬纸消耗
	
	
	private Double runTime;//运行时间
	private Double stopTime;//停机时间
	private Integer stopTimes;//停机次数
	private Integer speed;//车速
	private Integer runStatus;//运行状态 -1 断网 0停止 1低速运行 2正常运行
	
	private Integer tsQty;//提升机产量
	
	
	public Integer getTsQty() {
		return tsQty;
	}

	public void setTsQty(Integer tsQty) {
		this.tsQty = tsQty;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public PackerData() {
	}
	
	/**
	 * 包装机实时数据
	 * @param online 网络状态
	 * @param qty 产量
	 * @param badQty 剔除量
	 * @param xiaohemoVal 小盒膜消耗
	 * @param tiaohemoVal 条盒膜消耗
	 * @param xiaohezhiVal 小盒纸消耗
	 * @param tiaohezhiVal 条盒纸消耗
	 * @param neichenzhiVal 内衬纸消耗
	 * @param xiaohemoRank 小盒膜排名
	 * @param tiaohemoRank 条盒膜排名
	 * @param xiaohezhiRank 小盒子排名
	 * @param tiaohezhiRank 条盒纸排名
	 * @param neichenzhiRank 内衬纸排名
	 * @param runTime 运行时间
	 * @param stopTime 停机时间
	 * @param stopTimes 停机次数
	 * @param speed 速度
	 * @param tsQty 提升机产量
	 */
	public PackerData(Integer online, Double qty,double badQty, Double xiaohemoVal,
			Double tiaohemoVal, Double xiaohezhiVal, Double tiaohezhiVal,
			Double neichenzhiVal, Double runTime, Double stopTime,
			Integer stopTimes, Integer speed,Integer tsQty) {
		super();
		this.online = online;
		this.qty = qty;
		this.badQty=badQty;
		this.xiaohemoVal = xiaohemoVal;
		this.tiaohemoVal = tiaohemoVal;
		this.xiaohezhiVal = xiaohezhiVal;
		this.tiaohezhiVal = tiaohezhiVal;
		this.neichenzhiVal = neichenzhiVal;
		this.runTime = runTime;
		this.stopTime = stopTime;
		this.stopTimes = stopTimes;
		this.speed = speed;
		this.tsQty=tsQty;
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
	public String getWorkorderCode() {
		return workorderCode;
	}
	public void setWorkorderCode(String workorderCode) {
		this.workorderCode = workorderCode;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public Double getPlanQty() {
		return planQty;
	}
	public void setPlanQty(Double planQty) {
		this.planQty = planQty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPlanStim() {
		return planStim;
	}
	public void setPlanStim(String planStim) {
		this.planStim = planStim;
	}
	public String getPlanEtim() {
		return planEtim;
	}
	public void setPlanEtim(String planEtim) {
		this.planEtim = planEtim;
	}
	public String getBthCode() {
		return bthCode;
	}
	public void setBthCode(String bthCode) {
		this.bthCode = bthCode;
	}
	public String getStim() {
		return stim;
	}
	public void setStim(String stim) {
		this.stim = stim;
	}
	public Double getXiaohemoBzdh() {
		return xiaohemoBzdh;
	}
	public void setXiaohemoBzdh(Double xiaohemoBzdh) {
		this.xiaohemoBzdh = xiaohemoBzdh;
	}
	public Double getTiaohemoBzdh() {
		return tiaohemoBzdh;
	}
	public void setTiaohemoBzdh(Double tiaohemoBzdh) {
		this.tiaohemoBzdh = tiaohemoBzdh;
	}
	public Double getXiaohezhiBzdh() {
		return xiaohezhiBzdh;
	}
	public void setXiaohezhiBzdh(Double xiaohezhiBzdh) {
		this.xiaohezhiBzdh = xiaohezhiBzdh;
	}
	public Double getTiaohezhiBzdh() {
		return tiaohezhiBzdh;
	}
	public void setTiaohezhiBzdh(Double tiaohezhiBzdh) {
		this.tiaohezhiBzdh = tiaohezhiBzdh;
	}
	public Double getNeichenzhiBzdh() {
		return neichenzhiBzdh;
	}
	public void setNeichenzhiBzdh(Double neichenzhiBzdh) {
		this.neichenzhiBzdh = neichenzhiBzdh;
	}
	public String getXiaohemoMatId() {
		return xiaohemoMatId;
	}
	public void setXiaohemoMatId(String xiaohemoMatId) {
		this.xiaohemoMatId = xiaohemoMatId;
	}
	public String getTiaohemoMatId() {
		return tiaohemoMatId;
	}
	public void setTiaohemoMatId(String tiaohemoMatId) {
		this.tiaohemoMatId = tiaohemoMatId;
	}
	public String getXiaohezhMatId() {
		return xiaohezhMatId;
	}
	public void setXiaohezhMatId(String xiaohezhMatId) {
		this.xiaohezhMatId = xiaohezhMatId;
	}
	public String getTiaohezhMatId() {
		return tiaohezhMatId;
	}
	public void setTiaohezhMatId(String tiaohezhMatId) {
		this.tiaohezhMatId = tiaohezhMatId;
	}
	public String getNeichenzMatId() {
		return neichenzMatId;
	}
	public void setNeichenzMatId(String neichenzMatId) {
		this.neichenzMatId = neichenzMatId;
	}
	public Double getXiaohemoEuval() {
		return xiaohemoEuval;
	}
	public void setXiaohemoEuval(Double xiaohemoEuval) {
		this.xiaohemoEuval = xiaohemoEuval;
	}
	public Double getTiaohemoEuval() {
		return tiaohemoEuval;
	}
	public void setTiaohemoEuval(Double tiaohemoEuval) {
		this.tiaohemoEuval = tiaohemoEuval;
	}
	public Double getXiaohezhiEuval() {
		return xiaohezhiEuval;
	}
	public void setXiaohezhiEuval(Double xiaohezhiEuval) {
		this.xiaohezhiEuval = xiaohezhiEuval;
	}
	public Double getTiaohezhiEuval() {
		return tiaohezhiEuval;
	}
	public void setTiaohezhiEuval(Double tiaohezhiEuval) {
		this.tiaohezhiEuval = tiaohezhiEuval;
	}
	public Double getNeichenzhiEuval() {
		return neichenzhiEuval;
	}
	public void setNeichenzhiEuval(Double neichenzhiEuval) {
		this.neichenzhiEuval = neichenzhiEuval;
	}
	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
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
	public Double getXiaohemoVal() {
		return xiaohemoVal;
	}
	public void setXiaohemoVal(Double xiaohemoVal) {
		this.xiaohemoVal = xiaohemoVal;
	}
	public Double getTiaohemoVal() {
		return tiaohemoVal;
	}
	public void setTiaohemoVal(Double tiaohemoVal) {
		this.tiaohemoVal = tiaohemoVal;
	}
	public Double getXiaohezhiVal() {
		return xiaohezhiVal;
	}
	public void setXiaohezhiVal(Double xiaohezhiVal) {
		this.xiaohezhiVal = xiaohezhiVal;
	}
	public Double getTiaohezhiVal() {
		return tiaohezhiVal;
	}
	public void setTiaohezhiVal(Double tiaohezhiVal) {
		this.tiaohezhiVal = tiaohezhiVal;
	}
	public Double getNeichenzhiVal() {
		return neichenzhiVal;
	}
	public void setNeichenzhiVal(Double neichenzhiVal) {
		this.neichenzhiVal = neichenzhiVal;
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
	public Integer getStopTimes() {
		return stopTimes;
	}
	public void setStopTimes(Integer stopTimes) {
		this.stopTimes = stopTimes;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Integer getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}
	
}
