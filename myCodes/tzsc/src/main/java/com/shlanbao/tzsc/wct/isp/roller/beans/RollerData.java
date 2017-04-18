package com.shlanbao.tzsc.wct.isp.roller.beans;

/**
 * 卷烟机监控页面实体
 * @author Leejean
 * @create 2015年1月4日上午9:36:55
 */
public class RollerData {
	private String equipmentCode;//设备型号
	private String equipmentType;//设备型号
	private String equipmentEdcs;//设备编码
	private String workorderCode;//工单号
	private String shiftName;//班次
	private String teamName;//班组
	private String matName;//牌号
	private Double planQty;//计划产量
	private String unit;
	private String planStim;//计划开始时间
	private String planEtim;//计划结束时间
	private String bthCode;//批次号
	private String stim;//开始时间
	private Double lvbangBzdh;//滤棒标准单耗
	private Double juanyanzhiBzdh;//卷烟纸标准单耗
	private Double shuisongzhiBzdh;//水松纸标准单耗
	private Double lvbangEuval;//滤棒报警上限
	private Double juanyanzhiEuval;//卷烟纸报警上限
	private Double shuisongzhiEuval;//水松纸报警上限
	private String lvbangMatId;//滤棒辅料ID
	private String shuisongzhiMatId;//水松纸辅料id
	private String juanyanzhiMatId;//卷烟纸辅料id
	
