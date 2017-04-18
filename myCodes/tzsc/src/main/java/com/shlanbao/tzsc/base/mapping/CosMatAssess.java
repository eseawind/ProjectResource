package com.shlanbao.tzsc.base.mapping;

import java.util.HashSet;
import java.util.Set;

/**
 * 
* @ClassName: CosMatAssess 
* @Description: 辅料考核版本类
* @author luoliang
* @date 2015-1-7 上午08:55:15 
*
 */
public class CosMatAssess {

	private String id;// ID	varchar(50)	Unchecked
	private MdMat mdMat;//辅料ID MAT	varchar(50)	Checked
	private String version;//版本号 VERSION	varchar(50)	Checked
	private String status;//状态：新建、生效、归档、时间 STATUS	varchar(10)	Checked
	private String remark;//备注REMARK	varchar(100)	Checked
	private String statusLasting;//STATUS_TIME 状态变化日期 
	private MdEqpType eqpType;//设备类型
	private Set<CosMatAssessParam> cosMatAssessParam = new HashSet<CosMatAssessParam>(0);
	
	public Set<CosMatAssessParam> getCosMatAssessParam() {
		return cosMatAssessParam;
	}
	public void setCosMatAssessParam(Set<CosMatAssessParam> cosMatAssessParam) {
		this.cosMatAssessParam = cosMatAssessParam;
	}
	public MdEqpType getEqpType() {
		return eqpType;
	}
	public void setEqpType(MdEqpType eqpType) {
		this.eqpType = eqpType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MdMat getMdMat() {
		return mdMat;
	}
	public void setMdMat(MdMat mdMat) {
		this.mdMat = mdMat;
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
