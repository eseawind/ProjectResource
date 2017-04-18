package com.shlanbao.tzsc.wct.isp.packer.beans;
/**
 * 包装机监控页面实体
 * @author Leejean
 * @create 2015年1月4日上午9:36:55
 */
public class PackerData {
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
	private Double tiaohezhiVal;//条盒纸报警上限
	private Double neichenzhiVal;//内衬纸报警上限
	
	private Integer xiaohemoRank;//小盒膜单耗排名
	private Integer tiaohemoRank;//条盒膜单耗排名
	private Integer xiaohezhiRank;//小盒纸单耗排名
	private Integer tiaohezhiRank;//条盒纸单耗排名
	private Integer neichenzhiRank;//内衬纸单耗排名
	
	private Double runTime;//运行时间
	private Double stopTime;//停机时间
	private Integer stopTimes;//停机次数
	private Integer speed;//车速
	private Double tsQty;//提升机数据
	private Integer runStatus;//运行状态 -1 断网 0停止 1低速运行 2正常运行
	
	public PackerData() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 包装机初始化
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
	 * @param xiaohemoBzdh 小盒膜标准单耗
	 * @param tiaohemoBzdh 条盒膜标准单耗
	 * @param xiaohezhiBzdh 小盒纸标准单耗
	 * @param tiaohezhiBzdh 条盒纸标准单耗
	 * @param neichenzhiBzdh 内衬纸标准单耗
	 * @param xiaohemoEuval 小盒膜报警值
	 * @param tiaohemoEuval 条盒膜报警值
	 * @param xiaohezhiEuval 小盒膜报警值
	 * @param tiaohezhiEuval 条盒纸报警值
	 * @param neichenzhiEuval 内衬纸报警值
	 */
	public PackerData(String equipmentCode, String equipmentType,
			String equipmentEdcs, String workorderCode, String matName,
			Double planQty, String unit, String planStim, String planEtim,
			String bthCode, String stim, Double xiaohemoBzdh,
			Double tiaohemoBzdh, Double xiaohezhiBzdh, Double tiaohezhiBzdh,
			Double neichenzhiBzdh, Double xiaohemoEuval, Double tiaohemoEuval,
			Double xiaohezhiEuval, Double tiaohezhiEuval, Double neichenzhiEuval) {
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
		this.xiaohemoBzdh = xiaohemoBzdh;
		this.tiaohemoBzdh = tiaohemoBzdh;
		this.xiaohezhiBzdh = xiaohezhiBzdh;
		this.tiaohezhiBzdh = tiaohezhiBzdh;
		this.neichenzhiBzdh = neichenzhiBzdh;
		this.xiaohemoEuval = xiaohemoEuval;
		this.tiaohemoEuval = tiaohemoEuval;
		this.xiaohezhiEuval = xiaohezhiEuval;
		this.tiaohezhiEuval = tiaohezhiEuval;
		this.neichenzhiEuval = neichenzhiEuval;
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
	 */
	public PackerData(Integer online, Double qty,double badQty, Double xiaohemoVal,
			Double tiaohemoVal, Double xiaohezhiVal, Double tiaohezhiVal,
			Double neichenzhiVal, Integer xiaohemoRank, Integer tiaohemoRank,
			Integer xiaohezhiRank, Integer tiaohezhiRank,
			Integer neichenzhiRank, Double runTime, Double stopTime,
			Integer stopTimes, Integer speed,Double TSQty) {
		super();
		this.online = online;
		this.qty = qty;
		this.badQty=badQty;
		this.xiaohemoVal = xiaohemoVal;
		this.tiaohemoVal = tiaohemoVal;
		this.xiaohezhiVal = xiaohezhiVal;
		this.tiaohezhiVal = tiaohezhiVal;
		this.neichenzhiVal = neichenzhiVal;
		this.xiaohemoRank = xiaohemoRank;
		this.tiaohemoRank = tiaohemoRank;
		this.xiaohezhiRank = xiaohezhiRank;
		this.tiaohezhiRank = tiaohezhiRank;
		this.neichenzhiRank = neichenzhiRank;
		this.runTime = runTime;
		this.stopTime = stopTime;
		this.stopTimes = stopTimes;
		this.speed = speed;
		this.tsQty=TSQty;
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
	public Integer getXiaohemoRank() {
		return xiaohemoRank;
	}
	public void setXiaohemoRank(Integer xiaohemoRank) {
		this.xiaohemoRank = xiaohemoRank;
	}
	public Integer getTiaohemoRank() {
		return tiaohemoRank;
	}
	public void setTiaohemoRank(Integer tiaohemoRank) {
		this.tiaohemoRank = tiaohemoRank;
	}
	public Integer getXiaohezhiRank() {
		return xiaohezhiRank;
	}
	public void setXiaohezhiRank(Integer xiaohezhiRank) {
		this.xiaohezhiRank = xiaohezhiRank;
	}
	public Integer getTiaohezhiRank() {
		return tiaohezhiRank;
	}
	public void setTiaohezhiRank(Integer tiaohezhiRank) {
		this.tiaohezhiRank = tiaohezhiRank;
	}
	public Integer getNeichenzhiRank() {
		return neichenzhiRank;
	}
	public void setNeichenzhiRank(Integer neichenzhiRank) {
		this.neichenzhiRank = neichenzhiRank;
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
	
	public Double getTsQty() {
		return tsQty;
	}
	public void setTsQty(Double tsQty) {
		tsQty = tsQty;
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
