package com.shlanbao.tzsc.pms.equ.effective.beans;
/**
 * 
* @ClassName: EffectiveGraphBean 
* @Description: 设备有效作业率 图形使用的实体类 
* @author luo
* @date 2015年3月10日 下午2:48:14 
*
 */
public class EffectiveGraphBean {

	private String[] xvalue;
	private String[] yvalueType;
	private double[][] yvalue;
	
	public String[] getYvalueType() {
		return yvalueType;
	}
	public void setYvalueType(String[] yvalueType) {
		this.yvalueType = yvalueType;
	}
	public String[] getXvalue() {
		return xvalue;
	}
	public void setXvalue(String[] xvalue) {
		this.xvalue = xvalue;
	}
	public double[][] getYvalue() {
		return yvalue;
	}
	public void setYvalue(double[][] yvalue) {
		this.yvalue = yvalue;
	}
	
}
