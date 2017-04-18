package com.shlanbao.tzsc.base.mapping;
/**
 * 
 * @ClassName: CosSpareParts 
 * @Description: 备品备件Bean 
 * @author luo
 * @date 2015年2月9日 上午10:54:48 
 *
 */
public class CosSpareParts {
	private String id;//ID	varchar(50)	Unchecked
	private String name;//NAME 备件名称	varchar(50)	Checked
	private MdEqpType eqpType;//EQP_TYPE 机型code	varchar(50)	Checked
	private String remark;//REMARK 备注	varchar(250)	Checked
	private double price;//PRICE 价格	numeric(16, 4)	Checked
	private MdUnit unit;//UNIT 单位code	varchar(50)	Checked
	private String attr1;//ATTR1 备用1	varchar(50)	Checked
	private String attr2;//ATTR2 备用2	varchar(50)	Checked
	private String type;//型号
	private String SEARCH_W;//拼音首字母
	private String position;//存放位置
	private String sell_num;//寄售库存
	private String random_num;//随机库存
	private String amount;//总花费
	private String del;//状态
	
	
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
	public MdEqpType getEqpType() {
		return eqpType;
	}
	public void setEqpType(MdEqpType eqpType) {
		this.eqpType = eqpType;
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
	public MdUnit getUnit() {
		return unit;
	}
	public void setUnit(MdUnit unit) {
		this.unit = unit;
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
}
