package com.shlanbao.tzsc.pms.sch.workorder.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 工单实体
 * @author Leejean
 * @create 2014年11月18日下午4:21:29
 */
public class WorkOrderBean {
	private String id;//id
	private String code;//工单号
	/**
	 1: "卷烟机工单";
	 2: "包装机工单";
	 3: "封箱机工单";
	 4: "成型机工单";
	 */
	private Long type;//类型
	private String mat;//牌号
	private String matId;//牌号
	private String equipment;//
	private String equipmentId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String date;//生产日期
	private String shift;//班次
	private String team;//班组
	private String shiftId;//班次
	private String teamId;//班组
	
	private String shiftTwoId;//班次
	private String teamTwoId;//班组
	private String shiftThreeId;//班次
	private String teamThreeId;//班组
	
	private String shiftName;//班次
	private String teamName;//班组
	private String shiftTwoName;//班次
	private String teamTwoName;//班组
	private String shiftThreeName;//班次
	private String teamThreeName;//班组
	
	
	private Double qty;//计划产量
    private String unit;//单位
    private String unitId;//单位ID(新增生产实绩产量时需要同步工单计量单位)
    private String bth;//批次
    private Long isNew;//是否为新
    private Long prodType;//生产类型
    @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
    private String stim;//计划开始时间
    @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
    private String etim;//计划结束时间
    private String bomVersion;//bom版本号
    /**
     * 1,下发 
	 * 2,运行 -->运行时
	 * 3,暂停 -->MES取消撤销工单时
	 * 4,完成 -->工单完成,更新工单结束时间
	 * 5,终止 -->错误的工单运行时，执行本操作删除生产实绩相关数据
	 * 6,结束 -->工单已经反馈
	 * 7,锁定 -->MES发起撤销时
	 * 8,撤销 -->MES确定撤销时
     */
    private Long sts;//状态
    private Long isCheck;//是否审核
    @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
    private String checkTime;//审核时间
    @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
    private String realStim;//实际开始时间
    @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
    private String realEtim;//时间结束时间
    private Long runSeq;//运行次序
    @DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
    private String recvTime;//接收工单时间
    private Long enable;//启用
    private Long del;//删除
    private String workshop;//车间
    public WorkOrderBean() {
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
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
	public Long getIsNew() {
		return isNew;
	}
	public void setIsNew(Long isNew) {
		this.isNew = isNew;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getProdType() {
		return prodType;
	}
	public void setProdType(Long prodType) {
		this.prodType = prodType;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getBomVersion() {
		return bomVersion;
	}
	public void setBomVersion(String bomVersion) {
		this.bomVersion = bomVersion;
	}
	public Long getSts() {
		return sts;
	}
	public void setSts(Long sts) {
		this.sts = sts;
	}
	public Long getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Long isCheck) {
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
	public Long getRunSeq() {
		return runSeq;
	}
	public void setRunSeq(Long runSeq) {
		this.runSeq = runSeq;
	}
	public String getRecvTime() {
		return recvTime;
	}
	public void setRecvTime(String recvTime) {
		this.recvTime = recvTime;
	}
	public Long getEnable() {
		return enable;
	}
	public void setEnable(Long enable) {
		this.enable = enable;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
	}
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getMatId() {
		return matId;
	}
	public void setMatId(String matId) {
		this.matId = matId;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getShiftTwoId() {
		return shiftTwoId;
	}
	public void setShiftTwoId(String shiftTwoId) {
		this.shiftTwoId = shiftTwoId;
	}
	public String getTeamTwoId() {
		return teamTwoId;
	}
	public void setTeamTwoId(String teamTwoId) {
		this.teamTwoId = teamTwoId;
	}
	public String getShiftThreeId() {
		return shiftThreeId;
	}
	public void setShiftThreeId(String shiftThreeId) {
		this.shiftThreeId = shiftThreeId;
	}
	public String getTeamThreeId() {
		return teamThreeId;
	}
	public void setTeamThreeId(String teamThreeId) {
		this.teamThreeId = teamThreeId;
	}
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
	public String getShiftTwoName() {
		return shiftTwoName;
	}
	public void setShiftTwoName(String shiftTwoName) {
		this.shiftTwoName = shiftTwoName;
	}
	public String getTeamTwoName() {
		return teamTwoName;
	}
	public void setTeamTwoName(String teamTwoName) {
		this.teamTwoName = teamTwoName;
	}
	public String getShiftThreeName() {
		return shiftThreeName;
	}
	public void setShiftThreeName(String shiftThreeName) {
		this.shiftThreeName = shiftThreeName;
	}
	public String getTeamThreeName() {
		return teamThreeName;
	}
	public void setTeamThreeName(String teamThreeName) {
		this.teamThreeName = teamThreeName;
	}
}
