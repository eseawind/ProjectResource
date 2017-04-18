package com.shlanbao.tzsc.pms.md.matparam.beans;

/**
 * 辅料计数系数
 * @author Leejean
 * @create 2015年1月22日下午3:09:47
 */
public class MatParamBean {
	private String id;
	private String mat;
	private String matName;
	private Double length;
	private Double width;
	private Double density;
	private Double val;
	private String des;
	private String matType;
	private String eqp_type; //设备类型,如： zj17
	
	public String getMatType() {
		return matType;
	}
	public void setMatType(String matType) {
		this.matType = matType;
	}
	public String getEqp_type() {
		return eqp_type;
	}
	public void setEqp_type(String eqp_type) {
		this.eqp_type = eqp_type;
	}
	
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
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getDensity() {
		return density;
	}
	public void setDensity(Double density) {
		this.density = density;
	}
	public Double getVal() {
		return val;
	}
	public void setVal(Double val) {
		this.val = val;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	
}
