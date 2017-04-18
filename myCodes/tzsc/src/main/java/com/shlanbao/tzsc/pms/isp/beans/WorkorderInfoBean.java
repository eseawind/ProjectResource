package com.shlanbao.tzsc.pms.isp.beans;
/**
 * 工单info
 * @author Leejean
 * @create 2015年1月15日上午8:44:11
 */
public class WorkorderInfoBean {
	private String code;//设备号
	private String teamName;//班组
	private String matName;//牌号
	private Double qty;	//计划产量
	private String shiftName;//班次名称
	
	
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
}
