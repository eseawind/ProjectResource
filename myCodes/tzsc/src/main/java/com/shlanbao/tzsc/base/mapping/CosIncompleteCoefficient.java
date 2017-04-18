package com.shlanbao.tzsc.base.mapping;
/**
 * @ClassName: CosIncompleteCoefficient 
 * @Description: 残烟丝考核系数 
 * @author luo
 * @date 2015年1月22日 下午1:26:19 
 */
public class CosIncompleteCoefficient {
	private String id;
	private String name;
	private String code;
	private double modulus;
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
	public double getModulus() {
		return modulus;
	}
	public void setModulus(double modulus) {
		this.modulus = modulus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
