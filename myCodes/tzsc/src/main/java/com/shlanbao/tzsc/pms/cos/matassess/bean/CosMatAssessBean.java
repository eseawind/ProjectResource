package com.shlanbao.tzsc.pms.cos.matassess.bean;
/**
 * 
* @ClassName: CosMatAssess 
* @Description: 辅料考核版本类
* @author luoliang
* @date 2015-1-7 上午08:55:15 
*
 */
public class CosMatAssessBean {

	private String id;// ID	varchar(50)	Unchecked
	private String mdMatId;
	private String mdMatName;
	private String version;//版本号 VERSION	varchar(50)	Checked
	private String status;//状态：新建、生效、归档、时间 STATUS	varchar(10)	Checked
	private String remark;//备注REMARK	varchar(100)	Checked
	private String statusLasting;//STATUS_TIME 状态变化日期 
	private String eqpTypeID;//设备类型ID
	private String eqpTypeName;//设备类型名称
	private String eqpTypeDes;//设备类型描述
	
	public String getEqpTypeDes() {
		return eqpTypeDes;
	}
	public void setEqpTypeDes(String eqpTypeDes) {
		this.eqpTypeDes = eqpTypeDes;
	}
	public String getEqpTypeName() {
		return eqpTypeName;
	}
	public void setEqpTypeName(String eqpTypeName) {
		this.eqpTypeName = eqpTypeName;
	}
	public String getEqpTypeID() {
		return eqpTypeID;
	}
	public void setEqpTypeID(String eqpTypeID) {
		this.eqpTypeID = eqpTypeID;
	}
	public String getMdMatId() {
		return mdMatId;
	}
	public void setMdMatId(String mdMatId) {
		this.mdMatId = mdMatId;
	}
	public String getMdMatName() {
		return mdMatName;
	}
	public void setMdMatName(String mdMatName) {
		this.mdMatName = mdMatName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatusLasting() {
		return statusLasting;
	}
	public void setStatusLasting(String statusLasting) {
		this.statusLasting = statusLasting;
	}
	
	
	
}
