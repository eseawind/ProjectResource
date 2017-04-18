package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
* @ClassName: EqmLubricatPlan 
* @Description: 润滑计划 
* @author luo
* @date 2015年7月14日 上午10:19:39 
*
 */
public class EqmLubricantPlan {
	private String id;//ID	
	private String code;//code
	private MdEquipment eqp;//设备Id  
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date date_p;//计划执行日期
	private SysUser operatives;//保工确认人
	private SysUser lubricating;//润滑工确认人
	private int status;//状态
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date etim;//结束时间
	private String remark;//备注
	private String lub_id;//轮保周期id
	private String attr1;//备用字段一
	private String attr2;//备用字段二
	private String rule_id;//轮保周期id
	private String shift_id;
	
	
	public String getShift_id() {
		return shift_id;
	}
	public void setShift_id(String shift_id) {
		this.shift_id = shift_id;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public EqmLubricantPlan(){}
	public EqmLubricantPlan(String id){
		this.id=id;
	}
	public String getId() {
		return id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public MdEquipment getEqp() {
		return eqp;
	}
	public void setEqp(MdEquipment eqp) {
		this.eqp = eqp;
	}
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public Date getDate_p() {
		return date_p;
	}
	public void setDate_p(Date date_p) {
		this.date_p = date_p;
	}
	
	public SysUser getOperatives() {
		return operatives;
	}
	public void setOperatives(SysUser operatives) {
		this.operatives = operatives;
	}
	public SysUser getLubricating() {
		return lubricating;
	}
	public void setLubricating(SysUser lubricating) {
		this.lubricating = lubricating;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getEtim() {
		return etim;
	}
	public void setEtim(Date etim) {
		this.etim = etim;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLub_id() {
		return lub_id;
	}
	public void setLub_id(String lub_id) {
		this.lub_id = lub_id;
	}
	
	
}
