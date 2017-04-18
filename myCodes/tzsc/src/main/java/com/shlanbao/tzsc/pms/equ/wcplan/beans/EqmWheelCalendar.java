package com.shlanbao.tzsc.pms.equ.wcplan.beans;
/**
 * 
* @ClassName: EqmWheelCalendar 
* @Description: 轮保日历bean 
* @author luo
* @date 2015年6月25日 上午9:46:20 
*
 */
public class EqmWheelCalendar {
	private String id;
	private String name;
	private String start;
	private String end;
	private String color;
	private String title;
	
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
