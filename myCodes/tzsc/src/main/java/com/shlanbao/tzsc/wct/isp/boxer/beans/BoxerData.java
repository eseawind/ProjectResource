package com.shlanbao.tzsc.wct.isp.boxer.beans;
/**
 * 封箱机监控页面实体
 * @author Leejean
 * @create 2015年1月4日上午9:36:55
 */
public class BoxerData {
	private String equipmentCode;//设备型号
	private String equipmentType;//设备型号
	private String equipmentEdcs;//设备编码
	private String workorderCode;//工单号
	private String matName;//牌号
	private Double planQty;//计划产量
	private String unit;
	private String planStim;//计划开始时间
	private String planEtim;//计划结束时间
	private String bthCode;//批次号
	private String stim;//开始时间
	
	/**
	 * 消耗部分封箱机无法采集，待后期如果能够拿到辅料点表再议。Leejean
	 */
	
	
	//实时数据部分
	private Integer online;//网络状态  0异常，1正常
	private Double qty;//产量
	private Double badQty;//剔除产量
	
	
	private Double runTime;//运行时间
	private Double stopTime;//停机时间
	private Integer stopTimes;//停机次数
	private Integer speed;//车速
	private Integer runStatus;//运行状态 -1 断网 0停止 1低速运行 2正常运行
	
	public BoxerData() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 封箱机初始化
	  * @param equipmentCode 设备编码
	 * @param equipmentType 设备类型
	 * @param equipmentEdcs 设备额定车速
	 * @param workorderCode 工单号码
	 * @param matName 牌号
	 * @param planQty 计划产量
	 * @param planStim 计划开始时间
	 * @param planEtim 计划结束时间
	 * @param bthCode 批次号
	 * @Param stim 实际开始时间
	 */
	public BoxerData(String equipmentCode, String equipmentType,
			String equipmentEdcs, String workorderCode, String matName,
			Double planQty, String unit, String planStim, String planEtim,
			String bthCode, String stim) {
		super();
		this.equipmentCode = equipmentCode;
		this.equipmentType = equipmentType;
		this.equipmentEdcs = equipmentEdcs;
		this.workorderCode = workorderCode;
		this.matName = matName;
		this.planQty = planQty;
		this.unit = unit;
		this.planStim = planStim;
		this.planEtim = planEtim;
		this.stim = stim;
		this.bthCode = bthCode;
	}
	/**
	 * 封箱机实时数据
	 * @param online 网络状态
	 * @param qty 产量
	 * @param runTime 运行时间
	 * @param stopTime 停机时间
	 * @param stopTimes 停机次数
	 * @param speed 速度
	 */
	public BoxerData(Integer online, Double qty, Double runTime, Double stopTime,
			Integer stopTimes, Integer speed) {
		super();
		this.online = online;
		this.qty = qty;
		this.runTime = runTime;
		this.stopTime = stopTime;
		this.stopTimes = stopTimes;
		this.speed = speed;
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
	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}
	
	public Double getBadQty() {
		return badQty;
	}
	public void setBadQty(Double badQty) {
		this.badQty = badQty;
	}
	public Integer getRunStatus() {
		if(this.getOnline()!=null&&this.getOnline()==0){
			return -1;
		}
		if(this.getSpeed()!=null&&this.getSpeed()>0){			
			if(this.getSpeed()>200){
				return 2;//正常运行
			}
			return 1;//低速运行
		}
		return 0;//运行停止
	}
	
}
