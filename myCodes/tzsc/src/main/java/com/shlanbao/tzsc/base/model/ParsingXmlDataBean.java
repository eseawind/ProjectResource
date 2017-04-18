package com.shlanbao.tzsc.base.model;
/**
 * [功能描述]：工单撤销实体类，工单下发，工单信号
 * @author wanchanghuang
 * @createTime 2015年12月29日08:53:16
 * @[最后修改人]
 * @[最后修改日期]
 * 
 * */
public class ParsingXmlDataBean {
	
	//工单撤销
	private String equ_id;// 机台号
	private String wo_id;//工单号
	private String work_shop;//车间
	private String mod_id;// ERP订单号
	private String wo_state;//6-取消标识，固定值：7-删除   1-下发  2-开始   4-结束
	
	//工单信号
	private String startTime; //开始时间
	private String endTime; //结束时间
	private String feedback_type; //0-其他   1-换班
	
	
	//工单下发-主数据
	private String type; //类型   1-卷烟机   2-包装机  3-封箱机   4-成型机
	private String prod_type; //1-中式生产   2-正式生产
	private String bom_version; //版本号
	private String lot_id; //批次号
	private String ph_id; // 牌号
	private String ph_name; //牌号名称
	private String plan_quantity; //计划产量
	private String unit_code; //单位编号
	private String unit_name; //单位名称
	private String plan_startTime; //计划开始时间
	private String plan_endTime; //计划结束时间
	private String supply_time; //下发时间
	private String outputdev; //后续设备编号
	private String inputdev; //前序设备编号
	
	//工单下发-辅料
    private String bom_matid; //物料编号
    private String bom_matname; //物料名称
    private String bom_quantity; //物料量
    private String bom_unit_code; //单位编号
    private String bom_unit_name; //单位名称
    private String bom_lotid; //物料批次号
    
	//工单下发-工艺标准
	private String para_id; //工艺参数
	private String para_name; //工艺参数名称
	private String initial_value; //设定值
	private String controlup_value;
	private String upperlimit_value; //上线
	private String lowerLimit_value; //下线
	private String controllow_value; 
	private String para_unit_code; //单位ID
	private String  para_unit_name; //单位名称
	
	private String shift_code;
	private String team_code;
	
	
	
	public String getShift_code() {
		return shift_code;
	}
	public void setShift_code(String shift_code) {
		this.shift_code = shift_code;
	}
	public String getTeam_code() {
		return team_code;
	}
	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}
	public String getProd_type() {
		return prod_type;
	}
	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBom_version() {
		return bom_version;
	}
	public void setBom_version(String bom_version) {
		this.bom_version = bom_version;
	}
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	public String getPh_id() {
		return ph_id;
	}
	public void setPh_id(String ph_id) {
		this.ph_id = ph_id;
	}
	public String getPh_name() {
		return ph_name;
	}
	public void setPh_name(String ph_name) {
		this.ph_name = ph_name;
	}
	public String getPlan_quantity() {
		return plan_quantity;
	}
	public void setPlan_quantity(String plan_quantity) {
		this.plan_quantity = plan_quantity;
	}
	public String getUnit_code() {
		return unit_code;
	}
	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public String getPlan_startTime() {
		return plan_startTime;
	}
	public void setPlan_startTime(String plan_startTime) {
		this.plan_startTime = plan_startTime;
	}
	public String getPlan_endTime() {
		return plan_endTime;
	}
	public void setPlan_endTime(String plan_endTime) {
		this.plan_endTime = plan_endTime;
	}
	public String getSupply_time() {
		return supply_time;
	}
	public void setSupply_time(String supply_time) {
		this.supply_time = supply_time;
	}
	public String getOutputdev() {
		return outputdev;
	}
	public void setOutputdev(String outputdev) {
		this.outputdev = outputdev;
	}
	public String getInputdev() {
		return inputdev;
	}
	public void setInputdev(String inputdev) {
		this.inputdev = inputdev;
	}
	public String getBom_matid() {
		return bom_matid;
	}
	public void setBom_matid(String bom_matid) {
		this.bom_matid = bom_matid;
	}
	public String getBom_matname() {
		return bom_matname;
	}
	public void setBom_matname(String bom_matname) {
		this.bom_matname = bom_matname;
	}
	public String getBom_quantity() {
		return bom_quantity;
	}
	public void setBom_quantity(String bom_quantity) {
		this.bom_quantity = bom_quantity;
	}
	public String getBom_unit_code() {
		return bom_unit_code;
	}
	public void setBom_unit_code(String bom_unit_code) {
		this.bom_unit_code = bom_unit_code;
	}
	public String getBom_unit_name() {
		return bom_unit_name;
	}
	public void setBom_unit_name(String bom_unit_name) {
		this.bom_unit_name = bom_unit_name;
	}
	public String getBom_lotid() {
		return bom_lotid;
	}
	public void setBom_lotid(String bom_lotid) {
		this.bom_lotid = bom_lotid;
	}
	public String getPara_id() {
		return para_id;
	}
	public void setPara_id(String para_id) {
		this.para_id = para_id;
	}
	public String getPara_name() {
		return para_name;
	}
	public void setPara_name(String para_name) {
		this.para_name = para_name;
	}
	public String getInitial_value() {
		return initial_value;
	}
	public void setInitial_value(String initial_value) {
		this.initial_value = initial_value;
	}
	public String getControlup_value() {
		return controlup_value;
	}
	public void setControlup_value(String controlup_value) {
		this.controlup_value = controlup_value;
	}
	public String getUpperlimit_value() {
		return upperlimit_value;
	}
	public void setUpperlimit_value(String upperlimit_value) {
		this.upperlimit_value = upperlimit_value;
	}
	public String getLowerLimit_value() {
		return lowerLimit_value;
	}
	public void setLowerLimit_value(String lowerLimit_value) {
		this.lowerLimit_value = lowerLimit_value;
	}
	public String getControllow_value() {
		return controllow_value;
	}
	public void setControllow_value(String controllow_value) {
		this.controllow_value = controllow_value;
	}
	public String getPara_unit_code() {
		return para_unit_code;
	}
	public void setPara_unit_code(String para_unit_code) {
		this.para_unit_code = para_unit_code;
	}
	public String getPara_unit_name() {
		return para_unit_name;
	}
	public void setPara_unit_name(String para_unit_name) {
		this.para_unit_name = para_unit_name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getFeedback_type() {
		return feedback_type;
	}
	public void setFeedback_type(String feedback_type) {
		this.feedback_type = feedback_type;
	}
	public String getEqu_id() {
		return equ_id;
	}
	public void setEqu_id(String equ_id) {
		this.equ_id = equ_id;
	}
	public String getWo_id() {
		return wo_id;
	}
	public void setWo_id(String wo_id) {
		this.wo_id = wo_id;
	}
	public String getWork_shop() {
		return work_shop;
	}
	public void setWork_shop(String work_shop) {
		this.work_shop = work_shop;
	}
	public String getMod_id() {
		return mod_id;
	}
	public void setMod_id(String mod_id) {
		this.mod_id = mod_id;
	}
	public String getWo_state() {
		return wo_state;
	}
	public void setWo_state(String wo_state) {
		this.wo_state = wo_state;
	}
	

	
}
