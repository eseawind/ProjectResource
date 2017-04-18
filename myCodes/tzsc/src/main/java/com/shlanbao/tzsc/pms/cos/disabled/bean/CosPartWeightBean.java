package com.shlanbao.tzsc.pms.cos.disabled.bean;

/**
 * 
* @ClassName: CosPartWeight 
* @Description: 烟支重量维护 
* @author luoliang
* @date 2015-1-5 上午11:04:04 
*
 */
public class CosPartWeightBean {
	private String id; //ID	varchar(50)	Unchecked
	private String partNumber; //牌号名称 PARTNUMBER	varchar(50)	Unchecked
	private float weight;//重量  WEIGHT	float	Unchecked
	private String partName;//牌号名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
}
