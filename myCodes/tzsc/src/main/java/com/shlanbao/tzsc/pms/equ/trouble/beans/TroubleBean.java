package com.shlanbao.tzsc.pms.equ.trouble.beans;

import java.util.Date;

import com.shlanbao.tzsc.utils.tools.StringUtil;
/**
 * 
* @ClassName: TroubleBean 
* @Description: 设备点检、轮保、检修时发生故障记录 
* @author luo 
* @date 2015年10月14日 下午4:25:10 
*
 */
public class TroubleBean {

	private String id;
	private String equ_id;//设备ID
	private String equ_name;
	private String shift_id;//班次ID
	private String shift_name;
	private String name;//故障名称
	private String des;//故障描述
	private String source_id;//故障来源ID，存放点检   检修    检修的ID
	private String source_name;
	private String create_user_id;//创建故障信息的用户ID
	private String create_user_name;
	private String create_date;//创建故障信息时间
	private String source;//1-点检   2-维修呼叫   3-轮保   4-备品备件  5、检修   对应SOURCE_ID
	
	private String startDate;//查询条件,开始日期
	private String endDate;//查询条件,结束日期
	
	private String sourceString;
	
	String[] sourceArray=new String[]{"点检","维修呼叫","轮保","备品备件","检修"};
	public String getSourceString() {
		if(StringUtil.notNull(sourceString)){
			return sourceString;
		}else{
			if(StringUtil.isInteger(source)){
				return sourceArray[Integer.parseInt(source)-1];
			}else{
				return "";
			}
		}
		
	}
	public void setSourceString(String sourceString) {
		this.sourceString = sourceString;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqu_id() {
		return equ_id;
	}
	public void setEqu_id(String equ_id) {
		this.equ_id = equ_id;
	}
	public String getEqu_name() {
		return equ_name;
	}
	public void setEqu_name(String equ_name) {
		this.equ_name = equ_name;
	}
	public String getShift_id() {
		return shift_id;
	}
	public void setShift_id(String shift_id) {
		this.shift_id = shift_id;
	}
	public String getShift_name() {
		return shift_name;
	}
	public void setShift_name(String shift_name) {
		this.shift_name = shift_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getSource_id() {
		return source_id;
	}
	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}
	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
}
