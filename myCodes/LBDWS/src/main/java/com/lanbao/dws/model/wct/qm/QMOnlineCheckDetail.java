package com.lanbao.dws.model.wct.qm;

import javax.persistence.Entity;

//综合测试台检测详细数据
@Entity(name="QM_ONLINECHECK_DETAIL")
public class QMOnlineCheckDetail {
	private String id;
	private String pid;//主表唯一标志   pid=detailId
	private Float weight;//重量
	private Float cir;//圆周
	private Float round;//圆度
	private Float len;//长度
	private Float xz;//吸阻
	private Float vents;//总通风率
	private Float hd;//硬度
	private Integer num;//编号
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public Float getCir() {
		return cir;
	}
	public void setCir(Float cir) {
		this.cir = cir;
	}
	public Float getRound() {
		return round;
	}
	public void setRound(Float round) {
		this.round = round;
	}
	public Float getLen() {
		return len;
	}
	public void setLen(Float len) {
		this.len = len;
	}
	public Float getXz() {
		return xz;
	}
	public void setXz(Float xz) {
		this.xz = xz;
	}
	public Float getVents() {
		return vents;
	}
	public void setVents(Float vents) {
		this.vents = vents;
	}
	public Float getHd() {
		return hd;
	}
	public void setHd(Float hd) {
		this.hd = hd;
	}
	
}
