package com.lanbao.dws.model.wct.eqpManager;
/**
 * [功能说明]：首页-卷包烟机数据填充
 * @author wanchanghuang
 * @date 2016年6月29日10:51:33
 * */
public class MainDatasBean {

	/**卷烟机*/
	private double planRollerQty; //计划产量-箱
	private double lastRollerQty; //实际产量-箱
	private double planRollerNum; //计划产量-包
	private double lastRollerNum; //实际产量-包
	private double roolerEndTime; //结束时间-小时(根据当前车速计算，且车速为零，预计完成时间默认0)
	//实际辅料消耗（左上角数据）
	private double rollerLb;  //滤棒-万只(保留2位小数)
	private double rollerJyz; //卷烟纸-米
	private double rollerSsz; //水松纸-公斤
	//辅料标准消耗
	private double rollerLb2;  //滤棒-万只(保留2位小数)
	private double rollerJyz2; //卷烟纸-米
	private double rollerSsz2; //水松纸-公斤
	//辅料实际消耗百分比（计算之后的数据，含有%）数据取整数
	private String rollerLb3;  //滤棒-万只(保留2位小数)
	private String rollerJyz3; //卷烟纸-米
	private String rollerSsz3; //水松纸-公斤
	//运行统计
	private int rollerRunTime; //运行时间
	private int rollerStopTime;//停机时间
	private int rollerStopNum; //停机次数
	

	/**包装机*/
	private double planPackerQty; //计划产量-箱
	private double lastPackerQty; //实际产量-箱
	private double planPackerNum; //计划产量-包
	private double lastPackerNum; //实际产量-包
	private double packerEndTime; //结束时间-小时(根据当前车速计算，且车速为零，预计完成时间默认0)
	//辅料消耗（左上角）
	private double packerXhz;  //小盒子-张(保留2位小数)
	private double packerXhym; //小盒烟膜-公斤
	private double packerNcz; //内衬纸-公斤
	private double packerThz; //条盒纸-张
	private double packerThym; //条盒烟膜-公斤
	//辅料标准消耗
	private double packerXhz2;  //小盒子-张(保留2位小数)
	private double packerXhym2; //小盒烟膜-公斤
	private double packerNcz2; //内衬纸-公斤
	private double packerThz2; //条盒纸-张
	private double packerThym2; //条盒烟膜-公斤
	//辅料实际消耗百分比（计算之后的数据，含有%）数据取整数
	private String packerXhz3;  //小盒子-张
	private String packerXhym3; //小盒烟膜-公斤
	private String packerNcz3; //内衬纸-公斤
	private String packerThz3; //条盒纸-张
	private String packerThym3; //条盒烟膜-公斤
	//运行统计
	private int packerRunTime; //运行时间
	private int packerStopTime;//停机时间
	private int packerStopNum; //停机次数
	
	public double getPlanRollerQty() {
		return planRollerQty;
	}
	public void setPlanRollerQty(double planRollerQty) {
		this.planRollerQty = planRollerQty;
	}
	public double getLastRollerQty() {
		return lastRollerQty;
	}
	public void setLastRollerQty(double lastRollerQty) {
		this.lastRollerQty = lastRollerQty;
	}
	public double getPlanRollerNum() {
		return planRollerNum;
	}
	public void setPlanRollerNum(double planRollerNum) {
		this.planRollerNum = planRollerNum;
	}
	public double getLastRollerNum() {
		return lastRollerNum;
	}
	public void setLastRollerNum(double lastRollerNum) {
		this.lastRollerNum = lastRollerNum;
	}
	public double getRoolerEndTime() {
		return roolerEndTime;
	}
	public void setRoolerEndTime(double roolerEndTime) {
		this.roolerEndTime = roolerEndTime;
	}
	public double getRollerLb() {
		return rollerLb;
	}
	public void setRollerLb(double rollerLb) {
		this.rollerLb = rollerLb;
	}
	public double getRollerJyz() {
		return rollerJyz;
	}
	public void setRollerJyz(double rollerJyz) {
		this.rollerJyz = rollerJyz;
	}
	public double getRollerSsz() {
		return rollerSsz;
	}
	public void setRollerSsz(double rollerSsz) {
		this.rollerSsz = rollerSsz;
	}
	public double getRollerLb2() {
		return rollerLb2;
	}
	public void setRollerLb2(double rollerLb2) {
		this.rollerLb2 = rollerLb2;
	}
	public double getRollerJyz2() {
		return rollerJyz2;
	}
	public void setRollerJyz2(double rollerJyz2) {
		this.rollerJyz2 = rollerJyz2;
	}
	public double getRollerSsz2() {
		return rollerSsz2;
	}
	public void setRollerSsz2(double rollerSsz2) {
		this.rollerSsz2 = rollerSsz2;
	}
	
