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
public class EqmTroubleInfoBean {
	private String id;
	private String md_eqp_type_id;//存放MD_EQP_TYPE表的ID,故障信息对应机型
	private String trouble_part;//故障部位代码组序号
	private String trouble_part_description;//故障部位代码组描述
	private int part_code;//故障部位代码
	private String part_code_description;//故障部位描述
	private String trouble_code;//故障现象代码组序号
	private String trouble_code_description;//故障现象代码组描述
	private int trouble_phenomenon;//故障现象代码
	private String trouble_phenomenon_description;//故障现象描述
	private String trouble_reason;//故障原因代码
	private String trouble_reason_description;//故障原因描述	
	private String eqp_DES;//机组名称
	private String troubleName;//机组名称
	private String troubleCode;//机组名称
	
	
	private String code;//故障代码
	private String description;//故障描述
	private String type;//故障种类，级标题，
	private String parent_id;//父节点
	
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getTroubleName() {
		return troubleName;
	}
	public void setTroubleName(String troubleName) {
		this.troubleName = troubleName;
	}
	public String getTroubleCode() {
		return troubleCode;
	}
	public void setTroubleCode(String troubleCode) {
		this.troubleCode = troubleCode;
	}
	public String getEqp_DES() {
		return eqp_DES;
	}
	public void setEqp_DES(String eqp_DES) {
		this.eqp_DES = eqp_DES;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMd_eqp_type_id() {
		return md_eqp_type_id;
	}
	public void setMd_eqp_type_id(String md_eqp_type_id) {
		this.md_eqp_type_id = md_eqp_type_id;
	}
	public String getTrouble_part() {
		return trouble_part;
	}
	public void setTrouble_part(String trouble_part) {
		this.trouble_part = trouble_part;
	}
	public String getTrouble_part_description() {
		return trouble_part_description;
	}
	public void setTrouble_part_description(String trouble_part_description) {
		this.trouble_part_description = trouble_part_description;
	}
	public int getPart_code() {
		return part_code;
	}
	public void setPart_code(int part_code) {
		this.part_code = part_code;
	}
	public String getPart_code_description() {
		return part_code_description;
	}
	public void setPart_code_description(String part_code_description) {
		this.part_code_description = part_code_description;
	}
	public String getTrouble_code() {
		return trouble_code;
	}
	public void setTrouble_code(String trouble_code) {
		this.trouble_code = trouble_code;
	}
	public String getTrouble_code_description() {
		return trouble_code_description;
	}
	public void setTrouble_code_description(String trouble_code_description) {
		this.trouble_code_description = trouble_code_description;
	}
	public int getTrouble_phenomenon() {
		return trouble_phenomenon;
	}
	public void setTrouble_phenomenon(int trouble_phenomenon) {
		this.trouble_phenomenon = trouble_phenomenon;
	}
	public String getTrouble_phenomenon_description() {
		return trouble_phenomenon_description;
	}
	public void setTrouble_phenomenon_description(
			String trouble_phenomenon_description) {
		this.trouble_phenomenon_description = trouble_phenomenon_description;
	}
	public String getTrouble_reason() {
		return trouble_reason;
	}
	public void setTrouble_reason(String trouble_reason) {
		this.trouble_reason = trouble_reason;
	}
	public String getTrouble_reason_description() {
		return trouble_reason_description;
	}
	public void setTrouble_reason_description(String trouble_reason_description) {
		this.trouble_reason_description = trouble_reason_description;
	}
	
}
