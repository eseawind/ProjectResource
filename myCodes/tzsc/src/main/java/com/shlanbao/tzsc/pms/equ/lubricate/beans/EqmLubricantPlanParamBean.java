package com.shlanbao.tzsc.pms.equ.lubricate.beans;

/**
 * 
* @ClassName: EqmLubricantPlanParamBean 
* @Description: 设备润滑记录详情 
* @author luo
* @date 2015年7月15日 下午3:36:04 
*
 */
public class EqmLubricantPlanParamBean {
	
	private String id;// ID	nvarchar(50)	Unchecked
	private String code;//CODE
	private String name;//NAME
	private String des;//DES
	private String oiltd;//润滑济ID OILID
	private String fill_quantity;//润滑使用量FILL_QUANTITY
	private String fill_unit;//润滑单位FILL_UNIT
	private String end_times;//完成时间
	private String plan_id;//设备润滑计划
	private String end_user_name;//完成人
	private String end_user_id;//完成人id
	private String methods;//润滑方式
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
	
	public String getOiltd() {
		return oiltd;
	}
	public void setOiltd(String oiltd) {
		this.oiltd = oiltd;
	}
	public String getEnd_user_name() {
		return end_user_name;
	}
	public void setEnd_user_name(String end_user_name) {
		this.end_user_name = end_user_name;
	}
	public String getEnd_user_id() {
		return end_user_id;
	}
	public void setEnd_user_id(String end_user_id) {
		this.end_user_id = end_user_id;
	}
	public String getEnd_times() {
		return end_times;
	}
	public void setEnd_times(String end_times) {
		this.end_times = end_times;
	}
	
	
	
}
