package com.shlanbao.tzsc.pms.equ.sbglplan.beans;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;
/**
 * 点修记录表bean
 * @author shisihai
 *
 */
public class EqmSpotchRecodeBean {
	private String id;
	private String eqmId;//设备id
	private String eqpName;//设备名称
	private String shiftId;//班次id
	private String shiftName;//班次名字
	private String teamId;//班组id
	private String teamName;//班组名称
	private String chTypeId;//点检类型0--普工 1--维修工 2--维修组管
	private String chTypeName;//点检类型名称
	private String chLocation;//点检部位
	private String chStandard;//点检标准
	private String chMethod;//点检方式 听 闻 看
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String chDate;//date 型 点检日期
	private String endChDate;//点检结束时间
	private String createUserId;//点检人id
	private String createUserName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String createTime;//dataeTime型 记录时间
	private int chStatus; //点检状态 1通过 0不通过
	private String breLocation;//故障部位
	private String remark;//备注
	private String breId;//故障id
	private String updateUserId;//更新着id
	private String updateUserName;
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private String updateTime;//dateTime型  更新时间
	private int del;//是否删除 1删除  0未删除（需要显示的数据）
	private String orderState;//工单的状态
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd")
	private String orderDate;//工单时间
	private String czgName;//操作工名字
	private String czgId;//点修记录id
	private String wxgName;//维修工名字
	private String wxgId;
	private String wxzugName;//维修主管工名字
	private String wxzugId;
	private int status;//总的点检结果
	private String eqpTypeName;//sys_eqp_type中name字段
	private String eqpTypeDes;//sys_eqp_type中des字段
	
	
	public String getEqpTypeName() {
		return eqpTypeName;
	}
	public void setEqpTypeName(String eqpTypeName) {
		this.eqpTypeName = eqpTypeName;
	}
	public String getEqpTypeDes() {
		return eqpTypeDes;
	}
	public void setEqpTypeDes(String eqpTypeDes) {
		this.eqpTypeDes = eqpTypeDes;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCzgName() {
		return czgName;
	}
	public void setCzgName(String czgName) {
		this.czgName = czgName;
	}
	public String getCzgId() {
		return czgId;
	}
	public void setCzgId(String czgId) {
		this.czgId = czgId;
	}
	public String getWxgName() {
		return wxgName;
	}
	public void setWxgName(String wxgName) {
		this.wxgName = wxgName;
	}
	public String getWxgId() {
		return wxgId;
	}
	public void setWxgId(String wxgId) {
		this.wxgId = wxgId;
	}
	public String getWxzugName() {
		return wxzugName;
	}
	public void setWxzugName(String wxzugName) {
		this.wxzugName = wxzugName;
	}
	public String getWxzugId() {
		return wxzugId;
	}
	public void setWxzugId(String wxzugId) {
		this.wxzugId = wxzugId;
	}
	/**
	 * 未点检
	 * @param eqmId
	 * @param shiftId
	 * @param orderState
	 * @param orderDate
	 * @param chStatus
	 */
	public EqmSpotchRecodeBean(String eqmId, String shiftId, 
			String orderState, String orderDate,int chStatus) {
		super();
		this.eqmId = eqmId;
		this.shiftId = shiftId;
		this.orderState = orderState;
		this.orderDate = orderDate;
		this.chStatus=chStatus;
	}
	public EqmSpotchRecodeBean() {
	}
	/**
	 * /正常点检
	 * */
	public EqmSpotchRecodeBean(String id, String eqmId, String eqpName,
			String shiftId, String shiftName, String teamId, String teamName,
			String chTypeId, String chTypeName, String chLocation,
			String chStandard, String chMethod, String chDate,
			 String createUserId, String createUserName,
			String createTime, int chStatus, String breLocation, String remark,
			String breId, String updateUserId, String updateUserName,
			String updateTime, int del, String orderState,
			String orderDate) {
		this.id = id;
		this.eqmId = eqmId;
		this.eqpName = eqpName;
		this.shiftId = shiftId;
		this.shiftName = shiftName;
		this.teamId = teamId;
		this.teamName = teamName;
		this.chTypeId = chTypeId;
		this.chTypeName = chTypeName;
		this.chLocation = chLocation;
		this.chStandard = chStandard;
		this.chMethod = chMethod;
		this.chDate = chDate;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
		this.chStatus = chStatus;
		this.breLocation = breLocation;
		this.remark = remark;
		this.breId = breId;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.del = del;
		this.orderState = orderState;
		this.orderDate = orderDate;
	}
	/**
	 * map封装
	 * @param id
	 * @param eqmId
	 * @param eqpName
	 * @param shiftId
	 * @param shiftName
	 * @param teamId
	 * @param teamName
	 * @param chTypeId
	 * @param chTypeName
	 * @param chLocation
	 * @param chStandard
	 * @param chMethod
	 * @param chDate
	 * @param createUserId
	 * @param createUserName
	 * @param createTime
	 * @param chStatus
	 * @param breLocation
	 * @param remark
	 * @param breId
	 * @param updateUserId
	 * @param updateUserName
	 * @param updateTime
	 * @param del
	 */
	public EqmSpotchRecodeBean(String id, String eqmId, String eqpName,
			String shiftId, String shiftName, String teamId, String teamName,
			String chTypeId, String chTypeName, String chLocation,
			String chStandard, String chMethod, String chDate,
			 String createUserId, String createUserName,
			String createTime, int chStatus, String breLocation, String remark,
			String breId, String updateUserId, String updateUserName,
			String updateTime, int del) {
		this.id = id;
		this.eqmId = eqmId;
		this.eqpName = eqpName;
		this.shiftId = shiftId;
		this.shiftName = shiftName;
		this.teamId = teamId;
		this.teamName = teamName;
		this.chTypeId = chTypeId;
		this.chTypeName = chTypeName;
		this.chLocation = chLocation;
		this.chStandard = chStandard;
		this.chMethod = chMethod;
		this.chDate = chDate;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
		this.chStatus = chStatus;
		this.breLocation = breLocation;
		this.remark = remark;
		this.breId = breId;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.del = del;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEqmId() {
		return eqmId;
	}
	public void setEqmId(String eqmId) {
		this.eqmId = eqmId;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getChTypeId() {
		return chTypeId;
	}
	public void setChTypeId(String chTypeId) {
		this.chTypeId = chTypeId;
	}
	public String getChTypeName() {
		return chTypeName;
	}
	public void setChTypeName(String chTypeName) {
		this.chTypeName = chTypeName;
	}
	public String getChLocation() {
		return chLocation;
	}
	public void setChLocation(String chLocation) {
		this.chLocation = chLocation;
	}
	public String getChStandard() {
		return chStandard;
	}
	public void setChStandard(String chStandard) {
		this.chStandard = chStandard;
	}
	public String getChMethod() {
		return chMethod;
	}
	public void setChMethod(String chMethod) {
		this.chMethod = chMethod;
	}
	public String getChDate() {
		return chDate;
	}
	public void setChDate(String chDate) {
		this.chDate = chDate;
	}
	public String getEndChDate() {
		return endChDate;
	}
	public void setEndChDate(String endChDate) {
		this.endChDate = endChDate;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getChStatus() {
		return chStatus;
	}
	public void setChStatus(int chStatus) {
		this.chStatus = chStatus;
	}
	public String getBreLocation() {
		return breLocation;
	}
	public void setBreLocation(String breLocation) {
		this.breLocation = breLocation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBreId() {
		return breId;
	}
	public void setBreId(String breId) {
		this.breId = breId;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	
	
	
	
}
