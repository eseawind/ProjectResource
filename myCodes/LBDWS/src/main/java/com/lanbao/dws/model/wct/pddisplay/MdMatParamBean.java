package com.lanbao.dws.model.wct.pddisplay;
/**
 * 辅料系数表 md_mat_param
 * 
 * */
public class MdMatParamBean {
	
	private String id; 
	private String mat; //辅料表ID
	private double length; //高
	private double width; //宽
	private double density; //密度
	private double val; //系数值
	private String des; //备注
	private String eqpType; //设备型号  zj17等
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getDensity() {
		return density;
	}
	public void setDensity(double density) {
		this.density = density;
	}
	public double getVal() {
		return val;
	}
	public void setVal(double val) {
		this.val = val;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getEqpType() {
		return eqpType;
	}
	public void setEqpType(String eqpType) {
		this.eqpType = eqpType;
	}

	
}
