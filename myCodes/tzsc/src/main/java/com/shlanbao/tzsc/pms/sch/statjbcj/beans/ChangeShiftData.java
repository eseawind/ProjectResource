package com.shlanbao.tzsc.pms.sch.statjbcj.beans;

import java.util.ArrayList;
import java.util.List;
/**
 * 存放交接班产量信息
 * @author TRAVLER
 *
 */
public class ChangeShiftData {
private Double rSum;
private Double pSum;
//卷烟机产量修改所需字段
private String oid;
private Double rollerQty;
private Double szyQty;
//包装机产量修改所需字段
//调整数
private Double diff;

	
private List<Object[]> JRsumQty=new ArrayList<Object[]>();
private List<Object[]> YRsumQty=new ArrayList<Object[]>();
private List<Object[]> BRsumQty=new ArrayList<Object[]>();



private List<Object[]> JPsumQty=new ArrayList<Object[]>();
private List<Object[]> YPsumQty=new ArrayList<Object[]>();
private List<Object[]> BPsumQty=new ArrayList<Object[]>();


private List<Object[]> JRdata=new ArrayList<Object[]>();
private List<Object[]> YRdata=new ArrayList<Object[]>();
private List<Object[]> BRdata=new ArrayList<Object[]>();


private List<Object[]> JPdata=new ArrayList<Object[]>();
private List<Object[]> YPdata=new ArrayList<Object[]>();
private List<Object[]> BPdata=new ArrayList<Object[]>();

private List<Object[]> JFdata=new ArrayList<Object[]>();
private List<Object[]> YFdata=new ArrayList<Object[]>();
private List<Object[]> BFdata=new ArrayList<Object[]>();

public Double getrSum() {
	return rSum;
}
public void setrSum(Double rSum) {
	this.rSum = rSum;
}
public Double getpSum() {
	return pSum;
}
public void setpSum(Double pSum) {
	this.pSum = pSum;
}
public List<Object[]> getJRsumQty() {
	return JRsumQty;
}
public void setJRsumQty(List<Object[]> jRsumQty) {
	JRsumQty = jRsumQty;
}
public List<Object[]> getYRsumQty() {
	return YRsumQty;
}
public void setYRsumQty(List<Object[]> yRsumQty) {
	YRsumQty = yRsumQty;
}
public List<Object[]> getBRsumQty() {
	return BRsumQty;
}
public void setBRsumQty(List<Object[]> bRsumQty) {
	BRsumQty = bRsumQty;
}
public List<Object[]> getJPsumQty() {
	return JPsumQty;
}
public void setJPsumQty(List<Object[]> jPsumQty) {
	JPsumQty = jPsumQty;
}
public List<Object[]> getYPsumQty() {
	return YPsumQty;
}
public void setYPsumQty(List<Object[]> yPsumQty) {
	YPsumQty = yPsumQty;
}
public List<Object[]> getBPsumQty() {
	return BPsumQty;
}
public void setBPsumQty(List<Object[]> bPsumQty) {
	BPsumQty = bPsumQty;
}
public List<Object[]> getJRdata() {
	return JRdata;
}
public void setJRdata(List<Object[]> jRdata) {
	JRdata = jRdata;
}
public List<Object[]> getYRdata() {
	return YRdata;
}
public void setYRdata(List<Object[]> yRdata) {
	YRdata = yRdata;
}
public List<Object[]> getBRdata() {
	return BRdata;
}
public void setBRdata(List<Object[]> bRdata) {
	BRdata = bRdata;
}
public List<Object[]> getJPdata() {
	return JPdata;
}
public void setJPdata(List<Object[]> jPdata) {
	JPdata = jPdata;
}
public List<Object[]> getYPdata() {
	return YPdata;
}
public void setYPdata(List<Object[]> yPdata) {
	YPdata = yPdata;
}
public List<Object[]> getBPdata() {
	return BPdata;
}
public void setBPdata(List<Object[]> bPdata) {
	BPdata = bPdata;
}
public List<Object[]> getJFdata() {
	return JFdata;
}
public void setJFdata(List<Object[]> jFdata) {
	JFdata = jFdata;
}
public List<Object[]> getYFdata() {
	return YFdata;
}
public void setYFdata(List<Object[]> yFdata) {
	YFdata = yFdata;
}
public List<Object[]> getBFdata() {
	return BFdata;
}
public void setBFdata(List<Object[]> bFdata) {
	BFdata = bFdata;
}
public String getOid() {
	return oid;
}
public void setOid(String oid) {
	this.oid = oid;
}
public Double getRollerQty() {
	return rollerQty;
}
public void setRollerQty(Double rollerQty) {
	this.rollerQty = rollerQty;
}
public Double getSzyQty() {
	return szyQty;
}
public void setSzyQty(Double szyQty) {
	this.szyQty = szyQty;
}
public Double getDiff() {
	return diff;
}
public void setDiff(Double diff) {
	this.diff = diff;
}



}
