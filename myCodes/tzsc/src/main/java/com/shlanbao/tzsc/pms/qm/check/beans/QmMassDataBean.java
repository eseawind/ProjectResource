package com.shlanbao.tzsc.pms.qm.check.beans;

import java.util.List;

public class QmMassDataBean {
	private String team;//班组
	private String shift;//班次
	private String equ;//设备名
	private String matName;//牌名
	private String userD;//挡车工名
	private String userC;//操作工名
	private String date;//工单日期
	private String xh;//小盒钢印
	private String th;//条盒钢印
	List<Object[]> qmMassFirstC;//操作工首检纪录
	List<Object[]> qmMassFirstZ;//自检员首检
	List<Object[]> qmMassFirstG;//工段长首检纪录
	
	List<Object[]> qmMassProcessD;//挡车工过程检
	List<Object[]> qmMassProcessC;//操作工过程检
	
	
	List<Object[]> qmMassExcipient;//辅料自检自控确认纪录
	List<Object[]> qmMassOnline;//在线物理指标自检纪录（综合测试台      卷烟机）
	List<Object[]> qmMassStem;//丝含梗质量自检纪录（卷烟机）
	List<Object[]> qmMassSp;//三乙酸甘油酯质量自检纪录（成型机）


	
	
	public List<Object[]> getQmMassSp() {
		return qmMassSp;
	}

	public void setQmMassSp(List<Object[]> qmMassSp) {
		this.qmMassSp = qmMassSp;
	}

	public String getXh() {
		return xh;
	}

	public List<Object[]> getQmMassStem() {
		return qmMassStem;
	}


	public void setXh(String xh) {
		this.xh = xh;
	}


	public String getTh() {
		return th;
	}


	public void setTh(String th) {
		this.th = th;
	}


	public void setQmMassStem(List<Object[]> qmMassStem) {
		this.qmMassStem = qmMassStem;
	}


	public List<Object[]> getQmMassOnline() {
		return qmMassOnline;
	}


	public void setQmMassOnline(List<Object[]> qmMassOnline) {
		this.qmMassOnline = qmMassOnline;
	}


	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}


	public String getShift() {
		return shift;
	}


	public void setShift(String shift) {
		this.shift = shift;
	}


	public String getEqu() {
		return equ;
	}


	public void setEqu(String equ) {
		this.equ = equ;
	}


	public String getMatName() {
		return matName;
	}


	public void setMatName(String matName) {
		this.matName = matName;
	}


	public String getUserD() {
		return userD;
	}


	public void setUserD(String userD) {
		this.userD = userD;
	}


	public String getUserC() {
		return userC;
	}


	public void setUserC(String userC) {
		this.userC = userC;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public List<Object[]> getQmMassFirstC() {
		return qmMassFirstC;
	}


	public void setQmMassFirstC(List<Object[]> qmMassFirstC) {
		this.qmMassFirstC = qmMassFirstC;
	}


	public List<Object[]> getQmMassFirstZ() {
		return qmMassFirstZ;
	}


	public void setQmMassFirstZ(List<Object[]> qmMassFirstZ) {
		this.qmMassFirstZ = qmMassFirstZ;
	}


	public List<Object[]> getQmMassFirstG() {
		return qmMassFirstG;
	}


	public void setQmMassFirstG(List<Object[]> qmMassFirstG) {
		this.qmMassFirstG = qmMassFirstG;
	}


	public List<Object[]> getQmMassProcessD() {
		return qmMassProcessD;
	}


	public void setQmMassProcessD(List<Object[]> qmMassProcessD) {
		this.qmMassProcessD = qmMassProcessD;
	}


	public List<Object[]> getQmMassProcessC() {
		return qmMassProcessC;
	}


	public void setQmMassProcessC(List<Object[]> qmMassProcessC) {
		this.qmMassProcessC = qmMassProcessC;
	}


	public List<Object[]> getQmMassExcipient() {
		return qmMassExcipient;
	}


	public void setQmMassExcipient(List<Object[]> qmMassExcipient) {
		this.qmMassExcipient = qmMassExcipient;
	}
	
	
	
	
	
	
}
