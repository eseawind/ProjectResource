package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;

/**
* @ClassName: EqmLubricatPlanParam 
* @Description: 润滑计划项 
* @author luo
* @date 2015年7月14日 上午10:19:52 
*
 */
public class EqmLubricantPlanParam {
	private String id;// ID	nvarchar(50)	Unchecked
	private String code;//CODE
	private String name;//NAME
	private String des;//DES
	private String oiltd;//润滑济ID OILID
	private String fill_quantity;//润滑使用量FILL_QUANTITY
	private String fill_unit;//润滑单位FILL_UNIT
	private SysUser end_user;//完成人
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date end_time;//完成时间
	private String plan_id;//设备润滑计划
	private String methods;//润滑方式
	private SysEqpType sysEqpType;//数据字典id
	
	public SysEqpType getSysEqpType() {
		return sysEqpType;
	}
	public void setSysEqpType(SysEqpType sysEqpType) {
		this.sysEqpType = sysEqpType;
	}
	public String getMethods() {
		return methods;
	}
	public void setMethods(String methods) {
		this.methods = methods;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getOiltd() {
		return oiltd;
	}
	public void setOiltd(String oiltd) {
		this.oiltd = oiltd;
	}
	public String getFill_quantity() {
		return fill_quantity;
	}
	public void setFill_quantity(String fill_quantity) {
		this.fill_quantity = fill_quantity;
	}
	public String getFill_unit() {
		return fill_unit;
	}
	public void setFill_unit(String fill_unit) {
		this.fill_unit = fill_unit;
	}
	public SysUser getEnd_user() {
		return end_user;
	}
	public void setEnd_user(SysUser end_user) {
		this.end_user = end_user;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	
	
}
