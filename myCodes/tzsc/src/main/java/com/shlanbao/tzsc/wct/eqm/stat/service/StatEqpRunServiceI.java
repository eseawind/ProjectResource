package com.shlanbao.tzsc.wct.eqm.stat.service;

import com.shlanbao.tzsc.wct.eqm.stat.bean.StatEqpRunBean;

public interface StatEqpRunServiceI {
	
	public StatEqpRunBean getStatEqpRunBean(StatEqpRunBean bean,int pageSize);

	public StatEqpRunBean getEqpEfficiencyData(StatEqpRunBean bean,int pageSize,int type);
	
	public StatEqpRunBean getEqpUtilizationData(StatEqpRunBean bean,int pageSize);
	
	public StatEqpRunBean getEqpRateData(StatEqpRunBean bean,int pageSize,int type);
}
