package com.shlanbao.tzsc.wct.sch.fault.beans;

/**
 * 
* @ClassName: FaultBean 
* @Description: 生产故障详细信息 
* @author luo 
* @date 2015年9月22日 下午4:26:09 
*
 */
public class FaultBean {
    private String name;
    private double time;
    private Long times;
    private double time_s;
	
	
	public double getTime_s() {
		return time_s;
	}
	public void setTime_s(double time_s) {
		this.time_s = time_s;
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
	
}
