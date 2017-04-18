package com.shlanbao.tzsc.data.runtime.handler;
/**
 * 成型机计算系数
 * @author Leejean
 * @create 2015年1月8日下午3:37:33
 */
public class FilterCalcValue {
	/**
	 * 滤棒盘纸辅料系数
	 */
	private Double panzhiAxisValue;

	public Double getPanzhiAxisValue() {
		return panzhiAxisValue;
	}

	public void setPanzhiAxisValue(Double panzhiAxisValue) {
		this.panzhiAxisValue = panzhiAxisValue;
	}

	public FilterCalcValue() {
		// TODO Auto-generated constructor stub
	}
	
	public FilterCalcValue(Double panzhiAxisValue) {
		super();
		this.panzhiAxisValue = panzhiAxisValue;
	}

	@Override
	public String toString() {
		return "FilterCalcValue [成型机盘纸滚轴系数=" + panzhiAxisValue + "]";
	}
	
}
