package com.shlanbao.tzsc.wct.eqm.shiftGl.beans;

import java.io.Serializable;
import java.util.Date;

import com.shlanbao.tzsc.utils.extents.DateFmtAnnotation;


/**
 * [功能说明]：交接班辅料信息表
 * 
 * @createTime 2015年10月26日08:27:22
 * @author wanchanghuang
 */

public class StatShiftFLInfo implements Serializable {
	
	private String id;
	private String mec_id; //MD_EQP_CATEGORY表id   -卷烟机，包装机，封箱机等
	/**
	 *  2	包装机组
		1	卷接机组
		3	装封箱机
		4	成型机组
		5	发射机组
		6	装盘机
		7	卸盘机
	 * 
	 * 
	 * */
	private String eqp_id; //设备表ID
	private String team_id; //班组ID
	private String shift_id; //班次ID
	private String oid; //工单ID
	private String mat_id;//品牌ID
	private String mat_name; //品牌名称
	private Double qty; //产量
	private String handover_user_name; //交班人姓名
	private String handover_user_id;  //交班人ID
	private String receive_user_name; //接班人姓名
	private String receive_user_id;   //接班人ID
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time; //创建时间
	@DateFmtAnnotation(fmtPattern="yyyy-MM-dd HH:mm:ss")
	private Date update_time; //最后修改时间
	private String update_user_id; //最后修改人
	private Integer zc_type; //仲裁类型    1-已仲裁  0-默认 
	private Integer fl_type; //辅料类型    1-实领辅料   2-虚领辅料  3-虚退辅料   0-没有交接班
	private Integer status; //状态   1-通过   0-默认
	private Double zj_pz=0.0;  //卷烟机-盘纸
	private Double zj_ssz=0.0; //卷烟机-水松纸
	private Double zj_lb=0.0;  //卷烟机-滤棒
	private Double zj_szy=0.0; //卷烟机-散支烟
	private Double zb_lbz=0.0; //包装机-铝箔纸	
	private Double zb_xhz=0.0; //包装机-小盒纸	
	private Double zb_xhm=0.0; //包装机-小盒膜	
	private Double zb_thz=0.0; //包装机-条盒纸	
	private Double zb_thm=0.0; //包装机-条盒膜	卡纸（硬盒）/封签（软盒）	
	private Double zb_kz=0.0;  //包装机-卡纸
	private Double zb_fq=0.0;  //包装机-封签
	private Double zb_lx1=0.0;  //包装机-拉线1
	private Double zb_lx2=0.0;  //包装机-拉线2
	private Double zl_pz=0.0;  //成型机-盘纸
	private Double zl_ss=0.0;  //成型机-丝束
	private Double zl_gy=0.0;  //成型机-甘油
	private Double yf_xp=0.0;  //封箱机-箱皮
	private Double yf_jd=0.0;  //封箱机-胶带
	private Double qt_bfy=0.0; //残烟-报废烟
	private Double qt_cc=0.0;  //残烟-吹车
	private Double qt_jy=0.0;  //残烟 - 卷烟
	private Double qt_bz=0.0;  //残烟 - 包装
	
	private Double zl_dkj=0.0; //成型机——搭口胶
	private Double zl_rrj=0.0; //成型机- 热熔胶
	private Double oth_jzj=0.0; //其他-接嘴胶
	private Double oth_dkj=0.0; //其他-搭扣胶
	private Double oth_bzj=0.0; //其他-包装胶
	