	public int getRollerRunTime() {
		return rollerRunTime;
	}
	public void setRollerRunTime(int rollerRunTime) {
		this.rollerRunTime = rollerRunTime;
	}
	public int getRollerStopTime() {
		return rollerStopTime;
	}
	public void setRollerStopTime(int rollerStopTime) {
		this.rollerStopTime = rollerStopTime;
	}
	public int getRollerStopNum() {
		return rollerStopNum;
	}
	public void setRollerStopNum(int rollerStopNum) {
		this.rollerStopNum = rollerStopNum;
	}
	public double getPlanPackerQty() {
		return planPackerQty;
	}
	public void setPlanPackerQty(double planPackerQty) {
		this.planPackerQty = planPackerQty;
	}
	public double getLastPackerQty() {
		return lastPackerQty;
	}
	public void setLastPackerQty(double lastPackerQty) {
		this.lastPackerQty = lastPackerQty;
	}
	public double getPlanPackerNum() {
		return planPackerNum;
	}
	public void setPlanPackerNum(double planPackerNum) {
		this.planPackerNum = planPackerNum;
	}
	public double getLastPackerNum() {
		return lastPackerNum;
	}
	public void setLastPackerNum(double lastPackerNum) {
		this.lastPackerNum = lastPackerNum;
	}
	public double getPackerEndTime() {
		return packerEndTime;
	}
	public void setPackerEndTime(double packerEndTime) {
		this.packerEndTime = packerEndTime;
	}
	public double getPackerXhz() {
		return packerXhz;
	}
	public void setPackerXhz(double packerXhz) {
		this.packerXhz = packerXhz;
	}
	public double getPackerXhym() {
		return packerXhym;
	}
	public void setPackerXhym(double packerXhym) {
		this.packerXhym = packerXhym;
	}
	public double getPackerNcz() {
		return packerNcz;
	}
	public void setPackerNcz(double packerNcz) {
		this.packerNcz = packerNcz;
	}
	public double getPackerThz() {
		return packerThz;
	}
	public void setPackerThz(double packerThz) {
		this.packerThz = packerThz;
	}
	public double getPackerThym() {
		return packerThym;
	}
	public void setPackerThym(double packerThym) {
		this.packerThym = packerThym;
	}
	public double getPackerXhz2() {
		return packerXhz2;
	}
	public void setPackerXhz2(double packerXhz2) {
		this.packerXhz2 = packerXhz2;
	}
	public double getPackerXhym2() {
		return packerXhym2;
	}
	public void setPackerXhym2(double packerXhym2) {
		this.packerXhym2 = packerXhym2;
	}
	public double getPackerNcz2() {
		return packerNcz2;
	}
	public void setPackerNcz2(double packerNcz2) {
		this.packerNcz2 = packerNcz2;
	}
	public double getPackerThz2() {
		return packerThz2;
	}
	public void setPackerThz2(double packerThz2) {
		this.packerThz2 = packerThz2;
	}
	public double getPackerThym2() {
		return packerThym2;
	}
	public void setPackerThym2(double packerThym2) {
		this.packerThym2 = packerThym2;
	}
	
	public String getRollerLb3() {
		return rollerLb3;
	}
	public void setRollerLb3(String rollerLb3) {
		this.rollerLb3 = rollerLb3;
	}
	public String getRollerJyz3() {
		return rollerJyz3;
	}
	public void setRollerJyz3(String rollerJyz3) {
		this.rollerJyz3 = rollerJyz3;
	}
	public String getRollerSsz3() {
		return rollerSsz3;
	}
	public void setRollerSsz3(String rollerSsz3) {
		this.rollerSsz3 = rollerSsz3;
	}
	public String getPackerXhz3() {
		return packerXhz3;
	}
	public void setPackerXhz3(String packerXhz3) {
		this.packerXhz3 = packerXhz3;
	}
	public String getPackerXhym3() {
		return packerXhym3;
	}
	public void setPackerXhym3(String packerXhym3) {
		this.packerXhym3 = packerXhym3;
	}
	public String getPackerNcz3() {
		return packerNcz3;
	}
	public void setPackerNcz3(String packerNcz3) {
		this.packerNcz3 = packerNcz3;
	}
	public String getPackerThz3() {
		return packerThz3;
	}
	public void setPackerThz3(String packerThz3) {
		this.packerThz3 = packerThz3;
	}
	public String getPackerThym3() {
		return packerThym3;
	}
	public void setPackerThym3(String packerThym3) {
		this.packerThym3 = packerThym3;
	}
	public int getPackerRunTime() {
		return packerRunTime;
	}
	public void setPackerRunTime(int packerRunTime) {
		this.packerRunTime = packerRunTime;
	}
	public int getPackerStopTime() {
		return packerStopTime;
	}
	public void setPackerStopTime(int packerStopTime) {
		this.packerStopTime = packerStopTime;
	}
	public int getPackerStopNum() {
		return packerStopNum;
	}
	public void setPackerStopNum(int packerStopNum) {
		this.packerStopNum = packerStopNum;
	}
	

}
