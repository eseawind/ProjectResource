package com.shlanbao.tzsc.data.runtime.handler;
/**
 * 卷烟机计算系数
 * @author Leejean
 * @create 2015年1月8日下午3:55:32
 */
public class RollerCalcValue {
	/**
	 * 水松纸辅料系数  0.002356
	 */
	private Double shuisongzhiValue ;
	/**
	 * 水松纸滚轴系数  JZ17:0.0826 PASSIM 0.109956
	 */
	private Double shuisongzhiAxisValue ;
	/**
	 * 卷烟纸滚轴系数  JZ17:0.0826 PASSIM 0.094247778
	 */
	private Double juanyanzhiAxisValue;
	
	
	public RollerCalcValue() {
		// TODO Auto-generated constructor stub
	}
	
	public RollerCalcValue(Double shuisongzhiValue,
			Double shuisongzhiAxisValue, Double juanyanzhiAxisValue) {
		super();
		this.shuisongzhiValue = shuisongzhiValue;
		this.shuisongzhiAxisValue = shuisongzhiAxisValue;
		this.juanyanzhiAxisValue = juanyanzhiAxisValue;
	}



	public Double getShuisongzhiValue() {
		return shuisongzhiValue;
	}
	public void setShuisongzhiValue(Double shuisongzhiValue) {
		this.shuisongzhiValue = shuisongzhiValue;
	}
	public Double getShuisongzhiAxisValue() {
		return shuisongzhiAxisValue;
	}
	public void setShuisongzhiAxisValue(Double shuisongzhiAxisValue) {
		this.shuisongzhiAxisValue = shuisongzhiAxisValue;
	}
	public Double getJuanyanzhiAxisValue() {
		return juanyanzhiAxisValue;
	}
	public void setJuanyanzhiAxisValue(Double juanyanzhiAxisValue) {
		this.juanyanzhiAxisValue = juanyanzhiAxisValue;
	}

	@Override
	public String toString() {
		return "RollerCalcValue [水松纸系数=" + shuisongzhiValue
				+ ", 卷烟机水松纸滚轴系数=" + shuisongzhiAxisValue
				+ ", 卷烟机卷烟纸滚轴系数=" + juanyanzhiAxisValue + "]";
	}
	
	
	
}
