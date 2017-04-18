package com.lanbao.dws.model.wct.qm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

/**
 * 综合测试台数据
 * @author shisihai
 *
 */
@Entity(name="QM_ONLINECHECK")
public class QMOnlineCheck {
	private String id;
	private String materialCode;//物料代码 牌号
	private Integer inspectionType;//InspectionType		0:卷包、成型过程质量检验单类型 	 1:成品检验单类型		2:成型成品检验单类型
	private String orderNumber;//生产工单号					当检验类型是0，使用生产工单号		    当检验类型为1或2，该字段表示检验样本编号
	private String qCBatchs;//检验批次
	private Date timestamp_;//取样时间	yyyy-MM-dd HH:mm:ss
	private String subDate;//取样日期
	private String subBatchCode;//取样批次号	每一次取样为一个取样批yyyyMMdd HH MM KXX SSSS 年  月日 时 分 机台 流水号
	private Integer sampleNumber;//取样数
	private Integer sampleType;//取样类型	1 人工取样 2 自动取样
	private String detailId;//详细数据主键
	private String team;//班组
	//重量
	private Float weightStandVal;//标准值
	private Float weightUpperVal;//上限
	private Float weightLowerVal;//下限
	private Float weightMax;//最大值
	private Float weightMin;//最小值
	private Float weightAvg;//平均值
	private Float weightSD;//SD值（标准偏差）
	private Float weightCV;//变异系数
	private Float weightCPK;//CPK指数
	private Float weightOut;//超标支数
	//圆周
	private Float cirStandVal;//标准值
	private Float cirUpperVal;//上限
	private Float cirLowerVal;//下限
	private Float cirMax;//最大值
	private Float cirMin;//最小值
	private Float cirAvg;//平均值
	private Float cirSD;//SD值（标准偏差）
	private Float cirCV;//变异系数
	private Float cirCPK;//CPK指数
	private Float cirOut;//超标支数
	//圆度
	private Float roundStandVal;//标准值
	private Float roundUpperVal;//上限
	private Float roundLowerVal;//下限
	private Float roundMax;//最大值
	private Float roundMin;//最小值
	private Float roundAvg;//平均值
	private Float roundSD;//SD值（标准偏差）
	private Float roundCV;//变异系数
	private Float roundCPK;//CPK指数
	private Float roundOut;//超标支数
	//长度
	private Float lenStandVal;//标准值
	private Float lenUpperVal;//上限
	private Float lenLowerVal;//下限
	private Float lenMax;//最大值
	private Float lenMin;//最小值
	private Float lenAvg;//平均值
	private Float lenSD;//SD值（标准偏差）
	private Float lenCV;//变异系数
	private Float lenCPK;//CPK指数
	private Float lenOut;//超标支数
	//吸阻
	private Float xzStandVal;//标准值
	private Float xzUpperVal;//上限
	private Float xzLowerVal;//下限
	private Float xzMax;//最大值
	private Float xzMin;//最小值
	private Float xzAvg;//平均值
	private Float xzSD;//SD值（标准偏差）
	private Float xzCV;//变异系数
	private Float xzCPK;//CPK指数
	private Float xzOut;//超标支数
	//总通风率vents
	private Float ventsStandVal;//标准值
	private Float ventsUpperVal;//上限
	private Float ventsLowerVal;//下限
	private Float ventsMax;//最大值
	private Float ventsMin;//最小值
	private Float ventsAvg;//平均值
	private Float ventsSD;//SD值（标准偏差）
	private Float ventsCV;//变异系数
	private Float ventsCPK;//CPK指数
	private Float ventsOut;//超标支数
	//硬度
	private Float hdStandVal;//标准值
	private Float hdUpperVal;//上限
	private Float hdLowerVal;//下限
	private Float hdMax;//最大值
	private Float hdMin;//最小值
	private Float hdAvg;//平均值
	private Float hdSD;//SD值（标准偏差）
	private Float hdCV;//变异系数
	private Float hdCPK;//CPK指数
	private Float hdOut;//超标支数
	
