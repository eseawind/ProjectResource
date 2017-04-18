package com.lanbao.dws.model.wct.pdStat;

public class InputPageModel {
	private String eqpCod;
	private String eqpName;
	private String matId;
	private String mat;//牌号
	private String date;//日期
	private String shift;//班次
	private String team;//班组
	private String matType;//辅料类型
	//卷烟机标准单耗，水松纸，卷烟纸，滤棒
	private Double  shuisongzhiIn =0D;
	private Double  juanyanzhiIn =0D;
	private Double  lvbangIn =0D;
	private Double  shuisongzhiBzdh =0D;
	private Double  juanyanzhiBzdh =0D;
	private Double  lvbangBzdh =0D;
	private Double qty =0D;//产量
	
	//包装机标准单耗，水松纸，卷烟纸，滤棒
	private Double  xiaohemoIn =0D;
	private Double  tiaohemoIn =0D;
	private Double  xiaohezhiIn =0D;
	private Double  tiaohezhiIn =0D;
	private Double  neichenzhiIn =0D;
	private Double  xiaohemoBzdh =0D;
	private Double  tiaohemoBzdh =0D;
	private Double  xiaohezhiBzdh =0D;
	private Double  tiaohezhiBzdh =0D;
	private Double  neichenzhiBzdh =0D;
	//包装机辅料id
	private String xiaohemoid;
	private String xiaohezhiid;
	private String tiaohemoid;
	private String tiaohezhiid;
	private String neichenzhiid;
	private String juanyanzhiid;
	private String shuisongzhiid;
	private String lvbangid;
	
	private Double xiaohemodj;//小盒膜单价
	private Double xiaohezhidj;//小盒纸单价
	private Double tiaohemodj;//条盒膜单价
	private Double tiaohezhidj;//条盒纸单价
	private Double neichenzhidj;//内衬纸单价
	private Double juanyanzhidj;//卷烟纸单价、盘纸
	private Double shuisongzhidj;//水松纸单价
	private Double lvbangdj;//滤棒单价
	
	
	
	
	
	//成型机标准单耗，水松纸，卷烟纸，滤棒
	private Double  panzhiIn =0D;
	private Double  ganyouIn =0D;
	private Double  sishuIn =0D;
	private Double  panzhiBzdh =0D;
	private Double  ganyouBzdh =0D;
	private Double  sishuBzdh =0D;
	
	private String unit;//单位

	public InputPageModel(String eqpCod, Double shuisongzhiVal, Double juanyanzhiVal, Double lvbangVal,
			Double qty) {
		this.eqpCod=eqpCod;
		this.shuisongzhiIn=shuisongzhiVal;
		this.juanyanzhiIn=juanyanzhiVal;
		this.lvbangIn=lvbangVal;
		this.qty=qty;
	}

