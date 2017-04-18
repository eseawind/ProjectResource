package com.shlanbao.tzsc.pms.sch.statjbcj.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * PMS交接班信息查询显示数据bean
 * @author TRAVLER
 *
 */
public class MatterInfo {
	
	private Integer eqpCode;//机台code
	private String team_Name; //班组ID
	private String orderDate; //工单时间
	private String handover_user_name; //交班人姓名
	private String userEqpGroup;//人员定编机组id
	private String eqpName;//设备名称
	private String mat_name; //品牌名称
	private Double juQty; //卷烟机产量
	private Double bzQty;//包装机产量
	private Double filterQty;//成型机产量
	private Double qt_jy;  //残烟 - 卷烟
	private Double qt_cc;  //残烟-吹车
	private Double qt_bz;  //残烟 - 包装
	
	private String teamId;//班次
	private String shiftId;//班组
	
	
	//虚领/////////////////////////////////
	//卷烟机
	private Long   lx_jy_id;//表id
	private Double xl_zj_lb;//滤棒
	private Double xl_zj_pz;//盘纸
	private Double xl_zj_ssz;//水松纸
	//包装机
	private Long xl_bz_id;//
	private Double xl_zb_xhz;//商标纸
	private Double xl_zb_thz;//条盒纸
	private Double xl_zb_lbz;//铝箔纸
	private Double xl_zb_xhm;//小盒膜
	private Double xl_zb_thm;//条盒膜
	private Double xl_zb_kz;//卡纸
	private Double xl_zb_fq;//封签
	private Double xl_zb_lx1;//拉线1
	private Double xl_zb_lx2;//拉线2
	//封箱机
	private Long xl_yf_id;
	private Double xl_yf_xp;//箱皮
	private Double xl_yf_jd;//胶带
	//成型机
	private Long xl_zl_id;
	private Double xl_zl_pz;//盘纸
	private Double xl_zl_ss;//丝束
	private Double xl_zl_gy;//甘油
	private Double xl_zl_dkj;//搭口胶
	private Double xl_zl_rrj;//热融胶
	

	//实领/////////////////////////

	//卷烟机
	private Long   sl_jy_id;//表id
	private Double sl_zj_lb;//滤棒
	private Double sl_zj_pz;//盘纸
	private Double sl_zj_ssz;//水松纸
	//包装机
	private Long sl_bz_id;//
	private Double sl_zb_xhz;//商标纸
	private Double sl_zb_thz;//条盒纸
	private Double sl_zb_lbz;//铝箔纸
	private Double sl_zb_xhm;//小盒膜
	private Double sl_zb_thm;//条盒膜
	private Double sl_zb_kz;//卡纸
	private Double sl_zb_fq;//封签
	private Double sl_zb_lx1;//拉线1
	private Double sl_zb_lx2;//拉线2
	//封箱机
	private Long sl_yf_id;
	private Double sl_yf_xp;//箱皮
	private Double sl_yf_jd;//胶带
	//成型机
	private Long sl_zl_id;
	private Double sl_zl_pz;//盘纸
	private Double sl_zl_ss;//丝束
	private Double sl_zl_gy;//甘油
	private Double sl_zl_dkj;//搭口胶
	private Double sl_zl_rrj;//热融胶
	
	//虚退////////////////////////////////
	//卷烟机
	private Long   xt_jy_id;//表id
	private Double xt_zj_lb;//滤棒
	private Double xt_zj_pz;//盘纸
	private Double xt_zj_ssz;//水松纸
	//包装机
	private Long xt_bz_id;//
	private Double xt_zb_xhz;//商标纸
	private Double xt_zb_thz;//条盒纸
	private Double xt_zb_lbz;//铝箔纸
	private Double xt_zb_xhm;//小盒膜
	private Double xt_zb_thm;//条盒膜
	private Double xt_zb_kz;//卡纸
	private Double xt_zb_fq;//封签
	private Double xt_zb_lx1;//拉线1
	private Double xt_zb_lx2;//拉线2	
	//封箱机
	private Long xt_yf_id;
	private Double xt_yf_xp;//箱皮
	private Double xt_yf_jd;//胶带
	//成型机
	private Long xt_zl_id;
	private Double xt_zl_pz;//盘纸
	private Double xt_zl_ss;//丝束
	private Double xt_zl_gy;//甘油
	private Double xt_zl_dkj;//搭口胶
	private Double xt_zl_rrj;//热融胶
	//实用/////////////////////////////////

