package com.shlanbao.tzsc.pms.cos.brand.beans;

import com.shlanbao.tzsc.utils.tools.MathUtil;

/**
 * @description 成本考核 
 * @author luoliang
 * @time 2015年1月15日13:43:37
 */
public class CostsExamine {
	private String eqdCode;//设备code 注：非ID
	private String eqdID;//设备ＩＤ
	private String eqdType;//类型 卷烟机，包装机
	private String workOrderId;//工单ID
	private String matName;//牌号
	private String teamName;//班组
	private double qty;//产量
	private String unit;//产量单位
	private String time;//生产日期
	private String team;//班组
	private String batch;//批次
	private double sumCosts;//总成本
	private double sumAwardPunish;//奖罚金额
	private double singleCostSum;//单箱成本
	private double Jqty;//卷烟机产量
	private String Junit;//卷烟机单位
	private String shiftID;//班次ID
	private String shiftName;//班次名称
	private double disabled;//残烟量
	private double disabledRate;//残烟率
	private double disabledCheck;//残烟奖罚金额
	private double disabledStandard;//单箱残烟标准
	private double rewardStandard;//残烟一公斤奖励标准
	private double punishStandard;//残烟一公斤处罚标准
	private double singleDisabled;//单箱残烟量
	
	private double sumCosts_JuanBao;//卷包总成本
	private double sumCosts_Chengxing;//成型机总成本
	
	private double sumCosts_B;//包装机总成本
	private double disabledCheck_B;//包装机残烟奖罚金额
	private double disabledRate_B;//包装机残烟率
	private double excluding_B;//卷装机剔除量
	
