package com.shlanbao.tzsc.wct.ctm.machine.service;

import java.util.List;

import com.shlanbao.tzsc.wct.ctm.machine.beans.CostManager;
import com.shlanbao.tzsc.wct.sch.stat.beans.EqpRuntime;
/**
 * @ClassName: WctCostManagerServiceI 
 * @Description: 成本管理 
 * @author luo
 * @date 2015年1月27日 下午1:18:49 
 *
 */
public interface WctCostManagerServiceI {

	public CostManager initEqpInputInfos(String ecode) throws Exception ;
	
	public List<EqpRuntime> initOutDataPage(String ecode);
	
	public CostManager getDataSource(int ecode,String orderNumber);
}
