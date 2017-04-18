package com.shlanbao.tzsc.pms.cos.spare.beans;
/**
 * 
 * @ClassName: CosSpareParts 
 * @Description: 备品备件Bean 
 * @author luo
 * @date 2015年2月9日 上午11:04:48 
 *
 */
public class CosSparePartsBean {
	private String id;//ID		
	private String name;//NAME 备件名称		
	private String eqpTypeId;//EQP_TYPE 机型code		
	private String eqpTypeName;
	private String eqpTypeCode;
	private String remark;//REMARK 备注	
	private double price;//PRICE 价格	
	private String unitId;//UNIT 单位		
	private String unitName;
	private String attr1;//ATTR1 备用1		
	private String attr2;//ATTR2 备用2	
	private String type;//型号
	private String number;//数量
	private String SEARCH_W;//检索首字母
	private String position;//存放位置
	private String sell_num;//寄售库存
	private String random_num;//随机库存
	private String amount;//花费总金额
	private String del;
	
	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getSEARCH_W() {
		return SEARCH_W;
	}

	public void setSEARCH_W(String sEARCH_W) {
		SEARCH_W = sEARCH_W;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSell_num() {
		return sell_num;
	}

	public void setSell_num(String sell_num) {
		this.sell_num = sell_num;
	}

	public String getRandom_num() {
		return random_num;
	}

	public void setRandom_num(String random_num) {
		this.random_num = random_num;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public CosSparePartsBean(){}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

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

	public String getEqpTypeId() {
		return eqpTypeId;
	}

	public void setEqpTypeId(String eqpTypeId) {
		this.eqpTypeId = eqpTypeId;
	}

	public String getEqpTypeName() {
		return eqpTypeName;
	}

	public void setEqpTypeName(String eqpTypeName) {
		this.eqpTypeName = eqpTypeName;
	}

	public String getEqpTypeCode() {
		return eqpTypeCode;
	}

	public void setEqpTypeCode(String eqpTypeCode) {
		this.eqpTypeCode = eqpTypeCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CosSparePartsBean(String id, String name, String eqpTypeId,
			String eqpTypeName, String eqpTypeCode, String remark,
			double price, String unitId, String unitName, String attr1,
			String attr2) {
		super();
		this.id = id;
		this.name = name;
		this.eqpTypeId = eqpTypeId;
		this.eqpTypeName = eqpTypeName;
		this.eqpTypeCode = eqpTypeCode;
		this.remark = remark;
		this.price = price;
		this.unitId = unitId;
		this.unitName = unitName;
		this.attr1 = attr1;
		this.attr2 = attr2;
	}
}