	//实时数据部分
	private Integer online;//网络状态  0异常，1正常
	private Double qty;//产量
	private Double badQty;//剔除产量
	private Double lvbangVal;//滤棒消耗
	private Double juanyanzhiVal;//卷烟纸消耗
	private Double shuisongzhiVal;//水松纸消耗
	private Integer lvbangRank;//滤棒单耗排名
	private Integer juanyanzhiRank;//卷烟纸单耗排名
	private Integer shuisongzhiRank;//水松纸单耗排名
	private Double runTime;//运行时间
	private Double stopTime;//停机时间
	private Integer stopTimes;//停机次数
	private Integer speed;//车速
	private Integer runStatus;//运行状态 -1 断网 0停止 1低速运行 2正常运行
	
	
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
	public Double getBadQty() {
		return badQty;
	}
	public void setBadQty(Double badQty) {
		this.badQty = badQty;
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
	public Double getLvbangBzdh() {
		return lvbangBzdh;
	}
	public void setLvbangBzdh(Double lvbangBzdh) {
		this.lvbangBzdh = lvbangBzdh;
	}
	public Double getJuanyanzhiBzdh() {
		return juanyanzhiBzdh;
	}
	public void setJuanyanzhiBzdh(Double juanyanzhiBzdh) {
		this.juanyanzhiBzdh = juanyanzhiBzdh;
	}
	public Double getShuisongzhiBzdh() {
		return shuisongzhiBzdh;
	}
	public void setShuisongzhiBzdh(Double shuisongzhiBzdh) {
		this.shuisongzhiBzdh = shuisongzhiBzdh;
	}
	public Double getLvbangEuval() {
		return lvbangEuval;
	}
	public void setLvbangEuval(Double lvbangEuval) {
		this.lvbangEuval = lvbangEuval;
	}
	public Double getJuanyanzhiEuval() {
		return juanyanzhiEuval;
	}
	public void setJuanyanzhiEuval(Double juanyanzhiEuval) {
		this.juanyanzhiEuval = juanyanzhiEuval;
	}
	public Double getShuisongzhiEuval() {
		return shuisongzhiEuval;
	}
	public void setShuisongzhiEuval(Double shuisongzhiEuval) {
		this.shuisongzhiEuval = shuisongzhiEuval;
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
	public Double getLvbangVal() {
		return lvbangVal;
	}
	public void setLvbangVal(Double lvbangVal) {
		this.lvbangVal = lvbangVal;
	}
	public Double getJuanyanzhiVal() {
		return juanyanzhiVal;
	}
	public void setJuanyanzhiVal(Double juanyanzhiVal) {
		this.juanyanzhiVal = juanyanzhiVal;
	}
	public Double getShuisongzhiVal() {
		return shuisongzhiVal;
	}
	public void setShuisongzhiVal(Double shuisongzhiVal) {
		this.shuisongzhiVal = shuisongzhiVal;
	}
	public Integer getLvbangRank() {
		return lvbangRank;
	}
	public void setLvbangRank(Integer lvbangRank) {
		this.lvbangRank = lvbangRank;
	}
	public Integer getJuanyanzhiRank() {
		return juanyanzhiRank;
	}
	public void setJuanyanzhiRank(Integer juanyanzhiRank) {
		this.juanyanzhiRank = juanyanzhiRank;
	}
	public Integer getShuisongzhiRank() {
		return shuisongzhiRank;
	}
	public void setShuisongzhiRank(Integer shuisongzhiRank) {
		this.shuisongzhiRank = shuisongzhiRank;
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
	
	/*//public List<String> errors;//故障
	*/
	public RollerData() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	/**
	 * 
	 * 卷烟机监控基础信息
	 * @param equipmentCode 设备编码
	 * @param equipmentType 设备类型
	 * @param equipmentEdcs 设备额定车速
	 * @param workorderCode 工单号码
	 * @param matName 牌号
	 * @param planQty 计划产量
	 * @param planStim 计划开始时间
	 * @param planEtim 计划结束时间
	 * @param bthCode 批次号
	 * @param stim 实际开始时间
	 * @param lvbangBzdh 滤棒标准单耗
	 * @param juanyanzhiBzdh 卷烟纸标准单耗
	 * @param shuisongzhiBzdh 水松纸标准单耗
	 * @param lvbangEuval 滤棒超标上限
	 * @param juanyanzhiEuval 水松纸超标上限
	 * @param shuisongzhiEuval 水松纸超标上限
	 */
	public RollerData(String equipmentCode, String equipmentType,
			String equipmentEdcs, String workorderCode, String matName,
			Double planQty,String unit, String planStim, String planEtim, String bthCode,
			String stim, Double lvbangBzdh, Double juanyanzhiBzdh,
			Double shuisongzhiBzdh, Double lvbangEuval, Double juanyanzhiEuval,
			Double shuisongzhiEuval) {
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
		this.bthCode = bthCode;
		this.stim = stim;
		this.lvbangBzdh = lvbangBzdh;
		this.juanyanzhiBzdh = juanyanzhiBzdh;
		this.shuisongzhiBzdh = shuisongzhiBzdh;
		this.lvbangEuval = lvbangEuval;
		this.juanyanzhiEuval = juanyanzhiEuval;
		this.shuisongzhiEuval = shuisongzhiEuval;
	}


	/**
	 * 卷烟机实时数据
	* @param qty
	* @param badQty
	* @param lvbangVal
	* @param juanyanzhiVal
	* @param shuisongzhiVal
	* @param lvbangRank
	* @param juanyanzhiRank
	* @param shuisongzhiRank
	* @param runTime
	* @param stopTime
	* @param stopTimes
	* @param speed
	* @param online
	 */
	public RollerData(Double qty,double badQty, Double lvbangVal, Double juanyanzhiVal,
			Double shuisongzhiVal, Integer lvbangRank, Integer juanyanzhiRank,
			Integer shuisongzhiRank, Double runTime, Double stopTime,
			Integer stopTimes, Integer speed,Integer online) {
		super();
		this.qty = qty;
		this.badQty=badQty;
		this.lvbangVal = lvbangVal;
		this.juanyanzhiVal = juanyanzhiVal;
		this.shuisongzhiVal = shuisongzhiVal;
		this.lvbangRank = lvbangRank;
		this.juanyanzhiRank = juanyanzhiRank;
		this.shuisongzhiRank = shuisongzhiRank;
		this.runTime = runTime;
		this.stopTime = stopTime;
		this.stopTimes = stopTimes;
		this.speed = speed;
		this.online = online;
	}
	
	public Integer getRunStatus() {
		if(this.getOnline()!=null&&this.getOnline()==0){
			return -1;
		}
		if(this.getSpeed()!=null&&this.getSpeed()>0){			
			if(this.getSpeed()>5000){
				return 2;//正常运行
			}
			return 1;//低速运行
		}
		return 0;//运行停止
	}
	public String getLvbangMatId() {
		return lvbangMatId;
	}
	public void setLvbangMatId(String lvbangMatId) {
		this.lvbangMatId = lvbangMatId;
	}
	public String getShuisongzhiMatId() {
		return shuisongzhiMatId;
	}
	public void setShuisongzhiMatId(String shuisongzhiMatId) {
		this.shuisongzhiMatId = shuisongzhiMatId;
	}
	public String getJuanyanzhiMatId() {
		return juanyanzhiMatId;
	}
	public void setJuanyanzhiMatId(String juanyanzhiMatId) {
		this.juanyanzhiMatId = juanyanzhiMatId;
	}
	
	
}