	public InputPageModel(String equipmentCode, Double qty, Double xiaohemoVal, Double tiaohemoVal,
			Double xiaohezhiVal, Double tiaohezhiVal, Double neichenzhiVal) {
		this.eqpCod=equipmentCode;
		this.qty=qty;
		this.xiaohemoIn=xiaohemoVal;
		this.tiaohemoIn=tiaohemoVal;
		this.xiaohezhiIn=xiaohezhiVal;
		this.tiaohezhiIn=tiaohezhiVal;
		this.neichenzhiIn=neichenzhiVal;
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

	public String getMatId() {
		return matId;
	}

	public void setMatId(String matId) {
		this.matId = matId;
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

	public String getMatType() {
		return matType;
	}

	public void setMatType(String matType) {
		this.matType = matType;
	}

	public Double getShuisongzhiIn() {
		return shuisongzhiIn;
	}

	public void setShuisongzhiIn(Double shuisongzhiIn) {
		this.shuisongzhiIn = shuisongzhiIn;
	}

	public Double getJuanyanzhiIn() {
		return juanyanzhiIn;
	}

	public void setJuanyanzhiIn(Double juanyanzhiIn) {
		this.juanyanzhiIn = juanyanzhiIn;
	}

	public Double getLvbangIn() {
		return lvbangIn;
	}

	public void setLvbangIn(Double lvbangIn) {
		this.lvbangIn = lvbangIn;
	}

	public Double getShuisongzhiBzdh() {
		return shuisongzhiBzdh;
	}

	public void setShuisongzhiBzdh(Double shuisongzhiBzdh) {
		this.shuisongzhiBzdh = shuisongzhiBzdh;
	}

	public Double getJuanyanzhiBzdh() {
		return juanyanzhiBzdh;
	}

	public void setJuanyanzhiBzdh(Double juanyanzhiBzdh) {
		this.juanyanzhiBzdh = juanyanzhiBzdh;
	}

	public Double getLvbangBzdh() {
		return lvbangBzdh;
	}

	public void setLvbangBzdh(Double lvbangBzdh) {
		this.lvbangBzdh = lvbangBzdh;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getXiaohemoIn() {
		return xiaohemoIn;
	}

	public void setXiaohemoIn(Double xiaohemoIn) {
		this.xiaohemoIn = xiaohemoIn;
	}

	public Double getTiaohemoIn() {
		return tiaohemoIn;
	}

	public void setTiaohemoIn(Double tiaohemoIn) {
		this.tiaohemoIn = tiaohemoIn;
	}

	public Double getXiaohezhiIn() {
		return xiaohezhiIn;
	}

	public void setXiaohezhiIn(Double xiaohezhiIn) {
		this.xiaohezhiIn = xiaohezhiIn;
	}

	public Double getTiaohezhiIn() {
		return tiaohezhiIn;
	}

	public void setTiaohezhiIn(Double tiaohezhiIn) {
		this.tiaohezhiIn = tiaohezhiIn;
	}

	public Double getNeichenzhiIn() {
		return neichenzhiIn;
	}

	public void setNeichenzhiIn(Double neichenzhiIn) {
		this.neichenzhiIn = neichenzhiIn;
	}

	public Double getXiaohemoBzdh() {
		return xiaohemoBzdh;
	}

	public void setXiaohemoBzdh(Double xiaohemoBzdh) {
		this.xiaohemoBzdh = xiaohemoBzdh;
	}

	public Double getTiaohemoBzdh() {
		return tiaohemoBzdh;
	}

	public void setTiaohemoBzdh(Double tiaohemoBzdh) {
		this.tiaohemoBzdh = tiaohemoBzdh;
	}

	public Double getXiaohezhiBzdh() {
		return xiaohezhiBzdh;
	}

	public void setXiaohezhiBzdh(Double xiaohezhiBzdh) {
		this.xiaohezhiBzdh = xiaohezhiBzdh;
	}

	public Double getTiaohezhiBzdh() {
		return tiaohezhiBzdh;
	}

	public void setTiaohezhiBzdh(Double tiaohezhiBzdh) {
		this.tiaohezhiBzdh = tiaohezhiBzdh;
	}

	public Double getNeichenzhiBzdh() {
		return neichenzhiBzdh;
	}

	public void setNeichenzhiBzdh(Double neichenzhiBzdh) {
		this.neichenzhiBzdh = neichenzhiBzdh;
	}

	public String getXiaohemoid() {
		return xiaohemoid;
	}

	public void setXiaohemoid(String xiaohemoid) {
		this.xiaohemoid = xiaohemoid;
	}

	public String getXiaohezhiid() {
		return xiaohezhiid;
	}

	public void setXiaohezhiid(String xiaohezhiid) {
		this.xiaohezhiid = xiaohezhiid;
	}

	public String getTiaohemoid() {
		return tiaohemoid;
	}

	public void setTiaohemoid(String tiaohemoid) {
		this.tiaohemoid = tiaohemoid;
	}

	public String getTiaohezhiid() {
		return tiaohezhiid;
	}

	public void setTiaohezhiid(String tiaohezhiid) {
		this.tiaohezhiid = tiaohezhiid;
	}

	public String getNeichenzhiid() {
		return neichenzhiid;
	}

	public void setNeichenzhiid(String neichenzhiid) {
		this.neichenzhiid = neichenzhiid;
	}

	public String getJuanyanzhiid() {
		return juanyanzhiid;
	}

	public void setJuanyanzhiid(String juanyanzhiid) {
		this.juanyanzhiid = juanyanzhiid;
	}

	public String getShuisongzhiid() {
		return shuisongzhiid;
	}

	public void setShuisongzhiid(String shuisongzhiid) {
		this.shuisongzhiid = shuisongzhiid;
	}

	public String getLvbangid() {
		return lvbangid;
	}

	public void setLvbangid(String lvbangid) {
		this.lvbangid = lvbangid;
	}

	public Double getXiaohemodj() {
		return xiaohemodj;
	}

	public void setXiaohemodj(Double xiaohemodj) {
		this.xiaohemodj = xiaohemodj;
	}

	public Double getXiaohezhidj() {
		return xiaohezhidj;
	}

	public void setXiaohezhidj(Double xiaohezhidj) {
		this.xiaohezhidj = xiaohezhidj;
	}

	public Double getTiaohemodj() {
		return tiaohemodj;
	}

	public void setTiaohemodj(Double tiaohemodj) {
		this.tiaohemodj = tiaohemodj;
	}

	public Double getTiaohezhidj() {
		return tiaohezhidj;
	}

	public void setTiaohezhidj(Double tiaohezhidj) {
		this.tiaohezhidj = tiaohezhidj;
	}

	public Double getNeichenzhidj() {
		return neichenzhidj;
	}

	public void setNeichenzhidj(Double neichenzhidj) {
		this.neichenzhidj = neichenzhidj;
	}

	public Double getJuanyanzhidj() {
		return juanyanzhidj;
	}

	public void setJuanyanzhidj(Double juanyanzhidj) {
		this.juanyanzhidj = juanyanzhidj;
	}

	public Double getShuisongzhidj() {
		return shuisongzhidj;
	}

	public void setShuisongzhidj(Double shuisongzhidj) {
		this.shuisongzhidj = shuisongzhidj;
	}

	public Double getLvbangdj() {
		return lvbangdj;
	}

	public void setLvbangdj(Double lvbangdj) {
		this.lvbangdj = lvbangdj;
	}

	public Double getPanzhiIn() {
		return panzhiIn;
	}

	public void setPanzhiIn(Double panzhiIn) {
		this.panzhiIn = panzhiIn;
	}

	public Double getGanyouIn() {
		return ganyouIn;
	}

	public void setGanyouIn(Double ganyouIn) {
		this.ganyouIn = ganyouIn;
	}

	public Double getSishuIn() {
		return sishuIn;
	}

	public void setSishuIn(Double sishuIn) {
		this.sishuIn = sishuIn;
	}

	public Double getPanzhiBzdh() {
		return panzhiBzdh;
	}

	public void setPanzhiBzdh(Double panzhiBzdh) {
		this.panzhiBzdh = panzhiBzdh;
	}

	public Double getGanyouBzdh() {
		return ganyouBzdh;
	}

	public void setGanyouBzdh(Double ganyouBzdh) {
		this.ganyouBzdh = ganyouBzdh;
	}

	public Double getSishuBzdh() {
		return sishuBzdh;
	}

	public void setSishuBzdh(Double sishuBzdh) {
		this.sishuBzdh = sishuBzdh;
	}

	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