	//卷烟机
	private Long   sy_jy_id;//表id
	private Double sy_zj_lb;//滤棒
	private Double sy_zj_pz;//盘纸
	private Double sy_zj_ssz;//水松纸
	//包装机
	private Long sy_bz_id;//
	private Double sy_zb_xhz;//商标纸
	private Double sy_zb_thz;//条盒纸
	private Double sy_zb_lbz;//铝箔纸
	private Double sy_zb_xhm;//小盒膜
	private Double sy_zb_thm;//条盒膜
	private Double sy_zb_kz;//卡纸
	private Double sy_zb_fq;//封签
	private Double sy_zb_lx1;//拉线1
	private Double sy_zb_lx2;//拉线2	
	//封箱机
	private Double sy_yf_xp;//箱皮
	private Double sy_yf_jd;//胶带
	private Double oth_jzj; //其他-接嘴胶
	private Double oth_dkj; //其他-搭扣胶
	private Double oth_bzj; //其他-包装胶
	
	//成型机
	private Long sy_zl_id;
	private Double sy_zl_pz;//盘纸
	private Double sy_zl_ss;//丝束
	private Double sy_zl_gy;//甘油
	private Double sy_zl_dkj;//搭口胶
	private Double sy_zl_rrj;//热融胶
	
	private Double pa_gg;//盘纸规格
	private Integer zc_type; //仲裁类型    1-已仲裁  0-默认 
	private Integer status; //状态   1-通过   0-默认
	
	//卷烟机工单
	private String yz_doid; //下一班的工单ID
	private String yz_uoid; //上一班的工单ID
	private String yz_oid;//当前工单id
	//包装机工单
	private String bz_doid; //下一班的工单ID
	private String bz_uoid; //上一班的工单ID
	private String bz_oid;//当前工单id
	//单耗	//////////////////////////
	private Double lbDH;//滤棒
	private Double pzDH;//盘纸
	private Double sszDH;//水松纸
	//包装机
	private Double xhzDH;//商标纸
	private Double thzDH;//条盒纸
	private Double lbzDH;//铝箔纸
	private Double xhmDH;//小盒膜
	private Double thmDH;//条盒膜
	private Double kzDH;//卡纸
	private Double fqDH;//封签
	private Double lx1DH;//拉线1
	private Double lx2DH;//拉线2	
	//残烟量单耗
	private Double zjDH;//卷烟机残烟单耗
	private Double zbDH;//包装机残烟单耗
	private Double ccDH;//吹车残烟单耗
	
	//甲班
	List<MatterInfo> l1=new ArrayList<MatterInfo>();
	List<MatterInfo> l2=new ArrayList<MatterInfo>();
	List<MatterInfo> l3=new ArrayList<MatterInfo>();
	//乙班
	List<MatterInfo> l4=new ArrayList<MatterInfo>();
	List<MatterInfo> l5=new ArrayList<MatterInfo>();
	List<MatterInfo> l6=new ArrayList<MatterInfo>();
	//丙班
	List<MatterInfo> l7=new ArrayList<MatterInfo>();
	List<MatterInfo> l8=new ArrayList<MatterInfo>();
	List<MatterInfo> l9=new ArrayList<MatterInfo>();
	
