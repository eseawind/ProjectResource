package com.shlanbao.tzsc.wct.qm.qmCheckRecord.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * wct质检概要bean
 * @author shisihai
 *
 */
public class qmCheckRecordBean  {
	
	private String id;
	private String checkType;
	private String oid;
	private String sc;// 样本号(对应sc)
	private String st;// 物料编码（对应st）
	private Double scScore;// 得分（对应sc_score）
	private String scLevel;//质量水平
	private String stVersion;// 物料版本（st_version）
	private String matDes;//物料描述(对应description)
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createDate;//创建日期（xml对应creation_date）
	private String matName;//牌号（对应ii_brand）
	private String iiLot;//生产批次（ii_lot）
	private String teamId;//班组ID
	private String teamName;//班组（ii_team）
	private String shiftId;//班次ID
	private String shiftName;//班次（ii_shift）
	private String iiChk;//检验员名（ii_chk）
	private String iiAdk;//审核人名（ii_adt）
	private String remark;//备注（ii_remark）
	private String rollerCode;//卷烟机ID
	private String rollerName;//卷烟机（ii_coil_tz）
	private String packerCode;//包装机ID
	private String packerName;//包装机（ii_pack_jb_tz）
	private String sealerCode;//封装机ID
	private String sealerName;//封箱机（ii_sealer_tz）
	private String orderNum;//订单号（ii_order）
	private String samSequence;//取样序号（ii_samsequence）
	private String seasonCpsh;//季度（ii_season_cpsh_tz）
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String checkTime;//检测时间（ii_chk_time_cpsh）
	private String shiftTz;//滕州班次（ii_shift_tz）
	private String shiftTzCode;//滕州班次（ii_shift_tz）
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createTime;//数据存表时间
	private String fileInfo;//解析的xml文件信息
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScLevel() {
		return scLevel;
	}
	public void setScLevel(String scLevel) {
		this.scLevel = scLevel;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public Double getScScore() {
		return scScore;
	}
	public void setScScore(Double scScore) {
		this.scScore = scScore;
	}
	public String getStVersion() {
		return stVersion;
	}
	public void setStVersion(String stVersion) {
		this.stVersion = stVersion;
	}
	public String getMatDes() {
		return matDes;
	}
	public void setMatDes(String matDes) {
		this.matDes = matDes;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getIiLot() {
		return iiLot;
	}
	public void setIiLot(String iiLot) {
		this.iiLot = iiLot;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getIiChk() {
		return iiChk;
	}
	public void setIiChk(String iiChk) {
		this.iiChk = iiChk;
	}
	public String getIiAdk() {
		return iiAdk;
	}
	public void setIiAdk(String iiAdk) {
		this.iiAdk = iiAdk;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRollerCode() {
		return rollerCode;
	}
	public void setRollerCode(String rollerCode) {
		this.rollerCode = rollerCode;
	}
	public String getRollerName() {
		return rollerName;
	}
	public void setRollerName(String rollerName) {
		this.rollerName = rollerName;
	}
	public String getPackerCode() {
		return packerCode;
	}
	public void setPackerCode(String packerCode) {
		this.packerCode = packerCode;
	}
	public String getPackerName() {
		return packerName;
	}
	public void setPackerName(String packerName) {
		this.packerName = packerName;
	}
	public String getSealerCode() {
		return sealerCode;
	}
	public void setSealerCode(String sealerCode) {
		this.sealerCode = sealerCode;
	}
	public String getSealerName() {
		return sealerName;
	}
	public void setSealerName(String sealerName) {
		this.sealerName = sealerName;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getSamSequence() {
		return samSequence;
	}
	public void setSamSequence(String samSequence) {
		this.samSequence = samSequence;
	}
	public String getSeasonCpsh() {
		return seasonCpsh;
	}
	public void setSeasonCpsh(String seasonCpsh) {
		this.seasonCpsh = seasonCpsh;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getShiftTz() {
		return shiftTz;
	}
	public void setShiftTz(String shiftTz) {
		this.shiftTz = shiftTz;
	}
	public String getShiftTzCode() {
		return shiftTzCode;
	}
	public void setShiftTzCode(String shiftTzCode) {
		this.shiftTzCode = shiftTzCode;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}
	
	
	

}