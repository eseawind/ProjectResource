package com.shlanbao.tzsc.data.runtime.handler;
/**
 * 包装机计算系数
 * @author Leejean
 * @create 2015年1月8日下午3:55:32
 */
public class PackerCalcValue {
	/**
	 * 小盒膜 0.0003728
	 */
	private Double xiaohemoValue;
	/**
	 * 条盒膜 0.002072
	 */
	private Double tiaohemoValue;
	/**
	 * 内衬纸 0.00097185
	 */
	private Double neichenzhiValue;
	
	public PackerCalcValue() {
		// TODO Auto-generated constructor stub
	}
	
	public PackerCalcValue(Double xiaohemoValue, Double tiaohemoValue,
			Double neichenzhiValue) {
		super();
		this.xiaohemoValue = xiaohemoValue;
		this.tiaohemoValue = tiaohemoValue;
		this.neichenzhiValue = neichenzhiValue;
	}



	public Double getXiaohemoValue() {
		return xiaohemoValue;
	}
	public void setXiaohemoValue(Double xiaohemoValue) {
		this.xiaohemoValue = xiaohemoValue;
	}
	public Double getTiaohemoValue() {
		return tiaohemoValue;
	}
	public void setTiaohemoValue(Double tiaohemoValue) {
		this.tiaohemoValue = tiaohemoValue;
	}
	public Double getNeichenzhiValue() {
		return neichenzhiValue;
	}
	public void setNeichenzhiValue(Double neichenzhiValue) {
		this.neichenzhiValue = neichenzhiValue;
	}

	@Override
	public String toString() {
		return "PackerCalcValue [小盒膜系数=" + xiaohemoValue
				+ ", 条盒膜系数=" + tiaohemoValue + ", 内衬纸系数="
				+ neichenzhiValue + "]";
	}
	
	
	
}
