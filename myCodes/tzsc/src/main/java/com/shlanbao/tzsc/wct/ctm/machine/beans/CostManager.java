package com.shlanbao.tzsc.wct.ctm.machine.beans;

/**
 * @ClassName: CostManager 
 * @Description: 机台成本 
 * @author luo
 * @date 2015年1月27日 上午10:55:29 
 */
public class CostManager implements Comparable<CostManager>{
	private String eqpCod;
	private String eqpName;
	private String matId;
	private String mat;//牌号
	private String date;//日期
	private String shift;//班次
	private String team;//班组
	private String matType;//辅料类型
	private String eqpType;//机型
	//卷烟机标准单耗，水松纸，卷烟纸，滤棒
	private double  shuisongzhiIn =0D;//输出
	private double  shuisongzhiD =0D;//单价
	private double  shuisongzhiSumPrice =0D;//总价
	private double  juanyanzhiIn =0D;
	private double  juanyanzhiD =0D;
	private double  juanyanzhiSumPrice =0D;//总价
	private double  lvbangIn =0D;
	private double  lvbangD =0D;
	private double  lvbangSumPrice =0D;//总价
	private double  shuisongzhiBzdh =0D;
	private double  juanyanzhiBzdh =0D;
	private double  lvbangBzdh =0D;
	private double qty =0D;//产量
	private double rollerSumPrice=0D;//卷烟机总成本
	
	//包装机标准单耗，小盒烟膜、条盒烟膜、小盒纸、条盒纸、内衬纸
	private double  xiaohemoIn =0D;
	private double  xiaohemoD =0D;
	private double  xiaohemoSumPrice =0D;
	private double  tiaohemoIn =0D;
	private double  tiaohemoD =0D;
	private double  tiaohemoSumPrice =0D;
	private double  xiaohezhiIn =0D;
	private double  xiaohezhiD =0D;
	private double  xiaohezhiSumPrice =0D;
	private double  tiaohezhiIn =0D;
	private double  tiaohezhiD =0D;
	private double  tiaohezhiSumPrice =0D;
	private double  neichenzhiIn =0D;
	private double  neichenzhiD =0D;
	private double  neichenzhiSumPrice =0D;
	private double  xiaohemoBzdh =0D;
	private double  tiaohemoBzdh =0D;
	private double  xiaohezhiBzdh =0D;
	private double  tiaohezhiBzdh =0D;
	private double  neichenzhiBzdh =0D;
	private double  packSumPrice=0D;//包装机总成本
	
	//成型机标准单耗，盘纸，甘油，丝束
	private double  panzhiIn =0D;
	private double  panzhiD =0D;
	private double  panzhiSumPrice =0D;
	private double  ganyouIn =0D;
	private double  ganyouD =0D;
	private double  ganyouSumPrice =0D;
	private double  sishuIn =0D;
	private double  sishuD =0D;
	private double  sishuSumPrice =0D;
	private double  panzhiBzdh =0D;
	private double  ganyouBzdh =0D;
	private double  sishuBzdh =0D;
	private double  filterSumPrice=0D;//包装机总成本
	
	private String unit;//单位
	
	public CostManager() {
	}
	
	public double getRollerSumPrice() {
		if(rollerSumPrice>0){
			return rollerSumPrice;
		}
		return shuisongzhiSumPrice+juanyanzhiSumPrice+lvbangSumPrice;
	}

	public void setRollerSumPrice(double rollerSumPrice) {
		this.rollerSumPrice = rollerSumPrice;
	}
	public double getPackSumPrice() {
		if(packSumPrice>0){
			return packSumPrice;
		}
		return xiaohemoSumPrice+tiaohemoSumPrice+xiaohezhiSumPrice+tiaohezhiSumPrice+neichenzhiSumPrice;
	}
	public void setPackSumPrice(double packSumPrice) {
		this.packSumPrice = packSumPrice;
	}
	public double getFilterSumPrice() {
		if(filterSumPrice>0){
			return filterSumPrice;
		}else{
			return panzhiSumPrice+ganyouSumPrice+sishuSumPrice;
		}
	}
	public void setFilterSumPrice(double filterSumPrice) {
		this.filterSumPrice = filterSumPrice;
	}
	public double getShuisongzhiD() {
		return shuisongzhiD;
	}

