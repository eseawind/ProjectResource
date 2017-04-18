package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 滤棒过程自检
 * @author TRAVLER
 *
 */
public class QmMassFilterProcess {
	private String qmFilterProcessId;
	private QmMassCheck qmMassCheck;
	private String addUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addUserTime;
	private String modifyUserId;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	private String isDelete;
	private String  runCondition;
	private String  isError;
	private String  runStep;
	private String  runRemark;
	private String  shortTime;
	
	private Integer  weightTNum;
	private Integer  weightFNum;
	private Double  weightAVG;
	
	private Integer  pressureTnum;
	private Integer  pressureFnum;
	private Double  pressureAVG;
	
	private Integer  circleTnum;
	private Integer  circleFnum;
	private Double  circleAVG;
	
	private Integer  hardnessTnum;
	private Integer  hardnessFnum;
	private Double  hardnessAVG;
	
	private String  others;
	
	private Integer badNum;
	private String  numUnit;
	public String getQmFilterProcessId() {
		return qmFilterProcessId;
	}
	public void setQmFilterProcessId(String qmFilterProcessId) {
		this.qmFilterProcessId = qmFilterProcessId;
	}
	public QmMassCheck getQmMassCheck() {
		return qmMassCheck;
	}
	public void setQmMassCheck(QmMassCheck qmMassCheck) {
		this.qmMassCheck = qmMassCheck;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public Date getAddUserTime() {
		return addUserTime;
	}
	public void setAddUserTime(Date addUserTime) {
		this.addUserTime = addUserTime;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getRunCondition() {
		return runCondition;
	}
	public void setRunCondition(String runCondition) {
		this.runCondition = runCondition;
	}
	public String getIsError() {
		return isError;
	}
	public void setIsError(String isError) {
		this.isError = isError;
	}
	public String getRunStep() {
		return runStep;
	}
	public void setRunStep(String runStep) {
		this.runStep = runStep;
	}
	public String getRunRemark() {
		return runRemark;
	}
	public void setRunRemark(String runRemark) {
		this.runRemark = runRemark;
	}
	public String getShortTime() {
		return shortTime;
	}
	public void setShortTime(String shortTime) {
		this.shortTime = shortTime;
	}
	public Integer getWeightTNum() {
		return weightTNum;
	}
	public void setWeightTNum(Integer weightTNum) {
		this.weightTNum = weightTNum;
	}
	public Integer getWeightFNum() {
		return weightFNum;
	}
	public void setWeightFNum(Integer weightFNum) {
		this.weightFNum = weightFNum;
	}
	public Double getWeightAVG() {
		return weightAVG;
	}
	public void setWeightAVG(Double weightAVG) {
		this.weightAVG = weightAVG;
	}
	public Integer getPressureTnum() {
		return pressureTnum;
	}
	public void setPressureTnum(Integer pressureTnum) {
		this.pressureTnum = pressureTnum;
	}
	public Integer getPressureFnum() {
		return pressureFnum;
	}
	public void setPressureFnum(Integer pressureFnum) {
		this.pressureFnum = pressureFnum;
	}
	public Double getPressureAVG() {
		return pressureAVG;
	}
	public void setPressureAVG(Double pressureAVG) {
		this.pressureAVG = pressureAVG;
	}
	public Integer getCircleTnum() {
		return circleTnum;
	}
	public void setCircleTnum(Integer circleTnum) {
		this.circleTnum = circleTnum;
	}
	public Integer getCircleFnum() {
		return circleFnum;
	}
	public void setCircleFnum(Integer circleFnum) {
		this.circleFnum = circleFnum;
	}
	public Double getCircleAVG() {
		return circleAVG;
	}
	public void setCircleAVG(Double circleAVG) {
		this.circleAVG = circleAVG;
	}
	public Integer getHardnessTnum() {
		return hardnessTnum;
	}
	public void setHardnessTnum(Integer hardnessTnum) {
		this.hardnessTnum = hardnessTnum;
	}
	public Integer getHardnessFnum() {
		return hardnessFnum;
	}
	public void setHardnessFnum(Integer hardnessFnum) {
		this.hardnessFnum = hardnessFnum;
	}
	public Double getHardnessAVG() {
		return hardnessAVG;
	}
	public void setHardnessAVG(Double hardnessAVG) {
		this.hardnessAVG = hardnessAVG;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public Integer getBadNum() {
		return badNum;
	}
	public void setBadNum(Integer badNum) {
		this.badNum = badNum;
	}
	public String getNumUnit() {
		return numUnit;
	}
	public void setNumUnit(String numUnit) {
		this.numUnit = numUnit;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
