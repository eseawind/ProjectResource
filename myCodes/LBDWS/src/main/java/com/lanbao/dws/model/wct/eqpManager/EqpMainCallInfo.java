package com.lanbao.dws.model.wct.eqpManager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.lanbao.dws.common.tools.StringUtil;
/**
 * 维修呼叫bean
 * @author shisihai
 *
 */
@Entity(name="sys_service_info")
public class EqpMainCallInfo {
	private String id;
	private String shiftId;
	private String shiftName;//班次
	private String teamId;
	private String teamName;//班组
	private String eqpId;
	private String eqpName;//设备名称
	private String createName;//创建人
	private String createUserId;
	private String sCreateTime;//创建时间(呼叫时间)
	private String eCreateTime;//创建时间
	private String WorkOrderId;//工单id
	private String designatedTime;//接受呼叫处理维修开始时间
	private String updateUserTime;//结束时间
	private String designatedId;//接收处理人
	private String designatedName;
	private String status;//处理状态  0 待处理  1处理中   2处理完成  3未解决
	private String typeName;//维修工类型  1.机械  2电气
	private String sparePartName;//更换的备品备件 	以,隔开
	private String sparePartNum;//更换的备件数量		以,隔开
	private String unitName;//备件单位				以,隔开
	private String troubleDesc;//故障处理信息			以,隔开
	private String mes_id;//维保工单id
	
	private  List<String> showSparePart=new ArrayList<>();//组合备件信息，用于页面显示
	private  List<String> showTrouble=new ArrayList<>();//组合故障信息，用于页面显示
	
	private List<String> eqpCodes=new ArrayList<>();//查询该设备的呼叫记录
	
	
	
	public String getMes_id() {
		return mes_id;
	}
	public void setMes_id(String mes_id) {
		this.mes_id = mes_id;
	}
	public List<String> getEqpCodes() {
		return eqpCodes;
	}
	public void setEqpCodes(List<String> eqpCodes) {
		this.eqpCodes = eqpCodes;
	}
	public void setShowSparePart(List<String> showSparePart) {
		this.showSparePart = showSparePart;
	}
	public void setShowTrouble(List<String> showTrouble) {
		this.showTrouble = showTrouble;
	}
	//组合备件信息，用于页面显示
	public List<String> getShowSparePart() {
		StringBuffer sb=new StringBuffer();
		if(StringUtil.notEmpty(getSparePartName())){
			String[] ps=getSparePartName().split(",");
			String[] nums=getSparePartNum().split(",");
			String[] units=getUnitName().split(",");
			for (int i = 0; i < nums.length; i++) {
				sb.append(ps[i]).//name 
				append("\t").//
				append(nums[i]).//num
				append(units[i]);//unitName
				showSparePart.add(sb.toString());
				sb.setLength(0);
			}
		}
		return showSparePart;
	}
	//组合故障信息，用于页面显示
	public List<String> getShowTrouble() {
		if(StringUtil.notEmpty(getTroubleDesc())){
			String[] ts=getTroubleDesc().split(",");
			for (String string : ts) {
				showTrouble.add(string);
			}
		}
		return showTrouble;
	}
	
	
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getSparePartName() {
		return sparePartName;
	}
	public void setSparePartName(String sparePartName) {
		this.sparePartName = sparePartName;
	}
	public String getSparePartNum() {
		return sparePartNum;
	}
	public void setSparePartNum(String sparePartNum) {
		this.sparePartNum = sparePartNum;
	}
	public String getTroubleDesc() {
		return troubleDesc;
	}
	public void setTroubleDesc(String troubleDesc) {
		this.troubleDesc = troubleDesc;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getUpdateUserTime() {
		return updateUserTime;
	}
	public void setUpdateUserTime(String updateUserTime) {
		this.updateUserTime = updateUserTime;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getDesignatedTime() {
		return designatedTime;
	}
	public void setDesignatedTime(String designatedTime) {
		this.designatedTime = designatedTime;
	}
	public String getDesignatedId() {
		return designatedId;
	}
	public void setDesignatedId(String designatedId) {
		this.designatedId = designatedId;
	}
	public String getDesignatedName() {
		return designatedName;
	}
	public void setDesignatedName(String designatedName) {
		this.designatedName = designatedName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	
	public String getsCreateTime() {
		return sCreateTime;
	}
	public void setsCreateTime(String sCreateTime) {
		this.sCreateTime = sCreateTime;
	}
	public String geteCreateTime() {
		return eCreateTime;
	}
	public void seteCreateTime(String eCreateTime) {
		this.eCreateTime = eCreateTime;
	}
	public String getWorkOrderId() {
		return WorkOrderId;
	}
	public void setWorkOrderId(String workOrderId) {
		WorkOrderId = workOrderId;
	}
	
	
	
}