	public String getUserEqpGroup() {
		return userEqpGroup;
	}
	public void setUserEqpGroup(String userEqpGroup) {
		this.userEqpGroup = userEqpGroup;
	}
	public List<MatterInfo> getL1() {
		return l1;
	}
	public void setL1(List<MatterInfo> l1) {
		this.l1 = l1;
	}
	public List<MatterInfo> getL2() {
		return l2;
	}
	public void setL2(List<MatterInfo> l2) {
		this.l2 = l2;
	}
	public List<MatterInfo> getL3() {
		return l3;
	}
	public void setL3(List<MatterInfo> l3) {
		this.l3 = l3;
	}
	public List<MatterInfo> getL4() {
		return l4;
	}
	public void setL4(List<MatterInfo> l4) {
		this.l4 = l4;
	}
	public List<MatterInfo> getL5() {
		return l5;
	}
	public void setL5(List<MatterInfo> l5) {
		this.l5 = l5;
	}
	public List<MatterInfo> getL6() {
		return l6;
	}
	public void setL6(List<MatterInfo> l6) {
		this.l6 = l6;
	}
	public List<MatterInfo> getL7() {
		return l7;
	}
	public void setL7(List<MatterInfo> l7) {
		this.l7 = l7;
	}
	public List<MatterInfo> getL8() {
		return l8;
	}
	public void setL8(List<MatterInfo> l8) {
		this.l8 = l8;
	}
	public List<MatterInfo> getL9() {
		return l9;
	}
	public void setL9(List<MatterInfo> l9) {
		this.l9 = l9;
	}
	public Double getZjDH() {
		return zjDH;
	}
	public void setZjDH(Double zjDH) {
		this.zjDH = zjDH;
	}
	public Double getZbDH() {
		return zbDH;
	}
	public void setZbDH(Double zbDH) {
		this.zbDH = zbDH;
	}
	public Double getCcDH() {
		return ccDH;
	}
	public void setCcDH(Double ccDH) {
		this.ccDH = ccDH;
	}
	public Double getLbDH() {
		return lbDH;
	}
	public void setLbDH(Double lbDH) {
		this.lbDH = lbDH;
	}
	public Double getPzDH() {
		return pzDH;
	}
	public void setPzDH(Double pzDH) {
		this.pzDH = pzDH;
	}
	public Double getSszDH() {
		return sszDH;
	}
	public void setSszDH(Double sszDH) {
		this.sszDH = sszDH;
	}
	public Double getXhzDH() {
		return xhzDH;
	}
	public void setXhzDH(Double xhzDH) {
		this.xhzDH = xhzDH;
	}
	public Double getThzDH() {
		return thzDH;
	}
	public void setThzDH(Double thzDH) {
		this.thzDH = thzDH;
	}
	public Double getLbzDH() {
		return lbzDH;
	}
	public void setLbzDH(Double lbzDH) {
		this.lbzDH = lbzDH;
	}
	public Double getXhmDH() {
		return xhmDH;
	}
	public void setXhmDH(Double xhmDH) {
		this.xhmDH = xhmDH;
	}
	public Double getThmDH() {
		return thmDH;
	}
	public void setThmDH(Double thmDH) {
		this.thmDH = thmDH;
	}
	public Double getKzDH() {
		return kzDH;
	}
	public void setKzDH(Double kzDH) {
		this.kzDH = kzDH;
	}
	public Double getFqDH() {
		return fqDH;
	}
	public void setFqDH(Double fqDH) {
		this.fqDH = fqDH;
	}
	public Double getLx1DH() {
		return lx1DH;
	}
	public void setLx1DH(Double lx1dh) {
		lx1DH = lx1dh;
	}
	public Double getLx2DH() {
		return lx2DH;
	}
	public void setLx2DH(Double lx2dh) {
		lx2DH = lx2dh;
	}
	public Double getXl_yf_xp() {
		return xl_yf_xp;
	}
	public void setXl_yf_xp(Double xl_yf_xp) {
		this.xl_yf_xp = xl_yf_xp;
	}
	public Double getXl_yf_jd() {
		return xl_yf_jd;
	}
	public void setXl_yf_jd(Double xl_yf_jd) {
		this.xl_yf_jd = xl_yf_jd;
	}
	public Double getSl_yf_xp() {
		return sl_yf_xp;
	}
	public void setSl_yf_xp(Double sl_yf_xp) {
		this.sl_yf_xp = sl_yf_xp;
	}
	public Double getSl_yf_jd() {
		return sl_yf_jd;
	}
	public void setSl_yf_jd(Double sl_yf_jd) {
		this.sl_yf_jd = sl_yf_jd;
	}
	public Double getXt_yf_xp() {
		return xt_yf_xp;
	}
	public void setXt_yf_xp(Double xt_yf_xp) {
		this.xt_yf_xp = xt_yf_xp;
	}
	public Double getXt_yf_jd() {
		return xt_yf_jd;
	}
	public void setXt_yf_jd(Double xt_yf_jd) {
		this.xt_yf_jd = xt_yf_jd;
	}
	public Double getSy_yf_xp() {
		return sy_yf_xp;
	}
	