	public void setShuisongzhiD(double shuisongzhiD) {
		this.shuisongzhiD = shuisongzhiD;
	}

	public double getShuisongzhiSumPrice() {
		if(shuisongzhiSumPrice>0){
			return shuisongzhiSumPrice;
		}
		return shuisongzhiD*shuisongzhiIn;
	}

	public void setShuisongzhiSumPrice(double shuisongzhiSumPrice) {
		this.shuisongzhiSumPrice = shuisongzhiSumPrice;
	}

	public double getJuanyanzhiD() {
		return juanyanzhiD;
	}

	public void setJuanyanzhiD(double juanyanzhiD) {
		this.juanyanzhiD = juanyanzhiD;
	}

	public double getJuanyanzhiSumPrice() {
		if(juanyanzhiSumPrice>0){
			return juanyanzhiSumPrice;
		}
		return juanyanzhiD*juanyanzhiIn;
	}

	public void setJuanyanzhiSumPrice(double juanyanzhiSumPrice) {
		this.juanyanzhiSumPrice = juanyanzhiSumPrice;
	}

	public double getLvbangD() {
		return lvbangD;
	}

	public void setLvbangD(double lvbangD) {
		this.lvbangD = lvbangD;
	}

	public double getLvbangSumPrice() {
		if(lvbangSumPrice>0){
			return lvbangSumPrice;
		}
		return lvbangD*lvbangIn;
	}

	public void setLvbangSumPrice(double lvbangSumPrice) {
		this.lvbangSumPrice = lvbangSumPrice;
	}

	public double getXiaohemoD() {
		return xiaohemoD;
	}

	public void setXiaohemoD(double xiaohemoD) {
		this.xiaohemoD = xiaohemoD;
	}

	public double getXiaohemoSumPrice() {
		if(xiaohemoSumPrice>0){
			return xiaohemoSumPrice;
		}
		return xiaohemoD*xiaohemoIn;
	}

	public void setXiaohemoSumPrice(double xiaohemoSumPrice) {
		this.xiaohemoSumPrice = xiaohemoSumPrice;
	}

	public double getTiaohemoD() {
		return tiaohemoD;
	}

	public void setTiaohemoD(double tiaohemoD) {
		this.tiaohemoD = tiaohemoD;
	}

	public double getTiaohemoSumPrice() {
		if(tiaohemoSumPrice>0){
			return tiaohemoSumPrice;
		}
		return tiaohemoD*tiaohemoIn;
	}

	public void setTiaohemoSumPrice(double tiaohemoSumPrice) {
		this.tiaohemoSumPrice = tiaohemoSumPrice;
	}

	public double getXiaohezhiD() {
		return xiaohezhiD;
	}

	public void setXiaohezhiD(double xiaohezhiD) {
		this.xiaohezhiD = xiaohezhiD;
	}

	public double getXiaohezhiSumPrice() {
		if(xiaohezhiSumPrice>0){
			return xiaohezhiSumPrice;
		}
		return xiaohezhiD*xiaohezhiIn;
	}

	public void setXiaohezhiSumPrice(double xiaohezhiSumPrice) {
		this.xiaohezhiSumPrice = xiaohezhiSumPrice;
	}

	public double getTiaohezhiD() {
		return tiaohezhiD;
	}

	public void setTiaohezhiD(double tiaohezhiD) {
		this.tiaohezhiD = tiaohezhiD;
	}

	public double getTiaohezhiSumPrice() {
		if(tiaohezhiSumPrice>0){
			return tiaohezhiSumPrice;
		}
		return tiaohezhiD*tiaohezhiIn;
	}

	public void setTiaohezhiSumPrice(double tiaohezhiSumPrice) {
		this.tiaohezhiSumPrice = tiaohezhiSumPrice;
	}

	public double getNeichenzhiD() {
		return neichenzhiD;
	}

	public void setNeichenzhiD(double neichenzhiD) {
		this.neichenzhiD = neichenzhiD;
	}

	public double getNeichenzhiSumPrice() {
		if(neichenzhiSumPrice>0){
			return neichenzhiSumPrice;
		}
		return neichenzhiD*neichenzhiIn;
	}

	public void setNeichenzhiSumPrice(double neichenzhiSumPrice) {
		this.neichenzhiSumPrice = neichenzhiSumPrice;
	}