	private List<String> eqpCodes=new ArrayList<>();//设备号
	
	
	public List<String> getEqpCodes() {
		return eqpCodes;
	}
	public void setEqpCodes(List<String> eqpCodes) {
		this.eqpCodes = eqpCodes;
	}
	public String getSubDate() {
		return subDate;
	}
	public void setSubDate(String subDate) {
		this.subDate = subDate;
	}
	public Float getWeightStandVal() {
		return weightStandVal;
	}
	public void setWeightStandVal(Float weightStandVal) {
		this.weightStandVal = weightStandVal;
	}
	public Float getWeightUpperVal() {
		return weightUpperVal;
	}
	public void setWeightUpperVal(Float weightUpperVal) {
		this.weightUpperVal = weightUpperVal;
	}
	public Float getWeightLowerVal() {
		return weightLowerVal;
	}
	public void setWeightLowerVal(Float weightLowerVal) {
		this.weightLowerVal = weightLowerVal;
	}
	public Float getCirStandVal() {
		return cirStandVal;
	}
	public void setCirStandVal(Float cirStandVal) {
		this.cirStandVal = cirStandVal;
	}
	public Float getCirUpperVal() {
		return cirUpperVal;
	}
	public void setCirUpperVal(Float cirUpperVal) {
		this.cirUpperVal = cirUpperVal;
	}
	public Float getCirLowerVal() {
		return cirLowerVal;
	}
	public void setCirLowerVal(Float cirLowerVal) {
		this.cirLowerVal = cirLowerVal;
	}
	public Float getRoundStandVal() {
		return roundStandVal;
	}
	public void setRoundStandVal(Float roundStandVal) {
		this.roundStandVal = roundStandVal;
	}
	public Float getRoundUpperVal() {
		return roundUpperVal;
	}
	public void setRoundUpperVal(Float roundUpperVal) {
		this.roundUpperVal = roundUpperVal;
	}
	public Float getRoundLowerVal() {
		return roundLowerVal;
	}
	public void setRoundLowerVal(Float roundLowerVal) {
		this.roundLowerVal = roundLowerVal;
	}
	public Float getLenStandVal() {
		return lenStandVal;
	}
	public void setLenStandVal(Float lenStandVal) {
		this.lenStandVal = lenStandVal;
	}
	public Float getLenUpperVal() {
		return lenUpperVal;
	}
	public void setLenUpperVal(Float lenUpperVal) {
		this.lenUpperVal = lenUpperVal;
	}
	public Float getLenLowerVal() {
		return lenLowerVal;
	}
	public void setLenLowerVal(Float lenLowerVal) {
		this.lenLowerVal = lenLowerVal;
	}
	public Float getXzStandVal() {
		return xzStandVal;
	}
	public void setXzStandVal(Float xzStandVal) {
		this.xzStandVal = xzStandVal;
	}
	public Float getXzUpperVal() {
		return xzUpperVal;
	}
	public void setXzUpperVal(Float xzUpperVal) {
		this.xzUpperVal = xzUpperVal;
	}
	public Float getXzLowerVal() {
		return xzLowerVal;
	}
	public void setXzLowerVal(Float xzLowerVal) {
		this.xzLowerVal = xzLowerVal;
	}
	public Float getVentsStandVal() {
		return ventsStandVal;
	}
	public void setVentsStandVal(Float ventsStandVal) {
		this.ventsStandVal = ventsStandVal;
	}
	public Float getVentsUpperVal() {
		return ventsUpperVal;
	}
	public void setVentsUpperVal(Float ventsUpperVal) {
		this.ventsUpperVal = ventsUpperVal;
	}
	public Float getVentsLowerVal() {
		return ventsLowerVal;
	}
	public void setVentsLowerVal(Float ventsLowerVal) {
		this.ventsLowerVal = ventsLowerVal;
	}
	public Float getHdStandVal() {
		return hdStandVal;
	}
	public void setHdStandVal(Float hdStandVal) {
		this.hdStandVal = hdStandVal;
	}
	public Float getHdUpperVal() {
		return hdUpperVal;
	}
	public void setHdUpperVal(Float hdUpperVal) {
		this.hdUpperVal = hdUpperVal;
	}
	public Float getHdLowerVal() {
		return hdLowerVal;
	}
	public void setHdLowerVal(Float hdLowerVal) {
		this.hdLowerVal = hdLowerVal;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getTimestamp_() {
		return timestamp_;
	}
	public void setTimestamp_(Date timestamp_) {
		this.timestamp_ = timestamp_;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public Integer getInspectionType() {
		return inspectionType;
	}
	public void setInspectionType(Integer inspectionType) {
		this.inspectionType = inspectionType;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getqCBatchs() {
		return qCBatchs;
	}
	public void setqCBatchs(String qCBatchs) {
		this.qCBatchs = qCBatchs;
	}
	public String getSubBatchCode() {
		return subBatchCode;
	}
	public void setSubBatchCode(String subBatchCode) {
		this.subBatchCode = subBatchCode;
	}
	public Integer getSampleNumber() {
		return sampleNumber;
	}
	public void setSampleNumber(Integer sampleNumber) {
		this.sampleNumber = sampleNumber;
	}
	public Integer getSampleType() {
		return sampleType;
	}
	public void setSampleType(Integer sampleType) {
		this.sampleType = sampleType;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public Float getWeightMax() {
		return weightMax;
	}
	public void setWeightMax(Float weightMax) {
		this.weightMax = weightMax;
	}
	public Float getWeightMin() {
		return weightMin;
	}
	public void setWeightMin(Float weightMin) {
		this.weightMin = weightMin;
	}
	public Float getWeightAvg() {
		return weightAvg;
	}
	public void setWeightAvg(Float weightAvg) {
		this.weightAvg = weightAvg;
	}
	public Float getWeightSD() {
		return weightSD;
	}
	public void setWeightSD(Float weightSD) {
		this.weightSD = weightSD;
	}
	public Float getWeightCV() {
		return weightCV;
	}
	public void setWeightCV(Float weightCV) {
		this.weightCV = weightCV;
	}
	public Float getWeightCPK() {
		return weightCPK;
	}
	public void setWeightCPK(Float weightCPK) {
		this.weightCPK = weightCPK;
	}
	public Float getWeightOut() {
		return weightOut;
	}
	public void setWeightOut(Float weightOut) {
		this.weightOut = weightOut;
	}
	public Float getCirMax() {
		return cirMax;
	}
	public void setCirMax(Float cirMax) {
		this.cirMax = cirMax;
	}
	public Float getCirMin() {
		return cirMin;
	}
	public void setCirMin(Float cirMin) {
		this.cirMin = cirMin;
	}
	public Float getCirAvg() {
		return cirAvg;
	}
	public void setCirAvg(Float cirAvg) {
		this.cirAvg = cirAvg;
	}
	public Float getCirSD() {
		return cirSD;
	}
	public void setCirSD(Float cirSD) {
		this.cirSD = cirSD;
	}
	public Float getCirCV() {
		return cirCV;
	}
	public void setCirCV(Float cirCV) {
		this.cirCV = cirCV;
	}
	public Float getCirCPK() {
		return cirCPK;
	}
	public void setCirCPK(Float cirCPK) {
		this.cirCPK = cirCPK;
	}
	public Float getCirOut() {
		return cirOut;
	}
	public void setCirOut(Float cirOut) {
		this.cirOut = cirOut;
	}
	public Float getRoundMax() {
		return roundMax;
	}
	public void setRoundMax(Float roundMax) {
		this.roundMax = roundMax;
	}
	public Float getRoundMin() {
		return roundMin;
	}
	public void setRoundMin(Float roundMin) {
		this.roundMin = roundMin;
	}
	public Float getRoundAvg() {
		return roundAvg;
	}
	public void setRoundAvg(Float roundAvg) {
		this.roundAvg = roundAvg;
	}
	public Float getRoundSD() {
		return roundSD;
	}
	public void setRoundSD(Float roundSD) {
		this.roundSD = roundSD;
	}
	public Float getRoundCV() {
		return roundCV;
	}
	public void setRoundCV(Float roundCV) {
		this.roundCV = roundCV;
	}
	public Float getRoundCPK() {
		return roundCPK;
	}
	public void setRoundCPK(Float roundCPK) {
		this.roundCPK = roundCPK;
	}
	public Float getRoundOut() {
		return roundOut;
	}
	public void setRoundOut(Float roundOut) {
		this.roundOut = roundOut;
	}
	public Float getLenMax() {
		return lenMax;
	}
	public void setLenMax(Float lenMax) {
		this.lenMax = lenMax;
	}
	public Float getLenMin() {
		return lenMin;
	}
	public void setLenMin(Float lenMin) {
		this.lenMin = lenMin;
	}
	public Float getLenAvg() {
		return lenAvg;
	}
	public void setLenAvg(Float lenAvg) {
		this.lenAvg = lenAvg;
	}
	public Float getLenSD() {
		return lenSD;
	}
	public void setLenSD(Float lenSD) {
		this.lenSD = lenSD;
	}
	public Float getLenCV() {
		return lenCV;
	}
	public void setLenCV(Float lenCV) {
		this.lenCV = lenCV;
	}
	public Float getLenCPK() {
		return lenCPK;
	}
	public void setLenCPK(Float lenCPK) {
		this.lenCPK = lenCPK;
	}
	public Float getLenOut() {
		return lenOut;
	}
	public void setLenOut(Float lenOut) {
		this.lenOut = lenOut;
	}
	public Float getXzMax() {
		return xzMax;
	}
	public void setXzMax(Float xzMax) {
		this.xzMax = xzMax;
	}
	public Float getXzMin() {
		return xzMin;
	}
	public void setXzMin(Float xzMin) {
		this.xzMin = xzMin;
	}
	public Float getXzAvg() {
		return xzAvg;
	}
	public void setXzAvg(Float xzAvg) {
		this.xzAvg = xzAvg;
	}
	public Float getXzSD() {
		return xzSD;
	}
	public void setXzSD(Float xzSD) {
		this.xzSD = xzSD;
	}
	public Float getXzCV() {
		return xzCV;
	}
	public void setXzCV(Float xzCV) {
		this.xzCV = xzCV;
	}
	public Float getXzCPK() {
		return xzCPK;
	}
	public void setXzCPK(Float xzCPK) {
		this.xzCPK = xzCPK;
	}
	public Float getXzOut() {
		return xzOut;
	}
	public void setXzOut(Float xzOut) {
		this.xzOut = xzOut;
	}
	public Float getVentsMax() {
		return ventsMax;
	}
	public void setVentsMax(Float ventsMax) {
		this.ventsMax = ventsMax;
	}
	public Float getVentsMin() {
		return ventsMin;
	}
	public void setVentsMin(Float ventsMin) {
		this.ventsMin = ventsMin;
	}
	public Float getVentsAvg() {
		return ventsAvg;
	}
	public void setVentsAvg(Float ventsAvg) {
		this.ventsAvg = ventsAvg;
	}
	public Float getVentsSD() {
		return ventsSD;
	}
	public void setVentsSD(Float ventsSD) {
		this.ventsSD = ventsSD;
	}
	public Float getVentsCV() {
		return ventsCV;
	}
	public void setVentsCV(Float ventsCV) {
		this.ventsCV = ventsCV;
	}
	public Float getVentsCPK() {
		return ventsCPK;
	}
	public void setVentsCPK(Float ventsCPK) {
		this.ventsCPK = ventsCPK;
	}
	public Float getVentsOut() {
		return ventsOut;
	}
	public void setVentsOut(Float ventsOut) {
		this.ventsOut = ventsOut;
	}
	public Float getHdMax() {
		return hdMax;
	}
	public void setHdMax(Float hdMax) {
		this.hdMax = hdMax;
	}
	public Float getHdMin() {
		return hdMin;
	}
	public void setHdMin(Float hdMin) {
		this.hdMin = hdMin;
	}
	public Float getHdAvg() {
		return hdAvg;
	}
	public void setHdAvg(Float hdAvg) {
		this.hdAvg = hdAvg;
	}
	public Float getHdSD() {
		return hdSD;
	}
	public void setHdSD(Float hdSD) {
		this.hdSD = hdSD;
	}
	public Float getHdCV() {
		return hdCV;
	}
	public void setHdCV(Float hdCV) {
		this.hdCV = hdCV;
	}
	public Float getHdCPK() {
		return hdCPK;
	}
	public void setHdCPK(Float hdCPK) {
		this.hdCPK = hdCPK;
	}
	public Float getHdOut() {
		return hdOut;
	}
	public void setHdOut(Float hdOut) {
		this.hdOut = hdOut;
	}
	

}
