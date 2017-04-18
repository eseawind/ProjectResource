package com.shlanbao.tzsc.base.mapping;

/**
 * SchWorkorderBom entity. @author MyEclipse Persistence Tools
 */

public class SchWorkorderBom implements java.io.Serializable {

	// Fields

	private String id;
	private MdUnit mdUnit;
	private SchWorkorder schWorkorder;
	private MdMat mdMat;
	private Double qty;
	private String bom_lot_id; //辅料批次号
	private Integer del;
	private String materialClass; //物料类
	private String materialName;// 物料名称

	// Constructors

	

	public String getBom_lot_id() {
		return bom_lot_id;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialClass() {
		return materialClass;
	}

	public void setMaterialClass(String materialClass) {
		this.materialClass = materialClass;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public void setBom_lot_id(String bom_lot_id) {
		this.bom_lot_id = bom_lot_id;
	}

	/** default constructor */
	public SchWorkorderBom() {
	}

	/** full constructor */
	public SchWorkorderBom(MdUnit mdUnit, SchWorkorder schWorkorder,
			MdMat mdMat, Double qty) {
		this.mdUnit = mdUnit;
		this.schWorkorder = schWorkorder;
		this.mdMat = mdMat;
		this.qty = qty;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MdUnit getMdUnit() {
		return this.mdUnit;
	}

	public void setMdUnit(MdUnit mdUnit) {
		this.mdUnit = mdUnit;
	}

	public SchWorkorder getSchWorkorder() {
		return this.schWorkorder;
	}

	public void setSchWorkorder(SchWorkorder schWorkorder) {
		this.schWorkorder = schWorkorder;
	}

	public MdMat getMdMat() {
		return this.mdMat;
	}

	public void setMdMat(MdMat mdMat) {
		this.mdMat = mdMat;
	}

	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

}