	public double getPanzhiD() {
		return panzhiD;
	}

	public void setPanzhiD(double panzhiD) {
		this.panzhiD = panzhiD;
	}

	public double getPanzhiSumPrice() {
		if(panzhiSumPrice>0){
			return panzhiSumPrice;
		}
		return panzhiD*panzhiIn;
	}

	public void setPanzhiSumPrice(double panzhiSumPrice) {
		this.panzhiSumPrice = panzhiSumPrice;
	}

	public double getGanyouD() {
		return ganyouD;
	}

	public void setGanyouD(double ganyouD) {
		this.ganyouD = ganyouD;
	}

	public double getGanyouSumPrice() {
		if(ganyouSumPrice>0){
			return ganyouSumPrice;
		}
		return ganyouD*ganyouIn;
	}

	public void setGanyouSumPrice(double ganyouSumPrice) {
		this.ganyouSumPrice = ganyouSumPrice;
	}

	public double getSishuD() {
		return sishuD;
	}

	public void setSishuD(double sishuD) {
		this.sishuD = sishuD;
	}

	public double getSishuSumPrice() {
		if(sishuSumPrice>0){
			return sishuSumPrice;
		}
		return sishuD*sishuIn;
	}

	public void setSishuSumPrice(double sishuSumPrice) {
		this.sishuSumPrice = sishuSumPrice;
	}

	/**
	 * 卷烟机
	 * @param eqpCod 设备编号
	 * @param eqpName 设备名称
	 * @param mat 牌号
	 * @param date 日期
	 * @param shift 班次
	 * @param team 班组
	 * @param shuisongzhiBzdh 水松纸标准单耗
	 * @param juanyanzhiBzdh 卷烟纸标准单耗
	 * @param lvbangBzdh 滤棒
	 * @param unit 单位
	 */
	public CostManager(String eqpCod, String eqpName, String mat,
			String date, String shift, String team, double shuisongzhiBzdh,
			double juanyanzhiBzdh, double lvbangBzdh, String unit) {
		super();
		this.eqpCod = eqpCod;
		this.eqpName = eqpName;
		this.mat = mat;
		this.date = date;
		this.shift = shift;
		this.team = team;
		this.shuisongzhiBzdh = shuisongzhiBzdh;
		this.juanyanzhiBzdh = juanyanzhiBzdh;
		this.lvbangBzdh = lvbangBzdh;
		this.unit = unit;
	}
	
	
	/**
	 * 包装机
	 * @param eqpCod 设备编号
	 * @param eqpName 设备名称
	 * @param mat 牌号
	 * @param date 日期
	 * @param shift 班次
	 * @param team 班组
	 * @param xiaohemoBzdh 小盒烟膜
	 * @param tiaohemoBzdh 条盒烟膜
	 * @param xiaohezhiBzdh 小盒纸
	 * @param tiaohezhiBzdh 条盒纸
	 * @param neichenzhiBzdh 内衬纸
	 * @param unit 单位
	 */
	public CostManager(String eqpCod, String eqpName, String mat,
			String date, String shift, String team, double xiaohemoBzdh,
			double tiaohemoBzdh, double xiaohezhiBzdh, double tiaohezhiBzdh,
			double neichenzhiBzdh, String unit) {
		super();
		this.eqpCod = eqpCod;
		this.eqpName = eqpName;
		this.mat = mat;
		this.date = date;
		this.shift = shift;
		this.team = team;
		this.xiaohemoBzdh = xiaohemoBzdh;
		this.tiaohemoBzdh = tiaohemoBzdh;
		this.xiaohezhiBzdh = xiaohezhiBzdh;
		this.tiaohezhiBzdh = tiaohezhiBzdh;
		this.neichenzhiBzdh = neichenzhiBzdh;
		this.unit = unit;
	}


