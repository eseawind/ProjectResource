package com.shlanbao.tzsc.wct.qm.massprocess.beans;

import java.util.Date;

import com.shlanbao.tzsc.base.mapping.QmMassCheck;
import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
 * 滤棒过程自检
 * @author TRAVLER
 *
 */
public class QmMassFilterProcessBean {
	private String qmFilterProcessId;//id
	private String qmMassCheckId;//外键
	private String addUserId;//添加人id
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date addUserTime;//添加时间
	private String modifyUserId;//修改人id
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;//修改时间
	private String isDelete;//是否删除 0 未删除 1 删除
	private String  runCondition;//外观质量情况
	private String  isError;//判断
	private String  runStep;//处理措施
	private String  runRemark;//备注
	private String  shortTime;//页面显示的修改时间
	private String proWorkId;//工单主键ID
	
	
	private Integer  weightTNum;//重量正支数
	private Integer  weightFNum;//负
	private Double  weightAVG;//均值
	
	private Integer  pressureTnum;//吸阻正支数
	private Integer  pressureFnum;//负
	private Double  pressureAVG;//均值
	
	private Integer  circleTnum;//圆周正支数
	private Integer  circleFnum;//负
	private Double  circleAVG;//均值
	
	private Integer  hardnessTnum;//硬度正支数
	private Integer  hardnessFnum;//负
	private Double  hardnessAVG;//均值
	
	
	private String  others;//其他
	
	private Integer badNum;//质量问题支数
	private String  numUnit;//单位
	
	
	private String mdShiftId;//班次ID
	private String mdShiftName;
	private String mdMatId;//牌号ID
	private String mdMatName;
	private String mdEqmentId;//机台号(设备)ID
	private String mdEqmentName;
	
	private String mdDcgId;
	private String mdDcgName;//挡车工姓名
	
	public String getMdDcgId() {
		return mdDcgId;
	}
	public void setMdDcgId(String mdDcgId) {
		this.mdDcgId = mdDcgId;
	}
	public String getMdDcgName() {
		return mdDcgName;
	}
	public void setMdDcgName(String mdDcgName) {
		this.mdDcgName = mdDcgName;
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
	public String getMdEqmentId() {
		return mdEqmentId;
	}
	public void setMdEqmentId(String mdEqmentId) {
		this.mdEqmentId = mdEqmentId;
	}
	public String getMdEqmentName() {
		return mdEqmentName;
	}
	public void setMdEqmentName(String mdEqmentName) {
		this.mdEqmentName = mdEqmentName;
	}
	public String getQmFilterProcessId() {
		return qmFilterProcessId;
	}
	public void setQmFilterProcessId(String qmFilterProcessId) {
		this.qmFilterProcessId = qmFilterProcessId;
	}
	public String getQmMassCheckId() {
		return qmMassCheckId;
	}
	public void setQmMassCheckId(String qmMassCheckId) {
		this.qmMassCheckId = qmMassCheckId;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public String getProWorkId() {
		return proWorkId;
	}
	public void setProWorkId(String proWorkId) {
		this.proWorkId = proWorkId;
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
		if(weightTNum==null){
			weightTNum=0;
		}
		this.weightTNum = weightTNum;
	}
	public Integer getWeightFNum() {
		return weightFNum;
	}
	public void setWeightFNum(Integer weightFNum) {
		if(weightFNum==null){
			weightFNum=0;
		}
		this.weightFNum = weightFNum;
	}
	public Double getWeightAVG() {
		return weightAVG;
	}
	public void setWeightAVG(Double weightAVG) {
		if(weightAVG==null){
			weightAVG=0.0;
		}
		this.weightAVG = weightAVG;
	}
	public Integer getPressureTnum() {
		return pressureTnum;
	}
	public void setPressureTnum(Integer pressureTnum) {
		if(pressureTnum==null){
			pressureTnum=0;
		}
		this.pressureTnum = pressureTnum;
	}
	public Integer getPressureFnum() {
		return pressureFnum;
	}
	public void setPressureFnum(Integer pressureFnum) {
		if(pressureFnum==null){
			pressureFnum=0;
		}
		this.pressureFnum = pressureFnum;
	}
	public Double getPressureAVG() {
		return pressureAVG;
	}
	public void setPressureAVG(Double pressureAVG) {
		if(pressureAVG==null){
			pressureAVG=0.0;
		}
		this.pressureAVG = pressureAVG;
	}
	public Integer getCircleTnum() {
		return circleTnum;
	}
	public void setCircleTnum(Integer circleTnum) {
		if(circleTnum==null){
			circleTnum=0;
		}
		this.circleTnum = circleTnum;
	}
	public Integer getCircleFnum() {
		return circleFnum;
	}
	public void setCircleFnum(Integer circleFnum) {
		if(circleFnum==null){
			circleFnum=0;
		}
		this.circleFnum = circleFnum;
	}
	public Double getCircleAVG() {
		return circleAVG;
	}
	public void setCircleAVG(Double circleAVG) {
		if(circleAVG==null){
			circleAVG=0.0;
		}
		this.circleAVG = circleAVG;
	}
	public Integer getHardnessTnum() {
		return hardnessTnum;
	}
	public void setHardnessTnum(Integer hardnessTnum) {
		if(hardnessTnum==null){
			hardnessTnum=0;
		}
		this.hardnessTnum = hardnessTnum;
	}
	public Integer getHardnessFnum() {
		return hardnessFnum;
	}
	public void setHardnessFnum(Integer hardnessFnum) {
		if(hardnessFnum==null){
			hardnessFnum=0;
		}
		this.hardnessFnum = hardnessFnum;
	}
	public Double getHardnessAVG() {
		return hardnessAVG;
	}
	public void setHardnessAVG(Double hardnessAVG) {
		if(hardnessAVG==null){
			hardnessAVG=0.0;
		}
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
		if(badNum==null){
			badNum=0;
		}
		this.badNum = badNum;
	}
	public String getNumUnit() {
		return numUnit;
	}
	public void setNumUnit(String numUnit) {
		this.numUnit = numUnit;
	}
	
}
