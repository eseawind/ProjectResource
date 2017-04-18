package com.shlanbao.tzsc.base.mapping;
/**
 * 
* @ClassName: EqmParam 
* @Description: 设备模块参数同意维护界面 
* @author luo
* @date 2015年3月11日 上午9:16:21 
*
 */
public class EqmParam {
	private String id;
	private String name;
	private String code;
	private double modulu;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getModulu() {
		return modulu;
	}
	public void setModulu(double modulu) {
		this.modulu = modulu;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