	/**
	 * 成型机
	 * @param eqpCod 设备编号
	 * @param eqpName 设备名称
	 * @param mat 牌号
	 * @param date 日期
	 * @param shift 班次
	 * @param team 班组
	 * @param panzhiBzdh 盘纸
	 * @param ganyouBzdh 甘油
	 * @param sishuBzdh 丝束
	 * @param unit
	 * @param null_ 为区分卷烟机构造函数多设置的一个null参数
	 */
	public CostManager(String eqpCod, String eqpName, String mat,
			String date, String shift, String team, double panzhiBzdh,
			double ganyouBzdh, double sishuBzdh, String unit,Object null_) {
		super();
		this.eqpCod = eqpCod;
		this.eqpName = eqpName;
		this.mat = mat;
		this.date = date;
		this.shift = shift;
		this.team = team;
		this.panzhiBzdh = panzhiBzdh;
		this.ganyouBzdh = ganyouBzdh;
		this.sishuBzdh = sishuBzdh;
		this.unit = unit;
	}

	

	/**
	 * 卷烟机实时数据Bean
	 * @param eqpCod
	 * @param shuisongzhiIn
	 * @param juanyanzhiIn
	 * @param lvbangIn
	 * @param qty
	 * @param xiaohemoIn
	 */
	public CostManager(String eqpCod, double shuisongzhiIn,
			double juanyanzhiIn, double lvbangIn, double qty) {
		super();
		this.eqpCod = eqpCod;
		this.shuisongzhiIn = shuisongzhiIn;
		this.juanyanzhiIn = juanyanzhiIn;
		this.lvbangIn = lvbangIn;
		this.qty = qty;
	}
	
	
	/**
	 * 包装机实时数据bean
	 * @param eqpCod 设备编码
	 * @param qty 产量
	 * @param xiaohemoIn 小盒膜
	 * @param tiaohemoIn 条盒膜
	 * @param xiaohezhiIn 小盒纸
	 * @param tiaohezhiIn 条盒纸
	 * @param neichenzhiIn 内衬纸（水松纸）
	 */
	public CostManager(String eqpCod, double qty, double xiaohemoIn,
			double tiaohemoIn, double xiaohezhiIn, double tiaohezhiIn,
			double neichenzhiIn) {
		super();
		this.eqpCod = eqpCod;
		this.qty = qty;
		this.xiaohemoIn = xiaohemoIn;
		this.tiaohemoIn = tiaohemoIn;
		this.xiaohezhiIn = xiaohezhiIn;
		this.tiaohezhiIn = tiaohezhiIn;
		this.neichenzhiIn = neichenzhiIn;
	}

	
	/**
	 * 成型机实时数据bean
	 * @param eqpCod 设备编码
	 * @param qty 产量
	 * @param panzhiIn 盘纸
	 * @param ganyouIn 甘油
	 * @param sishuIn 丝束
	 * @param null_ 避免与包装机重载
	 */
	public CostManager(String eqpCod, double panzhiIn,
			double ganyouIn, double sishuIn, double qty, Object null_) {
		super();
		this.eqpCod = eqpCod;
		this.qty = qty;
		this.panzhiIn = panzhiIn;
		this.ganyouIn = ganyouIn;
		this.sishuIn = sishuIn;
	}

	public String getEqpType() {
		return eqpType;
	}

	public void setEqpType(String eqpType) {
		this.eqpType = eqpType;
	}

	public String getEqpCod() {
		return eqpCod;
	}

	public void setEqpCod(String eqpCod) {
		this.eqpCod = eqpCod;
	}

