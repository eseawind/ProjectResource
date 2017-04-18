package com.lanbao.dws.model.wct.eqpManager;


/**
 * [功能说明]：设备故障统计实时保存
 * 
 * */
public class SchStatFaultBean {
	//查询字段
	private String outId; //out表id
	private String eqp;//设备CODE
	private String faultName;//故障名称
	private String faultId;//故障表id
	private String date_;//工单计划开启日期

	//数据库表字段 sch_stat_fault
	private String id;
    private String oid;
    private String name;
    private double time;
    private Long times;
    private Long flag;
	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
	}
	public String getEqp() {
		return eqp;
	}
	public void setEqp(String eqp) {
		this.eqp = eqp;
	}
	public String getFaultName() {
		return faultName;
	}
	public void setFaultName(String faultName) {
		this.faultName = faultName;
	}
	public String getFaultId() {
		return faultId;
	}
	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}
	public String getDate_() {
		return date_;
	}
	public void setDate_(String date_) {
		this.date_ = date_;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public Long getTimes() {
		return times;
	}
	public void setTimes(Long times) {
		this.times = times;
	}
	public Long getFlag() {
		return flag;
	}
	public void setFlag(Long flag) {
		this.flag = flag;
	}
     
   
	 
}