	public Long getXl_yf_id() {
		return xl_yf_id;
	}
	public void setXl_yf_id(Long xl_yf_id) {
		this.xl_yf_id = xl_yf_id;
	}
	public Long getSl_yf_id() {
		return sl_yf_id;
	}
	public void setSl_yf_id(Long sl_yf_id) {
		this.sl_yf_id = sl_yf_id;
	}
	public Long getXt_yf_id() {
		return xt_yf_id;
	}
	public void setXt_yf_id(Long xt_yf_id) {
		this.xt_yf_id = xt_yf_id;
	}
	public void setSy_yf_xp(Double sy_yf_xp) {
		this.sy_yf_xp = sy_yf_xp;
	}
	public Double getSy_yf_jd() {
		return sy_yf_jd;
	}
	public void setSy_yf_jd(Double sy_yf_jd) {
		this.sy_yf_jd = sy_yf_jd;
	}
	public Double getOth_jzj() {
		return oth_jzj;
	}
	public void setOth_jzj(Double oth_jzj) {
		this.oth_jzj = oth_jzj;
	}
	public Double getOth_dkj() {
		return oth_dkj;
	}
	public void setOth_dkj(Double oth_dkj) {
		this.oth_dkj = oth_dkj;
	}
	public Double getOth_bzj() {
		return oth_bzj;
	}
	public void setOth_bzj(Double oth_bzj) {
		this.oth_bzj = oth_bzj;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public Integer getEqpCode() {
		return eqpCode;
	}
	public void setEqpCode(Integer eqpCode) {
		this.eqpCode = eqpCode;
	}
	public String getOrderDate() {
		return orderDate;
	}
	
	public String getTeam_Name() {
		return team_Name;
	}
	public void setTeam_Name(String team_Name) {
		this.team_Name = team_Name;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getHandover_user_name() {
		return handover_user_name;
	}
	public void setHandover_user_name(String handover_user_name) {
		this.handover_user_name = handover_user_name;
	}
	public String getEqpName() {
		return eqpName;
	}
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
	}
	public String getMat_name() {
		return mat_name;
	}
	public void setMat_name(String mat_name) {
		this.mat_name = mat_name;
	}
	public Double getJuQty() {
		return juQty;
	}
	public void setJuQty(Double juQty) {
		this.juQty = juQty;
	}
	public Double getBzQty() {
		return bzQty;
	}
	public void setBzQty(Double bzQty) {
		this.bzQty = bzQty;
	}
	public Double getQt_jy() {
		return qt_jy;
	}
	public void setQt_jy(Double qt_jy) {
		this.qt_jy = qt_jy;
	}
	public Double getQt_cc() {
		return qt_cc;
	}
	public void setQt_cc(Double qt_cc) {
		this.qt_cc = qt_cc;
	}
	public Double getQt_bz() {
		return qt_bz;
	}
	public void setQt_bz(Double qt_bz) {
		this.qt_bz = qt_bz;
	}
	public Long getLx_jy_id() {
		return lx_jy_id;
	}
	public void setLx_jy_id(Long lx_jy_id) {
		this.lx_jy_id = lx_jy_id;
	}
	public Double getXl_zj_lb() {
		return xl_zj_lb;
	}
	public void setXl_zj_lb(Double xl_zj_lb) {
		this.xl_zj_lb = xl_zj_lb;
	}
	public Double getXl_zj_pz() {
		return xl_zj_pz;
	}
	public void setXl_zj_pz(Double xl_zj_pz) {
		this.xl_zj_pz = xl_zj_pz;
	}
	public Double getXl_zj_ssz() {
		return xl_zj_ssz;
	}
	public void setXl_zj_ssz(Double xl_zj_ssz) {
		this.xl_zj_ssz = xl_zj_ssz;
	}
	public Long getXl_bz_id() {
		return xl_bz_id;
	}
	public void setXl_bz_id(Long xl_bz_id) {
		this.xl_bz_id = xl_bz_id;
	}
	public Double getXl_zb_xhz() {
		return xl_zb_xhz;
	}
	public void setXl_zb_xhz(Double xl_zb_xhz) {
		this.xl_zb_xhz = xl_zb_xhz;
	}
	public Double getXl_zb_thz() {
		return xl_zb_thz;
	}
	public void setXl_zb_thz(Double xl_zb_thz) {
		this.xl_zb_thz = xl_zb_thz;
	}
	public Double getXl_zb_lbz() {
		return xl_zb_lbz;
	}
	public void setXl_zb_lbz(Double xl_zb_lbz) {
		this.xl_zb_lbz = xl_zb_lbz;
	}
	public Double getXl_zb_xhm() {
		return xl_zb_xhm;
	}
	public void setXl_zb_xhm(Double xl_zb_xhm) {
		this.xl_zb_xhm = xl_zb_xhm;
	}
	public Double getXl_zb_thm() {
		return xl_zb_thm;
	}
	public void setXl_zb_thm(Double xl_zb_thm) {
		this.xl_zb_thm = xl_zb_thm;
	}
	public Double getXl_zb_kz() {
		return xl_zb_kz;
	}
	public void setXl_zb_kz(Double xl_zb_kz) {
		this.xl_zb_kz = xl_zb_kz;
	}
	public Double getXl_zb_fq() {
		return xl_zb_fq;
	}
	public void setXl_zb_fq(Double xl_zb_fq) {
		this.xl_zb_fq = xl_zb_fq;
	}
	public Double getXl_zb_lx1() {
		return xl_zb_lx1;
	}
	public void setXl_zb_lx1(Double xl_zb_lx1) {
		this.xl_zb_lx1 = xl_zb_lx1;
	}
	public Double getXl_zb_lx2() {
		return xl_zb_lx2;
	}
	public void setXl_zb_lx2(Double xl_zb_lx2) {
		this.xl_zb_lx2 = xl_zb_lx2;
	}
	public Long getSl_jy_id() {
		return sl_jy_id;
	}
	public void setSl_jy_id(Long sl_jy_id) {
		this.sl_jy_id = sl_jy_id;
	}
	public Double getSl_zj_lb() {
		return sl_zj_lb;
	}
	public void setSl_zj_lb(Double sl_zj_lb) {
		this.sl_zj_lb = sl_zj_lb;
	}
	public Double getSl_zj_pz() {
		return sl_zj_pz;
	}
	public void setSl_zj_pz(Double sl_zj_pz) {
		this.sl_zj_pz = sl_zj_pz;
	}
	public Double getSl_zj_ssz() {
		return sl_zj_ssz;
	}
	public void setSl_zj_ssz(Double sl_zj_ssz) {
		this.sl_zj_ssz = sl_zj_ssz;
	}
	public Long getSl_bz_id() {
		return sl_bz_id;
	}
	public void setSl_bz_id(Long sl_bz_id) {
		this.sl_bz_id = sl_bz_id;
	}
	public Double getSl_zb_xhz() {
		return sl_zb_xhz;
	}
	public void setSl_zb_xhz(Double sl_zb_xhz) {
		this.sl_zb_xhz = sl_zb_xhz;
	}
	public Double getSl_zb_thz() {
		return sl_zb_thz;
	}
	public void setSl_zb_thz(Double sl_zb_thz) {
		this.sl_zb_thz = sl_zb_thz;
	}
	public Double getSl_zb_lbz() {
		return sl_zb_lbz;
	}
	public void setSl_zb_lbz(Double sl_zb_lbz) {
		this.sl_zb_lbz = sl_zb_lbz;
	}
	public Double getSl_zb_xhm() {
		return sl_zb_xhm;
	}
	public void setSl_zb_xhm(Double sl_zb_xhm) {
		this.sl_zb_xhm = sl_zb_xhm;
	}
	public Double getSl_zb_thm() {
		return sl_zb_thm;
	}
	public void setSl_zb_thm(Double sl_zb_thm) {
		this.sl_zb_thm = sl_zb_thm;
	}
	public Double getSl_zb_kz() {
		return sl_zb_kz;
	}
	public void setSl_zb_kz(Double sl_zb_kz) {
		this.sl_zb_kz = sl_zb_kz;
	}
	public Double getSl_zb_fq() {
		return sl_zb_fq;
	}
	public void setSl_zb_fq(Double sl_zb_fq) {
		this.sl_zb_fq = sl_zb_fq;
	}
	public Double getSl_zb_lx1() {
		return sl_zb_lx1;
	}
	public void setSl_zb_lx1(Double sl_zb_lx1) {
		this.sl_zb_lx1 = sl_zb_lx1;
	}
	public Double getSl_zb_lx2() {
		return sl_zb_lx2;
	}
	public void setSl_zb_lx2(Double sl_zb_lx2) {
		this.sl_zb_lx2 = sl_zb_lx2;
	}
	public Long getXt_jy_id() {
		return xt_jy_id;
	}
	public void setXt_jy_id(Long xt_jy_id) {
		this.xt_jy_id = xt_jy_id;
	}
	public Double getXt_zj_lb() {
		return xt_zj_lb;
	}
	public void setXt_zj_lb(Double xt_zj_lb) {
		this.xt_zj_lb = xt_zj_lb;
	}
	public Double getXt_zj_pz() {
		return xt_zj_pz;
	}
	public void setXt_zj_pz(Double xt_zj_pz) {
		this.xt_zj_pz = xt_zj_pz;
	}
	public Double getXt_zj_ssz() {
		return xt_zj_ssz;
	}
	public void setXt_zj_ssz(Double xt_zj_ssz) {
		this.xt_zj_ssz = xt_zj_ssz;
	}
	public Long getXt_bz_id() {
		return xt_bz_id;
	}
	public void setXt_bz_id(Long xt_bz_id) {
		this.xt_bz_id = xt_bz_id;
	}
	public Double getXt_zb_xhz() {
		return xt_zb_xhz;
	}
	public void setXt_zb_xhz(Double xt_zb_xhz) {
		this.xt_zb_xhz = xt_zb_xhz;
	}
	public Double getXt_zb_thz() {
		return xt_zb_thz;
	}
	public void setXt_zb_thz(Double xt_zb_thz) {
		this.xt_zb_thz = xt_zb_thz;
	}
	public Double getXt_zb_lbz() {
		return xt_zb_lbz;
	}
	public void setXt_zb_lbz(Double xt_zb_lbz) {
		this.xt_zb_lbz = xt_zb_lbz;
	}
	public Double getXt_zb_xhm() {
		return xt_zb_xhm;
	}
	public void setXt_zb_xhm(Double xt_zb_xhm) {
		this.xt_zb_xhm = xt_zb_xhm;
	}
	public Double getXt_zb_thm() {
		return xt_zb_thm;
	}
	public void setXt_zb_thm(Double xt_zb_thm) {
		this.xt_zb_thm = xt_zb_thm;
	}
	public Double getXt_zb_kz() {
		return xt_zb_kz;
	}
	public void setXt_zb_kz(Double xt_zb_kz) {
		this.xt_zb_kz = xt_zb_kz;
	}
	public Double getXt_zb_fq() {
		return xt_zb_fq;
	}
	public void setXt_zb_fq(Double xt_zb_fq) {
		this.xt_zb_fq = xt_zb_fq;
	}
	public Double getXt_zb_lx1() {
		return xt_zb_lx1;
	}
	public void setXt_zb_lx1(Double xt_zb_lx1) {
		this.xt_zb_lx1 = xt_zb_lx1;
	}
	public Double getXt_zb_lx2() {
		return xt_zb_lx2;
	}
	public void setXt_zb_lx2(Double xt_zb_lx2) {
		this.xt_zb_lx2 = xt_zb_lx2;
	}
	public Long getSy_jy_id() {
		return sy_jy_id;
	}
	public void setSy_jy_id(Long sy_jy_id) {
		this.sy_jy_id = sy_jy_id;
	}
	public Double getSy_zj_lb() {
		return sy_zj_lb;
	}
	public void setSy_zj_lb(Double sy_zj_lb) {
		this.sy_zj_lb = sy_zj_lb;
	}
	public Double getSy_zj_pz() {
		return sy_zj_pz;
	}
	public void setSy_zj_pz(Double sy_zj_pz) {
		this.sy_zj_pz = sy_zj_pz;
	}
	public Double getSy_zj_ssz() {
		return sy_zj_ssz;
	}
	public void setSy_zj_ssz(Double sy_zj_ssz) {
		this.sy_zj_ssz = sy_zj_ssz;
	}
	public Long getSy_bz_id() {
		return sy_bz_id;
	}
	public void setSy_bz_id(Long sy_bz_id) {
		this.sy_bz_id = sy_bz_id;
	}
	public Double getSy_zb_xhz() {
		return sy_zb_xhz;
	}
	public void setSy_zb_xhz(Double sy_zb_xhz) {
		this.sy_zb_xhz = sy_zb_xhz;
	}
	public Double getSy_zb_thz() {
		return sy_zb_thz;
	}
	public void setSy_zb_thz(Double sy_zb_thz) {
		this.sy_zb_thz = sy_zb_thz;
	}
	public Double getSy_zb_lbz() {
		return sy_zb_lbz;
	}
	public void setSy_zb_lbz(Double sy_zb_lbz) {
		this.sy_zb_lbz = sy_zb_lbz;
	}
	public Double getSy_zb_xhm() {
		return sy_zb_xhm;
	}
	public void setSy_zb_xhm(Double sy_zb_xhm) {
		this.sy_zb_xhm = sy_zb_xhm;
	}
	public Double getSy_zb_thm() {
		return sy_zb_thm;
	}
	public void setSy_zb_thm(Double sy_zb_thm) {
		this.sy_zb_thm = sy_zb_thm;
	}
	public Double getSy_zb_kz() {
		return sy_zb_kz;
	}
	public void setSy_zb_kz(Double sy_zb_kz) {
		this.sy_zb_kz = sy_zb_kz;
	}
	public Double getSy_zb_fq() {
		return sy_zb_fq;
	}
	public void setSy_zb_fq(Double sy_zb_fq) {
		this.sy_zb_fq = sy_zb_fq;
	}
	public Double getSy_zb_lx1() {
		return sy_zb_lx1;
	}
	public void setSy_zb_lx1(Double sy_zb_lx1) {
		this.sy_zb_lx1 = sy_zb_lx1;
	}
	public Double getSy_zb_lx2() {
		return sy_zb_lx2;
	}
	public void setSy_zb_lx2(Double sy_zb_lx2) {
		this.sy_zb_lx2 = sy_zb_lx2;
	}
	public Double getPa_gg() {
		return pa_gg;
	}
	public void setPa_gg(Double pa_gg) {
		this.pa_gg = pa_gg;
	}
	public Integer getZc_type() {
		return zc_type;
	}
	public void setZc_type(Integer zc_type) {
		this.zc_type = zc_type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getYz_doid() {
		return yz_doid;
	}
	public void setYz_doid(String yz_doid) {
		this.yz_doid = yz_doid;
	}
	public String getYz_uoid() {
		return yz_uoid;
	}
	public void setYz_uoid(String yz_uoid) {
		this.yz_uoid = yz_uoid;
	}
	public String getYz_oid() {
		return yz_oid;
	}
	public void setYz_oid(String yz_oid) {
		this.yz_oid = yz_oid;
	}
	public String getBz_doid() {
		return bz_doid;
	}
	public void setBz_doid(String bz_doid) {
		this.bz_doid = bz_doid;
	}
	public String getBz_uoid() {
		return bz_uoid;
	}
	public void setBz_uoid(String bz_uoid) {
		this.bz_uoid = bz_uoid;
	}
	public String getBz_oid() {
		return bz_oid;
	}
	public void setBz_oid(String bz_oid) {
		this.bz_oid = bz_oid;
	}
	public Double getFilterQty() {
		return filterQty;
	}
	public void setFilterQty(Double filterQty) {
		this.filterQty = filterQty;
	}
	public Long getXl_zl_id() {
		return xl_zl_id;
	}
	public void setXl_zl_id(Long xl_zl_id) {
		this.xl_zl_id = xl_zl_id;
	}
	public Double getXl_zl_pz() {
		return xl_zl_pz;
	}
	public void setXl_zl_pz(Double xl_zl_pz) {
		this.xl_zl_pz = xl_zl_pz;
	}
	public Double getXl_zl_ss() {
		return xl_zl_ss;
	}
	public void setXl_zl_ss(Double xl_zl_ss) {
		this.xl_zl_ss = xl_zl_ss;
	}
	public Double getXl_zl_gy() {
		return xl_zl_gy;
	}
	public void setXl_zl_gy(Double xl_zl_gy) {
		this.xl_zl_gy = xl_zl_gy;
	}
	public Double getXl_zl_dkj() {
		return xl_zl_dkj;
	}
	public void setXl_zl_dkj(Double xl_zl_dkj) {
		this.xl_zl_dkj = xl_zl_dkj;
	}
	public Double getXl_zl_rrj() {
		return xl_zl_rrj;
	}
	public void setXl_zl_rrj(Double xl_zl_rrj) {
		this.xl_zl_rrj = xl_zl_rrj;
	}
	public Long getSl_zl_id() {
		return sl_zl_id;
	}
	public void setSl_zl_id(Long sl_zl_id) {
		this.sl_zl_id = sl_zl_id;
	}
	public Double getSl_zl_pz() {
		return sl_zl_pz;
	}
	public void setSl_zl_pz(Double sl_zl_pz) {
		this.sl_zl_pz = sl_zl_pz;
	}
	public Double getSl_zl_ss() {
		return sl_zl_ss;
	}
	public void setSl_zl_ss(Double sl_zl_ss) {
		this.sl_zl_ss = sl_zl_ss;
	}
	public Double getSl_zl_gy() {
		return sl_zl_gy;
	}
	public void setSl_zl_gy(Double sl_zl_gy) {
		this.sl_zl_gy = sl_zl_gy;
	}
	public Double getSl_zl_dkj() {
		return sl_zl_dkj;
	}
	public void setSl_zl_dkj(Double sl_zl_dkj) {
		this.sl_zl_dkj = sl_zl_dkj;
	}
	public Double getSl_zl_rrj() {
		return sl_zl_rrj;
	}
	public void setSl_zl_rrj(Double sl_zl_rrj) {
		this.sl_zl_rrj = sl_zl_rrj;
	}
	public Long getXt_zl_id() {
		return xt_zl_id;
	}
	public void setXt_zl_id(Long xt_zl_id) {
		this.xt_zl_id = xt_zl_id;
	}
	public Double getXt_zl_pz() {
		return xt_zl_pz;
	}
	public void setXt_zl_pz(Double xt_zl_pz) {
		this.xt_zl_pz = xt_zl_pz;
	}
	public Double getXt_zl_ss() {
		return xt_zl_ss;
	}
	public void setXt_zl_ss(Double xt_zl_ss) {
		this.xt_zl_ss = xt_zl_ss;
	}
	public Double getXt_zl_gy() {
		return xt_zl_gy;
	}
	public void setXt_zl_gy(Double xt_zl_gy) {
		this.xt_zl_gy = xt_zl_gy;
	}
	public Double getXt_zl_dkj() {
		return xt_zl_dkj;
	}
	public void setXt_zl_dkj(Double xt_zl_dkj) {
		this.xt_zl_dkj = xt_zl_dkj;
	}
	public Double getXt_zl_rrj() {
		return xt_zl_rrj;
	}
	public void setXt_zl_rrj(Double xt_zl_rrj) {
		this.xt_zl_rrj = xt_zl_rrj;
	}
	public Long getSy_zl_id() {
		return sy_zl_id;
	}
	public void setSy_zl_id(Long sy_zl_id) {
		this.sy_zl_id = sy_zl_id;
	}
	public Double getSy_zl_pz() {
		return sy_zl_pz;
	}
	public void setSy_zl_pz(Double sy_zl_pz) {
		this.sy_zl_pz = sy_zl_pz;
	}
	public Double getSy_zl_ss() {
		return sy_zl_ss;
	}
	public void setSy_zl_ss(Double sy_zl_ss) {
		this.sy_zl_ss = sy_zl_ss;
	}
	public Double getSy_zl_gy() {
		return sy_zl_gy;
	}
	public void setSy_zl_gy(Double sy_zl_gy) {
		this.sy_zl_gy = sy_zl_gy;
	}
	public Double getSy_zl_dkj() {
		return sy_zl_dkj;
	}
	public void setSy_zl_dkj(Double sy_zl_dkj) {
		this.sy_zl_dkj = sy_zl_dkj;
	}
	public Double getSy_zl_rrj() {
		return sy_zl_rrj;
	}
	public void setSy_zl_rrj(Double sy_zl_rrj) {
		this.sy_zl_rrj = sy_zl_rrj;
	}
	
}
