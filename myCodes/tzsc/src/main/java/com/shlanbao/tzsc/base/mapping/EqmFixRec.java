package com.shlanbao.tzsc.base.mapping;

import java.util.Date;

/**
 * EqmFixRec entity. @author MyEclipse Persistence Tools
 */

public class EqmFixRec implements java.io.Serializable {

	// Fields

	
	/**
	 * 
	 */
	private String id;
	private MdShift shift_id;//班次
	private MdEquipment eqp_id;//设备
	private String maintaiin_id;//维修人id
	private String maintaiin_name;//维修人姓名
	private Date maintaiin_time;//维修时间
	private int maintaiin_type;//维修人类型 1-机械工   2-电气工
	private String spare_parts_id;//备品备件id
	private int spare_parts_num;//备品使用数量
	private int status;//维修状态状态    1-完成   2-未完成
	private String source;//来源  来源   1-点检   2-维修呼叫   3-轮保   4-备品备件  5、检修
	private String remark;//记录
	private String trouble_id;//故障表id
	private String trouble_name;//故障原因
	private String is_send;//是否返回
	private Date repair_time;//复修时间
	private String repair_name;//复修指定人
	private String create_user_id;//创建人id
	private String create_user_name;//创建人name
	private Date create_user_time;//创建 时间
	private String update_user_id;//最后修改人id
	private String update_user_name;//最后修改人name
	private Date update_user_time;//最后修改时间
	
	
	private Date ask_date;//请求时间
	private String ask_userId;//请人id
	
	
	
	public Date getAsk_date() {
		return ask_date;
	}
	public void setAsk_date(Date ask_date) {
		this.ask_date = ask_date;
	}
	public String getAsk_userId() {
		return ask_userId;
	}
	public void setAsk_userId(String ask_userId) {
		this.ask_userId = ask_userId;
	}
	public EqmFixRec() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MdShift getShift_id() {
		return shift_id;
	}
	public void setShift_id(MdShift shift_id) {
		this.shift_id = shift_id;
	}
	public MdEquipment getEqp_id() {
		return eqp_id;
	}
	public void setEqp_id(MdEquipment eqp_id) {
		this.eqp_id = eqp_id;
	}
	public String getMaintaiin_id() {
		return maintaiin_id;
	}
	public void setMaintaiin_id(String maintaiin_id) {
		this.maintaiin_id = maintaiin_id;
	}
	public String getMaintaiin_name() {
		return maintaiin_name;
	}
	public void setMaintaiin_name(String maintaiin_name) {
		this.maintaiin_name = maintaiin_name;
	}
	public Date getMaintaiin_time() {
		return maintaiin_time;
	}
	public void setMaintaiin_time(Date maintaiin_time) {
		this.maintaiin_time = maintaiin_time;
	}
	public int getMaintaiin_type() {
		return maintaiin_type;
	}
	public void setMaintaiin_type(int maintaiin_type) {
		this.maintaiin_type = maintaiin_type;
	}
	public String getSpare_parts_id() {
		return spare_parts_id;
	}
	public void setSpare_parts_id(String spare_parts_id) {
		this.spare_parts_id = spare_parts_id;
	}
	public int getSpare_parts_num() {
		return spare_parts_num;
	}
	public void setSpare_parts_num(int spare_parts_num) {
		this.spare_parts_num = spare_parts_num;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTrouble_id() {
		return trouble_id;
	}
	public void setTrouble_id(String trouble_id) {
		this.trouble_id = trouble_id;
	}
	public String getTrouble_name() {
		return trouble_name;
	}
	public void setTrouble_name(String trouble_name) {
		this.trouble_name = trouble_name;
	}
	public String getIs_send() {
		return is_send;
	}
	public void setIs_send(String is_send) {
		this.is_send = is_send;
	}
	public Date getRepair_time() {
		return repair_time;
	}
	public void setRepair_time(Date repair_time) {
		this.repair_time = repair_time;
	}
	public String getRepair_name() {
		return repair_name;
	}
	public void setRepair_name(String repair_name) {
		this.repair_name = repair_name;
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
	public Date getCreate_user_time() {
		return create_user_time;
	}
	public void setCreate_user_time(Date create_user_time) {
		this.create_user_time = create_user_time;
	}
	public String getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
	public Date getUpdate_user_time() {
		return update_user_time;
	}
	public void setUpdate_user_time(Date update_user_time) {
		this.update_user_time = update_user_time;
	}
	 
	
	
	
	

	
}