package com.shlanbao.tzsc.wct.eqm.shiftGl.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.shlanbao.tzsc.wct.eqm.checkplan.beans.EqmShiftDataBean;
import com.shlanbao.tzsc.wct.eqm.shiftGl.beans.StatShiftFLInfo;
import com.shlanbao.tzsc.wct.sys.login.beans.LoginBean;

public interface StatShiftFLInfoService  {

	boolean saveShiftRoller(HttpSession session,String[] zj_pz1, String[] zj_ssz1, String[] zj_lb1,String[] zj_szy1);
	/**
	 * 保存呼叫要料量
	 * @param date
	 * @param eqpId
	 * @return
	 */
	boolean saveCallMatter(LoginBean loginBean,LoginBean bean2,StatShiftFLInfo bean);
	
	/** 卷烟机*/
	Map<String, String> queryEqmShiftDatas(EqmShiftDataBean eqmsb);
	
	/** 包装机*/
	void saveShiftPacker(HttpSession session, String[] zb_lbz1,
			String[] zb_xhz1, String[] zb_xhm1, String[] zb_thz1,
			String[] zb_thm1, String[] zb_kz1, String[] zb_fq1,
			String[] zb_lx11, String[] zb_lx21);
	
	Map<String, String> queryEqmShiftDatasPacker(EqmShiftDataBean eqmsb);
	
	/** 成型机*/
	void saveShiftFilter(HttpSession session, String[] zl_pz, String[] zl_ss);
	Map<String, String> queryEqmShiftDatasFilter(EqmShiftDataBean eqmsb);
	
	/** 封箱机*/
	void saveShiftBox(HttpSession session, String[] yf_xp, String[] yf_jd);
	Map<String, String> queryEqmShiftDatasBox(EqmShiftDataBean eqmsb);
	

}
