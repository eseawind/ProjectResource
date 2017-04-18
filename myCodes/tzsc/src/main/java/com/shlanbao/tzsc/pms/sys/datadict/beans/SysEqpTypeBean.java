package com.shlanbao.tzsc.pms.sys.datadict.beans;



public class SysEqpTypeBean {

	private String id;
	private String code;
	private String name;
	private String des;
	private Long enable;
	private String del;
	private String categoryId;
	private String categoryName;
	private String lxType;//类型
	private String number;//点数
	private String oilId;//润 滑 油（脂）ID
	private String oilName;//润 滑 油（脂）
	private String rhPart;//润滑部位
	private Integer period;//润滑周期
	 private String unit_id;//周期单位
	private Integer periodUnit;//周期单位
	private String periodUnit2;//周期单位
	
	private String fillQuantity;//加注量
	private String fillUnit;//加注量单位
	private String fillUnitName;//加注量单位
	private String fashion;//方 式
	private String fashionName;//方 式
	
	private String djType;//点检类型角色
    private String djMethodId;//点检方法
    private String setImagePoint;//设置图片代码
    private String filePath;//文件路径
    
	private String uploadUrl;//图片地址
	private String twoRowId;//子节点 主键ID
	private String contextPath;//项目路径
	private String contextDes;//内容
	
	
	public String getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}
	public String getPeriodUnit2() {
		return periodUnit2;
	}
	public void setPeriodUnit2(String periodUnit2) {
		this.periodUnit2 = periodUnit2;
	}
	public Integer getPeriodUnit() {
		return periodUnit;
	}
	public void setPeriodUnit(Integer periodUnit) {
		this.periodUnit = periodUnit;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Long getEnable() {
		return enable;
	}
	public void setEnable(Long enable) {
		this.enable = enable;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	@Override
	public String toString() {
		return "MdEqpTypeBean [id=" + id + ", code=" + code + ", name=" + name
				+ ", des=" + des + ", enable=" + enable + ", del=" + del + "]";
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getLxType() {
		return lxType;
	}
	public void setLxType(String lxType) {
		this.lxType = lxType;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getOilId() {
		return oilId;
	}
	public void setOilId(String oilId) {
		this.oilId = oilId;
	}
	public String getOilName() {
		return oilName;
	}
	public void setOilName(String oilName) {
		this.oilName = oilName;
	}
	public String getFillQuantity() {
		return fillQuantity;
	}
	public void setFillQuantity(String fillQuantity) {
		this.fillQuantity = fillQuantity;
	}
	public String getFillUnit() {
		return fillUnit;
	}
	public void setFillUnit(String fillUnit) {
		this.fillUnit = fillUnit;
	}
	public String getFillUnitName() {
		return fillUnitName;
	}
	public void setFillUnitName(String fillUnitName) {
		this.fillUnitName = fillUnitName;
	}
	public String getFashion() {
		return fashion;
	}
	public void setFashion(String fashion) {
		this.fashion = fashion;
	}
	public String getFashionName() {
		return fashionName;
	}
	public void setFashionName(String fashionName) {
		this.fashionName = fashionName;
	}
	public String getDjType() {
		return djType;
	}
	public void setDjType(String djType) {
		this.djType = djType;
	}
	public String getDjMethodId() {
		return djMethodId;
	}
	public void setDjMethodId(String djMethodId) {
		this.djMethodId = djMethodId;
	}
	public String getSetImagePoint() {
		return setImagePoint;
	}
	public void setSetImagePoint(String setImagePoint) {
		this.setImagePoint = setImagePoint;
	}
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	public String getTwoRowId() {
		return twoRowId;
	}
	public void setTwoRowId(String twoRowId) {
		this.twoRowId = twoRowId;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getContextDes() {
		return contextDes;
	}
	public void setContextDes(String contextDes) {
		this.contextDes = contextDes;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getRhPart() {
		return rhPart;
	}
	public void setRhPart(String rhPart) {
		this.rhPart = rhPart;
	}
}
