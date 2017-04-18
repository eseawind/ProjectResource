package com.shlanbao.tzsc.pms.equ.paul.beans;

public class BatchBean {
	private String eqp_type_id;//设备类型
	private int zao;//早班停机时长
	private int zhong;//中班停机时长
	private int wan;//晚班停机时长
	private int zaoT;//周一早班停机保养时长
	private int zhongT;//周四中班停机保养时长
	private String paul_category;//日保id
	private String date;//需要添加的日期 格式 yyyy-MM
	private int del;//是否删除
	private String zaoTD;//早班特殊日
	private String zhongTD;//中班特殊日
	private String date2;//截止日期
	private String paul_category2;//周日保id
	private String paul_category3;//精细保id
	
	public String getPaul_category2() {
		return paul_category2;
	}
	public void setPaul_category2(String paul_category2) {
		this.paul_category2 = paul_category2;
	}
	public String getPaul_category3() {
		return paul_category3;
	}
	public void setPaul_category3(String paul_category3) {
		this.paul_category3 = paul_category3;
	}
	public String getZaoTD() {
		return zaoTD;
	}
	public void setZaoTD(String zaoTD) {
		this.zaoTD = zaoTD;
	}
	public String getZhongTD() {
		return zhongTD;
	}
	public void setZhongTD(String zhongTD) {
		this.zhongTD = zhongTD;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	public String getEqp_type_id() {
		return eqp_type_id;
	}
	public void setEqp_type_id(String eqp_type_id) {
		this.eqp_type_id = eqp_type_id;
	}
	public int getZao() {
		return zao;
	}
	public void setZao(int zao) {
		this.zao = zao;
	}
	public int getZhong() {
		return zhong;
	}
	public void setZhong(int zhong) {
		this.zhong = zhong;
	}
	public int getWan() {
		return wan;
	}
	public void setWan(int wan) {
		this.wan = wan;
	}
	public int getZaoT() {
		return zaoT;
	}
	public void setZaoT(int zaoT) {
		this.zaoT = zaoT;
	}
	public int getZhongT() {
		return zhongT;
	}
	public void setZhongT(int zhongT) {
		this.zhongT = zhongT;
	}
	public String getPaul_category() {
		return paul_category;
	}
	public void setPaul_category(String paul_category) {
		this.paul_category = paul_category;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	
}
