package com.shlanbao.tzsc.base.mapping;

/**
 * 
* @ClassName: CosPartWeight 
* @Description: 烟支重量维护 
* @author luoliang
* @date 2015-1-5 上午11:04:04 
*
 */
public class CosPartWeight {
	private String id; //ID	varchar(50)	Unchecked
	private String partNumber; //牌号名称 PARTNUMBER	varchar(50)	Unchecked
	private float weight;//重量  WEIGHT	float	Unchecked
	private MdMat mdMat;//牌号对象
	private String partName;//牌号名称
	

	public String getPartName(){
		if(partName==null&&mdMat!=null){
			return mdMat.getDes();
		}
		return partName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartNumber() {
		if(this.partNumber==null){
			if(mdMat!=null){
				return mdMat.getId();
			}
		}
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
	public MdMat getMdMat() {
		return mdMat;
	}
	public void setMdMat(MdMat mdMat) {
		if(mdMat!=null){
			partNumber=mdMat.getId();
			partName=mdMat.getName();
		}
		this.mdMat = mdMat;
	}
	
	
	
	

}
