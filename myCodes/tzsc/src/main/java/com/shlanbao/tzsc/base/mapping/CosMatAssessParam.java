package com.shlanbao.tzsc.base.mapping;
/**
 * 
* @ClassName: CosMatAssess 
* @Description: 辅料考核版本类
* @author luoliang
* @date 2015-1-7 上午09:55:15 
*
 */
public class CosMatAssessParam {
	private String id;//ID	varchar(50)	Unchecked
	private double uval; //最大值 UVAL	numeric(19, 4)	Checked
	private double lval;//最小值LVAL	numeric(19, 4)	Checked
	private double val;//标准值 VAL	numeric(19, 4)	Checked
	private float unitprice;//价格UNITPRICE	float	Checked
	private SchConStd stdID;//SCH_CON_STD_ID	varchar(50)	Checked
	private String remark;//备注REMARK	varchar(100)	Checked	
	private CosMatAssess cid;//CosMatAssess ID, CID	varchar(50)	Checked
	private MdMat mat;
	
	public MdMat getMat() {
		return mat;
	}
	public void setMat(MdMat mat) {
		this.mat = mat;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getUval() {
		return uval;
	}
	public void setUval(double uval) {
		this.uval = uval;
	}
	public double getLval() {
		return lval;
	}
	public void setLval(double lval) {
		this.lval = lval;
	}
	public double getVal() {
		return val;
	}
	public void setVal(double val) {
		this.val = val;
	}
	public float getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(float unitprice) {
		this.unitprice = unitprice;
	}
	public SchConStd getStdID() {
		return stdID;
	}
	public void setStdID(SchConStd stdID) {
		this.stdID = stdID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public CosMatAssess getCid() {
		return cid;
	}
	public void setCid(CosMatAssess cid) {
		this.cid = cid;
	}
	
	
	
}