	private double sumCosts_J;//卷接机总成本
	private double disabledCheck_J;//卷接机残烟奖罚金额
	private double disabledRate_J;//卷接机残烟率
	private double excluding_J;//卷接机剔除量
	
	
	public double getSumCosts_JuanBao() {
		if(sumCosts_JuanBao==0.0){
			return MathUtil.roundHalfUp(countPrice1+countPrice2+countPrice3+countPrice4+countPrice5+countPrice6+countPrice7+countPrice8,2);
		}
		return sumCosts_JuanBao;
	}
	public void setSumCosts_JuanBao(double sumCosts_JuanBao) {
		this.sumCosts_JuanBao = sumCosts_JuanBao;
	}
	public double getSumCosts_Chengxing() {
		if(sumCosts_Chengxing==0.0)
			return MathUtil.roundHalfUp(countPrice9,2);
		return sumCosts_Chengxing;
	}
	public void setSumCosts_Chengxing(double sumCosts_Chengxing) {
		this.sumCosts_Chengxing = sumCosts_Chengxing;
	}
	public double getSumCosts_B() {
		if(sumCosts_B==0.0){
			return MathUtil.roundHalfUp(countPrice1+countPrice2+countPrice3,2);
		}
		return sumCosts_B;
	}
	public void setSumCosts_B(double sumCosts_B) {
		this.sumCosts_B = sumCosts_B;
	}
	public double getSumCosts_J() {
		if(sumCosts_J==0.0){
			return MathUtil.roundHalfUp(countPrice4+countPrice5+countPrice6+countPrice7+countPrice8,2);
		}
		return sumCosts_J;
	}
	public void setSumCosts_J(double sumCosts_J) {
		this.sumCosts_J = sumCosts_J;
	}
	public double getDisabledCheck_B() {
		//包装机残烟奖罚金额
		if(disabledCheck_B==0.0&&qty>0){
			return disabledCheck;
		}
		return disabledCheck_B;
	}
	public void setDisabledCheck_B(double disabledCheck_B) {
		this.disabledCheck_B = disabledCheck_B;
	}
	public double getDisabledCheck_J() {
		//卷接机残烟奖罚金额
		if(disabledCheck_J==0.0&&Jqty>0){
			return disabledCheck;
		}
		return disabledCheck_J;
	}
	public void setDisabledCheck_J(double disabledCheck_J) {
		this.disabledCheck_J = disabledCheck_J;
	}
	public double getExcluding_B() {
		return excluding_B;
	}
	public void setExcluding_B(double excluding_B) {
		this.excluding_B = excluding_B;
	}
	public double getExcluding_J() {
		return excluding_J;
	}
	public void setExcluding_J(double excluding_J) {
		this.excluding_J = excluding_J;
	}
	public double getDisabledRate_B() {
		return disabledRate_B;
	}
	public void setDisabledRate_B(double disabledRate_B) {
		this.disabledRate_B = disabledRate_B;
	}
	public double getDisabledRate_J() {
		return disabledRate_J;
	}
	public void setDisabledRate_J(double disabledRate_J) {
		this.disabledRate_J = disabledRate_J;
	}
	public double getSingleDisabled() {
		return singleDisabled;
	}
	public void setSingleDisabled(double singleDisabled) {
		this.singleDisabled = singleDisabled;
	}
	//计算残烟奖罚金额
	public double getDisabledCheck() {
		return disabledCheck;
	}
	public void setDisabledCheck(double disabledCheck) {
		this.disabledCheck = disabledCheck;
	}
	public double getDisabledStandard() {
		return disabledStandard;
	}
	public void setDisabledStandard(double disabledStandard) {
		this.disabledStandard = disabledStandard;
	}
	public double getRewardStandard() {
		return rewardStandard;
	}
	public void setRewardStandard(double rewardStandard) {
		this.rewardStandard = rewardStandard;
	}
	public double getPunishStandard() {
		return punishStandard;
	}
	public void setPunishStandard(double punishStandard) {
		this.punishStandard = punishStandard;
	}
	public double getDisabled() {
		return disabled;
	}
	public void setDisabled(double disabled) {
		this.disabled = disabled;
	}
	public double getDisabledRate() {
		return disabledRate;
	}
	public void setDisabledRate(double disabledRate) {
		this.disabledRate = disabledRate;
	}
	public String getShiftID() {
		return shiftID;
	}
	public void setShiftID(String shiftID) {
		this.shiftID = shiftID;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public double getJqty() {
		return Jqty;
	}
	public void setJqty(double jqty) {
		Jqty = jqty;
	}
	public String getJunit() {
		return Junit;
	}
	public void setJunit(String junit) {
		Junit = junit;
	}
	public double getSingleCostSum() {
		if(singleCostSum==0.0){
			if(qty>0){
				return MathUtil.roundHalfUp(getSumCosts()*1.00/qty,2);
			}else{
				return 0.0;
			}
		}
		return singleCostSum;
	}
	public void setSingleCostSum(double singleCostSum) {
		this.singleCostSum = singleCostSum;
	}
	public double getSumAwardPunish() {
		if(sumAwardPunish==0.0){
			return MathUtil.roundHalfUp(awardCount1+awardCount2+awardCount3+awardCount4+awardCount5+awardCount6+awardCount7+awardCount8+awardCount9,2);
		}
		return sumAwardPunish;
	}
	public void setSumAwardPunish(double sumAwardPunish) {
		this.sumAwardPunish = sumAwardPunish;
	}
	public String getEqdCode() {
		return eqdCode;
	}
	public void setEqdCode(String eqdCode) {
		this.eqdCode = eqdCode;
	}
	public String getEqdID() {
		return eqdID;
	}
	public void setEqdID(String eqdID) {
		this.eqdID = eqdID;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public double getSumCosts() {
		if(sumCosts!=0.0){
			return sumCosts;
		}
		return countPrice1+countPrice2+countPrice3+countPrice4+countPrice5+countPrice6+countPrice7+countPrice8+countPrice9;
	}
	public void setSumCosts(double sumCosts) {
		this.sumCosts = sumCosts;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	
	//物料类型  卷烟机：滤棒，卷烟纸，接装纸；
	//单耗/单位/标准单耗/单位/消耗量/单价/总成本/单箱成本、奖罚单价、奖罚金额
	//额定单耗、消耗量、单位、实际单耗、单价、总成本、单箱成本、奖罚单价、奖罚金额
	//滤棒
	private double consumption1;//单耗 
	private String unit1;//单位
	private double consumStand1;//标准单耗
	private String cunit1;//标准单耗单位
	private double consumQty1;//消耗量
	private double unitPrice1;//单价
	private double countPrice1;//总成本
	private double singleCost1;//单箱成本
	private double award1;//奖罚单价
	private double awardCount1;//奖罚金额
	private String funit1;//消耗辅料单位
	private String fname1;//辅料名称
	
	//卷烟纸
	private double consumption2;//单耗 
	private String unit2;//单位
	private double consumStand2;//标准单耗
	private String cunit2;//标准单耗单位
	private double consumQty2;//消耗量
	private double unitPrice2;//单价
	private double countPrice2;//总成本
	private double singleCost2;//单箱成本
	private double award2;//奖罚单价
	private double awardCount2;//奖罚金额
	private String funit2;//消耗辅料单位
	private String fname2;//辅料名称
		
	//接装纸
	private double consumption3;//单耗 
	private String unit3;//单位
	private double consumStand3;//标准单耗
	private String cunit3;//标准单耗单位
	private double consumQty3;//消耗量
	private double unitPrice3;//单价
	private double countPrice3;//总成本
	private double singleCost3;//单箱成本
	private double award3;//奖罚单价
	private double awardCount3;//奖罚金额
	private String funit3;//消耗辅料单位
	private String fname3;//辅料名称
	
	// 包装机：小盒商标纸，条盒商标纸，小盒烟膜，条盒烟膜，内衬纸；
	//小盒商标纸
	private double consumption4;//单耗 
	private String unit4;//单位
	private double consumStand4;//标准单耗
	private String cunit4;//标准单耗单位
	private double consumQty4;//消耗量
	private double unitPrice4;//单价
	private double countPrice4;//总成本
	private double singleCost4;//单箱成本
	private double award4;//奖罚单价
	private double awardCount4;//奖罚金额
	private String funit4;//消耗辅料单位
	private String fname4;//辅料名称
	
	//条盒商标纸
	private double consumption5;//单耗 
	private String unit5;//单位
	private double consumStand5;//标准单耗
	private String cunit5;//标准单耗单位
	private double consumQty5;//消耗量
	private double unitPrice5;//单价
	private double countPrice5;//总成本
	private double singleCost5;//单箱成本
	private double award5;//奖罚单价
	private double awardCount5;//奖罚金额
	private String funit5;//消耗辅料单位
	private String fname5;//辅料名称
	
	//小盒烟膜
	private double consumption6;//单耗 
	private String unit6;//单位
	private double consumStand6;//标准单耗
	private String cunit6;//标准单耗单位
	private double consumQty6;//消耗量
	private double unitPrice6;//单价
	private double countPrice6;//总成本
	private double singleCost6;//单箱成本
	private double award6;//奖罚单价
	private double awardCount6;//奖罚金额
	private String funit6;//消耗辅料单位
	private String fname6;//辅料名称
	
	//条盒烟膜
	private double consumption7;//单耗 
	private String unit7;//单位
	private double consumStand7;//标准单耗
	private String cunit7;//标准单耗单位
	private double consumQty7;//消耗量
	private double unitPrice7;//单价
	private double countPrice7;//总成本
	private double singleCost7;//单箱成本
	private double award7;//奖罚单价
	private double awardCount7;//奖罚金额
	private String funit7;//消耗辅料单位
	private String fname7;//辅料名称
	
	//内衬纸
	private double consumption8;//单耗 
	private String unit8;//单位
	private double consumStand8;//标准单耗
	private String cunit8;//标准单耗单位
	private double consumQty8;//消耗量
	private double unitPrice8;//单价
	private double countPrice8;//总成本
	private double singleCost8;//单箱成本
	private double award8;//奖罚单价
	private double awardCount8;//奖罚金额
	private String funit8;//消耗辅料单位
	private String fname8;//辅料名称
	
	//成型：盘纸
	//内衬纸
	private double consumption9;//单耗 
	private String unit9;//单位
	private double consumStand9;//标准单耗
	private String cunit9;//标准单耗单位
	private double consumQty9;//消耗量
	private double unitPrice9;//单价
	private double countPrice9;//总成本
	private double singleCost9;//单箱成本
	private double award9;//奖罚单价
	private double awardCount9;//奖罚金额
	private String funit9;//消耗辅料单位
	private String fname9;//辅料名称
	
	
	public String getFname1() {
		return fname1;
	}
	public void setFname1(String fname1) {
		this.fname1 = fname1;
	}
	public String getFname2() {
		return fname2;
	}
	public void setFname2(String fname2) {
		this.fname2 = fname2;
	}
	public String getFname3() {
		return fname3;
	}
	public void setFname3(String fname3) {
		this.fname3 = fname3;
	}
	public String getFname4() {
		return fname4;
	}
	public void setFname4(String fname4) {
		this.fname4 = fname4;
	}
	public String getFname5() {
		return fname5;
	}
	public void setFname5(String fname5) {
		this.fname5 = fname5;
	}
	public String getFname6() {
		return fname6;
	}
	public void setFname6(String fname6) {
		this.fname6 = fname6;
	}
	public String getFname7() {
		return fname7;
	}
	public void setFname7(String fname7) {
		this.fname7 = fname7;
	}
	public String getFname8() {
		return fname8;
	}
	public void setFname8(String fname8) {
		this.fname8 = fname8;
	}
	public String getFname9() {
		return fname9;
	}
	public void setFname9(String fname9) {
		this.fname9 = fname9;
	}
	public String getFunit1() {
		return funit1;
	}
	public void setFunit1(String funit1) {
		this.funit1 = funit1;
	}
	public String getFunit2() {
		return funit2;
	}
	public void setFunit2(String funit2) {
		this.funit2 = funit2;
	}
	public String getFunit3() {
		return funit3;
	}
	public void setFunit3(String funit3) {
		this.funit3 = funit3;
	}
	public String getFunit4() {
		return funit4;
	}
	public void setFunit4(String funit4) {
		this.funit4 = funit4;
	}
	public String getFunit5() {
		return funit5;
	}
	public void setFunit5(String funit5) {
		this.funit5 = funit5;
	}
	public String getFunit6() {
		return funit6;
	}
	public void setFunit6(String funit6) {
		this.funit6 = funit6;
	}
	public String getFunit7() {
		return funit7;
	}
	public void setFunit7(String funit7) {
		this.funit7 = funit7;
	}
	public String getFunit8() {
		return funit8;
	}
	public void setFunit8(String funit8) {
		this.funit8 = funit8;
	}
	public String getFunit9() {
		return funit9;
	}
	public void setFunit9(String funit9) {
		this.funit9 = funit9;
	}
	public double getSingleCost1() {
		return singleCost1;
	}
	public void setSingleCost1(double singleCost1) {
		this.singleCost1 = singleCost1;
	}
	public double getAward1() {
		return award1;
	}
	public void setAward1(double award1) {
		this.award1 = award1;
	}
	public double getAwardCount1() {
		return awardCount1;
	}
	public void setAwardCount1(double awardCount1) {
		this.awardCount1 = awardCount1;
	}
	public double getSingleCost2() {
		return singleCost2;
	}
	public void setSingleCost2(double singleCost2) {
		this.singleCost2 = singleCost2;
	}
	public double getAward2() {
		return award2;
	}
	public void setAward2(double award2) {
		this.award2 = award2;
	}
	public double getAwardCount2() {
		return awardCount2;
	}
	public void setAwardCount2(double awardCount2) {
		this.awardCount2 = awardCount2;
	}
	public double getSingleCost3() {
		return singleCost3;
	}
	public void setSingleCost3(double singleCost3) {
		this.singleCost3 = singleCost3;
	}
	public double getAward3() {
		return award3;
	}
	public void setAward3(double award3) {
		this.award3 = award3;
	}
	public double getAwardCount3() {
		return awardCount3;
	}
	public void setAwardCount3(double awardCount3) {
		this.awardCount3 = awardCount3;
	}
	public double getSingleCost4() {
		return singleCost4;
	}
	public void setSingleCost4(double singleCost4) {
		this.singleCost4 = singleCost4;
	}
	public double getAward4() {
		return award4;
	}
	public void setAward4(double award4) {
		this.award4 = award4;
	}
	public double getAwardCount4() {
		return awardCount4;
	}
	public void setAwardCount4(double awardCount4) {
		this.awardCount4 = awardCount4;
	}
	public double getSingleCost5() {
		return singleCost5;
	}
	public void setSingleCost5(double singleCost5) {
		this.singleCost5 = singleCost5;
	}
	public double getAward5() {
		return award5;
	}
	public void setAward5(double award5) {
		this.award5 = award5;
	}
	public double getAwardCount5() {
		return awardCount5;
	}
	public void setAwardCount5(double awardCount5) {
		this.awardCount5 = awardCount5;
	}
	public double getSingleCost6() {
		return singleCost6;
	}
	public void setSingleCost6(double singleCost6) {
		this.singleCost6 = singleCost6;
	}
	public double getAward6() {
		return award6;
	}
	public void setAward6(double award6) {
		this.award6 = award6;
	}
	public double getAwardCount6() {
		return awardCount6;
	}
	public void setAwardCount6(double awardCount6) {
		this.awardCount6 = awardCount6;
	}
	public double getSingleCost7() {
		return singleCost7;
	}
	public void setSingleCost7(double singleCost7) {
		this.singleCost7 = singleCost7;
	}
	public double getAward7() {
		return award7;
	}
	public void setAward7(double award7) {
		this.award7 = award7;
	}
	public double getAwardCount7() {
		return awardCount7;
	}
	public void setAwardCount7(double awardCount7) {
		this.awardCount7 = awardCount7;
	}
	public double getSingleCost8() {
		return singleCost8;
	}
	public void setSingleCost8(double singleCost8) {
		this.singleCost8 = singleCost8;
	}
	public double getAward8() {
		return award8;
	}
	public void setAward8(double award8) {
		this.award8 = award8;
	}
	public double getAwardCount8() {
		return awardCount8;
	}
	public void setAwardCount8(double awardCount8) {
		this.awardCount8 = awardCount8;
	}
	public double getSingleCost9() {
		return singleCost9;
	}
	public void setSingleCost9(double singleCost9) {
		this.singleCost9 = singleCost9;
	}
	public double getAward9() {
		return award9;
	}
	public void setAward9(double award9) {
		this.award9 = award9;
	}
	public double getAwardCount9() {
		return awardCount9;
	}
	public void setAwardCount9(double awardCount9) {
		this.awardCount9 = awardCount9;
	}
	public String getEqdType() {
		return eqdType;
	}
	public void setEqdType(String eqdType) {
		this.eqdType = eqdType;
	}
	public String getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	public double getConsumption1() {
		return consumption1;
	}
	public void setConsumption1(double consumption1) {
		this.consumption1 = consumption1;
	}
	public String getUnit1() {
		return unit1;
	}
	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}
	public double getConsumStand1() {
		return consumStand1;
	}
	public void setConsumStand1(double consumStand1) {
		this.consumStand1 = consumStand1;
	}
	public String getCunit1() {
		return cunit1;
	}
	public void setCunit1(String cunit1) {
		this.cunit1 = cunit1;
	}
	public double getConsumQty1() {
		return consumQty1;
	}
	public void setConsumQty1(double consumQty1) {
		this.consumQty1 = consumQty1;
	}
	public double getUnitPrice1() {
		return unitPrice1;
	}
	public void setUnitPrice1(double unitPrice1) {
		this.unitPrice1 = unitPrice1;
	}
	public double getCountPrice1() {
		return countPrice1;
	}
	public void setCountPrice1(double countPrice1) {
		this.countPrice1 = countPrice1;
	}
	public double getConsumption2() {
		return consumption2;
	}
	public void setConsumption2(double consumption2) {
		this.consumption2 = consumption2;
	}
	public String getUnit2() {
		return unit2;
	}
	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}
	public double getConsumStand2() {
		return consumStand2;
	}
	public void setConsumStand2(double consumStand2) {
		this.consumStand2 = consumStand2;
	}
	public String getCunit2() {
		return cunit2;
	}
	public void setCunit2(String cunit2) {
		this.cunit2 = cunit2;
	}
	public double getConsumQty2() {
		return consumQty2;
	}
	public void setConsumQty2(double consumQty2) {
		this.consumQty2 = consumQty2;
	}
	public double getUnitPrice2() {
		return unitPrice2;
	}
	public void setUnitPrice2(double unitPrice2) {
		this.unitPrice2 = unitPrice2;
	}
	public double getCountPrice2() {
		return countPrice2;
	}
	public void setCountPrice2(double countPrice2) {
		this.countPrice2 = countPrice2;
	}
	public double getConsumption3() {
		return consumption3;
	}
	public void setConsumption3(double consumption3) {
		this.consumption3 = consumption3;
	}
	public String getUnit3() {
		return unit3;
	}
	public void setUnit3(String unit3) {
		this.unit3 = unit3;
	}
	public double getConsumStand3() {
		return consumStand3;
	}
	public void setConsumStand3(double consumStand3) {
		this.consumStand3 = consumStand3;
	}
	public String getCunit3() {
		return cunit3;
	}
	public void setCunit3(String cunit3) {
		this.cunit3 = cunit3;
	}
	public double getConsumQty3() {
		return consumQty3;
	}
	public void setConsumQty3(double consumQty3) {
		this.consumQty3 = consumQty3;
	}
	public double getUnitPrice3() {
		return unitPrice3;
	}
	public void setUnitPrice3(double unitPrice3) {
		this.unitPrice3 = unitPrice3;
	}
	public double getCountPrice3() {
		return countPrice3;
	}
	public void setCountPrice3(double countPrice3) {
		this.countPrice3 = countPrice3;
	}
	public double getConsumption4() {
		return consumption4;
	}
	public void setConsumption4(double consumption4) {
		this.consumption4 = consumption4;
	}
	public String getUnit4() {
		return unit4;
	}
	public void setUnit4(String unit4) {
		this.unit4 = unit4;
	}
	public double getConsumStand4() {
		return consumStand4;
	}
	public void setConsumStand4(double consumStand4) {
		this.consumStand4 = consumStand4;
	}
	public String getCunit4() {
		return cunit4;
	}
	public void setCunit4(String cunit4) {
		this.cunit4 = cunit4;
	}
	public double getConsumQty4() {
		return consumQty4;
	}
	public void setConsumQty4(double consumQty4) {
		this.consumQty4 = consumQty4;
	}
	public double getUnitPrice4() {
		return unitPrice4;
	}
	public void setUnitPrice4(double unitPrice4) {
		this.unitPrice4 = unitPrice4;
	}
	public double getCountPrice4() {
		return countPrice4;
	}
	public void setCountPrice4(double countPrice4) {
		this.countPrice4 = countPrice4;
	}
	public double getConsumption5() {
		return consumption5;
	}
	public void setConsumption5(double consumption5) {
		this.consumption5 = consumption5;
	}
	public String getUnit5() {
		return unit5;
	}
	public void setUnit5(String unit5) {
		this.unit5 = unit5;
	}
	public double getConsumStand5() {
		return consumStand5;
	}
	public void setConsumStand5(double consumStand5) {
		this.consumStand5 = consumStand5;
	}
	public String getCunit5() {
		return cunit5;
	}
	public void setCunit5(String cunit5) {
		this.cunit5 = cunit5;
	}
	public double getConsumQty5() {
		return consumQty5;
	}
	public void setConsumQty5(double consumQty5) {
		this.consumQty5 = consumQty5;
	}
	public double getUnitPrice5() {
		return unitPrice5;
	}
	public void setUnitPrice5(double unitPrice5) {
		this.unitPrice5 = unitPrice5;
	}
	public double getCountPrice5() {
		return countPrice5;
	}
	public void setCountPrice5(double countPrice5) {
		this.countPrice5 = countPrice5;
	}
	public double getConsumption6() {
		return consumption6;
	}
	public void setConsumption6(double consumption6) {
		this.consumption6 = consumption6;
	}
	public String getUnit6() {
		return unit6;
	}
	public void setUnit6(String unit6) {
		this.unit6 = unit6;
	}
	public double getConsumStand6() {
		return consumStand6;
	}
	public void setConsumStand6(double consumStand6) {
		this.consumStand6 = consumStand6;
	}
	public String getCunit6() {
		return cunit6;
	}
	public void setCunit6(String cunit6) {
		this.cunit6 = cunit6;
	}
	public double getConsumQty6() {
		return consumQty6;
	}
	public void setConsumQty6(double consumQty6) {
		this.consumQty6 = consumQty6;
	}
	public double getUnitPrice6() {
		return unitPrice6;
	}
	public void setUnitPrice6(double unitPrice6) {
		this.unitPrice6 = unitPrice6;
	}
	public double getCountPrice6() {
		return countPrice6;
	}
	public void setCountPrice6(double countPrice6) {
		this.countPrice6 = countPrice6;
	}
	public double getConsumption7() {
		return consumption7;
	}
	public void setConsumption7(double consumption7) {
		this.consumption7 = consumption7;
	}
	public String getUnit7() {
		return unit7;
	}
	public void setUnit7(String unit7) {
		this.unit7 = unit7;
	}
	public double getConsumStand7() {
		return consumStand7;
	}
	public void setConsumStand7(double consumStand7) {
		this.consumStand7 = consumStand7;
	}
	public String getCunit7() {
		return cunit7;
	}
	public void setCunit7(String cunit7) {
		this.cunit7 = cunit7;
	}
	public double getConsumQty7() {
		return consumQty7;
	}
	public void setConsumQty7(double consumQty7) {
		this.consumQty7 = consumQty7;
	}
	public double getUnitPrice7() {
		return unitPrice7;
	}
	public void setUnitPrice7(double unitPrice7) {
		this.unitPrice7 = unitPrice7;
	}
	public double getCountPrice7() {
		return countPrice7;
	}
	public void setCountPrice7(double countPrice7) {
		this.countPrice7 = countPrice7;
	}
	public double getConsumption8() {
		return consumption8;
	}
	public void setConsumption8(double consumption8) {
		this.consumption8 = consumption8;
	}
	public String getUnit8() {
		return unit8;
	}
	public void setUnit8(String unit8) {
		this.unit8 = unit8;
	}
	public double getConsumStand8() {
		return consumStand8;
	}
	public void setConsumStand8(double consumStand8) {
		this.consumStand8 = consumStand8;
	}
	public String getCunit8() {
		return cunit8;
	}
	public void setCunit8(String cunit8) {
		this.cunit8 = cunit8;
	}
	public double getConsumQty8() {
		return consumQty8;
	}
	public void setConsumQty8(double consumQty8) {
		this.consumQty8 = consumQty8;
	}
	public double getUnitPrice8() {
		return unitPrice8;
	}
	public void setUnitPrice8(double unitPrice8) {
		this.unitPrice8 = unitPrice8;
	}
	public double getCountPrice8() {
		return countPrice8;
	}
	public void setCountPrice8(double countPrice8) {
		this.countPrice8 = countPrice8;
	}
	public double getConsumption9() {
		return consumption9;
	}
	public void setConsumption9(double consumption9) {
		this.consumption9 = consumption9;
	}
	public String getUnit9() {
		return unit9;
	}
	public void setUnit9(String unit9) {
		this.unit9 = unit9;
	}
	public double getConsumStand9() {
		return consumStand9;
	}
	public void setConsumStand9(double consumStand9) {
		this.consumStand9 = consumStand9;
	}
	public String getCunit9() {
		return cunit9;
	}
	public void setCunit9(String cunit9) {
		this.cunit9 = cunit9;
	}
	public double getConsumQty9() {
		return consumQty9;
	}
	public void setConsumQty9(double consumQty9) {
		this.consumQty9 = consumQty9;
	}
	public double getUnitPrice9() {
		return unitPrice9;
	}
	public void setUnitPrice9(double unitPrice9) {
		this.unitPrice9 = unitPrice9;
	}
	public double getCountPrice9() {
		return countPrice9;
	}
	public void setCountPrice9(double countPrice9) {
		this.countPrice9 = countPrice9;
	}
	
	
}
