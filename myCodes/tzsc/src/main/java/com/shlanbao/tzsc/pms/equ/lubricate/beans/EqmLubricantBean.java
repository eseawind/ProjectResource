package com.shlanbao.tzsc.pms.equ.lubricate.beans;

/**
 * 
* @ClassName: EquLubrimaintBean 
* @Description: 设备润滑 
* @author luo
* @date 2015年3月19日 下午4:15:03 
*
 */
public class EqmLubricantBean {
	
	private String id;//ID	
	private String name;
	private String eqp_typeId;//设备类型Id
	private String eqp_typeName;//设备类型名称
	private int cycle;//周期 
	private String last_time;//上一次执行日期 
	private String next_time;//预计下一次执行日期  
	private String create_uName;
	private String create_uId;//创建用户id 
	private String create_time;//创建时间  
	private int status;//状态  
	private String ruleId;//润滑规则Id
	private String ruleName;//润滑规则名称
	private String eqp_category;
	
	public String getEqp_category() {
		return eqp_category;
	}
	public void setEqp_category(String eqp_category) {
		this.eqp_category = eqp_category;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqp_typeId() {
		return eqp_typeId;
	}
	public void setEqp_typeId(String eqp_typeId) {
		this.eqp_typeId = eqp_typeId;
	}
	public String getEqp_typeName() {
		return eqp_typeName;
	}
	public void setEqp_typeName(String eqp_typeName) {
		this.eqp_typeName = eqp_typeName;
	}
	
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	
	public String getCreate_uName() {
		return create_uName;
	}
	public void setCreate_uName(String create_uName) {
		this.create_uName = create_uName;
	}
	public String getCreate_uId() {
		return create_uId;
	}
	public void setCreate_uId(String create_uId) {
		this.create_uId = create_uId;
	}
	
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	public String getNext_time() {
		return next_time;
	}
	public void setNext_time(String next_time) {
		this.next_time = next_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
