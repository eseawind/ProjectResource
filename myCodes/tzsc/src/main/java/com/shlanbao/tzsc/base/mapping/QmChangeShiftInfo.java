package com.shlanbao.tzsc.base.mapping;


/**
 * 说明：交接班（MES下发虚领虚退）虚领虚退详细
 * 日期时间：2016年9月8日10:21:29 
 * 编写者：wanchanghuang
 * */
public class QmChangeShiftInfo implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String id;//  ID
	private String materialClass;//MATERIAL_CLASS 物料类
	private String materialId;//MATERIAL_ID  --物料编码
	private String materialName;//MATERIAL_NAME --物料描述
	private String lotId;//LOT_ID   --物料批次ID
	private Float qty;//QTY  --产量
	private String uom;//UOM --单位
	private String opeTime;//OPE_TIME --实领时间
	private String type;//TYPE --类型  1-实领  2-实退   3-虚领  4-虚退
	private String del;//DEL --  0 默认   1-禁用
	private String des;//DES  --备注
	private String tid;//TID -- md_mat_type 表ID
	private String qmcsid;//QM_CSID -主表ID
	private String createTime;//CREATE_TIME --创建时间
	private String updateTime;//UPDATE_TIME --修改时间
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMaterialClass() {
		return materialClass;
	}
	public void setMaterialClass(String materialClass) {
		this.materialClass = materialClass;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getLotId() {
		return lotId;
	}
	public void setLotId(String lotId) {
		this.lotId = lotId;
	}
	public Float getQty() {
		return qty;
	}
	public void setQty(Float qty) {
		this.qty = qty;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getOpeTime() {
		return opeTime;
	}
	public void setOpeTime(String opeTime) {
		this.opeTime = opeTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getQmcsid() {
		return qmcsid;
	}
	public void setQmcsid(String qmcsid) {
		this.qmcsid = qmcsid;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


}