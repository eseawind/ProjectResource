package com.shlanbao.tzsc.pms.cos.matassess.bean;
/**
 * 
* @ClassName: CosMatAssess 
* @Description: 辅料考核版本类
* @author luoliang
* @date 2015-1-7 上午09:55:15 
*
 */
public class CosMatAssessParamBean {
	private String id;//ID	varchar(50)	Unchecked
	private double uval; //最大值 UVAL	numeric(19, 4)	Checked
	private double lval;//最小值LVAL	numeric(19, 4)	Checked
	private double val;//标准值 VAL	numeric(19, 4)	Checked
	private float unitprice;//价格UNITPRICE	float	Checked
	private String stdID;//SCH_CON_STD_ID	varchar(50)	Checked
	private String remark;//备注REMARK	varchar(100)	Checked	
	private String cid;//CosMatAssess ID, CID	varchar(50)	Checked
	private String matId;//辅料ID
	private String matName;//辅料名称
	
	public String getMatId() {
		return matId;
	}
	public void setMatId(String matId) {
		this.matId = matId;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
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
	public String getStdID() {
		return stdID;
	}
	public void setStdID(String stdID) {
		this.stdID = stdID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	
}