	public String getEqpName() {
		return eqpName;
	}

	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}

	public String getMat() {
		return mat;
	}

	public void setMat(String mat) {
		this.mat = mat;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public double getShuisongzhiBzdh() {
		return shuisongzhiBzdh;
	}

	public void setShuisongzhiBzdh(double shuisongzhiBzdh) {
		this.shuisongzhiBzdh = shuisongzhiBzdh;
	}

	public double getJuanyanzhiBzdh() {
		return juanyanzhiBzdh;
	}

	public void setJuanyanzhiBzdh(double juanyanzhiBzdh) {
		this.juanyanzhiBzdh = juanyanzhiBzdh;
	}

	public double getLvbangBzdh() {
		return lvbangBzdh;
	}

	public void setLvbangBzdh(double lvbangBzdh) {
		this.lvbangBzdh = lvbangBzdh;
	}

	public double getXiaohemoBzdh() {
		return xiaohemoBzdh;
	}

	public void setXiaohemoBzdh(double xiaohemoBzdh) {
		this.xiaohemoBzdh = xiaohemoBzdh;
	}

	public double getTiaohemoBzdh() {
		return tiaohemoBzdh;
	}

	public void setTiaohemoBzdh(double tiaohemoBzdh) {
		this.tiaohemoBzdh = tiaohemoBzdh;
	}

	public double getXiaohezhiBzdh() {
		return xiaohezhiBzdh;
	}

	public void setXiaohezhiBzdh(double xiaohezhiBzdh) {
		this.xiaohezhiBzdh = xiaohezhiBzdh;
	}

	public double getTiaohezhiBzdh() {
		return tiaohezhiBzdh;
	}

	public void setTiaohezhiBzdh(double tiaohezhiBzdh) {
		this.tiaohezhiBzdh = tiaohezhiBzdh;
	}

	public double getNeichenzhiBzdh() {
		return neichenzhiBzdh;
	}

	public void setNeichenzhiBzdh(double neichenzhiBzdh) {
		this.neichenzhiBzdh = neichenzhiBzdh;
	}

	public double getPanzhiBzdh() {
		return panzhiBzdh;
	}

	public void setPanzhiBzdh(double panzhiBzdh) {
		this.panzhiBzdh = panzhiBzdh;
	}

	public double getGanyouBzdh() {
		return ganyouBzdh;
	}

	public void setGanyouBzdh(double ganyouBzdh) {
		this.ganyouBzdh = ganyouBzdh;
	}

	public double getSishuBzdh() {
		return sishuBzdh;
	}

	public void setSishuBzdh(double sishuBzdh) {
		this.sishuBzdh = sishuBzdh;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMatId() {
		return matId;
	}

	public void setMatId(String matId) {
		this.matId = matId;
	}

	public String getMatType() {
		return matType;
	}

	public void setMatType(String matType) {
		this.matType = matType;
	}

	public double getShuisongzhiIn() {
		return shuisongzhiIn;
	}

	public void setShuisongzhiIn(double shuisongzhiIn) {
		this.shuisongzhiIn = shuisongzhiIn;
	}

	public double getJuanyanzhiIn() {
		return juanyanzhiIn;
	}

	public void setJuanyanzhiIn(double juanyanzhiIn) {
		this.juanyanzhiIn = juanyanzhiIn;
	}

	public double getLvbangIn() {
		return lvbangIn;
	}

	public void setLvbangIn(double lvbangIn) {
		this.lvbangIn = lvbangIn;
	}

	public double getXiaohemoIn() {
		return xiaohemoIn;
	}

	public void setXiaohemoIn(double xiaohemoIn) {
		this.xiaohemoIn = xiaohemoIn;
	}

	public double getTiaohemoIn() {
		return tiaohemoIn;
	}

	public void setTiaohemoIn(double tiaohemoIn) {
		this.tiaohemoIn = tiaohemoIn;
	}

	public double getXiaohezhiIn() {
		return xiaohezhiIn;
	}

	public void setXiaohezhiIn(double xiaohezhiIn) {
		this.xiaohezhiIn = xiaohezhiIn;
	}

	public double getTiaohezhiIn() {
		return tiaohezhiIn;
	}

	public void setTiaohezhiIn(double tiaohezhiIn) {
		this.tiaohezhiIn = tiaohezhiIn;
	}

	public double getNeichenzhiIn() {
		return neichenzhiIn;
	}

	public void setNeichenzhiIn(double neichenzhiIn) {
		this.neichenzhiIn = neichenzhiIn;
	}

	public double getPanzhiIn() {
		return panzhiIn;
	}

	public void setPanzhiIn(double panzhiIn) {
		this.panzhiIn = panzhiIn;
	}

	public double getGanyouIn() {
		return ganyouIn;
	}

	public void setGanyouIn(double ganyouIn) {
		this.ganyouIn = ganyouIn;
	}

	public double getSishuIn() {
		return sishuIn;
	}

	public void setSishuIn(double sishuIn) {
		this.sishuIn = sishuIn;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	@Override
	public int compareTo(CostManager o) {
		if(this.eqpCod!=null){
			Integer sort1=Integer.valueOf(this.eqpCod);
			Integer sort2=Integer.valueOf(o.getEqpCod());
			if(sort1>=sort2){
				return 1;
			}
			if(sort1<sort2){
				return -1;
			}
		}
		return 0;
	}

	
	
}