	private int del;  //0-默认   1-物理删除
	private String doid; //下一班的工单ID
	private String uoid; //上一班的工单ID
	private Double pz_gg=5000.0;//盘纸规格
	
	
	public Double getPz_gg() {
		return pz_gg;
	}
	public void setPz_gg(Double pz_gg) {
		this.pz_gg = pz_gg;
	}
	public String getUoid() {
		return uoid;
	}
	public void setUoid(String uoid) {
		this.uoid = uoid;
	}
	public String getMat_id() {
		return mat_id;
	}
	public void setMat_id(String mat_id) {
		this.mat_id = mat_id;
	}
	public String getDoid() {
		return doid;
	}
	public void setDoid(String doid) {
		this.doid = doid;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	public Double getQt_jy() {
		return qt_jy;
	}
	public void setQt_jy(Double qt_jy) {
		this.qt_jy = qt_jy;
	}
	public Double getQt_bz() {
		return qt_bz;
	}
	public void setQt_bz(Double qt_bz) {
		this.qt_bz = qt_bz;
	}
	public Double getZb_kz() {
		return zb_kz;
	}
	public void setZb_kz(Double zb_kz) {
		this.zb_kz = zb_kz;
	}
	public Double getZb_fq() {
		return zb_fq;
	}
	public void setZb_fq(Double zb_fq) {
		this.zb_fq = zb_fq;
	}
	public Double getZl_dkj() {
		return zl_dkj;
	}
	public void setZl_dkj(Double zl_dkj) {
		this.zl_dkj = zl_dkj;
	}
	public Double getZl_rrj() {
		return zl_rrj;
	}
	public void setZl_rrj(Double zl_rrj) {
		this.zl_rrj = zl_rrj;
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMec_id() {
		return mec_id;
	}
	public void setMec_id(String mec_id) {
		this.mec_id = mec_id;
	}
	public String getEqp_id() {
		return eqp_id;
	}
	public void setEqp_id(String eqp_id) {
		this.eqp_id = eqp_id;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getShift_id() {
		return shift_id;
	}
	public void setShift_id(String shift_id) {
		this.shift_id = shift_id;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getMat_name() {
		return mat_name;
	}
	public void setMat_name(String mat_name) {
		this.mat_name = mat_name;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getHandover_user_name() {
		return handover_user_name;
	}
	public void setHandover_user_name(String handover_user_name) {
		this.handover_user_name = handover_user_name;
	}
	public String getHandover_user_id() {
		return handover_user_id;
	}
	public void setHandover_user_id(String handover_user_id) {
		this.handover_user_id = handover_user_id;
	}
	public String getReceive_user_name() {
		return receive_user_name;
	}
	public void setReceive_user_name(String receive_user_name) {
		this.receive_user_name = receive_user_name;
	}
	public String getReceive_user_id() {
		return receive_user_id;
	}
	public void setReceive_user_id(String receive_user_id) {
		this.receive_user_id = receive_user_id;
	}
	
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}
	public Integer getZc_type() {
		return zc_type;
	}
	public void setZc_type(Integer zc_type) {
		this.zc_type = zc_type;
	}
	public Integer getFl_type() {
		return fl_type;
	}
	public void setFl_type(Integer fl_type) {
		this.fl_type = fl_type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getZj_pz() {
		return zj_pz;
	}
	public void setZj_pz(Double zj_pz) {
		this.zj_pz = zj_pz;
	}
	public Double getZj_ssz() {
		return zj_ssz;
	}
	public void setZj_ssz(Double zj_ssz) {
		this.zj_ssz = zj_ssz;
	}
	public Double getZj_lb() {
		return zj_lb;
	}
	public void setZj_lb(Double zj_lb) {
		this.zj_lb = zj_lb;
	}
	public Double getZj_szy() {
		return zj_szy;
	}
	public void setZj_szy(Double zj_szy) {
		this.zj_szy = zj_szy;
	}
	public Double getZb_lbz() {
		return zb_lbz;
	}
	public void setZb_lbz(Double zb_lbz) {
		this.zb_lbz = zb_lbz;
	}
	public Double getZb_xhz() {
		return zb_xhz;
	}
	public void setZb_xhz(Double zb_xhz) {
		this.zb_xhz = zb_xhz;
	}
	public Double getZb_xhm() {
		return zb_xhm;
	}
	public void setZb_xhm(Double zb_xhm) {
		this.zb_xhm = zb_xhm;
	}
	public Double getZb_thz() {
		return zb_thz;
	}
	public void setZb_thz(Double zb_thz) {
		this.zb_thz = zb_thz;
	}
	public Double getZb_thm() {
		return zb_thm;
	}
	public void setZb_thm(Double zb_thm) {
		this.zb_thm = zb_thm;
	}
	public Double getZb_lx1() {
		return zb_lx1;
	}
	public void setZb_lx1(Double zb_lx1) {
		this.zb_lx1 = zb_lx1;
	}
	public Double getZb_lx2() {
		return zb_lx2;
	}
	public void setZb_lx2(Double zb_lx2) {
		this.zb_lx2 = zb_lx2;
	}
	public Double getZl_pz() {
		return zl_pz;
	}
	public void setZl_pz(Double zl_pz) {
		this.zl_pz = zl_pz;
	}
	public Double getZl_ss() {
		return zl_ss;
	}
	public void setZl_ss(Double zl_ss) {
		this.zl_ss = zl_ss;
	}
	public Double getZl_gy() {
		return zl_gy;
	}
	public void setZl_gy(Double zl_gy) {
		this.zl_gy = zl_gy;
	}
	public Double getYf_xp() {
		return yf_xp;
	}
	public void setYf_xp(Double yf_xp) {
		this.yf_xp = yf_xp;
	}
	public Double getYf_jd() {
		return yf_jd;
	}
	public void setYf_jd(Double yf_jd) {
		this.yf_jd = yf_jd;
	}
	public Double getQt_bfy() {
		return qt_bfy;
	}
	public void setQt_bfy(Double qt_bfy) {
		this.qt_bfy = qt_bfy;
	}
	public Double getQt_cc() {
		return qt_cc;
	}
	public void setQt_cc(Double qt_cc) {
		this.qt_cc = qt_cc;
	}

	
	
	

}