package com.shlanbao.tzsc.pms.equ.lubricate.beans;

import java.util.Date;

/**
 * [功能说明]:设备润滑计划添加条件实体类
 * @author wanchanghuang
 * @time 2015年8月21日20:41:58
 * 
 * */
public class EqmBubricantfBean {
	private String id;
	private String date_plan1;
	private String date_plan2;
	private String eqp_category;
	private String eqp_typeId;
	private String eqp_typeName;
	private String ruleId;
	private String ruleName;
	private String ruleIdf;
	private String ruleNamef;
	private Integer lub_id;
	private String shift_id; 
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRuleIdf() {
		return ruleIdf;
	}
	public void setRuleIdf(String ruleIdf) {
		this.ruleIdf = ruleIdf;
	}
	public String getRuleNamef() {
		return ruleNamef;
	}
	public void setRuleNamef(String ruleNamef) {
		this.ruleNamef = ruleNamef;
	}
	public Integer getLub_id() {
		return lub_id;
	}
	public void setLub_id(Integer lub_id) {
		this.lub_id = lub_id;
	}
	public String getShift_id() {
		return shift_id;
	}
	public void setShift_id(String shift_id) {
		this.shift_id = shift_id;
	}
	public String getDate_plan1() {
		return date_plan1;
	}
	public void setDate_plan1(String date_plan1) {
		this.date_plan1 = date_plan1;
	}
	public String getDate_plan2() {
		return date_plan2;
	}
	public void setDate_plan2(String date_plan2) {
		this.date_plan2 = date_plan2;
	}
	public String getEqp_category() {
		return eqp_category;
	}
	public void setEqp_category(String eqp_category) {
		this.eqp_category = eqp_category;
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
	
	
	
	
	
}
