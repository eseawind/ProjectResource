package com.shlanbao.tzsc.pms.sch.constd.beans;



/**
 * 标准单耗
 * @author Leejean
 * @create 2014年11月25日下午1:21:29
 */
public class ConStdBean {
	private String id;
    private String matProd;//牌号
    private String mat;//辅料（ID）
    private String matName;//辅料名称
    private Double val;//理论值
    private Double uval;//超标上限
    private Double lval;//超标下限
    private Double euval;//报警上限
    private Double elval;//报警下限
    private String des;
    private Long del; //删除
    //add by luther.zhang time:20150429
    private String matUnitId;//辅料单位
    private String matUnitName;//辅料单位
    private String matCount;//辅料量
    private String matProdCode;//牌号
    //end
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMatProd() {
		return matProd;
	}
	public void setMatProd(String matProd) {
		this.matProd = matProd;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public Double getVal() {
		return val;
	}
	public void setVal(Double val) {
		this.val = val;
	}
	public Double getUval() {
		return uval;
	}
	public void setUval(Double uval) {
		this.uval = uval;
	}
	public Double getLval() {
		return lval;
	}
	public void setLval(Double lval) {
		this.lval = lval;
	}
	public Double getEuval() {
		return euval;
	}
	public void setEuval(Double euval) {
		this.euval = euval;
	}
	public Double getElval() {
		return elval;
	}
	public void setElval(Double elval) {
		this.elval = elval;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
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
	
	public String getMatUnitId() {
		return matUnitId;
	}
	public void setMatUnitId(String matUnitId) {
		this.matUnitId = matUnitId;
	}
	public String getMatUnitName() {
		return matUnitName;
	}
	public void setMatUnitName(String matUnitName) {
		this.matUnitName = matUnitName;
	}
	public String getMatCount() {
		return matCount;
	}
	public void setMatCount(String matCount) {
		this.matCount = matCount;
	}
	public String getMatProdCode() {
		return matProdCode;
	}
	public void setMatProdCode(String matProdCode) {
		this.matProdCode = matProdCode;
	}
    
}
