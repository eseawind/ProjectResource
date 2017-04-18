package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;
/**
* @ClassName: EqmLubricant 
* @Description: 设备轮保计划 
* @author luo
* @date 2015年7月8日 下午1:47:33 
 */
public class EqmLubricant {

	private String id;//ID	varchar(50)	Unchecked
	private String name;//名称
	private MdEqpType eqp_type;//设备类型 EQP_TYPE	varchar(50)	Unchecked
	private int cycle;//周期//CYCLE	int	Unchecked
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date last_time;//上一次执行日期  LAST_TIME	date	Checked
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date next_time;//预计下一次执行日期 NEXT_TIME	date	Checked
	private SysUser create_usr;//创建用户id CREATE_USR	varchar(50)	Unchecked
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time;//创建时间 CREATE_TIME	datetime	Unchecked
	private int status;//状态 STATUS	int	Unchecked
	private String rule_id;//润滑规则Id
	private String rule_name;//润滑规则名称
	private MdEqpCategory eqp_category;//设备类型ID
	
	public MdEqpCategory getEqp_category() {
		return eqp_category;
	}

	public void setEqp_category(MdEqpCategory eqp_category) {
		this.eqp_category = eqp_category;
	}

	public EqmLubricant(){}
	
	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EqmLubricant(String id){
		this.id=id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public Date getLast_time() {
		return last_time;
	}
	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}
	public Date getNext_time() {
		return next_time;
	}
	public void setNext_time(Date next_time) {
		this.next_time = next_time;
	}
	
	public MdEqpType getEqp_type() {
		return eqp_type;
	}
	public void setEqp_type(MdEqpType eqp_type) {
		this.eqp_type = eqp_type;
	}
	public SysUser getCreate_usr() {
		return create_usr;
	}
	public void setCreate_usr(SysUser create_usr) {
		this.create_usr = create_usr;
	}
	
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
